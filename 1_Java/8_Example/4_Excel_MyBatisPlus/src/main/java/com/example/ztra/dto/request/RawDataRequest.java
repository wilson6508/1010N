package com.example.ztra.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawDataRequest {
    private Map<Integer, String> colMap;
    private List<Map<String, Object>> dataList;
}
