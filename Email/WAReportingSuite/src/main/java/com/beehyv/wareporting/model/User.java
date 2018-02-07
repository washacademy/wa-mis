package com.beehyv.wareporting.model;

import com.beehyv.wareporting.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user_details")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", columnDefinition = "INT(11)")
	private Integer userId;

	@Column(name="username", columnDefinition = "VARCHAR(25)")
	private String username;

	@Column(name="password", columnDefinition = "VARCHAR(255)")
	private String password;

	@Column(name="full_name", columnDefinition = "VARCHAR(255)")
	private String fullName;

	@Column(name="phone_no", columnDefinition = "BIGINT(20)")
	private String phoneNumber;

	@Column(name="email_id", columnDefinition = "VARCHAR(45)")
	private String emailId;

//	@ManyToOne
//	@JoinColumn(name="location",insertable = false,updatable = false)
//	private Location locationId;

	@Column(name="state_id",columnDefinition = "TINYINT(4)")
	private Integer stateId;

	@Column(name="district_id", columnDefinition = "SMALLINT(6)")
	private Integer districtId;

	@Column(name="block_id", columnDefinition = "INT(11)")
	private Integer blockId;

	@Column(name="panchayat_id", columnDefinition = "INT(11)")
	private Integer panchayatId;

    @Column(name="circle_id", columnDefinition = "INT(11)")
    private Integer circleId;

	@Column(name="creation_date", columnDefinition = "DATETIME")
	private Date creationDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="created_by_user", columnDefinition = "VARCHAR(45)")
	private User createdByUser;

//	@OneToMany(mappedBy="createdByUser")
//	@LazyCollection(LazyCollectionOption.FALSE)
//	@JsonIgnore
//	private Set<User> createdUsers = new HashSet<>();

	@Column(name="role_id", columnDefinition = "INT(11)")
	private Integer roleId;

	@Column(name="account_status", columnDefinition = "VARCHAR(255)")
	private String accountStatus = AccountStatus.ACTIVE.getAccountStatus();

	@Column(name="access_level", columnDefinition = "VARCHAR(45)")
	private  String accessLevel;

	@Column(name = "state_name", columnDefinition = "VARCHAR(45)")
	private  String stateName;

	@Column(name = "district_name", columnDefinition = "VARCHAR(45)")
	private String districtName;

	@Column(name = "block_name", columnDefinition = "VARCHAR(45)")
	private String blockName;

    @Column(name = "panchayat_name", columnDefinition = "VARCHAR(45)")
    private String panchayatName;

    @Column(name = "circle_name", columnDefinition = "VARCHAR(45)")
    private String circleName;

	@Column(name = "role_name", columnDefinition = "VARCHAR(45)")
	private String roleName;

	@Column(name = "isLoggedOnce", columnDefinition = "BIT(1)")
	private Boolean loggedAtLeastOnce;

	@Column(name = "isPasswordDefault", columnDefinition = "BIT(1)")
	private Boolean isDefault;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getLoggedAtLeastOnce() {
		return loggedAtLeastOnce;
	}

	public void setLoggedAtLeastOnce(Boolean loggedAtLeastOnce) {
		this.loggedAtLeastOnce = loggedAtLeastOnce;
	}

	public Boolean getDefault() {
		return isDefault;
	}

	public void setDefault(Boolean aDefault) {
		isDefault = aDefault;
	}

    public Integer getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(Integer panchayatId) {
        this.panchayatId = panchayatId;
    }

    public Integer getCircleId() {
        return circleId;
    }

    public void setCircleId(Integer circleId) {
        this.circleId = circleId;
    }

    public String getPanchayatName() {
        return panchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }
}