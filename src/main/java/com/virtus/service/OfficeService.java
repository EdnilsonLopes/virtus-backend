package com.virtus.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SystemPropertyUtils;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.JurisdictionRequestDTO;
import com.virtus.domain.dto.request.MemberRequestDTO;
import com.virtus.domain.dto.request.OfficeRequestDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.JurisdictionResponseDTO;
import com.virtus.domain.dto.response.MemberResponseDTO;
import com.virtus.domain.dto.response.OfficeResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.entity.Jurisdiction;
import com.virtus.domain.entity.Member;
import com.virtus.domain.entity.Office;
import com.virtus.domain.model.CurrentUser;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.EntityVirtusRepository;
import com.virtus.persistence.JurisdictionRepository;
import com.virtus.persistence.MemberRepository;
import com.virtus.persistence.OfficeRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;

@Service
public class OfficeService extends BaseService<Office, OfficeRepository, OfficeRequestDTO, OfficeResponseDTO> {


    private final JurisdictionRepository jurisdictionRepository;
    private final EntityVirtusRepository entityVirtusRepository;
    private final MemberRepository memberRepository;
    private final AtomicReference<Integer> maxIdJurisdiction = new AtomicReference<>(0);

    public OfficeService(OfficeRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory,
                         JurisdictionRepository jurisdictionRepository,
                         EntityVirtusRepository entityVirtusRepository,
                         MemberRepository memberRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.jurisdictionRepository = jurisdictionRepository;
        this.entityVirtusRepository = entityVirtusRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    protected OfficeResponseDTO parseToResponseDTO(Office entity, boolean detailed) {
        OfficeResponseDTO response = new OfficeResponseDTO();
        response.setId(entity.getId());
        response.setAbbreviation(entity.getAbbreviation());
        response.setDescription(entity.getDescription());
        response.setName(entity.getName());
        response.setBoss(parseToUserResponseDTO(entity.getBoss()));
        return response;
    }

    @Override
    protected Office parseToEntity(OfficeRequestDTO body) {
        Office office = body.getId() != null ? getRepository().findById(body.getId()).orElse(new Office()) : new Office();

        office.setName(body.getName());
        office.setAbbreviation(body.getAbbreviation());
        office.setDescription(body.getDescription());
        if (body.getBoss().getId() != null)
            office.setBoss(getUserRepository().findById(body.getBoss().getId()).orElse(null));

        return office;
    }

    public OfficeResponseDTO updateJurisdictions(CurrentUser currentUser, OfficeRequestDTO body) {
        Office office = parseToEntity(body);
        if (office.getId() == null) {
            throw new VirtusException(getNotFoundMessage());
        }
        office.getJurisdictions().clear();
        office.getJurisdictions().addAll(parseToJurisdictionsEntity(body, office));
        office.setUpdatedAt(LocalDateTime.now());
        getRepository().save(office);
        return parseToResponseDTO(office, false);
    }

    public OfficeResponseDTO updateMembers(CurrentUser currentUser, OfficeRequestDTO body) {
        Office office = parseToEntity(body);
        if (office.getId() == null) {
            throw new VirtusException(getNotFoundMessage());
        }
        office.getMembers().clear();
        office.getMembers().addAll(parseToMembersEntity(body, office));
        office.setUpdatedAt(LocalDateTime.now());
        getRepository().save(office);
        return parseToResponseDTO(office, false);
    }

    private List<Jurisdiction> parseToJurisdictionsEntity(OfficeRequestDTO officeRequest, Office office) {
        if (CollectionUtils.isEmpty(officeRequest.getJurisdictions())) {
            return new ArrayList<>();
        }
        return officeRequest.getJurisdictions().stream().map(request -> parseToJurisdictionEntity(office, request)).collect(Collectors.toList());
    }

    private List<Member> parseToMembersEntity(OfficeRequestDTO officeRequest, Office office) {
        if (CollectionUtils.isEmpty(officeRequest.getMembers())) {
            return new ArrayList<>();
        }
        List<Member> members = officeRequest.getMembers().stream().map(request -> parseToMemberEntity(office, request)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(officeRequest.getMembers())) {
            AtomicReference<Integer> maxId = new AtomicReference<>(memberRepository.findMaxId());
            members.forEach(member -> {
                if (member.getId() == null) {
                    member.setId(maxId.updateAndGet(v -> v + 1));
                }
            });
        }        
        return members;
    }

    private Jurisdiction parseToJurisdictionEntity(Office office, JurisdictionRequestDTO request) {
        Jurisdiction jurisdiction = new Jurisdiction();
        jurisdiction.setOffice(office);
        AtomicReference<Integer> maxIdJurisdiction = new AtomicReference<>(jurisdictionRepository.findMaxId());
        if (request.getId() == null) {
            jurisdiction.setId(maxIdJurisdiction.updateAndGet(v -> v + 1));
        } else {
            jurisdiction.setId(request.getId());
        }

        jurisdiction.setStartsAt(request.getStartsAt());
        jurisdiction.setEndsAt(request.getEndsAt());
        if (jurisdiction.getId() == null) {
            jurisdiction.setAuthor(getLoggedUser());
            jurisdiction.setCreatedAt(LocalDateTime.now());
        }
        if (request.getEntity() != null && request.getEntity().getId() != null)
            jurisdiction.setEntity(entityVirtusRepository.findById(request.getEntity().getId()).orElse(null));

        jurisdiction.setUpdatedAt(LocalDateTime.now());

        return jurisdiction;
    }

    private Member parseToMemberEntity(Office office, MemberRequestDTO request) {
        System.out.println("Parsing Member Entity: " + request.getUser().getId() +" - "+ request.getUser().getName());
        Member member = new Member();
        member.setOffice(office);
        member.setId(request.getId());

        member.setStartsAt(request.getStartsAt());
        member.setEndsAt(request.getEndsAt());
        if (member.getId() == null) {
            member.setAuthor(getLoggedUser());
            member.setCreatedAt(LocalDateTime.now());
        }
        if (request.getUser() != null && request.getUser().getId() != null)
            member.setUser(getUserRepository().findById(request.getUser().getId()).orElse(null));

        member.setUpdatedAt(LocalDateTime.now());

        return member;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("office.not.found");
    }

    public PageableResponseDTO<JurisdictionResponseDTO> findJurisdictionByOfficeId(
            CurrentUser currentUser,
            String filter,
            int page,
            int size,
            Integer officeId) {
        Office office = getRepository().findById(officeId).orElseThrow(() -> new VirtusException(getNotFoundMessage()));
        Page<Jurisdiction> jurisdictionPage;
        if(Strings.isBlank(filter)){
            filter = null;
        }
        jurisdictionPage = jurisdictionRepository.findByOffice(office, filter, PageRequest.of(page, size));
        List<JurisdictionResponseDTO> content = jurisdictionPage.getContent().stream().map(jurisdiction -> {
            JurisdictionResponseDTO response = new JurisdictionResponseDTO();
            response.setId(jurisdiction.getId());
            response.setEndsAt(jurisdiction.getEndsAt());
            response.setStartsAt(jurisdiction.getStartsAt());
            response.setAuthor(parseToUserResponseDTO(jurisdiction.getAuthor()));
            response.setCreatedAt(jurisdiction.getCreatedAt());
            response.setUpdatedAt(jurisdiction.getUpdatedAt());
            response.setEntity(parseToEntityVirtus(jurisdiction.getEntity()));

            return response;
        }).collect(Collectors.toList());

        return new PageableResponseDTO<>(content, page, size, jurisdictionPage.getTotalPages(), jurisdictionPage.getTotalElements());
    }

    public PageableResponseDTO<MemberResponseDTO> findMemberByOfficeId(
            CurrentUser currentUser,
            String filter,
            int page,
            int size,
            Integer officeId) {
        Office office = getRepository().findById(officeId).orElseThrow(() -> new VirtusException(getNotFoundMessage()));
        if(Strings.isBlank(filter)){
            filter = null;
        }        
        Page<Member> memberPage = memberRepository.findAllByOffice(office, filter, PageRequest.of(page, size));

        List<MemberResponseDTO> content = memberPage.getContent().stream().map(jurisdiction -> {
            MemberResponseDTO response = new MemberResponseDTO();
            response.setId(jurisdiction.getId());
            response.setEndsAt(jurisdiction.getEndsAt());
            response.setStartsAt(jurisdiction.getStartsAt());
            response.setAuthor(parseToUserResponseDTO(jurisdiction.getAuthor()));
            response.setCreatedAt(jurisdiction.getCreatedAt());
            response.setUpdatedAt(jurisdiction.getUpdatedAt());
            response.setUser(parseToUserResponseDTO(jurisdiction.getUser()));

            return response;
        }).collect(Collectors.toList());

        return new PageableResponseDTO<>(content, page, size, memberPage.getTotalPages(), memberPage.getTotalElements());
    }

    private EntityVirtusResponseDTO parseToEntityVirtus(EntityVirtus entity) {

        EntityVirtusResponseDTO response = new EntityVirtusResponseDTO();
        response.setCode(entity.getCode());
        response.setUf(entity.getUf());
        response.setSituation(entity.getSituation());
        response.setCity(entity.getCity());
        response.setAcronym(entity.getAcronym());
        response.setEsi(entity.getEsi());
        response.setDescription(entity.getDescription());
        response.setName(entity.getName());
        response.setId(entity.getId());

        return response;
    }
}
