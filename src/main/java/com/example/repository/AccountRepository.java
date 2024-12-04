package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountByUsername(String username);
    //@Query("FROM Account WHERE username = :username")
    //Account checkAccountByUsername(@Param("username") String username);

    Account findAccountByUsernameAndPassword(String username,String password);
    //@Query("FROM Account WHERE username = :username AND password = :password")
    //Account accountVerification(@Param("username") String username, @Param("password") String password);
}