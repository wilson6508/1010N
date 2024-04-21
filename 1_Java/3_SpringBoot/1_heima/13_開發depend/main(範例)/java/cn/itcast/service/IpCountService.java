package cn.itcast.service;

import cn.itcast.properties.IpProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class IpCountService {

    private final ConcurrentMap<String, Integer> ipCountMap = new ConcurrentHashMap<>();

    @Autowired
    private HttpServletRequest httpServletRequest;

    public void count() {
        String ip = getIpAddress(httpServletRequest);
        ipCountMap.compute(ip, (k, v) -> (v == null) ? 1 : v + 1);
    }

    @Autowired
    private IpProperties ipProperties;

    @Scheduled(cron = "0/#{ipProperties.cycle} * * * * ?")
    public void print() {
        if (ipProperties.getModel().equals(IpProperties.LogModel.DETAIL.getValue())) {
            System.out.println("           IP訪問監控");
            System.out.println("+-----ip-address-----+--num--+");
            for (Map.Entry<String, Integer> entry : ipCountMap.entrySet()) {
                String str = String.format("|%18s  |%5d  |", entry.getKey(), entry.getValue());
                System.out.println(str);
            }
            System.out.println("+--------------------+-------+");
        } else if (ipProperties.getModel().equals(IpProperties.LogModel.SIMPLE.getValue())) {
            System.out.println("           IP訪問監控");
            System.out.println("+-----ip-address-----+");
            for (String key : ipCountMap.keySet()) {
                String str = String.format("|%18s  |", key);
                System.out.println(str);
            }
            System.out.println("+--------------------+");
        }
        if (ipProperties.getCycleReset()) {
            ipCountMap.clear();
        }
    }

    public static void main(String[] args) {
        System.out.println("           IP訪問監控");
        System.out.println("+-----ip-address-----+--num--+");
        String str01 = String.format("|%18s  |%5d  |", "abc", 123);
        String str02 = String.format("|%-18s  |%5d  |", "abc", 123);
        System.out.println(str01);
        System.out.println(str02);
        System.out.println("+--------------------+-------+");
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根據網卡取本機配置的 IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (inet != null) {
                    ip = inet.getHostAddress();
                }
            }
        }
        // 多個代理的情況，第一個IP為客戶端真實IP,多個IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if (ip == null) {
            return "127.0.0.1";
        }
        return ip;
    }

}
