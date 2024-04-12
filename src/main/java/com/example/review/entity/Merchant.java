package com.example.review.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "merchants")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placeId;
    private String displayName;
    private float rating;
    private int userRatingCount;
    private String googleMapsUri;
    private String websiteUri;

    // for jpa only, not use
    public Merchant() {
    }

    public Merchant(String placeId, String displayName, float rating, int userRatingCount, String googleMapsUri, String websiteUri) {
        this.placeId = placeId;
        this.displayName = displayName;
        this.rating = rating;
        this.userRatingCount = userRatingCount;
        this.googleMapsUri = googleMapsUri;
        this.websiteUri = websiteUri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getUserRatingCount() {
        return userRatingCount;
    }

    public void setUserRatingCount(int userRatingCount) {
        this.userRatingCount = userRatingCount;
    }

    public String getGoogleMapsUri() {
        return googleMapsUri;
    }

    public void setGoogleMapsUri(String googleMapsUri) {
        this.googleMapsUri = googleMapsUri;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(String websiteUri) {
        this.websiteUri = websiteUri;
    }
}
