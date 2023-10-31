package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.ExistAccountException;
import com.example.exception.GenericException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account){
        Account existAccount = accountRepository.findByUsername(account.getUsername());
        if(existAccount == null){
            if(account.getUsername() != null && account.getPassword().length() >=4){
                return accountRepository.save(account);
            }else{
                throw new GenericException("Username or Password invalid.");
            }
        }else{
            throw new ExistAccountException("Account already exist on the database.");
        }
    }

    public Account login(Account account){
        return accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());

    }
}
