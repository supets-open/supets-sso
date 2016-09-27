package com.supets.pet.threepartybase.utils;

import com.supets.pet.threepartybase.model.AuthToken;

public interface OauthListener {
	 void OauthSuccess(AuthToken obj);

	 void OauthFail();

	 void OauthCancel();
}
