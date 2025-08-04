package com.MyTutor2.controller;

import com.MyTutor2.service.impl.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/berlin")
    public Map<String,Object> getBerlinTemperature(){
        double temperature= weatherService.getCurrentTemperatureInBerlin();
        return Map.of("city","Berlin","temperature",temperature,"units","°C");
    }

}
