package com.chidinma.digital_wallet.user.service;

import com.chidinma.digital_wallet.user.dtos.RegisterRequest;
import com.chidinma.digital_wallet.user.dtos.UserResponse;
import com.chidinma.digital_wallet.user.entity.User;
import com.chidinma.digital_wallet.user.repository.UserRepository;
import com.chidinma.digital_wallet.utilies.EmailAlreadyExistsException;
import com.chidinma.digital_wallet.wallet.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    private final WalletService walletService;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException(normalizedEmail);
        }

        User user = User.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .email(normalizedEmail)
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        User saved = userRepository.save(user);
        return UserResponse.fromEntity(saved);
    }



    @Transactional
    public User registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException(
                    "Email is already registered"
            );
        }

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new IllegalStateException(
                    "Phone number is already registered"
            );
        }

        User savedUser = userRepository.save(user);

        walletService.createWallet(savedUser);

        return savedUser;
    }
}
