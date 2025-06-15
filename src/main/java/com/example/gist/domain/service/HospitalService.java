package com.example.gist.domain.service;

import com.example.gist.domain.dto.HospitalDistanceView;
import com.example.gist.domain.dto.HospitalItem;
import com.example.gist.domain.entity.HospitalGeography;
import com.example.gist.domain.entity.HospitalGeometry;
import com.example.gist.domain.repository.HospitalGeographyRepository;
import com.example.gist.domain.repository.HospitalGeometryRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalService {

    private final HospitalGeometryRepository geometryRepository;
    private final HospitalGeographyRepository geographyRepository;
    private final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional(readOnly = true)
    public List<HospitalDistanceView> getNearestHospitalsUsingGeometry(
            double lat,
            double lng
    ) {
        return geometryRepository.findNearestUsingGeometry(lat, lng);
    }

    @Transactional(readOnly = true)
    public List<HospitalDistanceView> getNearestHospitalsUsingGeography(
            double lat,
            double lng
    ) {
        return geographyRepository.findNearestUsingGeography(lat, lng);
    }

    public void createHospital(
            HospitalItem dto
    ) {
        Point point = geometryFactory.createPoint(
                new Coordinate(
                        dto.getLon(),
                        dto.getLat()
                )
        );
        point.setSRID(4326);

        HospitalGeometry geometry = new HospitalGeometry(dto, point);
        geometryRepository.save(geometry);

        HospitalGeography geography = new HospitalGeography(dto, point);
        geographyRepository.save(geography);
    }
}
