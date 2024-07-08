package com.wageul.wageul_server.participation.dto;

import com.wageul.wageul_server.user.dto.UserSimpleProfileDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipationUserResponse {
    private final long participationId;
    private final UserSimpleProfileDto userProfile;
}
