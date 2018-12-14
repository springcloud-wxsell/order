package com.sell.order.controller;

import com.sell.common.dto.ProductInfoDTO;
import com.sell.product.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author WangWei
 * @Title: ClientController
 * @ProjectName order
 * @date 2018/12/4 20:41
 * @description:
 */
@RestController
@Slf4j
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ProductClient productClient;

//    @Autowired
//    private RestTemplate restTemplate;


    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        // 1. 第一种方式(直接使用restTemplate, url直接写死)
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:8080/msg", String.class);

        //2. 第二种方式(利用loadBalancerClient通过应用名获取url, 然后在使用)
/*
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort() + "/msg");
        String response = restTemplate.getForObject(url, String.class);*/

        // 3. 第三种方式(利用@LoadBalanced, 可在restTemplate里直接使用应用名)
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);

        // 4. 第四种方式（feign)
        //String response = productClient.productMsg();

//        log.info("response={}", response);
//        return response;
        return null;
    }

    @PostMapping("/getProductList")
    public String getProductList() {
        List<ProductInfoDTO> productInfoDTOList = productClient.listForOrder(Arrays.asList("123456"));
        log.info("response={}", productInfoDTOList);
        return "ok";
    }
}

