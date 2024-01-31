package dev.kush.security2.service.impl;

import dev.kush.security2.models.ConformationToken;
import dev.kush.security2.repo.ConformationTokenRepo;
import dev.kush.security2.service.ConformationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConformationTokenImpl implements ConformationTokenService {

    private final ConformationTokenRepo conformationTokenRepo;

    public ConformationTokenImpl(ConformationTokenRepo conformationTokenRepo) {
        this.conformationTokenRepo = conformationTokenRepo;
    }

    @Override
    public void saveConformationToken(ConformationToken token) {
        conformationTokenRepo.save(token);
    }

    @Override
    public Optional<ConformationToken> getToken(String token) {
        return conformationTokenRepo.findByToken(token);
    }

    @Override
    public int setConfirmedAt(String token) {
        return conformationTokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    }
}
