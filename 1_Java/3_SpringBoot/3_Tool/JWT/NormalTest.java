package com.caili.todolist;

import com.caili.todolist.util.JwtToken;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

public class NormalTest {

    public static void main(String[] args) throws AuthException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "Tom");
        claims.put("gender", "male");
        claims.put("age", "18");
        String token = JwtToken.generateToken(claims);
        System.out.println(token);
    }

}
