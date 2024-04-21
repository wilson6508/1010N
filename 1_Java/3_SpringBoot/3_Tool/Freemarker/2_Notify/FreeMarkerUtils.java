package com.eland.utils.other;

import com.eland.pojo.info.ContentBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class FreeMarkerUtils {

    private static Template content;
    private static Template title;
    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 初始化模板
     */
    public void initTemplates() {
        try {
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            content = configuration.getTemplate("Content.ftl");
            title = configuration.getTemplate("Title.ftl");
        } catch (IOException e) {
            log.error("FreeMarkerUtils initTemplates error");
        }
    }

    /**
     * 根據模板取得文字內容
     */
    public String getTemplateString(Map<String, Object> model, Template template) {
        try {
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            log.error("FreeMarkerUtils getTemplateString error");
            return null;
        }
    }

    public String getMessage(ContentBean contentBean, Set<String> fields) {
        Map<String, Object> model = new HashMap<>();
        model.put("contentBean", contentBean);
        model.put("notification_field", String.join(";", fields));
        return getTemplateString(model, content);
    }

    public String getTitle(String topic) {
        Map<String, Object> model = new HashMap<>();
        model.put("topic", topic);
        model.put("time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        return getTemplateString(model, title);
    }

}
