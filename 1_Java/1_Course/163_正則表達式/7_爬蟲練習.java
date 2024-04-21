package com.itheima.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo08 {

    public static void main(String[] args) {
        String cell = "1[3-9]\\d{9}";
        String email = "\\w+@[a-zA-Z0-9]+(\\.[a-zA-Z]+){1,2}";
        String str = "學習Java" +
                     " 電話: 18512516758, 18512508907" +
                     " 郵箱: bozai@itcast.cn" +
                     " 隨便文字abc";

        String regex = String.format("(%s)|(%s)", cell, email);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

}
