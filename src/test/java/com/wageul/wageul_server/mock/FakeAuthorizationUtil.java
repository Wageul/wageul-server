package com.wageul.wageul_server.mock;

import com.wageul.wageul_server.oauth2.AuthorizationUtil;

public class FakeAuthorizationUtil implements AuthorizationUtil {
	private final long loginUserId;

	public FakeAuthorizationUtil(long loginUserId) {
		this.loginUserId = loginUserId;
	}

	@Override
	public long getLoginUserId() {
		return loginUserId;
	}
}
