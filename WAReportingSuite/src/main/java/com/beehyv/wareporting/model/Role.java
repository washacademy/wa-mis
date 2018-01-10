package com.beehyv.wareporting.model;

import javax.persistence.*;

@Entity
@Table(name="user_role")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id", columnDefinition = "INT(11)")
	private Integer roleId;
	
	@Column(name="role_desc", columnDefinition = "VARCHAR(255)")
	private String roleDescription;

	@ManyToOne
	@JoinColumn(name="permission_id", columnDefinition = "INT(11)")
	private Permissions permissionId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Permissions getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Permissions permissionId) {
		this.permissionId = permissionId;
	}
}
