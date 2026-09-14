package com.chidinma.digital_wallet.wallet.service;

import com.chidinma.digital_wallet.user.entity.User;
import com.chidinma.digital_wallet.wallet.entity.Wallet;
import com.chidinma.digital_wallet.wallet.entity.WalletStatus;
import com.chidinma.digital_wallet.wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final AccountNumberGenerator accountNumberGenerator;

    public WalletService(
            WalletRepository walletRepository,
            AccountNumberGenerator accountNumberGenerator
    ) {
        this.walletRepository = walletRepository;
        this.accountNumberGenerator = accountNumberGenerator;
    }

    @Transactional
    public Wallet createWallet(User user) {

        if (walletRepository.existsByUserId(user.getId())) {
            throw new IllegalStateException(
                    "User already has a wallet"
            );
        }

        String accountNumber = accountNumberGenerator.generate();

        Wallet wallet = Wallet.builder()
                .user(user)
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .currency("NGN")
                .status(WalletStatus.ACTIVE)
                .build();

        return walletRepository.save(wallet);
    }
}
