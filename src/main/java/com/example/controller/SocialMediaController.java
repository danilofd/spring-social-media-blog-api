package com.example.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping(value = "/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        Account newAccount = accountService.register(account);
        return ResponseEntity.ok().body(newAccount);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account accountLogged = accountService.login(account);
        if(accountLogged != null){
            return ResponseEntity.ok().body(accountLogged);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<Message> submit(@RequestBody Message message){
        Message newMessage = messageService.submit(message);
        return ResponseEntity.ok().body(newMessage);
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<List<Message>> listAllMessages(){
        List<Message> messages = messageService.listAllMessages();
        return ResponseEntity.ok().body(messages);
    }

    @GetMapping(value = "/messages/{message_id}")
    public ResponseEntity<Optional<Message>> messageById(@PathVariable("message_id") Integer message_id){
        Optional<Message> message = messageService.messageById(message_id);
        if(message.isEmpty()){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.ok().body(message);
        }
    }

    @DeleteMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> deleteById(@PathVariable("message_id") Integer message_id){
        int rowsUpdated = messageService.deleteMessage(message_id);
        if(rowsUpdated > 0){
            return ResponseEntity.ok().body(rowsUpdated);
        }else{
            return ResponseEntity.ok().build();
        }
    }

    @PatchMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> submit(@PathVariable("message_id") Integer message_id,
                                            @RequestBody Message message){
        int rowsUpdated = messageService.update(message_id, message);
        return ResponseEntity.ok().body(rowsUpdated);
    }

    @GetMapping(value = "/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> listAllMessagesByAccount(@PathVariable("account_id") Integer account_id){
        List<Message> messages = messageService.listAllMessagesByAccount(account_id);
        return ResponseEntity.ok().body(messages);
    }

}
