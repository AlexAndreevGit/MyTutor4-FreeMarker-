package com.MyTutor2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    public double getCurrentTemperatureInBerlin(){

        String url="https://api.open-meteo.com/v1/forecast?latitude=52&longitude=13&current=temperature_2m";

        ResponseEntity<Map> response= restTemplate.getForEntity(url, Map.class);

        Map current = (Map) response.getBody().get("current");

        return (double) current.get("temperature_2m");
    }



}
