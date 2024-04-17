package com.eland.utils.other;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class IoUtils {
	
	
    public String getResource() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/static/category_mapping.txt");
        byte[] byteArr = FileCopyUtils.copyToByteArray(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return new String(byteArr, StandardCharsets.UTF_8);
    }	

    /**
     * 取品牌及類別的編碼對應
     */
    public Map<String, String> getMapping(String filePath) {
        Map<String, String> resultMap = new LinkedHashMap<>();
        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath));
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            resultMap = br.lines().map(line -> line.split(","))
                    .collect(Collectors.toMap(o -> o[0], o -> o[1], (a, b) -> b, LinkedHashMap::new));
        } catch (Exception e) {
            log.error("IoUtils GetMapping Error: ", e);
        }
        return resultMap;
    }

    /**
     * 取得靜態資源
     * @param filePath 檔案路徑
     * @param clazz 反序列化的class
     * @param <E> 返回的型別
     * @return 指定的型別
     */
    public <E> E getResource(String filePath, Class<E> clazz) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            byte[] byteArr = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String content = new String(byteArr, StandardCharsets.UTF_8);
            return new Gson().fromJson(content, clazz);
        } catch (IOException e) {
            log.error("IoUtils GetResource Error: ", e);
        }
        return null;
    }

}
