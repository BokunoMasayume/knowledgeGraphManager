package com.example.demo.configure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//406
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE,reason = "不应返回空的请求返回空")
public class CommonNullException extends RuntimeException {
}
