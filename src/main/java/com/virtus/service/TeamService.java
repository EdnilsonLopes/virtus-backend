package com.virtus.service;

import com.virtus.domain.dto.request.TeamRequestDTO;
import com.virtus.domain.dto.response.*;
import com.virtus.domain.entity.*;
import com.virtus.domain.model.CurrentUser;
import com.virtus.domain.model.Team;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.*;
import com.virtus.translate.Translator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final OfficeRepository officeRepository;
    private final EntityVirtusRepository entityRepository;
    private final UserRepository userRepository;
    private final CycleRepository cycleRepository;
    private final CycleEntityRepository cycleEntityRepository;
    private final ProductComponentRepository productComponentRepository;
    private final MemberRepository memberRepository;
    private final TeamMemberRepository teamMemberRepository;

    public final VirtusException ERROR_USER_NOT_FOUND =
            new VirtusException(Translator.translate("user.not.found"));

    @PersistenceContext
    private EntityManager entityManager;

    public TeamService(OfficeRepository officeRepository, EntityVirtusRepository entityRepository, UserRepository userRepository,
                       CycleRepository cycleRepository,
                       CycleEntityRepository cycleEntityRepository,
                       ProductComponentRepository productComponentRepository,
                       MemberRepository memberRepository,
                       TeamMemberRepository teamMemberRepository) {
        this.officeRepository = officeRepository;
        this.entityRepository = entityRepository;
        this.userRepository = userRepository;
        this.cycleRepository = cycleRepository;
        this.cycleEntityRepository = cycleEntityRepository;
        this.productComponentRepository = productComponentRepository;
        this.memberRepository = memberRepository;
        this.teamMemberRepository = teamMemberRepository;
    }

    public PageableResponseDTO<TeamResponseDTO> findTeamsByCurrentUser(CurrentUser currentUser, int page, int size) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new VirtusException(Translator.translate("user.not.found")));

        Page<Team> teams = entityRepository.findByEntitiesByUserBoss(user.getId(), PageRequest.of(page, size));

        List<TeamResponseDTO> content = teams
                .getContent()
                .stream().map(this::parseToResponseDto)
                .collect(Collectors.toList());

        return new PageableResponseDTO<>(
                content,
                page,
                size,
                teams.getTotalPages(),
                teams.getTotalElements());
    }

    private TeamResponseDTO parseToResponseDto(Team team) {
        TeamResponseDTO response = new TeamResponseDTO();
        response.setOffice(parseOfficeResponse(team.getOffice()));
        response.setEntity(parseToEntityVirtusResponse(team.getEntity()));
        return response;
    }

    private EntityVirtusResponseDTO parseToEntityVirtusResponse(EntityVirtus entity) {
        EntityVirtusResponseDTO response = new EntityVirtusResponseDTO();
        response.setId(entity.getId());
        response.setDescription(entity.getDescription());
        response.setUf(entity.getUf());
        response.setName(entity.getName());
        response.setCity(entity.getCity());
        response.setAcronym(entity.getAcronym());
        response.setCode(entity.getCode());
        response.setEsi(entity.getEsi());
        response.setSituation(entity.getSituation());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    private OfficeResponseDTO parseOfficeResponse(Office office) {
        if (office == null) {
            return null;
        }
        OfficeResponseDTO response = new OfficeResponseDTO();
        response.setId(office.getId());
        response.setAbbreviation(office.getAbbreviation());
        response.setDescription(office.getDescription());
        response.setName(office.getName());
        return response;
    }

    @Transactional
    public void updateTeam(CurrentUser currentUser, TeamRequestDTO body) {
        User supervisor = userRepository
                .findById(body.getSupervisor().getUserId())
                .orElseThrow(() -> new VirtusException(Translator.translate("user.not.found")));

        CycleEntity cycleEntity = cycleEntityRepository
                .findByEntityIdAndCycleId(body.getEntity().getId(), body.getCycle().getId())
                .orElseThrow(() -> new VirtusException(Translator.translate("cycle.entity.not.found")));
        List<ProductComponent> productComponent = productComponentRepository
                .findByEntityIdAndCycleId(body.getEntity().getId(), body.getCycle().getId())
                .orElseThrow(() -> new VirtusException(Translator.translate("product.component.not.found")));

        cycleEntity.setSupervisor(supervisor);
        productComponent.stream().forEach(p -> p.setSupervisor(supervisor));

        cycleEntityRepository.saveAndFlush(cycleEntity);
        productComponentRepository.saveAllAndFlush(productComponent);
    }

    public List<MemberResponseDTO> findAllTeamMembersByBoss(CurrentUser currentUser) {

        List<Object[]> objects = memberRepository.findMembersByBoss(currentUser.getId());

        List<MemberResponseDTO> members = objects.stream().map(m -> {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(Integer.parseInt(m[0].toString()));
            userResponseDTO.setName(m[1].toString());
            RoleResponseDTO roleResponse = new RoleResponseDTO();
            roleResponse.setName(m[2].toString());
            userResponseDTO.setRole(roleResponse);

            return new MemberResponseDTO(
                    userResponseDTO.getId(),
                    userResponseDTO,
                    null, null);

        }).collect(Collectors.toList());
        return members;
    }

    public List<SupervisorResponseDTO> findAllSupervisorsByBoss(CurrentUser currentUser) {
        List<Object[]> objects = officeRepository.findAllSupervisorsByBossId(currentUser.getId());

        List<SupervisorResponseDTO> members = objects.stream().map(m -> new SupervisorResponseDTO(
                Integer.parseInt(m[0].toString()),
                m[1].toString(),
                m[2].toString())
        ).collect(Collectors.toList());
        return members;
    }

    @Transactional
    public void assignTeam(CurrentUser currentUser, TeamRequestDTO body) {
        if (body.getSupervisor() != null) {
            User user = userRepository.findById(body.getSupervisor().getUserId())
                    .orElseThrow(() -> new VirtusException(Translator.translate("user.not.found")));
            cycleEntityRepository.updateSupervisorByEntityIdAndCycleId(user.getId(), body.getEntity().getId(), body.getCycle().getId());
            productComponentRepository.updateSupervisorByEntityIdAndCycleId(user.getId(), body.getEntity().getId(), body.getCycle().getId());
            updateTeamMembers(body);
        } else {
            throw new VirtusException("O Supervisor deve ser informado");
        }
    }

    private void updateTeamMembers(TeamRequestDTO body) {
        List<TeamMember> persisted = teamMemberRepository.findByEntityIdAndCycleId(body.getEntity().getId(), body.getCycle().getId());
        persisted = persisted.stream().filter(teamMember -> body.getTeamMembers().stream().filter(teamMemberDTO -> teamMemberDTO.getId().equals(teamMember.getId())).findAny().isEmpty()).collect(Collectors.toList());
        teamMemberRepository.deleteAll(persisted);
        teamMemberRepository.flush();

        List<TeamMember> teamMembers = body.getTeamMembers().stream()
                .map(req -> parseToTeamMember(body, req))
                .collect(Collectors.toList());
        teamMemberRepository.saveAllAndFlush(teamMembers);
    }

    private TeamMember parseToTeamMember(TeamRequestDTO teamDto, TeamMemberDTO teamMemberDto) {
        TeamMember teamMember = teamMemberDto.getId() != null ? teamMemberRepository.findById(teamMemberDto.getId()).orElse(new TeamMember()) : new TeamMember();
        Cycle cycle = cycleRepository.findById(teamDto.getCycle().getId()).orElse(null);
        teamMember.setCycle(cycle);
        EntityVirtus entity = entityRepository.findById(teamDto.getEntity().getId()).orElse(null);
        teamMember.setEntity(entity);
        teamMember.setUser(userRepository.findById(teamMemberDto
                        .getMember().getId())
                .orElseThrow(() -> new VirtusException(Translator.translate("user.not.found"))));
        teamMember.setStartsAt(teamMember.getStartsAt());
        teamMember.setEndsAt(teamMember.getEndsAt());
        return teamMember;
    }

    public SupervisorResponseDTO getSupervisorByEntityIdAndCycleId(Integer entityId, Integer cycleId) {
        User user = cycleEntityRepository.findSupervisorByEntityIdAndCycleId(entityId, cycleId);
        if (user != null) {
            return new SupervisorResponseDTO(user.getId(), user.getName(), user.getRole().getName());
        }
        return null;
    }

    public List<TeamMemberDTO> getTeamMembersByEntityIdAndCycleId(Integer entityId, Integer cycleId) {
        List<TeamMember> members = teamMemberRepository.findByEntityIdAndCycleId(entityId, cycleId);

        List<TeamMemberDTO> response = members.stream().map(teamMember -> parseToTeamMemberDto(teamMember)).collect(Collectors.toList());
        return response;
    }

    private TeamMemberDTO parseToTeamMemberDto(TeamMember teamMember) {
        TeamMemberDTO dto = new TeamMemberDTO();
        dto.setId(teamMember.getId());
        dto.setMember(parseToMemberResponseDTO(teamMember.getUser()));
        dto.setStartsAt(teamMember.getStartsAt());
        dto.setEndsAt(teamMember.getEndsAt());

        return dto;
    }

    private MemberResponseDTO parseToMemberResponseDTO(User user) {
        MemberResponseDTO responseDTO = new MemberResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUser(parseToUserResponseDTO(user));
        return responseDTO;
    }

    protected UserResponseDTO parseToUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setRole(parseToRoleResponse(user.getRole()));
        return dto;
    }

    private RoleResponseDTO parseToRoleResponse(Role role) {
        RoleResponseDTO response = new RoleResponseDTO();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setDescription(role.getDescription());
        return response;
    }

    public void validateUserSelectedAsTeamMember(Integer idCycle, Integer userTeamMemberId, Integer supervisorId) {
        if(supervisorId == null){
            throw new VirtusException("O Supervisor deve ser informado!");
        }
        User user = userRepository.findById(userTeamMemberId).orElseThrow(() -> ERROR_USER_NOT_FOUND);
        Page<TeamMember> pageTeamMember = teamMemberRepository.findByUserAndCycle(user, PageRequest.of(0, 1)).orElse(null);
        if (!pageTeamMember.isEmpty()) {
            TeamMember teamMember = pageTeamMember.getContent().get(0);
            if (teamMember != null) {
                User supervisor = userRepository.findById(supervisorId).orElseThrow(() -> new VirtusException("Supervisor não encontrado!"));
                throw new VirtusException(Translator.translate("user.is.subordinate.by.supervisor",
                        teamMember.getUser().getName(),
                        teamMember.getUser().getRole().getName(),
                        supervisor.getName(),
                        supervisor.getRole().getName(),
                        teamMember.getCycle().getName(),
                        teamMember.getEntity().getName()
                ));
            }
        }
    }
}
