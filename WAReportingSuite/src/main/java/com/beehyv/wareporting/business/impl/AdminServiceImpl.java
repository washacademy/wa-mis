package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.AdminService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.entity.ReportRequest;
import com.beehyv.wareporting.enums.*;
import com.beehyv.wareporting.model.*;
import com.beehyv.wareporting.utils.Constants;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.beehyv.wareporting.utils.Global.retrieveDocuments;
import static com.beehyv.wareporting.utils.ServiceFunctions.StReplace;

/**
 * Created by beehyv on 19/4/17.
 */

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private PanchayatDao panchayatDao;
    
    @Autowired
    private WACourseAttemptDao waCourseAttemptDao;

    @Autowired
    private SwcImportRejectionDao swcImportRejectionDao;

    @Autowired
    private SwachchagrahiDao swachchagrahiDao;

    @Autowired
    private AnonymousUsersDao anonymousUsersDao;

    @Autowired
    private CircleDao circleDao;

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private final String documents = retrieveDocuments();
    private final String reports = documents+"Reports/";
    private Calendar c =Calendar.getInstance();
    private final String course1 = "Wash_Academy_Course";
    private final String course2 = "Wash_Academy_Course_Plus";
    @Override
    public HashMap startBulkDataImport(User loggedInUser) {
        Pattern pattern;
        Matcher matcher;
        Map<Integer, String> errorCreatingUsers = new HashMap<Integer, String>();
        createPropertiesFileForFileLocation();
        String fileName = null;
        fileName = retrievePropertiesFromFileLocationProperties();
        if (fileName == null) {
            fileName = documents+"BulkImportDatacr7ms10.csv";
            System.out.println("fileLocationProperties not working");
        }
        XSSFRow row;
        BufferedReader fis = null;
        try {
            fis = new BufferedReader(new FileReader(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                String line = "";
                String cvsSplitBy = ",";
                try {
                    int lineNumber = 1;
                    if ((line = fis.readLine()) != null) {
                    }
                    while ((line = fis.readLine()) != null) {
                        lineNumber++;
                        // use comma as separator
                        String[] Line = line.split(cvsSplitBy);
                        User user = new User();
                        Role role;
                        State state;
                        String userName = Line[6];
                        if (userName.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the username for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        User UsernameExist = null;
                        UsernameExist = userDao.findByUserName(Line[6]);
                        if (UsernameExist == null) {
                            user.setUsername(Line[6]);
                        } else {
                            Integer rowNum = lineNumber;
                            String userNameError = "Username already exists.";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        int loggedUserRole = loggedInUser.getRoleId();
                        String loggedUserAccess = loggedInUser.getAccessLevel();
                        AccessLevel loggedUserAccessLevel = AccessLevel.getLevel(loggedUserAccess);
                        String userPhone = Line[4];
                        if (userPhone.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the phone number for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        String regexStr1 = "^[0-9]*$";
                        String regexStr2 = "^[0-9]{10}$";
                        if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please check the format of phone number for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        user.setPassword(passwordEncoder.encode(Line[4]));
                        user.setFullName(Line[0]);
                        user.setPhoneNumber(Line[4]);
                        user.setAccountStatus(AccountStatus.ACTIVE.getAccountStatus());
                        String userEmail = Line[5];
                        if (userEmail.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the Email for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        String EWAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                        pattern = Pattern.compile(EWAIL_PATTERN);
                        matcher = pattern.matcher(userEmail);
                        boolean validate = matcher.matches();
                        if (validate) {
                            user.setEmailId(Line[5]);
                        } else {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please enter the valid Email for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        user.setCreatedByUser(loggedInUser);
                        user.setCreationDate(new Date());
                        List<Role> userRole = roleDao.findByRoleDescription(Line[8]);
                        String State = Line[1];
                        String District = Line[2];
                        String Block = Line[3];
                        boolean isLevel = AccessLevel.isLevel(Line[7]);
                        if (!(isLevel)) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the access level for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        boolean isType = AccessType.isType(Line[8]);
                        if (!(isType)) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        if (userRole == null || userRole.size() == 0) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role of user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        int userRoleId = userRole.get(0).getRoleId();
                        String UserRole = AccessType.getType(Line[8]);
                        AccessLevel accessLevel = AccessLevel.getLevel(Line[7]);
                        if (UserRole.equalsIgnoreCase("ADMIN")) {
                            if ((accessLevel == AccessLevel.NATIONAL) ) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else if (loggedUserAccessLevel == AccessLevel.DISTRICT) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            }  else if (accessLevel == AccessLevel.STATE && loggedUserAccessLevel != AccessLevel.NATIONAL) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            }else if (accessLevel == AccessLevel.STATE && loggedUserAccessLevel == AccessLevel.NATIONAL) {
                                List<State> userStateList = stateDao.findByName(State);
                                if(userStateList==null || userStateList.size()==0){
                                    Integer rowNum = lineNumber;
                                    String authorityError = "Please enter the valid state for this admin";
                                    errorCreatingUsers.put(rowNum, authorityError);
                                    continue;
                                }
                                State userState=userStateList.get(0);
                                boolean isAdminAvailable = userDao.isAdminCreated(userState);
                                if (!(isAdminAvailable)) {
                                    user.setAccessLevel(AccessLevel.STATE.getAccessLevel());
                                    user.setStateId(userState.getStateId());
                                } else {
                                    Integer rowNum = lineNumber;
                                    String authorityError = "Admin is available for this state.";
                                    errorCreatingUsers.put(rowNum, authorityError);
                                    continue;
                                }
                            }
                            else {
                                List<State> userStateList = stateDao.findByName(State);
                                List<District> userDistrictList = districtDao.findByName(District);
                                District userDistrict = null;
                                State userState = null;
                                if (userDistrictList.size() == 1) {

                                    userDistrict = userDistrictList.get(0);
                                    userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                } else {
                                    for (District district : userDistrictList) {
                                        State parent = stateDao.findByStateId(district.getStateOfDistrict());
                                        if ((userStateList != null) && (userStateList.size() != 0)) {
                                            if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                userDistrict = district;
                                                userState = parent;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (userDistrict == null) {
                                    Integer rowNum = lineNumber;
                                    String authorityError = "Please enter the valid district for this user.";
                                    errorCreatingUsers.put(rowNum, authorityError);
                                    continue;
                                } else {
                                    if ((loggedInUser.getStateId()!=null && !loggedInUser.getStateId().equals(userState.getStateId()))) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "You don't have authority to create this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        boolean isAdminAvailable = userDao.isAdminCreated(userDistrict);
                                        if (!(isAdminAvailable)) {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict.getDistrictId());
                                            user.setStateId(userState.getStateId());
                                        } else {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "Admin is available for this district.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                        if (UserRole.equalsIgnoreCase("USER")) {
                            if (loggedUserAccessLevel.ordinal() > accessLevel.ordinal()) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else {
                                if (accessLevel == AccessLevel.NATIONAL) {
                                    user.setAccessLevel(AccessLevel.NATIONAL.getAccessLevel());
                                } else if (accessLevel == AccessLevel.STATE) {
                                    user.setAccessLevel(accessLevel.getAccessLevel());
                                    List<State> userStateList = stateDao.findByName(State);
                                    if ((userStateList == null) || (userStateList.size() == 0)) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid State for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        if (loggedUserAccessLevel == AccessLevel.STATE) {
                                            if (!loggedInUser.getStateId().equals(userStateList.get(0).getStateId())) {
                                                Integer rowNum = lineNumber;
                                                String authorityError = "You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum, authorityError);
                                                continue;
                                            } else user.setStateId(userStateList.get(0).getStateId());
                                        } else user.setStateId(userStateList.get(0).getStateId());
                                    }
                                } else if (accessLevel == AccessLevel.DISTRICT) {
                                    List<State> userStateList = stateDao.findByName(State);
                                    List<District> userDistrictList = districtDao.findByName(District);
                                    District userDistrict = null;
                                    State userState = null;
                                    if (userDistrictList.size() == 1) {
                                        userDistrict = userDistrictList.get(0);
                                        userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                    } else {
                                        for (District district : userDistrictList) {
                                            State parent = stateDao.findByStateId(district.getStateOfDistrict());
                                            if ((userStateList != null) && (userStateList.size() != 0)) {
                                                if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                    userDistrict = district;
                                                    userState = parent;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (userDistrict == null) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid district for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        if (((loggedUserAccessLevel == AccessLevel.STATE) &&
                                                (!loggedInUser.getStateId().equals(userState.getStateId()))) ||
                                                ((loggedUserAccessLevel == AccessLevel.DISTRICT) &&
                                                        (!loggedInUser.getDistrictId().equals(userDistrict.getDistrictId())))) {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "You don't have authority to create this user.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        } else {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict.getDistrictId());
                                            user.setStateId(userState.getStateId());
                                        }
                                    }
                                } else {
                                    user.setAccessLevel(AccessLevel.BLOCK.getAccessLevel());
                                    List<State> userStateList = stateDao.findByName(State);
                                    List<District> userDistrictList = districtDao.findByName(District);
                                    List<Block> userBlockList = blockDao.findByName(Block);
                                    State userState = null;
                                    District userDistrict = null;
                                    Block userBlock = null;
                                    if (userBlockList.size() == 1) {
                                        userBlock = userBlockList.get(0);
                                        userDistrict = districtDao.findByDistrictId(userBlock.getDistrictOfBlock());
                                        userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                        user.setBlockId(userBlock.getBlockId());
                                        user.setStateId(userState.getStateId());
                                        user.setDistrictId(userDistrict.getDistrictId());
                                    } else if ((userBlockList.size() == 0) || userBlockList == null) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid Block for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                    } else {
                                        List<Block> commonDistrict = null;
                                        for (Block block : userBlockList) {
                                            District parent = districtDao.findByDistrictId(block.getDistrictOfBlock());
                                            if (userDistrictList.size() > 0) {
                                                for (District district : userDistrictList) {
                                                    if (parent.getDistrictId().equals(district.getDistrictId())) {
                                                        commonDistrict.add(block);
                                                    }
                                                }
                                            }
                                        }
                                        for (Block block : commonDistrict) {
                                            State parent = stateDao.findByStateId(block.getStateOfBlock());
                                            if (userState != null) {
                                                if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                    userBlock = block;
                                                    userDistrict = districtDao.findByDistrictId(userBlock.getDistrictOfBlock());
                                                    userState = stateDao.findByStateId(userBlock.getStateOfBlock());
                                                    break;
                                                }
                                            }
                                        }
                                        if (userBlock == null) {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "Please enter the valid location for this user.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        } else {
                                            if (((loggedUserAccessLevel == AccessLevel.STATE) &&
                                                    (!loggedInUser.getStateId().equals(userState.getStateId()))) ||
                                                    ((loggedUserAccessLevel == AccessLevel.DISTRICT) &&
                                                            (!loggedInUser.getDistrictId().equals(userDistrict.getDistrictId())))) {
                                                Integer rowNum = lineNumber;
                                                String authorityError = "You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum, authorityError);
                                                continue;
                                            } else {
                                                user.setBlockId(userBlock.getBlockId());
                                                user.setStateId(userState.getStateId());
                                                user.setDistrictId(userDistrict.getDistrictId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        user.setStateName(user.getStateId()==null ? "" : stateDao.findByStateId(user.getStateId()).getStateName());
                        user.setDistrictName(user.getDistrictId()==null? "" : districtDao.findByDistrictId(user.getDistrictId()).getDistrictName());
                        user.setBlockName(user.getBlockId()==null ? "" :  blockDao.findByBlockId(user.getBlockId()).getBlockName());
                        user.setRoleId(userRoleId);
                        user.setRoleName(roleDao.findByRoleId(userRoleId).getRoleDescription());
                        userDao.saveUser(user);
                        ModificationTracker modification = new ModificationTracker();
                        modification.setModificationDate(new Date(System.currentTimeMillis()));
                        modification.setModificationType(ModificationType.CREATE.getModificationType());
                        modification.setModifiedUserId(userDao.findByUserName(user.getUsername()).getUserId());
                        modification.setModifiedByUserId(loggedInUser.getUserId());
                        modificationTrackerDao.saveModification(modification);

                    }
                    if(lineNumber == 1){
                        errorCreatingUsers.put(0,"fail");
                        errorCreatingUsers.put(1,"No records present in the uploaded file");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return (HashMap) errorCreatingUsers;
    }

    public void getBulkDataImportCSV() {
        String NEW_LINE_SEPARATOR = "\n";

        //CSV file header
        String FILE_HEADER = "Full Name, State, District, Block, Phone number, Email ID, UserName, Access Level,Role";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(documents+"BulkImportData.csv");

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);
            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);
            //Write a new student object list to the CSV file
            System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createFiles(String reportType) {

        String[] courses = {"Wash_Academy_Course","Wash_Academy_Course_Plus"};

        List<State> states = stateDao.getAllStates();
        String rootPath = reports;
        for (int i =0;i < courses.length; i++){

            File dir = new File(rootPath + courses[i] +"/" + reportType);
            if (!dir.exists())
                dir.mkdirs();
            for (State state : states) {
                String stateName = StReplace(state.getStateName());
                String rootPathState = rootPath +courses[i] +"/"+ reportType + "/" + stateName;
                File dirState = new File(rootPathState);
                if (!dirState.exists())
                    dirState.mkdirs();
                int stateId = state.getStateId();
                List<District> districts = districtDao.getDistrictsOfState(stateId);
                for (District district : districts) {
                    String districtName = StReplace(district.getDistrictName());
                    String rootPathDistrict = rootPathState + "/" + districtName;
                    File dirDistrict = new File(rootPathDistrict);
                    if (!dirDistrict.exists())
                        dirDistrict.mkdirs();
                    int districtId = district.getDistrictId();
                    List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                    for (Block block : Blocks) {
                        String blockName = StReplace(block.getBlockName());
                        String rootPathBlock = rootPathDistrict + "/" + blockName;
                        File dirBlock = new File(rootPathBlock);
                        if (!dirBlock.exists())
                            dirBlock.mkdirs();
                    }
                }
            }

        }

    }

    @Override
    public void createFile(String reportType) {
        List<State> states = stateDao.getAllStates();
        String rootPath = reports;
        File dir = new File(rootPath + reportType);
        if (!dir.exists())
            dir.mkdirs();
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + reportType + "/" + stateName;
            File dirState = new File(rootPathState);
            if (!dirState.exists())
                dirState.mkdirs();
            int stateId = state.getStateId();
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + "/" + districtName;
                File dirDistrict = new File(rootPathDistrict);
                if (!dirDistrict.exists())
                    dirDistrict.mkdirs();
                int districtId = district.getDistrictId();
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathBlock = rootPathDistrict + "/" + blockName;
                    File dirBlock = new File(rootPathBlock);
                    if (!dirBlock.exists())
                        dirBlock.mkdirs();
                }
            }
        }
    }


        @Override
    public void createFolders(String reportType) {
        String[] courses = {"Wash_Academy_Course","Wash_Academy_Course_Plus"};
        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = reports;
        for (int i =0;i < courses.length; i++){
            File dir = new File(rootPath +courses[i]   + "/" + reportType);
            if (!dir.exists())
                dir.mkdirs();
            for (Circle circle : circleList) {
                String circleName = StReplace(circle.getCircleFullName());
                String rootPathCircle = rootPath +courses[i] + "/" + reportType + "/" + circleName;
                File dirCircle = new File(rootPathCircle);
                if (!dirCircle.exists())
                    dirCircle.mkdirs();
            }
        }

    }

    @Override
    public void createSpecificReport(ReportRequest reportRequest) {
        String course = "";
        Integer courseId = 0;
        try {
            courseId = reportRequest.getCourseId();
            if(courseId == 1)
                course = course1;
            if(courseId == 2 )
                course = course2;
        }
        catch(Exception NullPointerException) {
            //caught
        }
        String rootPath = reports+ course + "/" + reportRequest.getReportType()+ "/";
//        Date toDate = reportRequest.getToDate();
        Date startDate=new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);

//        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);

        Date fromDate = aCalendar.getTime();

        aCalendar.add(Calendar.MONTH, 1);

        Date toDate = aCalendar.getTime();
        int stateId=reportRequest.getStateId();
        int districtId=reportRequest.getDistrictId();
        int blockId=reportRequest.getBlockId();
        int circleId=reportRequest.getCircleId();

        if(reportRequest.getReportType().equals(ReportType.waCourseCompletion.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<WACourseFirstCompletion> successFullCandidates = waCourseAttemptDao.getSuccessFulCompletion(toDate, courseId);
                getCumulativeCourseCompletion(successFullCandidates, rootPath,AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<WACourseFirstCompletion> candidatesFromThisState = waCourseAttemptDao.getSuccessFulCompletionWithStateId(toDate,stateId, courseId);

                    getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<WACourseFirstCompletion> candidatesFromThisDistrict = waCourseAttemptDao.getSuccessFulCompletionWithDistrictId(toDate,districtId, courseId);

                        getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByBlockId(blockId).getBlockName());
                        String rootPathBlock = rootPathDistrict + blockName+ "/";

                        List<WACourseFirstCompletion> candidatesFromThisBlock = waCourseAttemptDao.getSuccessFulCompletionWithBlockId(toDate,blockId, courseId);

                        getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathBlock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.waInactive.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<Swachchagrahi> inactiveSwachchagrahis = swachchagrahiDao.getInactiveSwachchagrahis(toDate);
                getCumulativeInactiveUsers(inactiveSwachchagrahis, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<Swachchagrahi> candidatesFromThisState = swachchagrahiDao.getInactiveSwachchagrahisWithStateId(toDate,stateId);

                    getCumulativeInactiveUsers(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<Swachchagrahi> candidatesFromThisDistrict = swachchagrahiDao.getInactiveSwachchagrahisWithDistrictId(toDate,districtId);

                        getCumulativeInactiveUsers(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByBlockId(blockId).getBlockName());
                        String rootPathBlock = rootPathDistrict + blockName+ "/";

                        List<Swachchagrahi> candidatesFromThisBlock = swachchagrahiDao.getInactiveSwachchagrahisWithBlockId(toDate,blockId);

                        getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathBlock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.waCircleWiseAnonymous.getReportType())){
            reportRequest.setFromDate(toDate);

                String circleName=StReplace(circleDao.getByCircleId(circleId).getCircleName());
                String circleFullName = StReplace(circleDao.getByCircleId(circleId).getCircleFullName());
                String rootPathCircle=rootPath+circleFullName+"/";
                List<WACircleWiseAnonymousUsersLineListing> anonymousUsersListCircle = anonymousUsersDao.getAnonymousUsersByCircle(fromDate,toDate,circleName,courseId);
                getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleFullName, toDate, reportRequest);
        }

        else if(reportRequest.getReportType().equals(ReportType.swcRejected.getReportType())){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reportRequest.getFromDate());
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            toDate=calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH,-7);
            fromDate=calendar.getTime();
            //Date fromDate=calendar.getTime();
//            List<SwcImportRejection> childImportRejections = swcImportRejectionDao.getAllRejectedSwcImportRecords(nextDay);
            reportRequest.setFromDate(toDate);
            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<SwcImportRejection> candidatesFromThisState =
                        swcImportRejectionDao.getAllRejectedSwcImportRecordsWithStateId(fromDate,toDate,stateId);

                getCumulativeRejectedSwcImports(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<SwcImportRejection> candidatesFromThisDistrict =
                            swcImportRejectionDao.getAllRejectedSwcImportRecordsWithDistrictId(fromDate, toDate,districtId);

                    getCumulativeRejectedSwcImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByBlockId(blockId).getBlockName());
                    String rootPathBlock = rootPathDistrict + blockName+ "/";

                    List<SwcImportRejection> candidatesFromThisBlock =
                            swcImportRejectionDao.getAllRejectedSwcImportRecordsWithBlockId(fromDate, toDate,blockId);

                    getCumulativeRejectedSwcImports(candidatesFromThisBlock, rootPathBlock, blockName, toDate, reportRequest);
                }
            }

        }
    }


    private void getCumulativeRejectedSwcImports(List<SwcImportRejection> rejectedSwcImports, String rootPath,
                                                 String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Swachchagrahi Import Rejected Details");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State Name",
                "District Name",
                "Block Name",
                "Panchayat Name",
                "Swachchagrahi Id",
                "Swachchagrahi Name",
                "Swachchagrahi Job Status",
                "Swachchagrahi Mobile Number",
                "Reason For Rejection"
        });
        Integer counter = 2;
        if(rejectedSwcImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (SwcImportRejection swcRejection : rejectedSwcImports) {
            empinfo.put((counter.toString()), new Object[]{
                    (swcRejection.getStateId() == null) ? "No State Name": stateDao.findByStateId(swcRejection.getStateId()).getStateName(),
                    (swcRejection.getDistrictName() == null) ? "No District Name": swcRejection.getDistrictName(),
                    (swcRejection.getBlockName() == null) ? "No  Block": swcRejection.getBlockName(),
                   // (swcRejection.getPhcName() == null) ? "No  Facility" : swcRejection.getPhcName(),
                    (swcRejection.getPanchayatName() == null) ? "No Panchayat" : swcRejection.getPanchayatName(),
                    (swcRejection.getSwcId() == null) ? "No Swachchagrahi ID": swcRejection.getSwcId(),
                    (swcRejection.getFullName() == null) ? "No Swachchagrahi Name": swcRejection.getFullName(),
                    (swcRejection.getJobStatus() == null) ? "No Swachchagrahi Job Status": swcRejection.getJobStatus(),
                    (swcRejection.getMobileNumber() == null) ? "No Swachchagrahi Mobile Number": swcRejection.getMobileNumber(),
                    (swcRejection.getRejectionReason() == null) ? "No Rejection Reason": swcRejection.getRejectionReason(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid == 6 && rejectedSwcImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:L6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.swcRejected.getReportType() + "_" + place + "_" + getDateMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCumulativeCourseCompletion(List<WACourseFirstCompletion> successfulCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        //Create blank workbook

        XSSFWorkbook workbook = new XSSFWorkbook();

        String course = "";
        Integer courseId = 0;
        try {
            courseId = reportRequest.getCourseId();
            if(courseId == 1)
                course = course1;
            if(courseId == 2 )
                course = course2;
        }
        catch(Exception NullPointerException) {
            //caught
        }
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " WA Course Completion Report for "+ course);
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Mobile Number",
                "State",
                "District",
                "Block",
                "Panchayat",
                "Swachchagrahi Name",
                "Swachchagrahi ID",
                "Swachchagrahi Creation Date",
                "Course Start Date",
                "First Completion Date",
                "SMS Sent Notification"
        });
        Integer counter = 2;
        if(successfulCandidates.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (WACourseFirstCompletion waCourseFirstCompletion : successfulCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    (waCourseFirstCompletion.getMobileNumber() == null) ? "No Mobile Number":waCourseFirstCompletion.getMobileNumber(),
                    (waCourseFirstCompletion.getStateId() == null) ? "No State":stateDao.findByStateId(waCourseFirstCompletion.getStateId()).getStateName(),
                    (waCourseFirstCompletion.getDistrictId() == null) ? "No District":districtDao.findByDistrictId(waCourseFirstCompletion.getDistrictId()).getDistrictName(),
                    (waCourseFirstCompletion.getBlockId() == null) ? "No Block" : blockDao.findByBlockId(waCourseFirstCompletion.getBlockId()).getBlockName(),
                    (waCourseFirstCompletion.getPanchayatId() == null) ? "No Panchayat" : panchayatDao.findByPanchayatId(waCourseFirstCompletion.getPanchayatId()).getPanchayatName(),
                    (waCourseFirstCompletion.getFullName() == null) ? "No Name":waCourseFirstCompletion.getFullName(),
                    (waCourseFirstCompletion.getSwcId() == null) ? "No Swc_Id":waCourseFirstCompletion.getSwcId(),
                    (waCourseFirstCompletion.getCreationDate() == null) ? "No Creation_date":waCourseFirstCompletion.getCreationDate(),
                    (waCourseFirstCompletion.getCourseStartDate() == null) ? "No Course Start Date":waCourseFirstCompletion.getCourseStartDate(),
                    (waCourseFirstCompletion.getFirstCompletionDate() == null) ? "No Date":waCourseFirstCompletion.getFirstCompletionDate(),
                    (waCourseFirstCompletion.getSentNotification() == null) ? "No Details": waCourseFirstCompletion.getSentNotification()
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid == 6 && successfulCandidates.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:N6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.waCourseCompletion.getReportType() + "_" + place + "_" +course +"_"+ getMonthYear(toDate)  + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCircleWiseAnonymousUsers(List<WACircleWiseAnonymousUsersLineListing> anonymousUsersList, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        final String course1 = "Wash_Academy_Course";
        final String course2 = "Wash_Academy_Course_Plus";
        String course = "";
        Integer courseId = 0;
        try {
            courseId = reportRequest.getCourseId();
            if(courseId == 1)
                course = course1;
            if(courseId == 2 )
                course = course2;
        }
        catch(Exception NullPointerException) {
            //caught
        }
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Circle-wise Anonymous Users Report for" + course);
        //Create row object
        XSSFRow row;

        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Id",
                "Mobile Number",
                "Operator",
                "Circle Name",
                "Course Start Date",
                "Course First Successful End",
                "Last Call End Date",
                "Last Call End Time",
                "Total Minutes Used",
                "SMS Sent",
                "SMS Reference Number",
                "No. Of Attempts"
        });
        Integer counter = 2;
        if(anonymousUsersList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (WACircleWiseAnonymousUsersLineListing anonymousUser : anonymousUsersList) {
            empinfo.put((counter.toString()), new Object[]{
                    (anonymousUser.getId() == null) ? "No Id":anonymousUser.getId(),
                    (anonymousUser.getMobileNumber() == null) ? "No Mobile Number":anonymousUser.getMobileNumber(),
                    (anonymousUser.getOperator() == null) ? "No Operator":anonymousUser.getOperator(),
                    (anonymousUser.getCircleName() == null) ? "No Circle":anonymousUser.getCircleName(),
                    (anonymousUser.getCourseStartDate() == null) ? "No Date":anonymousUser.getCourseStartDate(),
                    (anonymousUser.getFirstCompletionDate() == null) ? "No Date":anonymousUser.getFirstCompletionDate(),
                    (anonymousUser.getLastCallEndDate() == null) ? "No Details":anonymousUser.getLastCallEndDate(),
                    (anonymousUser.getLastCallEndTime() == null) ? "No Details":anonymousUser.getLastCallEndTime(),
                    (anonymousUser.getTotalMinutesUsed() == null) ? "No Details":anonymousUser.getTotalMinutesUsed(),
                    (anonymousUser.getSMSSent() == null) ? "No Details":anonymousUser.getSMSSent(),
                    (anonymousUser.getSMSReferenceNo() == null) ? "No Details":anonymousUser.getSMSReferenceNo(),
                    (anonymousUser.getNoOfAttempts() == null) ? "No Details":anonymousUser.getNoOfAttempts()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==6 && anonymousUsersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:C6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.waCircleWiseAnonymous.getReportType() + "_" + place + "_" +course+ "_"+  getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCumulativeInactiveUsers(List<Swachchagrahi> inactiveCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Cumulative Inactive Users Report "+place+"_"+getMonthYear(toDate));
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Mobile Number",
                "State",
                "District",
                "Block",
                "Panchayat",
                "Swachchagrahi Name",
                "Swachchagrahi ID",
                "Swachchagrahi Creation Date"
        });
        Integer counter = 2;
        if(inactiveCandidates.isEmpty()) {
            empinfo.put(counter.toString(),new Object[]{"No Records to display"});
        }
        for (Swachchagrahi swachchagrahi : inactiveCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    (swachchagrahi.getMobileNumber() == null) ? "No Mobile Number":swachchagrahi.getMobileNumber(),
                    (swachchagrahi.getStateId() == null) ? "No State":stateDao.findByStateId(swachchagrahi.getStateId()).getStateName(),
                    (swachchagrahi.getDistrictId() == null) ? "No District":districtDao.findByDistrictId(swachchagrahi.getDistrictId()).getDistrictName(),
                    (swachchagrahi.getBlockId() == null) ? "No Block" : blockDao.findByBlockId(swachchagrahi.getBlockId()).getBlockName(),
                    (swachchagrahi.getPanchayatId() == null) ? "No Panchayat" : panchayatDao.findByPanchayatId(swachchagrahi.getPanchayatId()).getPanchayatName(),
                    (swachchagrahi.getFullName() == null) ? "No Name":swachchagrahi.getFullName(),
                    (swachchagrahi.getSwcId() == null) ? "No Swc_Id":swachchagrahi.getSwcId(),
                    (swachchagrahi.getCreationDate() == null) ? "No Creation_date":swachchagrahi.getCreationDate()
                    //(swachchagrahi.getJobStatus() == null) ? "No Details":swachchagrahi.getJobStatus()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=4;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==6 && inactiveCandidates.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A6:L6"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.waInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createHeadersForReportFiles(XSSFWorkbook workbook, ReportRequest reportRequest) {
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.createRow(rowid++);

        String encodingPrefix = "base64,";
        String pngImageURL = Constants.header_base64;
        int contentStartIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStartIndex));//workbook.addPicture can use this byte array

        final int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);


        final CreationHelper helper = workbook.getCreationHelper();
        final Drawing drawing = spreadsheet.createDrawingPatriarch();

        final ClientAnchor anchor = helper.createClientAnchor();
        anchor.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );


        anchor.setCol1( 0 );
        anchor.setRow1( 0 );
        anchor.setRow2( 3 );
        anchor.setCol2( 12 );
        drawing.createPicture( anchor, pictureIndex );

        XSSFCellStyle style1 = workbook.createCellStyle();//Create style
        style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style1.setBorderBottom(CellStyle.BORDER_MEDIUM);

        spreadsheet.addMergedRegion(new CellRangeAddress(0,2,0,11));

        rowid = rowid+2;
        XSSFRow row=spreadsheet.createRow(rowid++);
        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);//set it to bold
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        Cell cell1=row.createCell(0);
        Cell cell2=row.createCell(1);
        Cell cell3=row.createCell(7);
        Cell cell4=row.createCell(8);
        spreadsheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        spreadsheet.addMergedRegion(new CellRangeAddress(0,1,1,5));
        spreadsheet.addMergedRegion(new CellRangeAddress(0,1,7,7));
        XSSFRow row1=spreadsheet.createRow(++rowid);
        Cell cell5=row1.createCell(0);
        Cell cell6=row1.createCell(1);
        Cell cell7=row1.createCell(4);
        Cell cell8=row1.createCell(5);
        Cell cell9=row1.createCell(8);
        Cell cell10=row1.createCell(9);
        cell1.setCellValue("Report:");
        cell2.setCellValue(ReportType.getType(reportRequest.getReportType()).getReportHeader());
        if(reportRequest.getReportType().equals(ReportType.waCircleWiseAnonymous.getReportType())){
            String circleName;
            if(reportRequest.getCircleId()!=0) {
                circleName=circleDao.getByCircleId(reportRequest.getCircleId()).getCircleFullName();
            }else {
                circleName = "ALL";
            }
            cell3.setCellValue("Month:");
            cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
            cell5.setCellValue("Circle:");
            cell6.setCellValue(circleName);
        }else {
            if(reportRequest.getReportType().equals(ReportType.swcRejected.getReportType())){
                cell3.setCellValue("Week:");
                cell4.setCellValue(getDateMonthYearName(reportRequest.getFromDate()));
                spreadsheet.addMergedRegion(new CellRangeAddress(0,1,8,11));
            } else {
                cell3.setCellValue("Month:");
                cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
                spreadsheet.addMergedRegion(new CellRangeAddress(0,1,8,9));
            }
            String stateName;
            if(reportRequest.getStateId() !=0){
                stateName=stateDao.findByStateId(reportRequest.getStateId()).getStateName();
            }else {
                stateName="ALL";
            }
            cell5.setCellValue("State:");
            cell6.setCellValue(stateName);
            String districtName;
            if(reportRequest.getDistrictId() !=0){
                districtName =districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName();
            }else {
                districtName="ALL";
            }
            cell7.setCellValue("District:");
            cell8.setCellValue(districtName);
            String blockName;
            if(reportRequest.getBlockId() !=0){
                blockName =blockDao.findByBlockId(reportRequest.getBlockId()).getBlockName();
            }else {
                blockName ="ALL";
            }
            cell9.setCellValue("Block:");
            cell10.setCellValue(blockName);
        }
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);
        spreadsheet.addMergedRegion(new CellRangeAddress(2,2,0,0));
        spreadsheet.addMergedRegion(new CellRangeAddress(2,2,1,2));
        spreadsheet.addMergedRegion(new CellRangeAddress(2,2,5,6));
        spreadsheet.addMergedRegion(new CellRangeAddress(2,2,9,10));

    }

    @Override
    public void createSwcImportRejectedFiles(Date toDate) {
        List<State> states = stateDao.getAllStates();
        String rootPath = reports +ReportType.swcRejected.getReportType()+ "/";
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(toDate);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DAY_OF_MONTH,1);
        toDate=aCalendar.getTime();
        aCalendar.add(Calendar.DAY_OF_MONTH, -7);
        Date fromDate=aCalendar.getTime();
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.swcRejected.getReportType());

        List<SwcImportRejection> rejectedSwcImports = swcImportRejectionDao.getAllRejectedSwcImportRecords(fromDate, toDate);
//        getCumulativeRejectedSwcImports(rejectedSwcImports, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<SwcImportRejection> candidatesFromThisState = new ArrayList<>();
            for (SwcImportRejection rejectedImport : rejectedSwcImports) {
                if ((rejectedImport.getStateId()!=null)&&(rejectedImport.getStateId() == stateId)) {
                    candidatesFromThisState.add(rejectedImport);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getCumulativeRejectedSwcImports(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<SwcImportRejection> candidatesFromThisDistrict = new ArrayList<>();
                for (SwcImportRejection rejectedImport : candidatesFromThisState) {
                    if ((rejectedImport.getDistrictId()!=null)&&(rejectedImport.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(rejectedImport);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getCumulativeRejectedSwcImports(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathBlock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<SwcImportRejection> candidatesFromThisBlock = new ArrayList<>();
                    for (SwcImportRejection rejectedImport : candidatesFromThisDistrict) {
                        if ((rejectedImport.getBlockId()!=null)&&(rejectedImport.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(rejectedImport);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeRejectedSwcImports(candidatesFromThisBlock, rootPathBlock, blockName, toDate, reportRequest);
                }
            }
        }

    }

    @Override
    public void getCumulativeCourseCompletionFiles(Date toDate, Integer courseId) {

        List<State> states = stateDao.getAllStates();
        String rootPath = reports+ReportType.waCourseCompletion.getReportType()+ "/";
        List<WACourseFirstCompletion> successFullCandidates = waCourseAttemptDao.getSuccessFulCompletion(toDate, courseId);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.waCourseCompletion.getReportType());
        reportRequest.setCourseId(courseId);
        getCumulativeCourseCompletion(successFullCandidates, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            int stateId = state.getStateId();
            List<WACourseFirstCompletion> candidatesFromThisState = new ArrayList<>();
            for (WACourseFirstCompletion swachchagrahi : successFullCandidates) {
                if (swachchagrahi.getStateId() == stateId) {
                    candidatesFromThisState.add(swachchagrahi);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);

            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<WACourseFirstCompletion> candidatesFromThisDistrict = new ArrayList<>();
                for (WACourseFirstCompletion swachchagrahi : candidatesFromThisState) {
                    if (swachchagrahi.getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(swachchagrahi);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathBlock = rootPathDistrict + blockName+ "/";
                    int blockId = block.getBlockId();
                    List<WACourseFirstCompletion> candidatesFromThisBlock = new ArrayList<>();
                    for (WACourseFirstCompletion swachchagrahi : candidatesFromThisDistrict) {
                        if ((swachchagrahi.getBlockId()!=null)&&(swachchagrahi.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(swachchagrahi);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathBlock, blockName, toDate,reportRequest);
                }
            }
        }
    }

    @Override
    public void getCircleWiseAnonymousFiles(Date startDate, Date toDate, Integer courseId) {
        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = reports+ReportType.waCircleWiseAnonymous.getReportType()+ "/";
        List<WACircleWiseAnonymousUsersLineListing> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(startDate,toDate, courseId);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setCircleId(0);
        reportRequest.setReportType(ReportType.waCircleWiseAnonymous.getReportType());
        reportRequest.setCourseId(courseId);
        getCircleWiseAnonymousUsers(anonymousUsersList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (Circle circle : circleList) {
            String circleName = StReplace(circle.getCircleName());
            String circleFullName = StReplace(circle.getCircleFullName());
            String rootPathCircle=rootPath+circleFullName+"/";
            List<WACircleWiseAnonymousUsersLineListing> anonymousUsersListCircle = new ArrayList<>();
            for(WACircleWiseAnonymousUsersLineListing anonymousUser : anonymousUsersList){
                if(anonymousUser.getCircleName().equalsIgnoreCase(circleName)){
                    anonymousUsersListCircle.add(anonymousUser);
                }
            }
            reportRequest.setCircleId(circle.getCircleId());
            getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleFullName, toDate, reportRequest);
        }
    }

    @Override
    public void getCumulativeInactiveFiles(Date toDate) {
        List<State> states = stateDao.getAllStates();
        String rootPath = reports+ReportType.waInactive.getReportType()+ "/";
        List<Swachchagrahi> inactiveSwachchagrahis = swachchagrahiDao.getInactiveSwachchagrahis(toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.waInactive.getReportType());
        getCumulativeInactiveUsers(inactiveSwachchagrahis, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<Swachchagrahi> candidatesFromThisState = new ArrayList<>();
            for (Swachchagrahi swachchagrahi : inactiveSwachchagrahis) {
                if (swachchagrahi.getStateId() == stateId) {
                    candidatesFromThisState.add(swachchagrahi);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getCumulativeInactiveUsers(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState  + districtName+ "/";
                int districtId = district.getDistrictId();
                List<Swachchagrahi> candidatesFromThisDistrict = new ArrayList<>();
                for (Swachchagrahi swachchagrahi : candidatesFromThisState) {
                    if (swachchagrahi.getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(swachchagrahi);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getCumulativeInactiveUsers(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathBlock = rootPathDistrict  + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<Swachchagrahi> candidatesFromThisBlock = new ArrayList<>();
                    for (Swachchagrahi swachchagrahi : candidatesFromThisDistrict) {
                        if ((swachchagrahi.getBlockId()!=null)&&(swachchagrahi.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(swachchagrahi);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathBlock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    private String retrievePropertiesFromFileLocationProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        String fileLocation = null;
        try {
            input = new FileInputStream("fileLocation.properties");
            // load a properties file
            prop.load(input);
            fileLocation = prop.getProperty("fileLocation");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileLocation;
    }

    private void createPropertiesFileForFileLocation() {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("fileLocation.properties");
            prop.setProperty("fileLocation", documents+"BulkImportDatacr7ms10.csv");
            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    private String getMonthYear(Date toDate){
        c.setTime(toDate);
        c.add(Calendar.MONTH,-1);
        int month=c.get(Calendar.MONTH)+1;
        int year=(c.get(Calendar.YEAR))%100;
        String monthString;
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
       else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+"_"+yearString;
    }

    private String getDateMonthYear(Date toDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(toDate);
        calendar.add( Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK)-1));
        int date=calendar.get(Calendar.DATE);
        int month=calendar.get(Calendar.MONTH)+1;
        int year=(calendar.get(Calendar.YEAR))%100;
        String dateString;
        if(date<10) {
            dateString="0"+String.valueOf(date);
        }
        else dateString=String.valueOf(date);
        String monthString;
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return dateString + "_" + monthString+"_"+yearString;

    }


    private String getMonthYearName(Date toDate) {
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.MONTH, -1);
//        int month=c.get(Calendar.MONTH);
//        String monthString = "";
        int year=(c.get(Calendar.YEAR))%100;
        String monthString = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
//        if(month<10){
//            monthString="0"+String.valueOf(month);
//        }
//        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+" "+yearString;
    }

    /**
     * from date to todate in String format.
     * @param toDate
     * @return String containing fromdate to todate
     */
    private String getDateMonthYearName(Date toDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(toDate);
        int toDateValue =calendar.get(Calendar.DATE)-1;
        int toDateYear =(calendar.get(Calendar.YEAR))%100;
        String toDateString;
        if(toDateValue <10) {
            toDateString ="0"+String.valueOf(toDateValue);
        }
        else {
            toDateString =String.valueOf(toDateValue);
        }
        String toMonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        String toYearString =String.valueOf(toDateYear);
        calendar.add(Calendar.DAY_OF_MONTH, -6);

        int fromDateValue=calendar.get(Calendar.DATE)-1;
        String fromStringDate;
        if(fromDateValue <10) {
            fromStringDate ="0"+String.valueOf(fromDateValue);
        }
        else {
            fromStringDate =String.valueOf(fromDateValue);
        }
        String fromMonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        String fromYearString =String.valueOf((calendar.get(Calendar.YEAR))%100);

        return fromStringDate+"-"+fromMonthString+"-"+fromYearString+" to " +toDateString + "-" +toMonthString +"-"+ toYearString;
    }



}
