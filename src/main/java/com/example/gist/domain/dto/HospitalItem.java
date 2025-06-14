package com.example.gist.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalItem {

    @JsonProperty("hpid")
    private String id;

    @JsonProperty("dutyName")
    private String name;

    @JsonProperty("dutyAddr")
    private String address;

    @JsonProperty("dutyTel1")
    private String telephone;

    @JsonProperty("wgs84Lat")
    private Double lat;

    @JsonProperty("wgs84Lon")
    private Double lon;

    @JsonProperty("dutyDivNam")
    private String type;
}