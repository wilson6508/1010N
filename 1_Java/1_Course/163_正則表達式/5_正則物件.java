package com.itheima.demo1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo06 {

    public static void main(String[] args)  {
        // 文本
        String str = "Java自從95年balabalabala 目前Java8和Java11balabalabala";
        // 正則對象
        Pattern pattern = Pattern.compile("Java\\d{0,2}");
        // 在文本中找符合正則的文字
        Matcher matcher = pattern.matcher(str);
        // 沒找到false 有找到true 且底層紀錄該文字的起始索引和結束索引+1
        boolean boo01 = matcher.find();
        // substring(起始索引, 結束索引+1) boo01為false則噴錯
        String group01 = matcher.group();

        // 往下匹配
        boolean boo02 = matcher.find();
        String group02 = matcher.group();
    }

}
