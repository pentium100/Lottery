package com.itg.dao;


import javax.persistence.*;


@Entity
@Table(name = "Roles")
public class Role {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int rolesID;
	private String roleName;
	private String authObject;
	private String authValue;
	
	
	public int getRolesID() {
		return rolesID;
	}
	public void setRolesID(int rolesID) {
		this.rolesID = rolesID;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		roleName = roleName;
	}
	public String getAuthObject() {
		return authObject;
	}
	public void setAuthObject(String authObject) {
		authObject = authObject;
	}
	public String getAuthValue() {
		return authValue;
	}
	public void setAuthValue(String authValue) {
		authValue = authValue;
	}
	
}
