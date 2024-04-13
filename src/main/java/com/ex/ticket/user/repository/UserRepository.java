package com.ex.ticket.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.ticket.user.domain.entity.AccountRole;
import com.ex.ticket.user.domain.entity.AccountState;
import com.ex.ticket.user.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String account);

    List<User> findAllByAccountRole(AccountRole role);

    Optional<User> findByEmailAndAccountState(String email, AccountState accountState);

    List<User> findAllByUserIdIn(List<Long> userId);

    List<User> findByUserIdIn(List<Long> userIds);

}
