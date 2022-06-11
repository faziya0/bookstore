package com.bookstore.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnAuthorizeException extends RuntimeException {
    public String message;
}
