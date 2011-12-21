package com.itg.dao;

import java.util.List;

public interface IUserRolesDAO {

	public abstract void insertUserRole(UserRole userRole);

	public abstract List<UserRole> findRolesByID(String userName);

}