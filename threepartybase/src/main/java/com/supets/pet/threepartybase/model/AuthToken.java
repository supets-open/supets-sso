package com.supets.pet.threepartybase.model;

import java.io.Serializable;

public class AuthToken implements Serializable{

	public int authtype;

	public int getAuthtype() {
		return authtype;
	}

	public void setAuthtype(int authtype) {
		this.authtype = authtype;
	}

}
