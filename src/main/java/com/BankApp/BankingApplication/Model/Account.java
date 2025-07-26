package com.BankApp.BankingApplication.Model;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account implements UserDetails  {
    
    public Account(String username, String password, BigDecimal balance, List<Transaction> transactions, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactions = transactions;
        this.authorities = authorities;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private BigDecimal balance;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
    
    @Transient
    private Collection<? extends GrantedAuthority > authorities;
  
}
