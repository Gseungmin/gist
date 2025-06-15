package com.example.gist.domain.repository;

import com.example.gist.domain.dto.HospitalDistanceView;
import com.example.gist.domain.entity.HospitalGeography;
import com.example.gist.domain.entity.HospitalGeometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalGeographyRepository extends JpaRepository<HospitalGeography, Long> {

    @Query(value = """
        WITH param AS (
          SELECT ST_MakePoint(:lng, :lat)::geography AS pos
        )
        SELECT  h.*,
                ST_Distance(h.location, param.pos) AS distance
        FROM    param
        JOIN LATERAL (
                SELECT *
                FROM   hospital_geography
                ORDER  BY location <-> param.pos
                LIMIT  50
        ) h ON TRUE
        """, nativeQuery = true)
    List<HospitalDistanceView> findNearestUsingGeography(
            @Param("lat") double latitude,
            @Param("lng") double longitude
    );
}
