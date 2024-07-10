package com.wageul.wageul_server.user.domain;

import com.wageul.wageul_server.user.dto.UserUpdate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void 상세정보_업데이트() {
        // given
        User user = User.builder()
                .id(1)
                .email("ywonchae62@gmail.com")
                .profileImg("abc.png")
                .name("wonchae")
                .nationality("Korea")
                .introduce("")
                .build();
        UserUpdate userUpdate = UserUpdate.builder()
                .nationality("Korea")
                .introduce("hello world~")
                .build();
        // when
        user = user.update(userUpdate);

        // then
        Assertions.assertThat(user.getProfileImg()).isEqualTo("abc.png");
        Assertions.assertThat(user.getNationality()).isEqualTo("Korea");
        Assertions.assertThat(user.getIntroduce()).isEqualTo("hello world~");
    }
}