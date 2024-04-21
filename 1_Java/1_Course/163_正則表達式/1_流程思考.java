package com.itheima.demo1;

public class RegexDemo01 {

    public static void main(String[] args) {
        String qq = "1234567890";
        String regex = "[1-9]\\d{5,19}";
        boolean matches = qq.matches(regex);
    }

    public static boolean check(String qq) {
        int len = qq.length();
        if (len < 6 || len > 20) {
            return false;
        }
        if (qq.startsWith("0")) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            char c = qq.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

}
