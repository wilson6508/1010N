package com.controller;

import com.service.ProductService;
import com.service.factory.ProductStrategyFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProductController {

    @Resource
    private ProductStrategyFactory factoryForStrategy;

    /**
     * 执行下单订购产品
     *
     * @param type 产品类型(策略)
     * @return 订购结果
     */
    @GetMapping("/order")
    public String order(@RequestParam(value = "type") String type) {
        ProductService productService = factoryForStrategy.getProductStrategy(type);
        return productService != null ? productService.orderingProduct() : "没有发现对应的产品处理策略";
    }

}
