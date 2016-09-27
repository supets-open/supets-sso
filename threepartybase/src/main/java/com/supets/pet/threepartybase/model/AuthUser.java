package com.supets.pet.threepartybase.model;


import java.io.Serializable;

public class AuthUser implements Serializable {

	private static final long serialVersionUID = 1L;

	public int authtype;

	public int getAuthtype() {
		return authtype;
	}

	public void setAuthtype(int authtype) {
		this.authtype = authtype;
	}

}
