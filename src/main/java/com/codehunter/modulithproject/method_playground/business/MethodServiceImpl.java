package com.codehunter.modulithproject.method_playground.business;

import com.codehunter.modulithproject.method_playground.domain.Entity;
import com.codehunter.modulithproject.method_playground.mapper.EntityMapper;
import com.codehunter.modulithproject.method_playground.method.MethodEntity;
import com.codehunter.modulithproject.method_playground.method.MethodResponse;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

@Service
public class MethodServiceImpl {
    private final String methodBaseUrl;
    private final RestTemplate methodClient;

    public MethodServiceImpl(@Value("${app.method.url}") String methodBaseUrl, RestTemplate methodClient) {
        this.methodBaseUrl = methodBaseUrl;
        this.methodClient = methodClient;
    }

    @RegisterReflectionForBinding({MethodEntity.class, LinkedList.class, MethodResponse.class})
    public List<Entity> getAllEntities() {
        ResponseEntity<MethodResponse<List<MethodEntity>>> response = methodClient.exchange(methodBaseUrl + "/entities", HttpMethod.GET, null, new ParameterizedTypeReference<MethodResponse<List<MethodEntity>>>(){});
        MethodResponse<List<MethodEntity>> body = response.getBody();
        List<MethodEntity> data = body.data();
        return data.stream().map(EntityMapper::toEntity).toList();
    }


}
