package com.virtus.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.virtus.client.dto.ExternalIndicatorDTO;
import com.virtus.client.dto.LastReferenceDTO;

@Service
public class VirtusApiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_ROOT_URL = "https://virtus.previc.gov.br";
    private static final String API_INDICADORES_URL = "/indicadores";
    private static final String API_ULTIMA_REFERENCIA_URL = "/ultima-referencia-conformidade";

    public List<ExternalIndicatorDTO> fetchAllIndicators() {
        ExternalIndicatorDTO[] response = restTemplate.getForObject(API_ROOT_URL + API_INDICADORES_URL,
                ExternalIndicatorDTO[].class);
        return Arrays.asList(response);
    }

    public LastReferenceDTO fetchLastReference() {
        final String url = API_ROOT_URL + API_ULTIMA_REFERENCIA_URL;
        LastReferenceDTO response = restTemplate.getForObject(url, LastReferenceDTO.class);
        return response;
    }

}
