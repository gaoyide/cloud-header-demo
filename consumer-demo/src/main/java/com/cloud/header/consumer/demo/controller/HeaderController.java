package com.cloud.header.consumer.demo.controller;

import com.cloud.header.consumer.demo.feign.HeaderFeign;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;

/**
 * @author linzf
 * @since 2019/7/23
 * 类描述：
 */
@RestController
public class HeaderController {

    @Autowired
    private HeaderFeign headerFeign;

    @GetMapping("getConsumerHeader")
    public String getConsumerHeader() throws UnsupportedEncodingException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 中文在请求头中会变为乱码，因此要进行转化
        reflectSetHeader(request,"consumerHeader", URLEncoder.encode("我是从消费者传过来的请求头", "GBK"));
        return headerFeign.getConsumerHeader();
    }

    /**
     * 功能描述：实现往请求里加入我们的header
     * @param request request对象
     * @param key header的key
     * @param value header的值
     */
    private void reflectSetHeader(HttpServletRequest request, String key, String value){
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders)headers.get(o1);
            o2.removeHeader(key);
            o2.addValue(key).setString(value);
        } catch (Exception e) {

        }
    }



}
