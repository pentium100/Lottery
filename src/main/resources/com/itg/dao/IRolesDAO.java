package com.itg.dao;

import java.util.List;

public interface IRolesDAO {

	public abstract void insertRole(Role role);

	public abstract List<Role> findRolesByRole(String role, String authObj);

	@SuppressWarnings("unchecked")
	public abstract List<String> findAuthValue(List<String> role,
			String authObj, List<String> authValue);

}