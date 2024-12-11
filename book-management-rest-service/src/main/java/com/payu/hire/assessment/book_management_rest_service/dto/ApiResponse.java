package com.payu.hire.assessment.book_management_rest_service.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private String responseStatus;
    private String responseMessage;
    private Object responseDetails;

    public static ApiResponse success() {
        return ApiResponse.builder()
                .responseStatus("Successful")
                .responseMessage(null)
                .responseDetails(null)
                .build();
    }

    public static ApiResponse success(String msg) {
        return ApiResponse.builder()
                .responseStatus("Successful")
                .responseMessage(msg)
                .responseDetails(null)
                .build();
    }

    public static ApiResponse success(Object body) {
        return ApiResponse.builder()
                .responseStatus("Successful")
                .responseMessage(null)
                .responseDetails(body)
                .build();
    }

    public static ApiResponse success(Object body, String msg) {
        return ApiResponse.builder()
                .responseStatus("Successful")
                .responseMessage(msg)
                .responseDetails(body)
                .build();
    }

    public static ApiResponse error(String message) {
        return ApiResponse.builder()
                .responseStatus("Failure")
                .responseMessage(message)
                .responseDetails(null)
                .build();
    }

}
