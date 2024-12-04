package com.example.controller;

import com.example.entity.Account;
//import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    //private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService/* , MessageService messageService*/){
        this.accountService = accountService;
        //this.messageService = messageService;
    }

    //account
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Account account){
        String status = accountService.register(account);
        if(Objects.equals(status, "Success")) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully registered");
        }
        else if(Objects.equals(status, "Duplicate")){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Duplicate username");
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User name empty or password too short");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account logged = accountService.login(account);
        if(logged!=null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(logged);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
    }

}
