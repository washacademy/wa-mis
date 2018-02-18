package com.beehyv.wareporting.controller;

import com.beehyv.wareporting.business.*;
import com.beehyv.wareporting.dao.BlockDao;
import com.beehyv.wareporting.dao.DistrictDao;
import com.beehyv.wareporting.dao.PanchayatDao;
import com.beehyv.wareporting.dao.StateDao;
import com.beehyv.wareporting.entity.*;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.enums.AccessType;
import com.beehyv.wareporting.enums.ModificationType;
import com.beehyv.wareporting.enums.ReportType;
import com.beehyv.wareporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.beehyv.wareporting.utils.Global.retrieveDocuments;
import static com.beehyv.wareporting.utils.ServiceFunctions.StReplace;
import static com.beehyv.wareporting.utils.ServiceFunctions.dateAdder;

/**
 * Created by beehyv on 15/3/17.
 */
@Controller
@RequestMapping("/wa/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WAAggregateReportsService waAggregateReportsService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ModificationTrackerService modificationTrackerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private PanchayatDao panchayatDao;

    @Autowired
    private WAPerformanceService waPerformanceService;

    @Autowired
    private BreadCrumbService breadCrumbService;

    private final Date bigBang = new Date(0);
    private final String documents = retrieveDocuments();
    private final String reports = documents + "Reports/";
    private Calendar c = Calendar.getInstance();

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public @ResponseBody
    List<User> getAllUsers() {
        return userService.findAllActiveUsers();
    }


    @RequestMapping(value = {"/myUserList"})
    public @ResponseBody
    List<User> getMyUsers() {
        return userService.findMyUsers(userService.getCurrentUser());
    }

    @RequestMapping(value = {"/roles"})
    public @ResponseBody
    List<Role> getRoles() {
        return roleService.getRoles();
    }

    @RequestMapping(value = {"/currentUser"})
    public @ResponseBody
    User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @RequestMapping(value = {"/profile"})
    public @ResponseBody
    UserDto profile() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            UserDto user1 = new UserDto();
            user1.setId(currentUser.getUserId());
            user1.setName(currentUser.getFullName());
            user1.setUsername(currentUser.getUsername());
            user1.setEmail(currentUser.getEmailId());
            user1.setPhoneNumber(currentUser.getPhoneNumber());
            user1.setAccessLevel(currentUser.getAccessLevel());
            if (currentUser.getStateId() != null) {
                user1.setState(locationService.findStateById(currentUser.getStateId()).getStateName());
            } else {
                user1.setState("");
            }
            if (currentUser.getDistrictId() != null) {
                user1.setDistrict(locationService.findDistrictById(currentUser.getDistrictId()).getDistrictName());
            } else {
                user1.setDistrict("");
            }
            if (currentUser.getBlockId() != null) {
                user1.setBlock(locationService.findBlockById(currentUser.getBlockId()).getBlockName());
            } else {
                user1.setBlock("");
            }
            user1.setAccessType(currentUser.getRoleName());
            user1.setCreatedBy(true);
            return user1;
        }
        return null;
    }

    @RequestMapping(value = {"/isLoggedIn"})
    public @ResponseBody
    Boolean isLoggedIn() {
        return userService.getCurrentUser() != null;
    }

    @RequestMapping(value = {"/isAdminLoggedIn"})
    public @ResponseBody
    Boolean isAdminLoggedIn() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null || currentUser.getRoleName().equals(AccessType.USER.getAccessType())) {
            return false;
        }
        return true;
    }

    //To be changed
    @RequestMapping(value = {"/tableList"})
    public @ResponseBody
    List<UserDto> getTableList() {
        List<UserDto> tabDto = new ArrayList<>();
        List<User> tabUsers = userService.findMyUsers(userService.getCurrentUser());
        String[] levels = {"National", "State", "District", "Block"};
        for (User user : tabUsers) {
            UserDto user1 = new UserDto();
            user1.setId(user.getUserId());
            user1.setName(user.getFullName());
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmailId());
            user1.setPhoneNumber(user.getPhoneNumber());
            user1.setAccessLevel(user.getAccessLevel());
            if (user.getStateId() == null) {
                user1.setState("");
            } else {
                user1.setState(user.getStateName());
            }
            if (user.getDistrictId() == null) {
                user1.setDistrict("");
            } else {
                user1.setDistrict(user.getDistrictName());
            }
            if (user.getBlockId() == null) {
                user1.setBlock("");
            } else {
                user1.setBlock(user.getBlockName());
            }
            user1.setAccessType(user.getRoleName());
            int a;
            try {
                a = user.getCreatedByUser().getUserId();
            } catch (NullPointerException e) {
                a = 0;
            }
            int b = getCurrentUser().getUserId();
            user1.setCreatedBy(a == b || getCurrentUser().getRoleId() == 1);
            tabDto.add(user1);

        }
        return tabDto;
    }

    @RequestMapping(value = {"/user/{userId}"})
    public @ResponseBody
    User getUserById(@PathVariable("userId") Integer userId) {
        return userService.findUserByUserId(userId);
    }



    @RequestMapping(value = {"/createUser"}, method = RequestMethod.POST)
    @ResponseBody
    public Map<Integer, String> createNewUser(@RequestBody User user) {

        user = locationService.SetLocations(user);
        Map<Integer, String> map = userService.createNewUser(user);
        if (map.get(0).equals("User Created")) {
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.CREATE.getModificationType());
            modification.setModifiedUserId(userService.findUserByUsername(user.getUsername()).getUserId());
            modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
            modificationTrackerService.saveModification(modification);
        }
        return map;
    }

    @RequestMapping(value = {"/updateUser"}, method = RequestMethod.POST)
    @ResponseBody
    public Map updateExistingUser(@RequestBody User user) {

        user = locationService.SetLocations(user);
        User oldUser = userService.findUserByUserId(user.getUserId());
        Map<Integer, String> map = userService.updateExistingUser(user);
        if (map.get(0).equals("User Updated")) {
            userService.TrackModifications(oldUser, user);
        }
        return map;
    }

    @RequestMapping(value = {"/updateContacts"}, method = RequestMethod.POST)
    @ResponseBody
    public Map updateContacts(@RequestBody ContactInfo contactInfo) {
        User user = userService.findUserByUserId(contactInfo.getUserId());
        Map<Integer, String> map = userService.updateContacts(contactInfo);
        if (map.get(0).equals("Contacts Updated")) {
            TrackContactInfoModifications(user, contactInfo);
        }
        return map;
    }

    private void TrackContactInfoModifications(User oldUser, ContactInfo contactInfo) {
        if (!oldUser.getEmailId().equals(contactInfo.getEmail())) {
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.UPDATE.getModificationType());
            modification.setModifiedField("email_id");
            modification.setPreviousValue(oldUser.getEmailId());
            modification.setNewValue(contactInfo.getEmail());
            modification.setModifiedUserId(oldUser.getUserId());
            modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
            modificationTrackerService.saveModification(modification);
        }
        if (!oldUser.getPhoneNumber().equals(contactInfo.getPhoneNumber())) {
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.UPDATE.getModificationType());
            modification.setModifiedField("phone_no");
            modification.setPreviousValue(oldUser.getPhoneNumber());
            modification.setNewValue(contactInfo.getPhoneNumber());
            modification.setModifiedUserId(oldUser.getUserId());
            modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
            modificationTrackerService.saveModification(modification);
        }
    }

    @RequestMapping(value = {"/resetPassword"}, method = RequestMethod.POST)
    @ResponseBody
    public Map resetPassword(@RequestBody PasswordDto passwordDto) {

        User user = userService.findUserByUserId(passwordDto.getUserId());
        Map<Integer, String> map = userService.changePassword(passwordDto);
        if (map.get(0).equals("Password changed successfully")) {
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.UPDATE.getModificationType());
            modification.setModifiedUserId(passwordDto.getUserId());
            modification.setModifiedField("password");
            modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
            modificationTrackerService.saveModification(modification);
        }
        return map;
    }


    @RequestMapping(value = {"/forgotPassword"}, method = RequestMethod.POST)
    @ResponseBody
    public Map forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {

        User user = userService.findUserByUsername(forgotPasswordDto.getUsername());
        Map<Integer, String> map = userService.forgotPasswordCredentialChecker(forgotPasswordDto);
        if (map.get(0).equals("Password changed successfully")) {
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.UPDATE.getModificationType());
            modification.setModifiedUserId(user.getUserId());
            modification.setModifiedField("password");
            modification.setModifiedByUserId(user.getUserId());
            modificationTrackerService.saveModification(modification);
        }
        return map;
    }


    @RequestMapping(value = {"/deleteUser/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Map deleteExistingUser(@PathVariable("id") Integer id) {

        Map<Integer, String> map = userService.deleteExistingUser(id);
        if (map.get(0).equals("User deleted")) {
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.DELETE.getModificationType());
            modification.setModifiedField("account_status");
            modification.setModifiedUserId(id);
            modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
            modificationTrackerService.saveModification(modification);
        }
        return map;
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.POST/*,produces = "application/vnd.ms-excel"*/)
    @ResponseBody
    @Transactional
    public Object getReport(@RequestBody ReportRequest reportRequest/*,HttpServletResponse response*/) throws ParseException, java.text.ParseException {
        String reportPath = "";
        String reportName = "";
        String rootPath = "";
        String place = AccessLevel.NATIONAL.getAccessLevel();

        Integer stateId = reportRequest.getStateId();
        Integer districtId = reportRequest.getDistrictId();
        Integer blockIdId = reportRequest.getBlockId();
        Integer circleId = reportRequest.getCircleId();

        Map<String, String> m = new HashMap<>();
        User currentUser = userService.getCurrentUser();
        List<BreadCrumbDto> breadCrumbs = breadCrumbService.getBreadCrumbs(currentUser, reportRequest);
        AggregateResponseDto aggregateResponseDto;

        if (currentUser.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel()) && !currentUser.getStateId().equals(reportRequest.getStateId())) {
            m.put("status", "fail");
            return m;
        }
        if (currentUser.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel()) && !currentUser.getDistrictId().equals(reportRequest.getDistrictId())) {
            m.put("status", "fail");
            return m;
        }
        if (currentUser.getAccessLevel().equals(AccessLevel.BLOCK.getAccessLevel()) && !currentUser.getBlockId().equals(reportRequest.getBlockId())) {
            m.put("status", "fail");
            return m;
        }

        if (reportRequest.getReportType().equals(ReportType.waPerformance.getReportType())) {

            Date fromDate = dateAdder(reportRequest.getFromDate(), 0);
            Date toDate = dateAdder(reportRequest.getToDate(), 1);

            aggregateResponseDto = waAggregateReportsService.getWAPerformanceReport(fromDate, toDate, circleId,stateId, districtId, blockIdId);
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.waSubscriber.getReportType())) {

            Date fromDate = dateAdder(reportRequest.getFromDate(), 0);
            Date toDate = dateAdder(reportRequest.getToDate(), 1);

            aggregateResponseDto = waAggregateReportsService.getWASubscriberReport(fromDate, toDate, circleId, stateId, districtId, blockIdId);
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.waCumulativeSummary.getReportType())) {

            Date toDate = dateAdder(reportRequest.getToDate(), 1);

            aggregateResponseDto = waAggregateReportsService.getWACumulativeSummaryReport(toDate, circleId, stateId, districtId, blockIdId);
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateResponseDto;

        }

        if (reportRequest.getReportType().equals(ReportType.waAnonymousSummary.getReportType())) {

            Date fromDate = dateAdder(reportRequest.getFromDate(), 0);
            Date toDate = dateAdder(reportRequest.getToDate(), 1);

            aggregateResponseDto = waAggregateReportsService.getWAAnonymousSummaryReport(fromDate, toDate, circleId);
            aggregateResponseDto.setBreadCrumbData(breadCrumbs);
            return aggregateResponseDto;
        }

        if (reportRequest.getReportType().equals(ReportType.waCircleWiseAnonymous.getReportType())) {
            if (!currentUser.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel()) && reportRequest.getCircleId() == 0) {
                m.put("status", "fail");
                return m;
            }

            if (reportRequest.getCircleId() != 0) {
                place = StReplace(locationService.findCircleById(reportRequest.getCircleId()).getCircleFullName());
                rootPath += place + "/";
            }
        } else {
            if (currentUser.getAccessLevel().equals(AccessLevel.STATE.getAccessLevel()) && !currentUser.getStateId().equals(reportRequest.getStateId())) {
                m.put("status", "fail");
                return m;
            }
            if (currentUser.getAccessLevel().equals(AccessLevel.DISTRICT.getAccessLevel()) && !currentUser.getDistrictId().equals(reportRequest.getDistrictId())) {
                m.put("status", "fail");
                return m;
            }
            if (currentUser.getAccessLevel().equals(AccessLevel.BLOCK.getAccessLevel()) && !currentUser.getBlockId().equals(reportRequest.getBlockId())) {
                m.put("status", "fail");
                return m;
            }

            if (reportRequest.getStateId() != 0) {
                place = StReplace(locationService.findStateById(reportRequest.getStateId()).getStateName());
                rootPath += place + "/";
            }

            if (reportRequest.getDistrictId() != 0) {
                place = StReplace(locationService.findDistrictById(reportRequest.getDistrictId()).getDistrictName());
                rootPath += place + "/";
            }

            if (reportRequest.getBlockId() != 0) {
                place = StReplace(locationService.findBlockById(reportRequest.getBlockId()).getBlockName());
                rootPath += place + "/";
            }
        }
        String filename = reportRequest.getReportType() + "_" + place + "_" + getMonthYear(reportRequest.getFromDate()) + ".xlsx";

        if (reportRequest.getReportType().equals(ReportType.swcRejected.getReportType())) {
            filename = reportRequest.getReportType() + "_" + place + "_" + getDateMonthYear(reportRequest.getFromDate()) + ".xlsx";
        }
        reportPath = reports + reportRequest.getReportType() + "/" + rootPath;
        reportName = filename;

        File file = new File(reportPath + reportName);
        if (!(file.exists())) {
            adminService.createSpecificReport(reportRequest);
        }

        m.put("status", "success");
        m.put("file", reportName);
        m.put("path", reportRequest.getReportType() + "/" + rootPath);
        return m;
    }


    @RequestMapping(value = "/downloadReport", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
    @ResponseBody
    public String getBulkDataImportCSV(HttpServletResponse response, @DefaultValue("") @QueryParam("fileName") String fileName,
                                       @DefaultValue("") @QueryParam("rootPath") String rootPath) throws ParseException {
//        adminService.getBulkDataImportCSV();
        response.setContentType("APPLICATION/OCTECT-STREAM");
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(rootPath)) {
            fileName = "";
            rootPath = "";
            return "fail";
        }
        String reportName = fileName;
        String reportPath = reports + rootPath;
        try {
            ServletOutputStream out = response.getOutputStream();
            String filename = reportName;
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            FileInputStream fl = new FileInputStream(reportPath + reportName);
            int i;
            while ((i = fl.read()) != -1) {
                out.write(i);
            }
            fl.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = {"/reportsMenu"})
    public @ResponseBody
    List<Map<String, Object>> getReportsMenu() {
        User currentUser = userService.getCurrentUser();
        Map<String, Object> maMenu = new HashMap<>();
        maMenu.put("name", "WASH Academy Reports");
        maMenu.put("icon", "images/drop-down-3.png");

        List<Report> maList = new ArrayList<>();

        maList.add(new Report(
                ReportType.waCumulativeSummary.getReportName(),
                ReportType.waCumulativeSummary.getReportType(),
                "images/drop-down-2.png",
                ReportType.waCumulativeSummary.getServiceType())
        );

        maList.add(new Report(
                ReportType.waPerformance.getReportName(),
                ReportType.waPerformance.getReportType(),
                "images/drop-down-2.png",
                ReportType.waPerformance.getServiceType())
        );

        maList.add(new Report(
                ReportType.waSubscriber.getReportName(),
                ReportType.waSubscriber.getReportType(),
                "images/drop-down-2.png",
                ReportType.waSubscriber.getServiceType())
        );

        maList.add(new Report(
                ReportType.waAnonymousSummary.getReportName(),
                ReportType.waAnonymousSummary.getReportType(),
                "images/drop-down-2.png",
                ReportType.waAnonymousSummary.getServiceType())
        );


        maList.add(new Report(
                ReportType.waCourseCompletion.getReportName(),
                ReportType.waCourseCompletion.getReportType(),
                "images/drop-down-3.png",
                ReportType.waCourseCompletion.getServiceType())
        );

        maList.add(new Report(
                ReportType.waCircleWiseAnonymous.getReportName(),
                ReportType.waCircleWiseAnonymous.getReportType(),
                "images/drop-down-3.png",
                ReportType.waCircleWiseAnonymous.getServiceType())
        );

        maList.add(new Report(
                ReportType.waInactive.getReportName(),
                ReportType.waInactive.getReportType(),
                "images/drop-down-3.png",
                ReportType.waInactive.getServiceType())
        );

        maList.add(new Report(
                ReportType.swcRejected.getReportName(),
                ReportType.swcRejected.getReportType(),
                "images/drop-down-3.png",
                ReportType.swcRejected.getServiceType())
        );



        maMenu.put("service", maList.get(0).getService());
        maMenu.put("options", maList);

        List<Map<String, Object>> l = new ArrayList<>();

        /*if (currentUser.getAccessLevel().equals(AccessLevel.NATIONAL.getAccessLevel())) {
            l.add(maMenu);
        } */

        l.add(maMenu);
        return l;
    }

    @RequestMapping(value = {"/createMaster"}, method = RequestMethod.GET)
    @ResponseBody
    String createNewUser() {
//
////        ModificationTracker modification = new ModificationTracker();
////        modification.setModificationDate(new Date(System.currentTimeMillis()));
////        modification.setModificationDescription("Account creation");
////        modification.setModificationType(ModificationType.CREATE.getModificationType());
////        modification.setModifiedUserId(user);
////        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
////        modificationTrackerService.saveModification(modification);
//
        return userService.createMaster();
    }

    private String getMonthYear(Date toDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(toDate);
        int month = c.get(Calendar.MONTH) + 1;
        String monthString = "";
        int year = (c.get(Calendar.YEAR)) % 100;
        //        String monthString = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        if (month < 10) {
            monthString = "0" + String.valueOf(month);
        } else monthString = String.valueOf(month);

        String yearString = String.valueOf(year);

        return monthString + "_" + yearString;
    }

    private String getDateMonthYear(Date toDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate);
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = (calendar.get(Calendar.YEAR)) % 100;
        String dateString;
        if (date < 10) {
            dateString = "0" + String.valueOf(date);
        } else dateString = String.valueOf(date);
        String monthString;
        if (month < 10) {
            monthString = "0" + String.valueOf(month);
        } else monthString = String.valueOf(month);

        String yearString = String.valueOf(year);

        return dateString + "_" + monthString + "_" + yearString;

    }

}
