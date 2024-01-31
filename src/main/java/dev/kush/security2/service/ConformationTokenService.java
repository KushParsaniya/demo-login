package dev.kush.security2.service;

import dev.kush.security2.models.ConformationToken;

import java.util.Optional;

public interface ConformationTokenService {

    void saveConformationToken(ConformationToken token);

    Optional<ConformationToken> getToken(String token);

    int setConfirmedAt(String token);
}
