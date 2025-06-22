package com.virtus.service;

import com.virtus.domain.entity.Plan;
import com.virtus.persistence.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository repository;

    /**
     * Busca planos pelo ID da entidade associada.
     */
    public List<Plan> findByEntityId(Integer entityId) {
        return repository.findByEntityId(entityId);
    }

    public Plan findByCnpb(String cnpb) {
        return repository.findByCnpb(cnpb);
    }

}
