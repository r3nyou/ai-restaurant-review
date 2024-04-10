package com.example.review.jobs;

import com.example.review.dto.PlaceDto;
import com.example.review.entity.Merchant;
import com.example.review.service.MerchantService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class SearchPlaceJob extends QuartzJobBean {
    private static final Logger LOG = LoggerFactory.getLogger(SearchPlaceJob.class);

    @Autowired
    private MerchantService merchantService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("SearchPlaceJob...");


        WebClient webClient = WebClient.builder()
                .baseUrl("https://places.googleapis.com")
                .build();

        Map<String, String> requestBody = new HashMap();
        requestBody.put("textQuery", "餐廳, 台北中山區");
        requestBody.put("languageCode", "zh-TW");
        requestBody.put("maxResultCount", "5");

        String[] fieldMasks = new String[]{
            "places.id",
            "places.displayName",
            "places.rating",
            "places.userRatingCount",
            "places.googleMapsUri",
            "places.websiteUri",
        };

        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                    .path("v1/places:searchText")
                    .queryParam("key", "")
                    .queryParam("fields", String.join(",", fieldMasks))
                    .build())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .toEntity(String.class)
                .block()
                .getBody();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response);
            JsonNode placesNode = root.get("places");
            List<PlaceDto> placeDtos = mapper.readValue(placesNode.toString(), new TypeReference<List<PlaceDto>>(){});

            List<Merchant> merchants = new LinkedList<>();
            for (PlaceDto dto : placeDtos) {
                Merchant merchant = new Merchant(
                    dto.getId(),
                    dto.getDisplayName().get("text"),
                    dto.getRating(),
                    dto.getUserRatingCount(),
                    dto.getGoogleMapsUri(),
                    dto.getWebsiteUri()
                );

                merchants.add(merchant);
            }

            merchantService.saveAll(merchants);

            placeDtos.forEach(placeDto -> System.out.println(placeDto.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
