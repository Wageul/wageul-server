package com.wageul.wageul_server.user.service;

import com.wageul.wageul_server.mock.FakeAuthorizationUtil;
import com.wageul.wageul_server.oauth2.AuthorizationUtil;
import com.wageul.wageul_server.user.domain.User;
import com.wageul.wageul_server.user.dto.UserUpdate;
import com.wageul.wageul_server.mock.FakeUserRepository;
import com.wageul.wageul_server.user.service.port.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserService userService;
    private UserRepository fakeUserRepository;
    private FakeAuthorizationUtil fakeAuthorizationUtil;

    @BeforeEach
    void init() {
        fakeUserRepository = new FakeUserRepository();
        fakeAuthorizationUtil = new FakeAuthorizationUtil(1L);
        userService = new UserService(
            fakeUserRepository,
            fakeAuthorizationUtil);
    }

    @Test
    void 회원정보_수정() {
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
        User updatedUser = userService.update(user, userUpdate);

        // then
        Assertions.assertThat(updatedUser.getProfileImg()).isEqualTo("abc.png");
        Assertions.assertThat(updatedUser.getNationality()).isEqualTo("Korea");
        Assertions.assertThat(updatedUser.getIntroduce()).isEqualTo("hello world~");
    }

    @Test
    void 로그인한_회원정보_가져오기() {
        // given
        User user = User.builder()
            .id(1)
            .email("ywonchae62@gmail.com")
            .profileImg("abc.png")
            .name("wonchae")
            .nationality("Korea")
            .introduce("")
            .build();

        fakeUserRepository.save(user);

        // when
        User myInfo = userService.getMyInfo(1L);

        // then
        Assertions.assertThat(myInfo.getEmail()).isEqualTo("ywonchae62@gmail.com");
        Assertions.assertThat(myInfo.getProfileImg()).isEqualTo("abc.png");
        Assertions.assertThat(myInfo.getName()).isEqualTo("wonchae");
        Assertions.assertThat(myInfo.getNationality()).isEqualTo("Korea");
        Assertions.assertThat(myInfo.getIntroduce()).isEqualTo("");
    }

    @Test
    void 회원삭제() {
        // given
        User user = User.builder()
                .id(1)
                .email("ywonchae62@gmail.com")
                .profileImg("abc.png")
                .name("wonchae")
                .nationality("Korea")
                .introduce("")
                .build();

        fakeUserRepository.save(user);

        // when
        userService.deleteById(user.getId());

        // then
        Assertions.assertThat(userService.getById(user.getId())).isEqualTo(null);
    }

    @Test
    void 회원삭제는_본인만() {
        // given
        User user = User.builder()
                .id(1)
                .email("ywonchae62@gmail.com")
                .profileImg("abc.png")
                .name("wonchae")
                .nationality("Korea")
                .introduce("")
                .build();

        fakeUserRepository.save(user);
        fakeAuthorizationUtil.setLoginUserId(2L);

        // when

        // then
        Assertions.assertThatThrownBy(
                () -> userService.deleteById(user.getId())
        ).hasMessage("ONLY LOGIN USER CAN DELETE HIMSELF");
    }
}