package com.wageul.wageul_server.user.service;

import com.wageul.wageul_server.mock.FakeAuthorizationUtil;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.mock.FakeUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    @Test
    void 회원정보_수정() {
        // given
        UserService userService = new UserService(new FakeUserRepository(), new FakeAuthorizationUtil(1L));
        User user = User.builder()
                .id(1)
                .email("ywonchae62@gmail.com")
                .profileImg("abc.png")
                .name("wonchae")
                .nationality("Korea")
                .introduce("")
                .build();
        UserUpdate userUpdate = UserUpdate.builder()
                .profileImg("haha.png")
                .nationality("Korea")
                .introduce("hello world~")
                .build();

        // when
        User updatedUser = userService.update(user, userUpdate);

        // then
        Assertions.assertThat(updatedUser.getProfileImg()).isEqualTo("haha.png");
        Assertions.assertThat(updatedUser.getNationality()).isEqualTo("Korea");
        Assertions.assertThat(updatedUser.getIntroduce()).isEqualTo("hello world~");
    }
}