package com.example.gist.web.controller;

import com.example.gist.domain.dto.HospitalDistanceView;
import com.example.gist.domain.service.CommonApiService;
import com.example.gist.domain.service.HospitalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;
    private final CommonApiService commonApiService;

    /*병원 데이터 조회 + 거리 조회 - Geography*/
    @GetMapping("/geography")
    public List<HospitalDistanceView> getHospitalUsingGeography(
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng
    ) {
        return hospitalService.getNearestHospitalsUsingGeography(
                lat,
                lng
        );
    }

    /*병원 데이터 조회 + 거리 조회 - Geometry*/
    @GetMapping("/geometry")
    public List<HospitalDistanceView> getHospitalUsingGeometry(
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng
    ) {
        return hospitalService.getNearestHospitalsUsingGeometry(
                lat,
                lng
        );
    }

    /*병원 데이터 생성*/
    @PostMapping("/geography")
    public void create() throws JsonProcessingException {
        commonApiService.create();
    }
}