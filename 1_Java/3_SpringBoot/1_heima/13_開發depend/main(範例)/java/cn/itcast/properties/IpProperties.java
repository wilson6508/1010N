package cn.itcast.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("ipProperties")
@ConfigurationProperties(prefix = "tools.ip")
public class IpProperties {

    /**
     * 日誌顯示週期
     */
    private Long cycle = 5L;

    /**
     * 是否重置
     */
    private Boolean cycleReset = false;

    /**
     *detail:詳細模式 simple:極簡模式
     */
    private String model = LogModel.DETAIL.value;

    public Long getCycle() {
        return cycle;
    }

    public void setCycle(Long cycle) {
        this.cycle = cycle;
    }

    public Boolean getCycleReset() {
        return cycleReset;
    }

    public void setCycleReset(Boolean cycleReset) {
        this.cycleReset = cycleReset;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public enum LogModel {
        DETAIL("detail"),
        SIMPLE("simple");
        private final String value;
        LogModel(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
