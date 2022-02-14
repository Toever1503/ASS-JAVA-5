package com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String result;

    private Object data;

    private String message;

    private Integer currentPage;
    private Boolean hasNextPage;

    public ResponseData(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseData(Object data, Integer currentPage, Boolean hasNextPage) {
        this.data = data;
        this.result = "success";
        this.currentPage = currentPage;
        this.hasNextPage = hasNextPage;
    }

    public JSONObject toJson() {
        return new JSONObject(this);
    }
}
