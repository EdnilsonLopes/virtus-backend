package com.virtus.client.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndicatorScoreDTO {

    @JsonProperty("id_nota_periodica")
    private Integer id;

    private String cnpb;

    @JsonProperty("data_referencia")
    private String dataReferencia;

    @JsonProperty("id_indicador")
    private Integer indicadorId;

    @JsonProperty("sigla_indicador")
    private String siglaIndicador;

    private BigDecimal nota;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnpb() {
        return cnpb;
    }

    public void setCnpb(String cnpb) {
        this.cnpb = cnpb;
    }

    public String getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(String dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public Integer getIndicadorId() {
        return indicadorId;
    }

    public void setIndicadorId(Integer indicadorId) {
        this.indicadorId = indicadorId;
    }

    public String getSiglaIndicador() {
        return siglaIndicador;
    }

    public void setSiglaIndicador(String siglaIndicador) {
        this.siglaIndicador = siglaIndicador;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }
}
