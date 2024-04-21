package com.service.factory;

import com.service.ProductService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProductStrategyFactory {

    /**
     * 使用依赖注入引入ProductService产品实现类 以Bean名称作为Map的Key 以Bean实现类作为Value
     */
    @Resource
    Map<String, ProductService> strategyMap = new ConcurrentHashMap<>(2);

    /**
     * 查找对应的产品的处理策略
     *
     * @param productName 产品名称
     * @return 对应的产品订购逻辑实现策略
     */
    public ProductService getProductStrategy(String productName) {
        // 根据从 productName 从 strategyMap 集合中查询对应的产品下单策略
        return strategyMap.get(productName);
    }

}
