package com.example.gist.domain.service;

import com.example.gist.domain.dto.HospitalItem;
import com.example.gist.domain.dto.HospitalItemList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonApiService {

    private final HospitalService service;
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    private final int rows = 1000;

    @Value("${hospital.api.base-url}")
    private String apiBaseUrl;
    @Value("${hospital.api.service-key}")
    private String serviceKey;

    public HospitalItemList getRawHospitalData(
            int page,
            int rows
    ) throws JsonProcessingException {
        String apiUrl = apiBaseUrl + "/getHsptlMdcncFullDown";

        String urlBuilder = apiUrl +
                "?ServiceKey=" + URLEncoder.encode(serviceKey, StandardCharsets.UTF_8) +
                "&pageNo="      + page +
                "&numOfRows="   + rows;

        URI uri = URI.create(urlBuilder);
        String json = restTemplate.getForObject(uri, String.class);
        JsonNode node = mapper.readTree(json).at("/response/body/items");

        if (node.isMissingNode() || node.isNull() ||
                (node.isTextual() && node.asText().isBlank())) {
            return new HospitalItemList();
        }

        return mapper.treeToValue(node, HospitalItemList.class);
    }

    @Transactional
    public void create() throws JsonProcessingException {
        int page = 1;
        List<HospitalItem> itemList = new ArrayList<>();

        while (true) {
            HospitalItemList part = getRawHospitalData(page, rows);
            if (part.getItems() == null || part.getItems().isEmpty()) break;

            itemList.addAll(part.getItems());

            if (part.getItems().size() < rows) break;
            page++;
        }

        for (HospitalItem item : itemList) {
            service.createHospital(item);
        }
    }
}
