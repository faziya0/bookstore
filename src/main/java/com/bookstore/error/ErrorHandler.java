package com.bookstore.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestController
public class ErrorHandler implements ErrorController {
    @Autowired
    DefaultErrorAttributes errorAttributes;
    @RequestMapping("/error")
    public ErrorResponse errorHandler(WebRequest webRequest){
       Map<String,Object> attributes=this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.BINDING_ERRORS, ErrorAttributeOptions.Include.EXCEPTION));
      String message=(String)attributes.get("message");
        String path=(String)attributes.get("path");
        Integer status=(Integer)attributes.get("status");
        ErrorResponse errorResponse = new ErrorResponse(status,message,path);
        if(attributes.containsKey("errors")){
            List<FieldError> fieldErrors = (List<FieldError>) attributes.get("errors");
            HashMap<String,String> validationErrors = new HashMap<>();
            for(FieldError fieldError:fieldErrors){
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            errorResponse.setValidationErrors(validationErrors);

        }
        return errorResponse;
    }

}
