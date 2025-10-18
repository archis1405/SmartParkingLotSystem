package com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto;

import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private boolean success;
    private String message;
    private T data;
    private String errorCode;

    public static <T> ApiResponse<T> success(String message , T data){
        return ApiResponse.<T>builder().
                success(true).
                message(message).
                data(data).
                build();
    }

    public static <T> ApiResponse<T> error(String message , T errorCode){
        return ApiResponse.<T>builder().
                success(false).
                message(message).
                data(errorCode).
                build();
    }
}
