package com.ex.ticket.user.repository;

import com.ex.ticket.auth.model.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {

    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
}
