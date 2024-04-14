package com.ex.ticket.group.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupUserRole {
    // 마스터 (모든 권한)
    MASTER("MASTER", "마스터"),
    // 슈퍼 호스트 (조회, 변경 가능)
    MANAGER("MANAGER", "매니저"),
    // 일반 호스트 (조회만 가능)
    GUEST("GUEST", "게스트");

    private final String name;
    @JsonValue
    private final String value;

    // Enum Validation 을 위한 코드, enum 에 속하지 않으면 null 리턴
    @JsonCreator
    public static GroupUserRole fromGroupUserRole(String val) {
        for (GroupUserRole groupUserRole : GroupUserRole.values()) {
            if (groupUserRole.name().equals(val)) {
                return groupUserRole;
            }
        }
        return null;
    }
}
