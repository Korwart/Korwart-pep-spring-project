package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //register new account
    public String register(Account newAccount){
        if(newAccount.getUsername()!=null && newAccount.getPassword().length()>3) {
            Account accountOptional = accountRepository.findAccountByUsername(newAccount.getUsername());
            if(accountOptional!=null) return "Duplicate";
            accountRepository.save(newAccount); return "Success";
        }
        return "Other errors";
    }

    //account login
    public Account login(Account account){
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}