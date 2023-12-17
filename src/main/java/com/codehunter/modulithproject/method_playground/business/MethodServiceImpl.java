package com.codehunter.modulithproject.method_playground.business;

import com.codehunter.modulithproject.method_playground.domain.Entity;
import com.codehunter.modulithproject.method_playground.mapper.EntityMapper;
import com.codehunter.modulithproject.method_playground.method.MethodEntity;
import com.codehunter.modulithproject.method_playground.method.MethodResponse;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.LinkedList;
import java.util.List;

@Service
public class MethodServiceImpl {
    private final RestClient methodRestClient;

    public MethodServiceImpl(RestClient methodRestClient) {
        this.methodRestClient = methodRestClient;
    }

    @RegisterReflectionForBinding({MethodEntity.class, LinkedList.class, MethodResponse.class})
    public List<Entity> getAllEntities() {
        MethodResponse<List<MethodEntity>> body = methodRestClient.get()
                .uri("/entities")
                .retrieve()
                .body(new ParameterizedTypeReference<MethodResponse<List<MethodEntity>>>() {
                });
        List<MethodEntity> data = body.data();
        return data.stream().map(EntityMapper::toEntity).toList();
    }


}
