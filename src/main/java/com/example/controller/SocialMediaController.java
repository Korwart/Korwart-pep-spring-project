package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(null);
    }

    //message
    @PostMapping("/messages")
    public ResponseEntity<Message> newMessage(@RequestBody Message message){
        Message newmessage = messageService.createMessage(message);
        if(newmessage!=null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(newmessage);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(){
        List<Message> messages = messageService.getMessages();
        return ResponseEntity.status(HttpStatus.OK)
                .body(messages);
    }
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer messageId){
        String row = messageService.deleteMessageById(messageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(row);
    }
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessageById(@PathVariable int messageId, @RequestBody Map<String, Object> messageText){
        String newMessage = (String) messageText.get("messageText");
        String row = messageService.updateMessageById(messageId, newMessage);
        if(row!=null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(row);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessageByAccount(@PathVariable Integer accountId){
        List<Message> message = messageService.getMessageByAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }
}
