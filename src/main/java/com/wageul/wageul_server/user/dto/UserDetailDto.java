package com.wageul.wageul_server.user.dto;

import com.wageul.wageul_server.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailDto {
    private User user;
    private Long createdExCnt;
    private Long joinedPtCnt;

    @Builder
    public UserDetailDto(User user, Long createdExCnt, Long joinedPtCnt) {
        this.user = user;
        this.createdExCnt = createdExCnt;
        this.joinedPtCnt = joinedPtCnt;
    }
}
