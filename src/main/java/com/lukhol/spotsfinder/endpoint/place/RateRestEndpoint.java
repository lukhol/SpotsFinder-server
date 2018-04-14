package com.lukhol.spotsfinder.endpoint.place;

import com.lukhol.spotsfinder.service.place.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/places/rate")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RateRestEndpoint {

    private final RateService rateService;

    @PostMapping
    public ResponseEntity<?> ratePlace(@RequestParam("placeId") long placeId, @RequestParam("rate") int rate, Authentication auth) {
        rateService.rate(placeId, auth.getName(), rate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getPlaceRate(@RequestParam("placeId") long placeId) {
        double totalRate = rateService.getTotalRate(placeId);
        int rateCount = rateService.getRateCount(placeId);
        Map<String, Number> responseMap = new HashMap<>();
        responseMap.put("placeId", placeId);
        responseMap.put("rate", totalRate);
        responseMap.put("rateCount", rateCount);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }
}