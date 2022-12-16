package com.br.livraria.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("server-message")
public interface InvokeServiceFeign {
    @RequestMapping(method = RequestMethod.POST, value = "/message", consumes="application/json") String postDataMessege();

}
