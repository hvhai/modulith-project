package com.codehunter.modulithproject.gateway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseDTO<T> implements Serializable {
    @JsonProperty(value = "data")
    private transient T data;

    @JsonProperty(value = "errors")
    private transient List<?> errorList;

    @JsonProperty(value = "meta")
    private transient MetaDataModel metaData;
}
