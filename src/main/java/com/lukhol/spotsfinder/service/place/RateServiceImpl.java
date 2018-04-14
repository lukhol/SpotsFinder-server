package com.lukhol.spotsfinder.service.place;

import com.lukhol.spotsfinder.model.PlaceRate;
import com.lukhol.spotsfinder.repository.PlaceRepository;
import com.lukhol.spotsfinder.repository.RateRepository;
import com.lukhol.spotsfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RateServiceImpl implements  RateService {

    private final RateRepository rateRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Override
    public void rate(long placeId, String userIdString, int rate) {
        //TODO: Validate
        long userId = Long.parseLong(userIdString);

        Optional<PlaceRate> placeRateOptional = rateRepository.findOneByPlaceAndUser(placeId, userId);

        if(placeRateOptional.isPresent()) {
            placeRateOptional.get().setRate(rate);
        } else {
            PlaceRate placeRate = new PlaceRate();
            placeRate.setRate(rate);
            placeRate.setPlace(placeRepository.getReference(placeId));
            placeRate.setUser(userRepository.findOne(userId));
            rateRepository.save(placeRate);
        }
    }

    @Override
    public double getTotalRate(long placeId) {
        return rateRepository.getTotalRateForPlace(placeId);
    }

    @Override
    public int getRateCount(long placeId) {
        return rateRepository.getRateCount(placeId);
    }
}