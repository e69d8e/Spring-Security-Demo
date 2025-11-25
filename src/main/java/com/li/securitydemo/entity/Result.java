package com.li.securitydemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;
    private Long total;

    public static Result ok(Object data) {
        return new Result(1, "success", data, null);
    }
    public static Result ok(Object data, Long total) {
        return new Result(1, "success", data, total);
    }
    public static Result ok() {
        return new Result(1, "success", null,  null);
    }
    public static Result error(String message) {
        return new Result(0, message, null,  null);
    }
    public static Result error() {
        return new Result(0, "error", null,  null);
    }


}
