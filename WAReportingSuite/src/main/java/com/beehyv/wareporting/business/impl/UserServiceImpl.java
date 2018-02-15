package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.UserService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.entity.ContactInfo;
import com.beehyv.wareporting.entity.ForgotPasswordDto;
import com.beehyv.wareporting.entity.PasswordDto;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.enums.AccessType;
import com.beehyv.wareporting.enums.AccountStatus;
import com.beehyv.wareporting.enums.ModificationType;
import com.beehyv.wareporting.model.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by beehyv on 15/3/17.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PanchayatDao panchayatDao;

    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AggregateCumulativeWADao aggregateCumulativeWADao;

    private Role getAdminRole(){
        return roleDao.findByRoleDescription(AccessType.ADMIN.getAccessType()).get(0);
    }

    @Override
    public User findUserByUserId(Integer userId) {
        return userDao.findByUserId(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User findUserByEmailId(String emailId) {
        return userDao.findByEmailId(emailId);
    }

    @Override
    public User getCurrentUser() {
        final Integer currentUserId = (Integer) SecurityUtils.getSubject().getPrincipal();
        if(currentUserId != null) {
            return findUserByUserId(currentUserId);
        } else {
            return null;
        }
    }

    @Override
    public List<User> findUsersByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<User> findUsersByCreationDate(Date creationDate) {
        return userDao.findByCreationDate(creationDate);
    }

    @Override
    public List<User> findAllActiveUsers() {
        return userDao.getActiveUsers();
    }

    @Override
    public List<User> findAllActiveUsersByRole(Integer roleId) {
        return userDao.getUsersByRole(roleDao.findByRoleId(roleId));
    }

    @Override
    public List<User> findUsersByAccountStatus(String accountStatus) {
        return userDao.getUsersByAccountStatus(accountStatus);
    }

    @Override
    public List<User> findMyUsers(User currentUser) {

        String accessLevel = currentUser.getAccessLevel();
        if(accessLevel.equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
            return userDao.getUsersByLocation("stateId", currentUser.getStateId());
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())){
            return userDao.getUsersByLocation("districtId", currentUser.getDistrictId());
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.BLOCK.getAccessLevel())){
            return userDao.getUsersByLocation("blockId", currentUser.getBlockId());
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            return userDao.getActiveUsers();
        }
        else
            return new ArrayList<>();
    }

    @Override
    public Map<Integer, String> createNewUser(User user) {
        User currentUser = getCurrentUser();
        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<Integer, String>();

        if (user.getUsername().isEmpty()) {
            String userNameError = "Please specify the username for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        if (userDao.findByUserName(user.getUsername()) != null) {
            String userNameError = "Username already exists.";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        String userPhone = user.getPhoneNumber();
        String regexStr1 = "^[0-9]*$";
        String regexStr2 = "^[0-9]{10}$";
        if (userPhone.isEmpty()) {
            String userNameError = "Please specify the phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        else if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
            String userNameError = "Please check the format of phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        if (user.getEmailId().isEmpty()) {
            String userNameError = "Please specify the Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        String EWAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EWAIL_PATTERN);
        Matcher matcher = pattern.matcher(user.getEmailId());
        if (!matcher.matches()){
            String userNameError = "Please enter the valid Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

//        System.out.println(user.getAccessLevel());
//        System.out.println(AccessLevel.BLOCK.name());
        if (!AccessLevel.isLevel(user.getAccessLevel())) {
            String userNameError = "Please specify the access level for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        if(roleDao.findByRoleId(currentUser.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.USER.getAccessType())) {
            String authorityError = "No authority : Not an admin";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        if(AccessLevel.getLevel(currentUser.getAccessLevel()).ordinal() > AccessLevel.getLevel(user.getAccessLevel()).ordinal()){
            String authorityError = "No authority : not in scope";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }


        if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())) {
            if (roleDao.findByRoleId(user.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                User nationalAdmin = userDao.getNationalAdmin(getAdminRole());
                if (nationalAdmin != null) {
                    String authorityError = "Admin exists at this level";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            user.setStateId(null);
            user.setDistrictId(null);
            user.setBlockId(null);
        }
        else if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())) {
            if (roleDao.findByRoleId(user.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                User stateAdmin = userDao.getStateAdmin(getAdminRole(), stateDao.findByStateId(user.getStateId()));
                if (stateAdmin != null) {
                    String authorityError = "Admin exists in this state";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }

            user.setDistrictId(null);
            user.setBlockId(null);
        }
        else if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())) {
            if (roleDao.findByRoleId(user.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                User districtAdmin = userDao.getDistrictAdmin(getAdminRole(), districtDao.findByDistrictId(user.getDistrictId()));
                if (districtAdmin != null) {
                    String authorityError = "Admin exists in this district";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getDistrictId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getDistrictId() != null && !user.getDistrictId().equals(currentUser.getDistrictId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
//            if(!user.getDistrictId().getStateOfDistrict().equals(user.getStateId())) {
//                String authorityError = "invalid property: district";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
            user.setBlockId(null);
        }
        else {
            if (roleDao.findByRoleId(user.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                String authorityError = "Cannot create admin here";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getDistrictId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getDistrictId() != null && !user.getDistrictId().equals(currentUser.getDistrictId())) {
                String authorityError = "invalid property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getBlockId() == null) {
                String authorityError = "missing property: block";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
//
//            if(!user.getDistrictId().getStateOfDistrict().equals(user.getStateId())) {
//                String authorityError = "invalid property: district";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
//            if(!user.getBlockId().getStateOfBlock().equals(user.getStateId())) {
//                String authorityError = "invalid property: block";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
        }

        user.setPassword(passwordEncoder.encode(user.getPhoneNumber()));
        user.setCreationDate(new Date());
        user.setCreatedByUser(currentUser);
        user.setAccountStatus(AccountStatus.ACTIVE.getAccountStatus());
        user.setDefault(true);
        user.setLoggedAtLeastOnce(false);
        userDao.saveUser(user);
        String authorityError = "User Created";
        responseMap.put(rowNum, authorityError);
        return responseMap;
    }

    @Override
    public Map<Integer, String> updateContacts(ContactInfo contactInfo) {
        User user = userDao.findByUserId(contactInfo.getUserId());
        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<Integer, String>();

        User currentUser = getCurrentUser();

        if(user == null){
            responseMap.put(rowNum, "User does not exist");
            return responseMap;
        }
        if(currentUser == null || !(currentUser.getUserId().equals(user.getUserId()))){
            responseMap.put(rowNum, "Unauthorised");
            return responseMap;
        }

        String userPhone = contactInfo.getPhoneNumber();

        String regexStr1 = "^[0-9]*$";
        String regexStr2 = "^[0-9]{10}$";
        if (userPhone.isEmpty()) {
            String userNameError = "Please specify the phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        else if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
            String userNameError = "Please check the format of phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }


        String emailId = contactInfo.getEmail();

        if (emailId.isEmpty()) {
            String userNameError = "Please specify the Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        String EWAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EWAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailId);
        if (!matcher.matches()){
            String userNameError = "Please enter the valid Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        user.setPhoneNumber(contactInfo.getPhoneNumber());
        user.setEmailId(contactInfo.getEmail());

        responseMap.put(rowNum, "Contacts Updated");
        return responseMap;
    }

    @Override
    public Map<Integer, String> updateExistingUser(User user) {
        User entity = userDao.findByUserId(user.getUserId());
        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<Integer, String>();

        User currentUser = getCurrentUser();

        if(!(roleDao.findByRoleId(currentUser.getRoleId()).getRoleId() == 1 || entity.getCreatedByUser().getUserId().equals(currentUser.getUserId()))){
            String authorityError = "No authority : this user was created by someone else";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        if(roleDao.findByRoleId(currentUser.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.USER.getAccessType())) {
            String authorityError = "No authority : not an admin";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        if(entity == null) {
            responseMap.put(rowNum, "invalid user");
            return responseMap;
        }

        if (user.getFullName().isEmpty()) {
            String userNameError = "Please specify the Full name for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        String userPhone = user.getPhoneNumber();
        String regexStr1 = "^[0-9]*$";
        String regexStr2 = "^[0-9]{10}$";
        if (userPhone.isEmpty()) {
            String userNameError = "Please specify the phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        else if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
            String userNameError = "Please check the format of phone number for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        if (user.getEmailId().isEmpty()) {
            String userNameError = "Please specify the Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }
        String EWAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EWAIL_PATTERN);
        Matcher matcher = pattern.matcher(user.getEmailId());
        if (!matcher.matches()){
            String userNameError = "Please enter the valid Email for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        entity.setFullName(user.getFullName());
        entity.setEmailId(user.getEmailId());
        entity.setPhoneNumber(user.getPhoneNumber());

        if (!AccessLevel.isLevel(user.getAccessLevel())) {
            String userNameError = "Please specify the access level for user";
            responseMap.put(rowNum, userNameError);
            return responseMap;
        }

        if(AccessLevel.getLevel(currentUser.getAccessLevel()).ordinal() > AccessLevel.getLevel(user.getAccessLevel()).ordinal()){
            String authorityError = "No authority : out of scope";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        entity.setRoleId(user.getRoleId());
        entity.setAccessLevel(user.getAccessLevel());

        if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())) {
            User nationalAdmin = userDao.getNationalAdmin(getAdminRole());
            if (roleDao.findByRoleId(user.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                if (nationalAdmin != null && !nationalAdmin.getUserId().equals(user.getUserId())) {
                    String authorityError = "Admin exists at this level";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            user.setStateId(null);
            user.setDistrictId(null);
            user.setBlockId(null);
        }
        else if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())) {
            User stateAdmin = userDao.getStateAdmin(getAdminRole(), stateDao.findByStateId(user.getStateId()));
            if (roleDao.findByRoleId(user.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                if (stateAdmin != null && !stateAdmin.getUserId().equals(user.getUserId())) {
                    String authorityError = "Admin exists in this State";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }

            user.setDistrictId(null);
            user.setBlockId(null);
        }
        else if (user.getAccessLevel().equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())) {
            User districtAdmin = userDao.getDistrictAdmin(getAdminRole(), districtDao.findByDistrictId(user.getDistrictId()));
            if (districtAdmin != null && !districtAdmin.getUserId().equals(user.getUserId())) {
                if (roleDao.findByRoleId(user.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                    String authorityError = "Admin exists at this District";
                    responseMap.put(rowNum, authorityError);
                    return responseMap;
                }
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getDistrictId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getDistrictId() != null && !user.getDistrictId().equals(currentUser.getDistrictId())) {
                String authorityError = "invalid property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
//            if(!user.getDistrictId().getStateOfDistrict().equals(user.getStateId())) {
//                String authorityError = "invalid property: district";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
            user.setBlockId(null);
        }
        else {
            if (roleDao.findByRoleId(user.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.ADMIN.getAccessType())) {
                String authorityError = "Cannot create admin here";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getStateId() == null) {
                String authorityError = "missing property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getStateId() != null && !user.getStateId().equals(currentUser.getStateId())) {
                String authorityError = "invalid property: state";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getDistrictId() == null) {
                String authorityError = "missing property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(currentUser.getDistrictId() != null && !user.getDistrictId().equals(currentUser.getDistrictId())) {
                String authorityError = "invalid property: district";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
            if(user.getBlockId() == null) {
                String authorityError = "missing property: block";
                responseMap.put(rowNum, authorityError);
                return responseMap;
            }
//
//            if(!user.getDistrictId().getStateOfDistrict().equals(user.getStateId())) {
//                String authorityError = "invalid property: district";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
//            if(!user.getBlockId().getStateOfBlock().equals(user.getStateId())) {
//                String authorityError = "invalid property: block";
//                responseMap.put(rowNum, authorityError);
//                return responseMap;
//            }
        }

        entity.setStateId(user.getStateId());
        entity.setDistrictId(user.getDistrictId());
        entity.setBlockId(user.getBlockId());

        responseMap.put(rowNum, "User Updated");
        return responseMap;
    }

    @Override
    public Map<Integer, String> updatePassword(PasswordDto passwordDto) {
        User currentUser = getCurrentUser();
        User entity = userDao.findByUserId(passwordDto.getUserId());

        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<>();

        if(roleDao.findByRoleId(currentUser.getRoleId()).getRoleDescription().equalsIgnoreCase(AccessType.USER.getAccessType())) {
            String authorityError = "No authority";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }

        if(entity == null) {
            responseMap.put(rowNum, "User not found");
            return responseMap;
        }

        if(!(entity.getCreatedByUser().getUserId().equals(currentUser.getUserId())) &&
                !roleDao.findByRoleId(currentUser.getRoleId()).getRoleDescription().equals(AccessType.MASTER_ADMIN.getAccessType())){
            String authorityError = "Reset not allowed";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }
        entity.setPassword(passwordEncoder.encode(entity.getPhoneNumber()));
        entity.setDefault(true);

        responseMap.put(rowNum, "Password changed successfully");
        return responseMap;
    }

    @Override
    public  void saveUser(User user){
        userDao.saveUser(user);
    }

    @Override
    public void setLoggedIn(){
        User currentUser = getCurrentUser();
        currentUser.setLoggedAtLeastOnce(true);
    }

    @Override
    public Map<Integer, String> changePassword(PasswordDto passwordDto) {

        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<>();

        User currentUser = getCurrentUser();

        if(currentUser == null){
            responseMap.put(rowNum, "Not logged in");
            return responseMap;
        }

        User entity = userDao.findByUserId(passwordDto.getUserId());

        if(entity == null){
            responseMap.put(rowNum, "User not found");
            return responseMap;
        }

        if(!entity.getUserId().equals(currentUser.getUserId())){
            responseMap.put(rowNum, "Unauthorised");
            return responseMap;
        }

        System.out.println(passwordDto.getNewPassword());
        System.out.println(currentUser.getPassword());
        if(!passwordEncoder.matches(passwordDto.getOldPassword(), currentUser.getPassword())){
            String authorityError = "Current Password is incorrect";
            responseMap.put(rowNum, authorityError);
            return responseMap;
        }
        currentUser.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        currentUser.setDefault(false);
        String success="Password changed successfully";
        responseMap.put(rowNum, success);
        return responseMap;
    }

    @Override
    public Map<Integer, String> forgotPasswordCredentialChecker(ForgotPasswordDto forgotPasswordDto){

        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<>();

        User entity = userDao.findByUserName(forgotPasswordDto.getUsername());

        if(entity == null){
            responseMap.put(rowNum, "User Details incorrect");
            return responseMap;
        }

        if(entity.getPhoneNumber().equals(forgotPasswordDto.getPhoneNumber())){
            entity.setPassword(passwordEncoder.encode(forgotPasswordDto.getConfirmPassword()));
            String success="Password changed successfully";
            responseMap.put(rowNum, success);
            return responseMap;

        }
        else{
            String failure="User Details incorrect";
            responseMap.put(rowNum, failure);
            return responseMap;
        }


    }

    @Override
    public Map deleteExistingUser(Integer userId) {
        User entity = userDao.findByUserId(userId);
        Integer rowNum = 0;
        Map<Integer, String> responseMap = new HashMap<>();
        if((entity != null) && (getCurrentUser().equals(entity.getCreatedByUser())) && !(entity.getLoggedAtLeastOnce())) {
            entity.setAccountStatus(AccountStatus.INACTIVE.getAccountStatus());
            responseMap.put(rowNum,"User deleted");
        }
        else
            responseMap.put(rowNum,"Cannot delete User");
        return responseMap;
    }

    @Override
    public boolean isUsernameUnique(String username, Integer userId) {
        User user = userDao.findByUserName(username);
        return (user == null || ((userId != null) && (user.getUserId().equals(userId))));
    }

    @Override
    public String createMaster() {
        User user = new User();
        user.setUsername("master");
        user.setPassword(passwordEncoder.encode("beehyv123"));
        user.setFullName("MasterNHM");
        user.setRoleId(roleDao.findByRoleDescription(AccessType.MASTER_ADMIN.getAccessType()).get(0).getRoleId());
        user.setAccessLevel(AccessLevel.NATIONAL.getAccessLevel());
        user.setPhoneNumber("9885200112");
        user.setEmailId("sairahul@beehyv.com");
        user.setAccountStatus(AccountStatus.ACTIVE.getAccountStatus());
        user.setCreationDate(new Date());
        user.setCreatedByUser(null);
        user.setStateId(null);
        user.setDistrictId(null);
        user.setBlockId(null);

        userDao.saveUser(user);

        return "master created";
    }

    @Override
    public Role getRoleById(Integer roleId) {
        return roleDao.findByRoleId(roleId);
    }

    @Override
    public void TrackModifications(User oldUser, User newUser){
        if(!oldUser.getFullName().equals(newUser.getFullName())){
            ModificationTracker modificationTracker=new ModificationTracker();
            modificationTracker.setModifiedField("full_name");
            modificationTracker.setModificationDate(new Date());
            modificationTracker.setModificationType(ModificationType.UPDATE.getModificationType());
            modificationTracker.setModifiedByUserId(getCurrentUser().getUserId());
            modificationTracker.setModifiedUserId(oldUser.getUserId());
            modificationTracker.setNewValue(newUser.getFullName());
            modificationTracker.setPreviousValue(oldUser.getFullName());
            modificationTrackerDao.saveModification(modificationTracker);
        }

        if(!oldUser.getPhoneNumber().equals(newUser.getPhoneNumber())){
            ModificationTracker modificationTracker=new ModificationTracker();
            modificationTracker.setModifiedField("phone_no");
            modificationTracker.setModificationDate(new Date());
            modificationTracker.setModificationType(ModificationType.UPDATE.getModificationType());
            modificationTracker.setModifiedByUserId(getCurrentUser().getUserId());
            modificationTracker.setModifiedUserId(oldUser.getUserId());
            modificationTracker.setNewValue(newUser.getPhoneNumber());
            modificationTracker.setPreviousValue(oldUser.getPhoneNumber());
            modificationTrackerDao.saveModification(modificationTracker);
        }

        if(!oldUser.getEmailId().equals(newUser.getEmailId())){
            ModificationTracker modificationTracker=new ModificationTracker();
            modificationTracker.setModifiedField("email_id");
            modificationTracker.setModificationDate(new Date());
            modificationTracker.setModificationType(ModificationType.UPDATE.getModificationType());
            modificationTracker.setModifiedByUserId(getCurrentUser().getUserId());
            modificationTracker.setModifiedUserId(oldUser.getUserId());
            modificationTracker.setNewValue(newUser.getEmailId());
            modificationTracker.setPreviousValue(oldUser.getEmailId());
            modificationTrackerDao.saveModification(modificationTracker);
        }

        if(oldUser.getStateId() != null && newUser.getStateId() != null && !oldUser.getStateId().equals(newUser.getStateId())){
            ModificationTracker modificationTracker=new ModificationTracker();
            modificationTracker.setModifiedField("state");
            modificationTracker.setModificationDate(new Date());
            modificationTracker.setModificationType(ModificationType.UPDATE.getModificationType());
            modificationTracker.setModifiedByUserId(getCurrentUser().getUserId());
            modificationTracker.setModifiedUserId(oldUser.getUserId());
            modificationTracker.setNewValue(newUser.getStateId().toString());
            modificationTracker.setPreviousValue(oldUser.getStateId().toString());
            modificationTrackerDao.saveModification(modificationTracker);
        }

        if(oldUser.getDistrictId() != null && oldUser.getDistrictId() != null && !oldUser.getDistrictId().equals(newUser.getDistrictId())){
            ModificationTracker modificationTracker=new ModificationTracker();
            modificationTracker.setModifiedField("district");
            modificationTracker.setModificationDate(new Date());
            modificationTracker.setModificationType(ModificationType.UPDATE.getModificationType());
            modificationTracker.setModifiedByUserId(getCurrentUser().getUserId());
            modificationTracker.setModifiedUserId(oldUser.getUserId());
            modificationTracker.setNewValue(newUser.getDistrictId().toString());
            modificationTracker.setPreviousValue(oldUser.getDistrictId().toString());
            modificationTrackerDao.saveModification(modificationTracker);
        }

        if( oldUser.getBlockId()!=null && newUser.getBlockId()!=null && !oldUser.getBlockId().equals(newUser.getBlockId())){
            ModificationTracker modificationTracker=new ModificationTracker();
            modificationTracker.setModifiedField("block");
            modificationTracker.setModificationDate(new Date());
            modificationTracker.setModificationType(ModificationType.UPDATE.getModificationType());
            modificationTracker.setModifiedByUserId(getCurrentUser().getUserId());
            modificationTracker.setModifiedUserId(oldUser.getUserId());
            modificationTracker.setNewValue(newUser.getBlockId().toString());
            modificationTracker.setPreviousValue(oldUser.getBlockId().toString());
            modificationTrackerDao.saveModification(modificationTracker);
        }

        if(!oldUser.getRoleId().equals(newUser.getRoleId())){
            ModificationTracker modificationTracker=new ModificationTracker();
            modificationTracker.setModifiedField("role_id");
            modificationTracker.setModificationDate(new Date());
            modificationTracker.setModificationType(ModificationType.UPDATE.getModificationType());
            modificationTracker.setModifiedByUserId(getCurrentUser().getUserId());
            modificationTracker.setModifiedUserId(oldUser.getUserId());
            modificationTracker.setNewValue(newUser.getRoleId().toString());
            modificationTracker.setPreviousValue(oldUser.getRoleId().toString());
            modificationTrackerDao.saveModification(modificationTracker);
        }

        if(!oldUser.getAccessLevel().equals(newUser.getAccessLevel())){
            ModificationTracker modificationTracker=new ModificationTracker();
            modificationTracker.setModifiedField("access_level");
            modificationTracker.setModificationDate(new Date());
            modificationTracker.setModificationType(ModificationType.UPDATE.getModificationType());
            modificationTracker.setModifiedByUserId(getCurrentUser().getUserId());
            modificationTracker.setModifiedUserId(oldUser.getUserId());
            modificationTracker.setNewValue(newUser.getAccessLevel());
            modificationTracker.setPreviousValue(oldUser.getAccessLevel());
            modificationTrackerDao.saveModification(modificationTracker);
        }

    }


}
