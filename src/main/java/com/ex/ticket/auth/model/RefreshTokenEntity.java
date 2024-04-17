package com.ex.ticket.auth.model;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "refreshToken")
@ToString
@Getter
public class RefreshTokenEntity {

    @Id
    private String email;
    @Indexed
    private String refreshToken;
    @TimeToLive // TTL
    private Long ttl;

    @Builder
    public RefreshTokenEntity(String email, String refreshToken, Long ttl) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.ttl = ttl;
    }

    public void updateTTL(Long ttl) {
        this.ttl += ttl;
    }

}
