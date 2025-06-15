package com.example.gist.domain.entity;

import com.example.gist.domain.dto.HospitalItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HospitalGeography {

    @Id @Column(name = "hospitalGeographyId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pid;

    private String name;
    private String address;

    @Column(columnDefinition = "geography(Point,4326)", nullable = false)
    private Point location;

    public HospitalGeography(HospitalItem dto, Point location) {
        this.name     = dto.getName();
        this.address  = dto.getAddress();
        this.pid      = dto.getId();
        this.location = location;
    }
}
