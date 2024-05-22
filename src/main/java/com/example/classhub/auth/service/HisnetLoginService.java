package com.example.classhub.auth.service;
import java.util.HashMap;
import java.util.Map;

import com.example.classhub.domain.member.dto.MemberDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
@Service
public class HisnetLoginService {

    @Value("${hisnet.access-key}")
    private String accessKey;

    public MemberDto callHisnetLoginApi(MemberDto dto) {
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("token", dto.getHisnetToken());
        requestBody.put("accessKey", accessKey);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://walab.info:8443/HisnetLogin/api/hisnet/login/validate";
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

        try {
            ParameterizedTypeReference<Map<String, Object>> typeRef =
                    new ParameterizedTypeReference<>() {};
            ResponseEntity<Map<String, Object>> resultMap =
                    restTemplate.exchange(uri.toString(), HttpMethod.POST, entity, typeRef);
            Map<String, Object> result = resultMap.getBody();
            assert result != null;
            return MemberDto.builder()
                    .uniqueId(result.get("uniqueId").toString())
                    .member_name(result.get("name").toString())
                    .email(result.get("email").toString())
                    .department(result.get("department").toString())
                    .build();
        } catch (HttpStatusCodeException e) {
            Map<String, Object> result = new HashMap<>();
            try {
                result = new ObjectMapper().readValue(e.getResponseBodyAsString(), new TypeReference<>() {});
            } catch (Exception ex) {
                throw new IllegalArgumentException("Hisnet login failed");
            }
            throw new IllegalArgumentException(result.get("message").toString());
        }
    }
}