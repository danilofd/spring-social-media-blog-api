package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.GenericException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    public MessageRepository messageRepository;

    @Autowired
    public AccountRepository accountRepository;

    public Message submit(Message message){
        Optional<Account> account = accountRepository.findById(message.getPosted_by());
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255 && !account.isEmpty()){
            return messageRepository.save(message);
        }else{
            throw new GenericException("Message text is blank, message text is over 255 characters or account id not found.");
        }
    }

    public int update(Integer message_id, Message message){
        Optional<Message> messageExist = messageRepository.findById(message_id);
        if(!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255 && !messageExist.isEmpty()){
            return messageRepository.updateMessage(message_id, message.getMessage_text());
        }else{
            throw new GenericException("Message text is blank, message text is over 255 characters or message id not found.");
        }
    }

    public List<Message> listAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> listAllMessagesByAccount(Integer account_id){
        return messageRepository.findByPosted_by(account_id);
    }

    public Optional<Message> messageById(Integer message_id){
        return messageRepository.findById(message_id);
    }

    public int deleteMessage(Integer message_id){
        return messageRepository.deleteByIdCustom(message_id);
    }

    
}
