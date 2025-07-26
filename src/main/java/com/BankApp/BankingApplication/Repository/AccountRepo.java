package com.BankApp.BankingApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankApp.BankingApplication.Model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account , Long> {
    
    Optional<Account> findByUsername(String username);

}
