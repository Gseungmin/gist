package com.example.gist.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalItemList {

    @JsonProperty("item")
    private List<HospitalItem> items;

    public HospitalItemList(List<HospitalItem> dto) {
        this.items = dto;
    }
}