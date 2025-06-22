package com.virtus.service;

import com.virtus.domain.entity.Indicator;
import com.virtus.persistence.IndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndicatorService {

    @Autowired
    private IndicatorRepository repository;

    public Page<Indicator> findByFilter(String filter, int page, int size) {
        if (filter == null || filter.trim().isEmpty()) {
            // JPA Query derivada funciona perfeitamente com camelCase da entidade
            // Se não houver filtro, retorna todos os indicadores ordenados por nome
            Pageable pageable = PageRequest.of(page, size, Sort.by("indicatorName"));
            return repository.findAll(pageable);
        }
        // Native Query para busca por nome, descrição ou sigla, 
        // tem que usar o nome da coluna do banco de dados
        // e não o nome do atributo da entidade.    
        Pageable pageable = PageRequest.of(page, size, Sort.by("nome_indicador"));
        return repository.searchAllByFilter(filter.trim(), pageable);
    }

    /**
     * Busca por parte do nome do indicador (case-insensitive).
     * 
     * @param size
     * @param page
     */
    public Page<Indicator> findByNameContaining(String filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("indicatorName").ascending());

        if (filter == null || filter.trim().isEmpty()) {
            return repository.findAll(pageable);
        }

        return repository.findByIndicatorNameContainingIgnoreCase(filter, pageable);
    }

    /**
     * Busca por parte da descrição do indicador (case-insensitive).
     */
    public List<Indicator> findByDescriptionContaining(String description) {
        return repository.findByDescriptionContainingIgnoreCase(description);
    }

    /**
     * Retorna indicadores cujas siglas estão na lista informada.
     */
    public List<Indicator> findByAcronyms(List<String> acronyms) {
        return repository.findByAcronyms(acronyms);
    }

    /**
     * Retorna todas as siglas cadastradas.
     */
    public List<String> findAllAcronyms() {
        return repository.findAllAcronyms();
    }

    /**
     * Salva ou atualiza um indicador.
     */
    public Indicator save(Indicator indicator) {
        return repository.save(indicator);
    }

    /**
     * Retorna todos os indicadores.
     */
    public List<Indicator> findAll() {
        return repository.findAll();
    }

    public Page<Indicator> findAllPaginated(String filter, Pageable pageable) {
        if (filter == null || filter.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByIndicatorNameContainingIgnoreCase(filter, pageable);
    }

    /**
     * Exclui um indicador pelo ID.
     */
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    /**
     * Busca um indicador pelo ID.
     */
    public Indicator findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
