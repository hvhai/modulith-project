package com.codehunter.modulithproject.gateway;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationError {
    @JsonIgnore
    private HttpStatus status;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "detail")
    private String detail;

    public ApplicationError(String message) {
        this.status = ErrorCodes.valueOf(message).getStatus();
        this.title = ErrorCodes.valueOf(message).getTitle();
        this.detail = ErrorCodes.valueOf(message).getDetail();
    }

    public static ApplicationError fromErrorCode(ErrorCodes err, Optional<String> detailMsg) {
        return ApplicationError.builder()
                .title(err.getTitle())
                .status(err.getStatus())
                .detail(detailMsg.isPresent()
                        ? detailMsg.get()
                        : err.getDetail())
                .build();
    }
}
