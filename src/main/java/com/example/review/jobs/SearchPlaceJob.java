package com.example.review.jobs;

import com.example.review.dto.PlaceDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SearchPlaceJob extends QuartzJobBean {
    private static final Logger LOG = LoggerFactory.getLogger(SearchPlaceJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("SearchPlaceJob...");

        Map<String, String> bodyMap = new HashMap();
        bodyMap.put("textQuery", "餐廳, 台北中山區");
        bodyMap.put("languageCode", "zh-TW");

         WebClient webClient = WebClient.builder()
                 .baseUrl("https://places.googleapis.com")
                 .build();

        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("v1/places:searchText")
                        .queryParam("key", "AIzaSyDj10r83c2uSLcagH34qa1s1aZ84QRZx4Q")
                        .queryParam("fields", "places.id,places.displayName")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bodyMap))
                .retrieve()
                .toEntity(String.class)
                .block()
                .getBody();

        ObjectMapper mapper = new ObjectMapper();
        try {
                JsonNode root = mapper.readTree(response);
                JsonNode placesNode = root.get("places");
                List<PlaceDto> placeDtos = mapper.readValue(placesNode.toString(), new TypeReference<List<PlaceDto>>(){});

                placeDtos.forEach(placeDto -> System.out.println(placeDto.toString()));
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
