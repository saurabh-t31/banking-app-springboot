package com.BankApp.BankingApplication.Service;

import com.BankApp.BankingApplication.Model.Account;
import com.BankApp.BankingApplication.Model.Transaction;
import com.BankApp.BankingApplication.Repository.AccountRepo;
import com.BankApp.BankingApplication.Repository.TransactionRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    private  AccountService accountService;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TransactionRepo transactionRepo;
    
    private Account testAccount;

    @BeforeEach
    void setUp(){
        testAccount = new Account();
        testAccount.setUsername("john");
        testAccount.setPassword("test123");
        testAccount.setBalance(BigDecimal.valueOf(1000));
        testAccount = accountRepo.save(testAccount);
    }

    @Test
    void testRegisterAccountSuccess(){
        Account acc = accountService.registerAccount("Alice","pass@123");
        assertNotNull(acc.getId());
    }

    @Test
    void testFindAccountByUsername_Success() {
        Account found = accountService.findAccountByUsername("john");
        assertEquals("john", found.getUsername());
    }
    @Test
    void testDeposit() {
        accountService.deposit(testAccount, BigDecimal.valueOf(500));
        Account updated = accountRepo.findByUsername("john").get();
        assertEquals(BigDecimal.valueOf(1500), updated.getBalance());

        List<Transaction> transactions = transactionRepo.findByAccountId(updated.getId());
        assertEquals(1, transactions.size());
        assertEquals("Deposit", transactions.get(0).getType());
    }
    @Test
    void testWithdraw_Success() {
        accountService.withdraw(testAccount, BigDecimal.valueOf(400));
        Account updated = accountRepo.findByUsername("john").get();
        assertEquals(BigDecimal.valueOf(600), updated.getBalance());
    }
    
    @Test
    void testWithdraw_InsufficientFunds() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                accountService.withdraw(testAccount, BigDecimal.valueOf(2000)));
        assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void testTransferAmount_Success() {
        Account receiver = new Account();
        receiver.setUsername("bob");
        receiver.setPassword("0000");
        receiver.setBalance(BigDecimal.valueOf(300));
        receiver = accountRepo.save(receiver);

        accountService.transferAmount(testAccount, "bob", BigDecimal.valueOf(200));

        Account updatedSender = accountRepo.findByUsername("john").get();
        Account updatedReceiver = accountRepo.findByUsername("bob").get();

        assertEquals(BigDecimal.valueOf(800), updatedSender.getBalance());
        assertEquals(BigDecimal.valueOf(500), updatedReceiver.getBalance());
    }
    
    @Test
    void testGetTransactionHistory() {
        accountService.deposit(testAccount, BigDecimal.valueOf(200));
        accountService.withdraw(testAccount, BigDecimal.valueOf(100));

        List<Transaction> history = accountService.getTransactionHistory(testAccount);
        assertEquals(2, history.size());
    }


}
