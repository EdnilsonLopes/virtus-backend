package com.virtus.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.virtus.client.VirtusApiClient;
import com.virtus.client.dto.IndicatorScoreDTO;
import com.virtus.client.dto.LastReferenceDTO;
import com.virtus.common.BaseService;
import com.virtus.domain.dto.request.IndicatorScoreRequestDTO;
import com.virtus.domain.dto.response.IndicatorScoreResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.Indicator;
import com.virtus.domain.entity.IndicatorScore;
import com.virtus.domain.model.CurrentUser;
import com.virtus.persistence.IndicatorRepository;
import com.virtus.persistence.IndicatorScoreRepository;
import com.virtus.persistence.UserRepository;

@Service
public class IndicatorScoreService
        extends
        BaseService<IndicatorScore, IndicatorScoreRepository, IndicatorScoreRequestDTO, IndicatorScoreResponseDTO> {

    @Autowired
    private VirtusApiClient virtusApiClient;
    @Autowired
    private IndicatorService indicatorService;
    @Autowired
    private IndicatorRepository indicatorRepository;

    public IndicatorScoreService(IndicatorScoreRepository repository, UserRepository userRepository,
            EntityManagerFactory entityManagerFactory, VirtusApiClient indicatorApiClient,
            IndicatorService indicatorService,
            IndicatorRepository indicatorRepository) {
        super(repository, userRepository, entityManagerFactory);
        this.virtusApiClient = indicatorApiClient;
        this.indicatorService = indicatorService;
        this.indicatorRepository = indicatorRepository;
    }

    @Override
    protected IndicatorScoreResponseDTO parseToResponseDTO(IndicatorScore entity, boolean detailed) {
        return parseToIndicatorScoreResponseDTO(entity, detailed);
    }

    @Override
    protected IndicatorScore parseToEntity(IndicatorScoreRequestDTO body) {
        IndicatorScore entity = new IndicatorScore();
        entity.setId(body.getId());
        entity.setCnpb(body.getCnpb());
        entity.setIndicatorId(body.getIndicatorId());
        entity.setIndicatorSigla(body.getIndicatorSigla());
        entity.setReferenceDate(body.getReferenceDate());
        entity.setScore(body.getScore());
        return entity;
    }

    @Override
    protected String getNotFoundMessage() {
        throw new UnsupportedOperationException("Unimplemented method 'getNotFoundMessage'");
    }

    @Transactional
    public String getLastReferenceFromConformity() {
        LastReferenceDTO dto = virtusApiClient.fetchLastReference();
        return dto != null ? dto.getUltimaReferencia() : null;
    }

    @Transactional
    public void syncScores(String referenceDate) {
        if (referenceDate == null || referenceDate.isBlank()) {
            LastReferenceDTO dto = virtusApiClient.fetchLastReference();
            referenceDate = (dto != null) ? dto.getUltimaReferencia() : null;
        }

        if (referenceDate == null || referenceDate.isBlank()) {
            throw new IllegalArgumentException("Não foi possível obter a data de referência.");
        }

        // Carrega o mapa com key = indicatorAcronym e value = componentName
        Map<String, String> acronymToComponentMap = new HashMap<>();
        Map<Integer, String> idToComponentMap = indicatorService.getFirstComponentNameByIndicator();
        List<Indicator> indicators = indicatorRepository.findAll(); // injete o repository se necessário

        for (Indicator indicator : indicators) {
            String componentName = idToComponentMap.get(indicator.getId());
            if (componentName != null) {
                acronymToComponentMap.put(indicator.getIndicatorAcronym(), componentName);
            }
        }

        int page = 1;
        boolean hasData = true;

        while (hasData) {
            try {
                List<IndicatorScoreDTO> dtoList = virtusApiClient.fetchScoresByReference(referenceDate, page);

                if (dtoList == null || dtoList.isEmpty()) {
                    hasData = false;
                    break;
                }

                List<IndicatorScore> scores = dtoList.stream()
                        .map(dto -> {
                            IndicatorScore score = new IndicatorScore();
                            score.setId(dto.getId());
                            score.setCnpb(dto.getCnpb());
                            score.setReferenceDate(dto.getDataReferencia());
                            score.setIndicatorId(dto.getIndicadorId());
                            score.setIndicatorSigla(dto.getSiglaIndicador());

                            score.setScore(dto.getNota());
                            return score;
                        })
                        .collect(Collectors.toList());

                List<IndicatorScore> allSaved = getRepository().saveAll(scores);
                getRepository().flush();
                System.out.println("Salvos na página " + page + ": " + allSaved.size() + " registros.");
                page++;

            } catch (Exception e) {
                throw new RuntimeException("Erro ao sincronizar a página " + page, e);
            }
        }
    }

    @Override
    public PageableResponseDTO<IndicatorScoreResponseDTO> findAllByFilter(CurrentUser currentUser, String filter,
            int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IndicatorScore> pageResult = getRepository().findFiltered(filter, pageable);

        List<IndicatorScoreResponseDTO> content = pageResult.getContent().stream()
                .map(score -> parseToResponseDTO(score, false))
                .collect(Collectors.toList());

        return new PageableResponseDTO<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.getTotalElements());
    }

}
