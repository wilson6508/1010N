package com.service.impl;

import com.service.ProductService;
import org.springframework.stereotype.Service;

@Service("productA")
public class ProductFirstServiceImpl implements ProductService {

    @Override
    public String orderingProduct() {
        return "成功订购产品A";
    }

}
