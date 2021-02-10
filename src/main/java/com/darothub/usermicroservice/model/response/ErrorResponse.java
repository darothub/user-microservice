package com.darothub.usermicroservice.model.response;

import com.darothub.usermicroservice.model.response.ResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@EqualsAndHashCode(callSuper = false)
public class ErrorResponse extends ResponseModel {
    private int status;
    private String message;
    private Object error;
}
