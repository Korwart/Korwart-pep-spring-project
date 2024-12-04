package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message newMessage) {
        if(newMessage.getMessageText()!=null && !newMessage.getMessageText().isEmpty() && newMessage.getMessageText().length()<256){
            Optional<Account> account = accountRepository.findById(newMessage.getPostedBy());
            if(account.isPresent()){
                return messageRepository.save(newMessage);
            }
        }
        return null;
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId) {
        Optional<Message> optMessage = messageRepository.findById(messageId);
        return optMessage.orElse(null);
    }

    public String deleteMessageById(int messageId) {
        Optional<Message> optMessage = messageRepository.findById(messageId);
        if (optMessage.isPresent()) {
            messageRepository.deleteById(messageId);
            return "1";
        }
        return null;
    }

    public String updateMessageById(int messageId, String messageText) {
        if (messageText!=null && !messageText.isBlank() && messageText.length()<256) {
            Optional<Message> optMessage = messageRepository.findById(messageId);
            if (optMessage.isPresent()) {
                Message message = optMessage.get();
                message.setMessageText(messageText);
                messageRepository.save(message);
                return "1";
            }
        }
        return null;
    }

    public List<Message> getMessageByAccount(Integer accountId) {
        Optional<Account> optAccount = accountRepository.findById(accountId);
        if(optAccount.isPresent()){
            return messageRepository.findByPostedBy(accountId);
        }
        return null;
    }

}