package com.service;

import com.dao.SearchLogDao;
import com.pojo.entity.SearchLogEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SearchLogService {

    @Resource
    private SearchLogDao searchLogDao;

    public void createLog(SearchLogEntity entity) {
        if (entity.getFrontName() == null) {
            entity.setFrontName("");
        }
        if (entity.getModelName() == null) {
            entity.setModelName("");
        }
        searchLogDao.insert(entity);
    }

}
