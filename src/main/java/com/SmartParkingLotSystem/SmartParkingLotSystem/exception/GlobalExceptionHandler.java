package com.SmartParkingLotSystem.SmartParkingLotSystem.exception;

import com.SmartParkingLotSystem.SmartParkingLotSystem.models.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSpotAvailableException.class)
    public ResponseEntity<ApiResponse> handleNoSpotAvailable(NoSpotAvailableException ex , WebRequest request){
        ApiResponse response = ApiResponse.error(ex.getMessage() , ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<ApiResponse> handleVehicleNotFound(VehicleNotFoundException ex , WebRequest request){
        ApiResponse response = ApiResponse.error(ex.getMessage() , ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidVehicleException.class)
    public ResponseEntity<ApiResponse> handleInvalidVehicle(InvalidVehicleException ex , WebRequest request) {
        ApiResponse response = ApiResponse.error(ex.getMessage(), ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex){
        HashMap<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });

        ApiResponse response = ApiResponse.<HashMap<String,String>>builder().
                success(false).
                message("Validation Failed").
                errorCode("VALIDATION_FAILED").
                data(errors).
                build();

        return new ResponseEntity<>(response , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex, WebRequest request) {
        ApiResponse<Void> response = ApiResponse.error("An unexpected error occurred: " + ex.getMessage(), "INTERNAL_ERROR");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
