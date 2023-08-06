package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.GradeTypeRequestDTO;
import com.virtus.domain.dto.response.GradeTypeResponseDTO;
import com.virtus.domain.entity.GradeType;
import com.virtus.domain.entity.User;
import com.virtus.exception.VirtusException;
import com.virtus.persistence.GradeTypeRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Service
public class GradeTypeService
        extends BaseService<
        GradeType,
        GradeTypeRepository,
        GradeTypeRequestDTO,
        GradeTypeResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    public GradeTypeService(GradeTypeRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    protected void validate(VirtusException ex, User user, GradeType entity) throws VirtusException {
        if (Strings.isBlank(entity.getName())) {
            ex.addError(Translator.translate("name.is.required"));
        }
        if (Strings.isBlank(entity.getLetterColor())) {
            ex.addError(Translator.translate("letter.color.is.required"));
        }
        if (Strings.isBlank(entity.getLetter())) {
            ex.addError(Translator.translate("letter.is.required"));
        }
    }

    @Override
    protected GradeTypeResponseDTO parseToResponseDTO(GradeType entity, boolean detailed) {
        return parseToGradeTypeResponseDTO(entity, detailed);
    }

    @Override
    protected GradeType parseToEntity(GradeTypeRequestDTO body) {
        GradeType entity = new GradeType();
        entity.setId(body.getId());
        entity.setName(body.getName());
        entity.setDescription(body.getDescription());
        entity.setLetter(body.getLetter());
        entity.setReference(body.getReference());
        entity.setLetterColor(body.getLetterColor());
        return entity;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("type.of.note.not.found");
    }
}
