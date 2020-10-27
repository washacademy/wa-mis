package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.AdminService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.entity.ReportRequest;
import com.beehyv.wareporting.enums.*;
import com.beehyv.wareporting.model.*;
import com.beehyv.wareporting.utils.Constants;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
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

import static com.beehyv.wareporting.utils.Global.*;
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
    private CourseDao courseDao;

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

    private final Integer[] courseIds = retrieveCourseIdsFromFileLocationProperties();
    private final String documents = retrieveDocuments();
    private final String reports = documents+"Reports/";
    private Calendar c =Calendar.getInstance();



    String[] getCourseNamesArray(){
        String[] courses = new String[courseIds.length];
        for(int i =0;i<courseIds.length;i++){
            courses[i]= courseDao.findByCourseId(courseIds[i]).getName();
        }
        return courses;
    };

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
        String[] courses = getCourseNamesArray();
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
        String[] courses = getCourseNamesArray();
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
        String rootPath = "";
        if ((reportRequest.getReportType().equals("SBM_Circle_Wise_Anonymous_Users") || reportRequest.getReportType().equals("SBM_Cumulative_Course_Completion"))){
            courseId = reportRequest.getCourseId();
            course = courseDao.findByCourseId(courseId).getName();
        }

        rootPath  = reports + course + "/" + reportRequest.getReportType()+ "/";


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
        Map<Integer, Object[]> empinfo =
                new TreeMap<>();
        empinfo.put(1, new Object[]{
                "S.No",
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
            empinfo.put(counter, new Object[]{"No Records to display"});
        }
        for (int i= 0;i<rejectedSwcImports.size();i++) {
            empinfo.put(counter, new Object[]{
                    i+1,
                    (rejectedSwcImports.get(i).getStateId() == null) ? "No State Name": stateDao.findByStateId(rejectedSwcImports.get(i).getStateId()).getStateName(),
                    (rejectedSwcImports.get(i).getDistrictName() == null) ? "No District Name": rejectedSwcImports.get(i).getDistrictName(),
                    (rejectedSwcImports.get(i).getBlockName() == null) ? "No  Block": rejectedSwcImports.get(i).getBlockName(),
                   // (swcRejection.getPhcName() == null) ? "No  Facility" : swcRejection.getPhcName(),
                    (rejectedSwcImports.get(i).getPanchayatName() == null) ? "No Panchayat" : rejectedSwcImports.get(i).getPanchayatName(),
                    (rejectedSwcImports.get(i).getSwcId() == null) ? "No Swachchagrahi ID": rejectedSwcImports.get(i).getSwcId(),
                    (rejectedSwcImports.get(i).getFullName() == null) ? "No Swachchagrahi Name": rejectedSwcImports.get(i).getFullName(),
                    (rejectedSwcImports.get(i).getJobStatus() == null) ? "No Swachchagrahi Job Status": rejectedSwcImports.get(i).getJobStatus(),
                    (rejectedSwcImports.get(i).getMobileNumber() == null) ? "No Swachchagrahi Mobile Number": rejectedSwcImports.get(i).getMobileNumber(),
                    (rejectedSwcImports.get(i).getRejectionReason() == null) ? "No Rejection Reason": rejectedSwcImports.get(i).getRejectionReason(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<Integer> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);

        XSSFCellStyle backgroundStyle = createColumnHeaderStyle(workbook);

        for (int i = 1; i < 12; i++) {
            spreadsheet.setColumnWidth(i, 4000);
        }
        spreadsheet.setColumnWidth(0,2000);

        int rowid=7;
        for (Integer key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid ==8){
                    row.setHeight((short) 1100);
                    cell.setCellStyle(backgroundStyle);
                }
                if(rowid == 9 && rejectedSwcImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:J9"));
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

    private XSSFCellStyle createColumnHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle backgroundStyle = workbook.createCellStyle();
        backgroundStyle.setAlignment(CellStyle.ALIGN_CENTER);
        backgroundStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        backgroundStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(33, 100, 178)));
        backgroundStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        backgroundStyle.setBorderBottom(CellStyle.BORDER_THIN);
        backgroundStyle.setBottomBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderLeft(CellStyle.BORDER_THIN);
        backgroundStyle.setLeftBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderRight(CellStyle.BORDER_THIN);
        backgroundStyle.setRightBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setBorderTop(CellStyle.BORDER_THIN);
        backgroundStyle.setTopBorderColor(new XSSFColor(new java.awt.Color(212, 212, 212)));
        backgroundStyle.setWrapText(true);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.WHITE.index);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        backgroundStyle.setFont(font);

        return backgroundStyle;
    }

    private void getCumulativeCourseCompletion(List<WACourseFirstCompletion> successfulCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        //Create blank workbook

        XSSFWorkbook workbook = new XSSFWorkbook();

        String course = "";
        Integer courseId = 0;
        try {
            courseId = reportRequest.getCourseId();
            course = courseDao.findByCourseId(courseId).getName();
        }
        catch(Exception NullPointerException) {
            //caught
        }
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " SBM Course Completion Report for "+ course);
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<Integer, Object[]> empinfo =
                new TreeMap<>();
        empinfo.put(1, new Object[]{
                "S.No",
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
            empinfo.put(counter, new Object[]{"No Records to display"});
        }
        for (int i =0;i<successfulCandidates.size();i++) {
            empinfo.put((counter), new Object[]{
                    i+1,
                    (successfulCandidates.get(i).getMobileNumber() == null) ? "No Mobile Number":successfulCandidates.get(i).getMobileNumber(),
                    (successfulCandidates.get(i).getStateId() == null) ? "No State":stateDao.findByStateId(successfulCandidates.get(i).getStateId()).getStateName(),
                    (successfulCandidates.get(i).getDistrictId() == null) ? "No District":districtDao.findByDistrictId(successfulCandidates.get(i).getDistrictId()).getDistrictName(),
                    (successfulCandidates.get(i).getBlockId() == null) ? "No Block" : blockDao.findByBlockId(successfulCandidates.get(i).getBlockId()).getBlockName(),
                    (successfulCandidates.get(i).getPanchayatId() == null) ? "No Panchayat" : panchayatDao.findByPanchayatId(successfulCandidates.get(i).getPanchayatId()).getPanchayatName(),
                    (successfulCandidates.get(i).getFullName() == null) ? "No Name":successfulCandidates.get(i).getFullName(),
                    (successfulCandidates.get(i).getSwcId() == null) ? "No Swc_Id":successfulCandidates.get(i).getSwcId(),
                    (successfulCandidates.get(i).getCreationDate() == null) ? "No Creation_date":successfulCandidates.get(i).getCreationDate(),
                    (successfulCandidates.get(i).getCourseStartDate() == null) ? "No Course Start Date":successfulCandidates.get(i).getCourseStartDate(),
                    (successfulCandidates.get(i).getFirstCompletionDate() == null) ? "No Date":successfulCandidates.get(i).getFirstCompletionDate(),
                    (successfulCandidates.get(i).getSentNotification() == null) ? "No Details": successfulCandidates.get(i).getSentNotification()
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<Integer> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);

        XSSFCellStyle backgroundStyle = createColumnHeaderStyle(workbook);

        for (int i = 1; i < 12; i++) {
            spreadsheet.setColumnWidth(i, 4000);
        }
        spreadsheet.setColumnWidth(0, 2000);
        int rowid=8;
        for (Integer key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid ==9){
                    row.setHeight((short) 1100);
                    cell.setCellStyle(backgroundStyle);
                }
                if(rowid == 10 && successfulCandidates.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A10:L10"));
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

        String course = "";
        Integer courseId = 0;
        try {
            courseId = reportRequest.getCourseId();
            course = courseDao.findByCourseId(courseId).getName();
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
        Map<Integer, Object[]> empinfo =
                new TreeMap<>();
        empinfo.put(1, new Object[]{
                "S.No",
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
            empinfo.put(counter, new Object[]{"No Records to display"});
        }
        for (int i = 0; i<anonymousUsersList.size();i++) {
            empinfo.put((counter), new Object[]{
                    i+1,
                    (anonymousUsersList.get(i).getId() == null) ? "No Id":anonymousUsersList.get(i).getId(),
                    (anonymousUsersList.get(i).getMobileNumber() == null) ? "No Mobile Number":anonymousUsersList.get(i).getMobileNumber(),
                    (anonymousUsersList.get(i).getOperator() == null) ? "No Operator":anonymousUsersList.get(i).getOperator(),
                    (anonymousUsersList.get(i).getCircleName() == null) ? "No Circle":anonymousUsersList.get(i).getCircleName(),
                    (anonymousUsersList.get(i).getCourseStartDate() == null) ? "No Date":anonymousUsersList.get(i).getCourseStartDate(),
                    (anonymousUsersList.get(i).getFirstCompletionDate() == null) ? "No Date":anonymousUsersList.get(i).getFirstCompletionDate(),
                    (anonymousUsersList.get(i).getLastCallEndDate() == null) ? "No Details":anonymousUsersList.get(i).getLastCallEndDate(),
                    (anonymousUsersList.get(i).getLastCallEndTime() == null) ? "No Details":anonymousUsersList.get(i).getLastCallEndTime(),
                    (anonymousUsersList.get(i).getTotalMinutesUsed() == null) ? "No Details":anonymousUsersList.get(i).getTotalMinutesUsed(),
                    (anonymousUsersList.get(i).getSMSSent() == null) ? "No Details":anonymousUsersList.get(i).getSMSSent(),
                    (anonymousUsersList.get(i).getSMSReferenceNo() == null) ? "No Details":anonymousUsersList.get(i).getSMSReferenceNo(),
                    (anonymousUsersList.get(i).getNoOfAttempts() == null) ? "No Details":anonymousUsersList.get(i).getNoOfAttempts()
            });
            counter++;
        }
        Set<Integer> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);

        XSSFCellStyle backgroundStyle = createColumnHeaderStyle(workbook);

        for (int i = 1; i < 13; i++) {
            spreadsheet.setColumnWidth(i, 4000);
        }
        spreadsheet.setColumnWidth(0,2000);

        int rowid=8;
        for (Integer key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if (rowid ==9){
                    row.setHeight((short) 1100);
                    cell.setCellStyle(backgroundStyle);
                }
                if(rowid==10 && anonymousUsersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A10:M10"));
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
        Map<Integer, Object[]> empinfo =
                new TreeMap<>();
        empinfo.put(1, new Object[]{
                "S.No",
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
            empinfo.put(counter,new Object[]{"No Records to display"});
        }
        for (int i =0; i<inactiveCandidates.size();i++) {
            empinfo.put((counter), new Object[]{
                    i+1,
                    (inactiveCandidates.get(i).getMobileNumber() == null) ? "No Mobile Number":inactiveCandidates.get(i).getMobileNumber(),
                    (inactiveCandidates.get(i).getStateId() == null) ? "No State":stateDao.findByStateId(inactiveCandidates.get(i).getStateId()).getStateName(),
                    (inactiveCandidates.get(i).getDistrictId() == null) ? "No District":districtDao.findByDistrictId(inactiveCandidates.get(i).getDistrictId()).getDistrictName(),
                    (inactiveCandidates.get(i).getBlockId() == null) ? "No Block" : blockDao.findByBlockId(inactiveCandidates.get(i).getBlockId()).getBlockName(),
                    (inactiveCandidates.get(i).getPanchayatId() == null) ? "No Panchayat" : panchayatDao.findByPanchayatId(inactiveCandidates.get(i).getPanchayatId()).getPanchayatName(),
                    (inactiveCandidates.get(i).getFullName() == null) ? "No Name":inactiveCandidates.get(i).getFullName(),
                    (inactiveCandidates.get(i).getSwcId() == null) ? "No Swc_Id":inactiveCandidates.get(i).getSwcId(),
                    (inactiveCandidates.get(i).getCreationDate() == null) ? "No Creation_date":inactiveCandidates.get(i).getCreationDate()
                    //(swachchagrahi.getJobStatus() == null) ? "No Details":swachchagrahi.getJobStatus()
            });
            counter++;
        }
        Set<Integer> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);

        XSSFCellStyle backgroundStyle = createColumnHeaderStyle(workbook);

        for (int i = 1; i < 12; i++) {
            spreadsheet.setColumnWidth(i, 4000);
        }
        spreadsheet.setColumnWidth(0,2000);

        int rowid=7;
        for (Integer key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if (rowid ==8 ){
                    row.setHeight((short) 1100);
                    cell.setCellStyle(backgroundStyle);
                }
                if(rowid==9 && inactiveCandidates.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:I9"));
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
        String course = "";
        Integer courseId =0;
        if ((reportRequest.getReportType().equals("SBM_Circle_Wise_Anonymous_Users") || reportRequest.getReportType().equals("SBM_Cumulative_Course_Completion"))){
            courseId = reportRequest.getCourseId();
            course = courseDao.findByCourseId(courseId).getName();
        }
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.createRow(rowid++);

        String encodingPrefix = "base64,";
        String pngImageURL = Constants.header_Pradan_base64;
        String pngImageURL1 = Constants.header_Pradan_base64;
        int contentStartIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
        int contentStartIndex1 = pngImageURL1.indexOf(encodingPrefix) + encodingPrefix.length();
        byte[] imageData = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStartIndex));//workbook.addPicture can use this byte array
        byte[] imageData1 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL1.substring(contentStartIndex1));

        final int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);
        final int pictureIndex1 = workbook.addPicture(imageData1,Workbook.PICTURE_TYPE_PNG);


        final CreationHelper helper = workbook.getCreationHelper();
        final Drawing drawing = spreadsheet.createDrawingPatriarch();

        final ClientAnchor anchor = helper.createClientAnchor();
        anchor.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );


        anchor.setCol1( 0 );
        anchor.setRow1( 0 );
        anchor.setRow2( 4 );
        anchor.setCol2( 8 );
        drawing.createPicture( anchor, pictureIndex );

        final ClientAnchor anchor1 = helper.createClientAnchor();
        anchor1.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );

        anchor1.setRow1(0);
        anchor1.setRow2(4);
        anchor1.setCol1(8);
        anchor1.setCol2(12);
        drawing.createPicture(anchor1,pictureIndex1);

        XSSFCellStyle style1 = workbook.createCellStyle();//Create style
        style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style1.setBorderBottom(CellStyle.BORDER_MEDIUM);

        spreadsheet.addMergedRegion(new CellRangeAddress(0,3,0,11));

        rowid = rowid+3;
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
        spreadsheet.addMergedRegion(new CellRangeAddress(4,5,0,0));
        spreadsheet.addMergedRegion(new CellRangeAddress(4,5,1,6));
        spreadsheet.addMergedRegion(new CellRangeAddress(4,5,7,7));
        spreadsheet.addMergedRegion(new CellRangeAddress(4,5,8,11));
        XSSFRow row1=spreadsheet.createRow(++rowid);
        Cell cell5=row1.createCell(0);
        Cell cell6=row1.createCell(1);
        Cell cell7=row1.createCell(5);
        Cell cell8=row1.createCell(6);
        Cell cell9=row1.createCell(9);
        Cell cell10=row1.createCell(10);
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
            } else {
                cell3.setCellValue("Month:");
                cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
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
            cell7.setCellStyle(style);
            cell8.setCellStyle(style);
            String blockName;
            if(reportRequest.getBlockId() !=0){
                blockName =blockDao.findByBlockId(reportRequest.getBlockId()).getBlockName();
            }else {
                blockName ="ALL";
            }
            cell9.setCellValue("Block:");
            cell10.setCellValue(blockName);
            cell9.setCellStyle(style);
            cell10.setCellStyle(style);
        }

        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);


        spreadsheet.addMergedRegion(new CellRangeAddress(6,6,0,0));
        spreadsheet.addMergedRegion(new CellRangeAddress(6,6,1,4));
        spreadsheet.addMergedRegion(new CellRangeAddress(6,6,5,5));
        spreadsheet.addMergedRegion(new CellRangeAddress(6,6,6,8));
        spreadsheet.addMergedRegion(new CellRangeAddress(6,6,9,9));
        spreadsheet.addMergedRegion(new CellRangeAddress(6,6,10,11));




        XSSFRow courseRow = spreadsheet.createRow(7);
        Cell cell11 = courseRow.createCell(0);
        Cell cell12 = courseRow.createCell(1);
        cell11.setCellValue("Course: ");
        cell12.setCellValue(course);
        cell11.setCellStyle(style);
        cell12.setCellStyle(style);
        spreadsheet.addMergedRegion(new CellRangeAddress(7,7,1,4));
        spreadsheet.addMergedRegion(new CellRangeAddress(7,7,5,11));


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

//    private void cleanBeforeMergeOnValidCells(XSSFSheet sheet,CellRangeAddress region, XSSFCellStyle cellStyle )
//    {
//        for(int rowNum =region.getFirstRow();rowNum<=region.getLastRow();rowNum++){
//            XSSFRow row= sheet.getRow(rowNum);
//            if(row==null){
//                row = sheet.createRow(rowNum);
//            }
//            for(int colNum=region.getFirstColumn();colNum<=region.getLastColumn();colNum++){
//                XSSFCell currentCell = row.getCell(colNum);
//                if(currentCell==null){
//                    currentCell = row.createCell(colNum);
//
//                }
//
//                currentCell.setCellStyle(cellStyle);
//
//            }
//        }
//
//
//    }


}
