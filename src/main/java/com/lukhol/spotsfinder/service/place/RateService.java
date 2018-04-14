package com.lukhol.spotsfinder.service.place;

public interface RateService {
    void rate(long placeId, String userId, int rate);
    double getTotalRate(long placeId);

    int getRateCount(long placeId);
}