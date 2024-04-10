package com.example.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Data
@Builder
@Jacksonized
public class PlaceDto {
    private String id;
    private Map<String, String> displayName;
    private float rating;
    private int userRatingCount;
    private String googleMapsUri;
    private String websiteUri;
}
