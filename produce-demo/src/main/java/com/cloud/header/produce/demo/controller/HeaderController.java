package com.cloud.header.produce.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author linzf
 * @since 2019/7/23
 * 类描述：
 */
@RestController
public class HeaderController {

    @GetMapping("getConsumerHeader")
    public String getConsumerHeader() throws UnsupportedEncodingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String consumerHeader = request.getHeader("consumerHeader");
        if(consumerHeader!=null && !"".equals(consumerHeader)){
            System.out.println("----" + URLDecoder.decode(consumerHeader,"GBK"));
        }else{
            System.out.println("----" +  consumerHeader);
        }
        return "success";
    }

}
