package com.service.impl;

import com.service.ProductService;
import org.springframework.stereotype.Service;

@Service("productB")
public class ProductSecondServiceImpl implements ProductService {

    @Override
    public String orderingProduct() {
        return "成功订购产品B";
    }

}
