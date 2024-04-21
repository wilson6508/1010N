package com.utils.other;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class DeserializationUtils {

    @Resource
    private ObjectMapper objectMapper;

    public String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("DeserializationUtils serialize Error : {}", e.getMessage());
            return null;
        }
    }

    public <O> O readObject(String json, Class<O> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("DeserializationUtils readObject Error : " + e.getMessage());
            return null;
        }
    }

    public <O> O readObject(Object apiRep, String fieldName, Class<O> clazz) {
        try {
            HashMap<String, Object> hashMap = getHashMap(apiRep);
            if (hashMap.isEmpty()) {
                return null;
            }
            if (!hashMap.containsKey(fieldName)) {
                return null;
            }
            String targetJson = objectMapper.writeValueAsString(hashMap.get(fieldName));
            return objectMapper.readValue(targetJson, clazz);
        } catch (JsonProcessingException e) {
            log.error("DeserializationUtils readObject Error : " + e.getMessage());
            return null;
        }
    }

    public <O> List<O> readObjectList(Object apiRep, String fieldName, TypeReference<List<O>> typeReference) {
        try {
            HashMap<String, Object> hashMap = getHashMap(apiRep);
            if (hashMap.isEmpty()) {
                return new ArrayList<>();
            }
            if (!hashMap.containsKey(fieldName)) {
                return new ArrayList<>();
            }
            String targetJson = objectMapper.writeValueAsString(hashMap.get(fieldName));
            return objectMapper.readValue(targetJson, typeReference);
        } catch (JsonProcessingException e) {
            log.error("DeserializationUtils readObjectList Error : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public HashMap<String, Object> getHashMap(Object apiRep) {
        try {
            String json = objectMapper.writeValueAsString(apiRep);
            TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() {};
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("DeserializationUtils getHashMap Error : " + e.getMessage());
            return new HashMap<>();
        }
    }

}
