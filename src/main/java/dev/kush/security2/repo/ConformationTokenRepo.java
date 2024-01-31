package dev.kush.security2.repo;

import dev.kush.security2.models.ConformationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConformationTokenRepo extends JpaRepository<ConformationToken,Long> {
    Optional<ConformationToken> findByToken(String token);


    @Transactional
    @Modifying
    @Query("UPDATE ConformationToken c" +
            " SET c.confiremdAt =:confirmedAt WHERE c.token = :token")
    int updateConfirmedAt(@Param("token") String token,@Param("confirmedAt") LocalDateTime confirmedAt);

}
