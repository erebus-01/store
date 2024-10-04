package io.store.steam.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private Date timestamp;
    private List<String> message;
    private String httpMethod;
    private String path;
    private String error;

}
