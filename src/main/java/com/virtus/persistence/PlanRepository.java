package com.virtus.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.virtus.domain.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

    /**
     * Retorna todos os planos vinculados a uma entidade específica.
     *
     * @param entityId ID da entidade
     * @return Lista de planos
     */
    @Query("SELECT p FROM Plan p WHERE p.entity.id = :entityId")
    List<Plan> findByEntityId(@Param("entityId") Integer entityId);

    /**
     * Retorna o maior ID atual da tabela Plan, ou 0 se estiver vazia.
     *
     * @return Maior ID existente
     */
    @Query("SELECT COALESCE(MAX(p.id), 0) FROM Plan p")
    Integer findMaxId();

    /**
     * Busca um plano pelo CNPB (chave única).
     *
     * @param cnpb CNPB do plano
     * @return Plano correspondente, ou null se não encontrado
     */
    Plan findByCnpb(String cnpb);
}