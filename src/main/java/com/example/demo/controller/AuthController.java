package com.example.demo.controller;

import com.example.demo.POJO.User;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("${jwt.header}")
    private String authHeader;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User userToAdd ){
        return authService.register(userToAdd);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String , Object>> issueToken( String username, String password) throws AuthenticationException{
        System.out.println(String.format("username :%s     password: %s" , username, password));

        String token = authService.login(username , password);
        Map<String , Object> tokenMap = new HashMap<>();
        tokenMap.put("token" , token);

        return ResponseEntity.ok(tokenMap);

    }

    @GetMapping("/refresh")
    public ResponseEntity<Map<String , Object>> refreshToken(@RequestHeader("${jwt.header}") String token){
        String refreshToken = authService.refresh(token);
        if(refreshToken == null){
            return ResponseEntity.badRequest().body(null);
        }else{
            Map<String , Object> tokenMap = new HashMap<>();
            tokenMap.put("token" , refreshToken);
            return ResponseEntity.ok(tokenMap);
        }
    }

}
