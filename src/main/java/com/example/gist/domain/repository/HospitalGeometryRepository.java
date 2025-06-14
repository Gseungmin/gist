package com.example.gist.domain.repository;

import com.example.gist.domain.dto.HospitalDistanceView;
import com.example.gist.domain.entity.HospitalGeometry;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalGeometryRepository extends JpaRepository<HospitalGeometry, Long> {

    @Query(value = """
        WITH param AS (
          SELECT ST_SetSRID(ST_MakePoint(:lng,:lat),4326) AS p
        )
        SELECT  h.*,
                ST_DistanceSphere(h.location, param.p) AS distance
        FROM    param
        JOIN LATERAL (
                SELECT *
                FROM   hospital_geometry
                ORDER  BY location <-> param.p
                LIMIT  50
        ) h ON TRUE;
        """, nativeQuery = true)
    List<HospitalDistanceView> findNearestUsingGeometry(
            @Param("lat") double latitude,
            @Param("lng") double longitude
    );
}
