package com.virtus.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalIndicatorDTO {

    @JsonProperty("id_indicador")
    private Integer idIndicador;

    @JsonProperty("sg_indicador")
    private String sgIndicador;

    @JsonProperty("descr_indicador")
    private String descrIndicador;

    public Integer getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(Integer idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getSgIndicador() {
        return sgIndicador;
    }

    public void setSgIndicador(String sgIndicador) {
        this.sgIndicador = sgIndicador;
    }

    public String getDescrIndicador() {
        return descrIndicador;
    }

    public void setDescrIndicador(String descrIndicador) {
        this.descrIndicador = descrIndicador;
    }
}
