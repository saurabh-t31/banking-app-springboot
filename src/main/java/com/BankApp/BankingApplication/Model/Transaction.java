package com.BankApp.BankingApplication.Model;

import java.math.BigDecimal;
import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
       
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private BigDecimal amount;

    private String type;

    private LocalDate timestamp;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction(BigDecimal amount, String type, LocalDate timestamp, Account account) {
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
        this.account = account;
    }
    
}
