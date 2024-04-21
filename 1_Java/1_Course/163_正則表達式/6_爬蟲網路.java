package com.itheima.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo07 {

    public static void main(String[] args) throws IOException {
        URL url = new URL("");
        URLConnection connection = url.openConnection();

        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String regex = "";
        Pattern pattern = Pattern.compile(regex);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
        }
        bufferedReader.close();
    }

}
