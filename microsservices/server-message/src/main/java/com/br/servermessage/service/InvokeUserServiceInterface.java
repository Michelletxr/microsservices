package com.br.servermessage.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-server")
public interface InvokeUserServiceInterface {
    @RequestMapping(method = RequestMethod.GET, value = "/user", consumes="application/json") String getData();
}
