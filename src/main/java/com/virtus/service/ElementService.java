package com.virtus.service;

import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.ElementItemRequestDTO;
import com.virtus.domain.dto.request.ElementRequestDTO;
import com.virtus.domain.dto.response.ElementResponseDTO;
import com.virtus.domain.entity.Element;
import com.virtus.domain.entity.ElementItem;
import com.virtus.persistence.ElementRepository;
import com.virtus.persistence.UserRepository;
import com.virtus.translate.Translator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElementService extends BaseService<Element, ElementRepository, ElementRequestDTO, ElementResponseDTO> {

    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    public ElementService(ElementRepository repository, UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        super(repository, userRepository, entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    protected ElementResponseDTO parseToResponseDTO(Element entity, boolean detailed) {
        return parseToElementResponseDTO(entity, detailed);
    }

    @Override
    protected Element parseToEntity(ElementRequestDTO body) {
        Element element = new Element();
        element.setId(body.getId());
        element.setName(body.getName());
        element.setDescription(body.getDescription());
        element.setDescription(body.getDescription());
        element.setReference(body.getReference());

        element.setItems(parseToCreateItemsEntity(body.getItems(), element));
        return element;
    }

    private List<ElementItem> parseToCreateItemsEntity(List<ElementItemRequestDTO> items, Element element) {
        if (CollectionUtils.isEmpty(items)) {
            return new ArrayList<>();
        }
        return items.stream().map(itemDto -> parseToItemEntity(itemDto, element)).collect(Collectors.toList());
    }

    private ElementItem parseToItemEntity(ElementItemRequestDTO itemRequestDTO, Element element) {
        ElementItem item = new ElementItem();
        item.setId(itemRequestDTO.getId());
        item.setElement(element);
        item.setName(itemRequestDTO.getName());
        item.setDescription(itemRequestDTO.getDescription());
        item.setReference(itemRequestDTO.getReference());
        return item;
    }

    @Override
    protected String getNotFoundMessage() {
        return Translator.translate("element.not.found");
    }
}
