package com.supets.pet.threepartybase.utils;

import com.supets.pet.threepartybase.model.AuthToken;
import com.supets.pet.threepartybase.model.AuthUser;

public interface OauthLoginListener {
     void OauthLoginSuccess(AuthToken token, AuthUser user);

	 void OauthLoginFail();

}