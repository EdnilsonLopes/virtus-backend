package com.virtus.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.JurisdictionRequestDTO;
import com.virtus.domain.dto.response.DistributeActivitiesResponseDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.JurisdictionResponseDTO;
import com.virtus.domain.dto.response.OfficeResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.entity.Jurisdiction;
import com.virtus.domain.entity.Office;
import com.virtus.domain.model.CurrentUser;
import com.virtus.persistence.JurisdictionRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;

@Service
public class JurisdictionService extends BaseService<Jurisdiction, JurisdictionRepository, JurisdictionRequestDTO, JurisdictionResponseDTO> {


    private final JurisdictionRepository repository;
    private final UserRepository userRepository;

    private final EntityManagerFactory entityManagerFactory;

    public JurisdictionService(JurisdictionRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
        this.repository = repository;
        this.userRepository = userRepository;
        this.entityManagerFactory = entityManagerFactory;
    }

    public PageableResponseDTO<JurisdictionResponseDTO> findAllJurisdictionsByCurrentUser(CurrentUser currentUser, int page, int size) {
        Page<Jurisdiction> pageResult = repository.findAllByUserId(currentUser.getId(), PageRequest.of(page, size));
        List<JurisdictionResponseDTO> content = pageResult.getContent().stream().map(c -> parseToResponseDTO(c, false)).collect(Collectors.toList());

        return new PageableResponseDTO<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements());
    }

    @Override
    protected JurisdictionResponseDTO parseToResponseDTO(Jurisdiction entity, boolean detailed) {
        JurisdictionResponseDTO response = new JurisdictionResponseDTO();
        response.setId(entity.getId());
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setCreatedAt(entity.getCreatedAt());
        response.setStartsAt(entity.getStartsAt());
        response.setEndsAt(entity.getEndsAt());
        response.setEntity(parseToEntityVirtus(entity.getEntity()));
        response.setOffice(parseToOfficeResponseDTO(entity.getOffice()));
        return response;
    }

    protected OfficeResponseDTO parseToOfficeResponseDTO(Office entity) {
        OfficeResponseDTO response = new OfficeResponseDTO();
        response.setId(entity.getId());
        response.setAbbreviation(entity.getAbbreviation());
        response.setDescription(entity.getDescription());
        response.setName(entity.getName());
        response.setBoss(parseToUserResponseDTO(entity.getBoss()));
        return response;
    }

    private EntityVirtusResponseDTO parseToEntityVirtus(EntityVirtus entity) {
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
        response.setAuthor(parseToUserResponseDTO(entity.getAuthor()));
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }


    @Override
    protected Jurisdiction parseToEntity(JurisdictionRequestDTO body) {
        Jurisdiction jurisdiction = body.getId() != null ? getRepository().findById(body.getId()).orElse(new Jurisdiction()) : new Jurisdiction();

        jurisdiction.setStartsAt(body.getStartsAt());
        jurisdiction.setEndsAt(body.getEndsAt());
        jurisdiction.setUpdatedAt(LocalDateTime.now());

        return jurisdiction;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("jurisdiction.not.found");
    }

    public PageableResponseDTO<DistributeActivitiesResponseDTO> findEntidadeCiclosByUser(CurrentUser currentUser, int page, int size) {
        List<Object[]> results = repository.findObjectsToDistributeActivitiesByUser(currentUser.getId());
        List<DistributeActivitiesResponseDTO> dtos = new ArrayList<>();
        for (Object[] result : results) {
            DistributeActivitiesResponseDTO dto = new DistributeActivitiesResponseDTO();
            dto.setCode((String) result[0]);
            dto.setEntityId((Integer) result[1]);
            dto.setName((String) result[2]);
            dto.setAcronym((String) result[3]);
            dtos.add(dto);
        }

        return new PageableResponseDTO<>(
                dtos,
                page,
                size,
                0,
                0);
    }
}
