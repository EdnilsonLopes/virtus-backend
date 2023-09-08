package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.OfficeRequestDTO;
import com.virtus.domain.dto.response.OfficeResponseDTO;
import com.virtus.domain.entity.Office;
import com.virtus.persistence.OfficeRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;

@Service
public class OfficeService extends BaseService<Office, OfficeRepository, OfficeRequestDTO, OfficeResponseDTO> {


    public OfficeService(OfficeRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
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
        Office office = new Office();
        office.setId(body.getId());
        office.setName(body.getName());
        office.setAbbreviation(body.getAbbreviation());
        office.setDescription(body.getDescription());
        if (body.getBoss().getId() != null)
            office.setBoss(getUserRepository().findById(body.getBoss().getId()).orElse(null));
        return office;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("office.not.found");
    }
}
