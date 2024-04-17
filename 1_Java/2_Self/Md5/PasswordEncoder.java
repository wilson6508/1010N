package com;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PasswordEncoder {

    public static void main(String[] args) {
        String password = "1234";
        String encode = encode(password);
        boolean matches = matches(encode, password);
    }

    public static String encode(String password) {
        String salt = getRandStr(20);
        return encode(password, salt);
    }

    public static boolean matches(String encodedPassword, String rawPassword) {
        if (encodedPassword == null || rawPassword == null) {
            return false;
        }
        if (!encodedPassword.contains("@")) {
            throw new RuntimeException("密碼格式不正確！");
        }
        String[] arr = encodedPassword.split("@");
        String salt = arr[0];
        return encodedPassword.equals(encode(rawPassword, salt));
    }

    private static String encode(String password, String salt) {
        return salt + "@" + DigestUtils.md5DigestAsHex((password + salt).getBytes(StandardCharsets.UTF_8));
    }

    private static String getRandStr(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int rand = random.nextInt(62);
            if (rand < 10) {
                sb.append(rand);
            } else if (rand < 36) {
                sb.append((char) (rand - 10 + 'a'));
            } else {
                sb.append((char) (rand - 36 + 'A'));
            }
        }
        return sb.toString();
    }

}
