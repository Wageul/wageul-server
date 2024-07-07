package com.wageul.wageul_server.review.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wageul.wageul_server.mock.FakeAuthorizationUtil;
import com.wageul.wageul_server.mock.FakeReviewRepository;
import com.wageul.wageul_server.mock.FakeUserRepository;
import com.wageul.wageul_server.review.domain.Review;
import com.wageul.wageul_server.review.dto.ReviewCreate;
import com.wageul.wageul_server.user.domain.User;

import java.util.List;

class ReviewServiceTest {

	ReviewService reviewService;
	FakeUserRepository userRepository;
	FakeReviewRepository reviewRepository;
	FakeAuthorizationUtil authorizationUtil;

	long userId = 1L;

	@BeforeEach
	void init() {
		userRepository = new FakeUserRepository();
		reviewRepository = new FakeReviewRepository();
		authorizationUtil = new FakeAuthorizationUtil(userId);
		reviewService = new ReviewService(
			userRepository,
			reviewRepository,
			authorizationUtil
		);
	}

	@Test
	void 리뷰작성() {
		// given
		User user1 = User.builder()
			.id(userId)
			.email("abc@gmail.com")
			.build();
		userRepository.save(user1);

		User user2 = User.builder()
			.id(2L)
			.email("efg@gmail.com")
			.build();
		userRepository.save(user2);

		ReviewCreate reviewCreate = new ReviewCreate(
			user2.getId(), "this is review", 5
		);

		// when
		Review review = reviewService.create(reviewCreate);

		// then
		Assertions.assertThat(review.getWriter()).isEqualTo(user1);
		Assertions.assertThat(review.getTarget()).isEqualTo(user2);
		Assertions.assertThat(review.getContent()).isEqualTo("this is review");
		Assertions.assertThat(review.getRate()).isEqualTo(5);
	}

	@Test
	void 리뷰삭제() {
		// given
		User user1 = User.builder()
			.id(userId)
			.email("abc@gmail.com")
			.build();
		userRepository.save(user1);

		User user2 = User.builder()
			.id(2L)
			.email("efg@gmail.com")
			.build();
		userRepository.save(user2);

		ReviewCreate reviewCreate = new ReviewCreate(
			user2.getId(), "this is review", 5
		);

		Review review = reviewService.create(reviewCreate);

		// when
		reviewService.delete(review.getId());

		// then
		Assertions.assertThat(reviewService.findByTargetId(user2.getId()).size()).isEqualTo(0);
	}

	@Test
	void 리뷰삭제는_본인만_가능() {
		// given
		User user1 = User.builder()
			.id(userId)
			.email("abc@gmail.com")
			.build();
		userRepository.save(user1);

		User user2 = User.builder()
			.id(2L)
			.email("efg@gmail.com")
			.build();
		userRepository.save(user2);

		ReviewCreate reviewCreate = new ReviewCreate(
			user1.getId(), "this is review", 5
		);

		Review review = reviewService.create(reviewCreate);

		// when
		authorizationUtil.setLoginUserId(2L);

		// then
		Assertions.assertThatThrownBy(
			() -> reviewService.delete(review.getId())
		).hasMessage("WRITER IS NOT EQUAL TO LOGIN USER");
	}

	@Test
	void 회원별_리뷰읽기() {
		// given
		User user1 = User.builder()
			.id(userId)
			.email("abc@gmail.com")
			.build();
		userRepository.save(user1);

		User user2 = User.builder()
			.id(2L)
			.email("efg@gmail.com")
			.build();
		userRepository.save(user2);

		User user3 = User.builder()
			.id(3L)
			.email("jkl@gmail.com")
			.build();
		userRepository.save(user3);

		// user1이 user2에게 후기 작성
		ReviewCreate reviewCreate1 = new ReviewCreate(
			user2.getId(), "this is review", 5
		);

		Review review1 = reviewService.create(reviewCreate1);

		authorizationUtil.setLoginUserId(3L);

		// user3이 user2에게 후기 작성
		ReviewCreate reviewCreate2 = new ReviewCreate(
			user2.getId(), "this is another review", 3
		);

		Review review2 = reviewService.create(reviewCreate2);

		// when
		List<Review> reviews = reviewService.findByTargetId(user2.getId());
		Double avg = reviewService.getRate(reviews);

		// then
		Assertions.assertThat(avg).isEqualTo(4);
		Assertions.assertThat(reviews.size()).isEqualTo(2);
		Assertions.assertThat(reviews.get(0).getContent()).isEqualTo("this is review");
		Assertions.assertThat(reviews.get(0).getRate()).isEqualTo(5);
		Assertions.assertThat(reviews.get(1).getContent()).isEqualTo("this is another review");
		Assertions.assertThat(reviews.get(1).getRate()).isEqualTo(3);
	}
}