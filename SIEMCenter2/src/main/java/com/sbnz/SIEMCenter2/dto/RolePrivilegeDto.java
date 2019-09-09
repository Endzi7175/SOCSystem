package com.sbnz.SIEMCenter2.dto;

public class RolePrivilegeDto {
String privilege;
String role;
public String getPrivilege() {
	return privilege;
}
public void setPrivilege(String privilege) {
	this.privilege = privilege;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public RolePrivilegeDto(String privilege, String role) {
	super();
	this.privilege = privilege;
	this.role = role;
}
public RolePrivilegeDto() {
	super();
}

}
