package com.ex.ticket.group.domain.entity;

import com.ex.ticket.common.domain.BaseTimeEntity;
import com.ex.ticket.group.exception.AlreadyJoinedGroupException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_group_user")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "member_id"})})
public class GroupUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_user_id")
    private Long id;

    // 소속 호스트 아이디
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    // 소속 호스트를 관리중인 유저 아이디
    @Column(name = "member_id")
    private Long userId;

    // 초대 승락 여부
    private Boolean active = Boolean.FALSE;

    // 유저의 권한
    @Enumerated(EnumType.STRING)
    private GroupUserRole role = GroupUserRole.GUEST;

    public void setGroupUserRole(GroupUserRole role) {
        this.role = role;
    }

    public void activate() {
        if (this.active) throw AlreadyJoinedGroupException.EXCEPTION;
        this.active = true;
    }

    @Builder
    public GroupUser(Group group, Long userId, GroupUserRole role) {
        this.group = group;
        this.userId = userId;
        this.role = role;
    }
}
