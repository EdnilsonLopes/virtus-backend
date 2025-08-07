package com.virtus.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtus.client.dto.ExternalIndicatorDTO;
import com.virtus.client.dto.IndicatorScoreDTO;
import com.virtus.client.dto.LastReferenceDTO;

@Service
public class VirtusApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_ROOT_URL = "https://virtus.previc.gov.br";
    private static final String API_NOTAS_INDICADORES_URL = "/notas-indicadores";
    private static final String API_INDICADORES_URL = "/indicadores-conformidade";
    private static final String API_ULTIMA_REFERENCIA_CONFORMIDADE_URL = "/ultima-referencia-conformidade";

    public List<ExternalIndicatorDTO> fetchAllIndicators() {
        ExternalIndicatorDTO[] response = restTemplate.getForObject(API_ROOT_URL + API_INDICADORES_URL,
                ExternalIndicatorDTO[].class);
        return Arrays.asList(response);
    }

    public LastReferenceDTO fetchLastReference() {
        final String url = API_ROOT_URL + API_ULTIMA_REFERENCIA_CONFORMIDADE_URL;
        return restTemplate.getForObject(url, LastReferenceDTO.class);
    }

    public List<IndicatorScoreDTO> fetchScoresByReference(String referenceDate, int page) {
        final String url = String.format("%s%s?data_referencia=%s&pagina=%d",
                API_ROOT_URL, API_NOTAS_INDICADORES_URL, referenceDate, page);

        ResponseEntity<String> rawResponse = restTemplate.getForEntity(url, String.class);
        String body = rawResponse.getBody();

        if (body != null && body.trim().startsWith("[")) {
            // É um array JSON válido
            ObjectMapper mapper = new ObjectMapper();
            try {
                IndicatorScoreDTO[] array = mapper.readValue(body, IndicatorScoreDTO[].class);
                return Arrays.asList(array);
            } catch (Exception e) {
                System.out.println("Erro ao processar JSON: " + e.getMessage());
                return List.of();
            }
        } else {
            // Pode ser um objeto com mensagem de erro
            System.out.println("Resposta da API: " + body);
            return List.of();
        }

    }
}
