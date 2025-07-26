package com.BankApp.BankingApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankApp.BankingApplication.Model.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction , Long>{
    
    List<Transaction> findByAccountId(Long accountId);
}
