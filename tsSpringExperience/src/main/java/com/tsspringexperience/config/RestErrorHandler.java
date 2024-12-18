package com.tsspringexperience.config;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tsspringexperience.utils.Constants;
import com.tsspringexperience.utils.SystemError;
import com.tsspringexperience.utils.SystemResponse;
import com.tsspringexperience.utils.Utils;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object badRequest(BindException ex) throws JSONException {

        SystemResponse systemResponse = Utils.GetJsonFormat(Constants.INTERNAL_ERROR_203, "Bind exception.", null, null);
        
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        List<SystemError> errorDetails = new ArrayList<>();
        for (FieldError fieldError : errors) {
        	SystemError error = new SystemError();
            error.setErrorCode(fieldError.getField());
            error.setErrorMessage(fieldError.getDefaultMessage());
            error.setUserMessage(fieldError.getDefaultMessage());
            errorDetails.add(error);
        }
        
        systemResponse.setErrors(errorDetails);

        return systemResponse;
    }
    
}