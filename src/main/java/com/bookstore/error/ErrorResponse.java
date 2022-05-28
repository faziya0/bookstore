package com.bookstore.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.HashMap;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private String path;
    private Date timeStamp=new Date(System.currentTimeMillis());
    private HashMap<String,String> validationErrors;

    public ErrorResponse(int status, String message, String path) {
    this.status=status;
    this.message=message;
    this.path=path;
    }
}
