package com.ex.ticket.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.ticket.group.domain.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByMasterUserId(Long userId);

    List<Group> findAllByGroupUsers_UserId(Long userId);

    List<Group> findByGroupUsersIdIn(List<Long> userId);

    List<Group> findAllByGroupUsers_UserIdAndGroupUsers_ActiveTrue(Long userId);
}
