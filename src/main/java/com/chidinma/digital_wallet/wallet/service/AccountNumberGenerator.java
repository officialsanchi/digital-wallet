package com.chidinma.digital_wallet.wallet.service;

import com.chidinma.digital_wallet.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
@Service
public class AccountNumberGenerator {
    private static final int ACCOUNT_NUMBER_LENGTH = 10;

    private final WalletRepository walletRepository;

    private final SecureRandom secureRandom = new SecureRandom();

    public AccountNumberGenerator(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public String generate() {

        String accountNumber;

        do {
            accountNumber = generateRandomAccountNumber();
        } while (walletRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    private String generateRandomAccountNumber() {

        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int digit = secureRandom.nextInt(10);
            accountNumber.append(digit);
        }

        return accountNumber.toString();
    }
}
