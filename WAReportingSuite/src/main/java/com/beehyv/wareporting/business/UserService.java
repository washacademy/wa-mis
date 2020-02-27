package com.beehyv.wareporting.business;

import com.beehyv.wareporting.entity.ContactInfo;
import com.beehyv.wareporting.entity.ForgotPasswordDto;
import com.beehyv.wareporting.entity.PasswordDto;
import com.beehyv.wareporting.model.Role;
import com.beehyv.wareporting.model.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by beehyv on 14/3/17.
 */
public interface UserService {
    User findUserByUserId(Integer userId);

    User findUserByUsername(String username);

    User getCurrentUser();

    void saveUser(User user);

    void setLoggedIn();

    User findUserByEmailId(String emailId);

    List<User> findUsersByPhoneNumber(String phoneNumber);

    List<User> findUsersByCreationDate(Date creationDate);

    List<User> findAllActiveUsers();

    List<User> findAllActiveUsersByRole(Integer roleId);

    List<User> findUsersByAccountStatus(String accountStatus);

    List<User> findMyUsers(User currentUser);

    Map<Integer, String> createNewUser(User user);

    Map<Integer, String> updateExistingUser(User user);

    Map<Integer, String> updatePassword(PasswordDto passwordDto);

    Map<Integer, String> updateContacts(ContactInfo contactInfo);

    Map<Integer, String> deleteExistingUser(Integer userId);

    boolean isUsernameUnique(String username, Integer userId);

    String createMaster();

    Map<Integer, String> changePassword(PasswordDto changePasswordDTO) throws Exception;

    Role getRoleById(Integer roleId);

    void TrackModifications(User oldUser, User newUser);

    Map<Integer, String> forgotPasswordCredentialChecker(ForgotPasswordDto forgotPasswordDto);

    void setUnSuccessfulAttemptsCount(Integer userId, Integer unSuccessfulCount);

}
