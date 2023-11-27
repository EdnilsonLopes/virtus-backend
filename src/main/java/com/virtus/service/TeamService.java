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

    public TeamService(OfficeRepository officeRepository, EntityVirtusRepository entityRepository, UserRepository userRepository,
                       CycleRepository cycleRepository,
                       CycleEntityRepository cycleEntityRepository,
                       ProductComponentRepository productComponentRepository,
                       MemberRepository memberRepository) {
        this.officeRepository = officeRepository;
        this.entityRepository = entityRepository;
        this.userRepository = userRepository;
        this.cycleRepository = cycleRepository;
        this.cycleEntityRepository = cycleEntityRepository;
        this.productComponentRepository = productComponentRepository;
        this.memberRepository = memberRepository;
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
        ProductComponent productComponent = productComponentRepository
                .findByEntityIdAndCycleId(body.getEntity().getId(), body.getCycle().getId())
                .orElseThrow(() -> new VirtusException(Translator.translate("product.component.not.found")));

        cycleEntity.setSupervisor(supervisor);
        productComponent.setSupervisor(supervisor);

        cycleEntityRepository.save(cycleEntity);
        productComponentRepository.save(productComponent);
    }

    public List<TeamMemberResponseDTO> findAllTeamMembersByBoss(CurrentUser currentUser) {

        List<Object[]> objects = memberRepository.findMembersByBoss(currentUser.getId());

        List<TeamMemberResponseDTO> members = objects.stream().map(m -> new TeamMemberResponseDTO(
                Integer.parseInt(m[0].toString()),
                m[1].toString(),
                m[2].toString(),
                Integer.parseInt(m[3].toString())
        )).collect(Collectors.toList());
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
}
