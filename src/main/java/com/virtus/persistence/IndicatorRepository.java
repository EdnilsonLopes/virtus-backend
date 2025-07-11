package com.virtus.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Indicator;

@Repository
public interface IndicatorRepository extends BaseRepository<Indicator> {

    // Busca por sigla (JPA Query derivada)
    List<Indicator> findByIndicatorAcronym(String acronym);

    // Busca paginada por nome (query derivada funciona perfeitamente com camelCase da entidade)
    Page<Indicator> findByIndicatorNameContainingIgnoreCase(String indicatorName, Pageable pageable);

    // Busca por descrição (usando JPQL ao invés de nativeQuery)
    @Query("SELECT i FROM Indicator i WHERE LOWER(i.indicatorDescription) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<Indicator> findByDescriptionContainingIgnoreCase(@Param("description") String description);

    // Busca por lista de siglas (JPQL seguro)
    @Query("SELECT i FROM Indicator i WHERE i.indicatorAcronym IN :acronyms")
    List<Indicator> findByAcronyms(@Param("acronyms") List<String> acronyms);

    // Lista apenas as siglas (JPQL)
    @Query("SELECT i.indicatorAcronym FROM Indicator i")
    List<String> findAllAcronyms();

    @Query(value = "SELECT * FROM virtus.indicadores " +
            "WHERE LOWER(nome_indicador) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(descricao_indicador) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "OR LOWER(sigla_indicador) LIKE LOWER(CONCAT('%', :filter, '%')) " +
            "ORDER BY sigla_indicador", countQuery = "SELECT COUNT(*) FROM virtus.indicadores "
                    +
                    "WHERE LOWER(nome_indicador) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(descricao_indicador) LIKE LOWER(CONCAT('%', :filter, '%')) " +
                    "OR LOWER(sigla_indicador) LIKE LOWER(CONCAT('%', :filter, '%'))", nativeQuery = true)
    Page<Indicator> searchAllByFilter(@Param("filter") String filter, Pageable pageable);

    @Query("SELECT i FROM Indicator i WHERE i.indicatorName LIKE %:filter% OR i.indicatorAcronym LIKE %:filter% OR i.indicatorDescription LIKE %:filter% ORDER BY i.indicatorAcronym ASC")
    Page<Indicator> findAllByFilter(String filter, PageRequest pageRequest);

}
