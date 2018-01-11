package com.beehyv.wareporting.controller;

import com.beehyv.wareporting.business.AdminService;
import com.beehyv.wareporting.business.LocationService;
import com.beehyv.wareporting.business.ModificationTrackerService;
import com.beehyv.wareporting.business.UserService;
import com.beehyv.wareporting.dao.StateServiceDao;
import com.beehyv.wareporting.entity.PasswordDto;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.enums.AccessType;
import com.beehyv.wareporting.enums.ModificationType;
import com.beehyv.wareporting.enums.ReportType;
import com.beehyv.wareporting.model.ModificationTracker;
import com.beehyv.wareporting.model.State;
import com.beehyv.wareporting.model.User;
import com.beehyv.wareporting.utils.Global;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static com.beehyv.wareporting.enums.AccessType.MASTER_ADMIN;
import static com.beehyv.wareporting.enums.ReportType.waCourse;
import static com.beehyv.wareporting.utils.Global.retrieveDocuments;


/**
 * Created by beehyv on 15/3/17.
 */

@Controller
@RequestMapping("/wa/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private StateServiceDao stateServiceDao;

    @Autowired
    private ModificationTrackerService modificationTrackerService;

    private final String documents = retrieveDocuments();
    private final String reports = documents+"Reports/";

    @RequestMapping(value = "/uploadFile",headers=("content-type=multipart/*"), method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFileHandler(@RequestParam("bulkCsv") MultipartFile file) {
        String name = "BulkImportDatacr7ms10.csv";

        Map<Integer, String> responseMap = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = documents;
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                User user = userService.getCurrentUser();
                HashMap object = adminService.startBulkDataImport(user);
                return object;

                /*return "You successfully uploaded file=" + name;*/
            } catch (Exception e) {
                System.out.println(e);
                responseMap.put(0, "fail");
                responseMap.put(1, "You failed to upload " + file.getOriginalFilename());
                return responseMap;
            }
        } else {
            responseMap.put(0, "fail");
            responseMap.put(1, "You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
            return responseMap;
        }

    }

    @RequestMapping(value = "/getBulkDataImportCSV", method = RequestMethod.GET,produces = "application/vnd.ms-excel")
    @ResponseBody
    public String getBulkDataImportCSV(HttpServletResponse response) throws ParseException {

       response.setContentType("APPLICATION/OCTECT-STREAM");
        try {
            PrintWriter out=response.getWriter();
            String filename="BulkImportData.csv";
            response.setHeader("Content-Disposition","attachment;filename=\""+filename+"\"");
            String rootPath=documents+"BulkImportData.csv";
            File file=new File(rootPath);
            if(!(file.exists())){
                adminService.getBulkDataImportCSV();
            }
            FileInputStream fl=new FileInputStream(rootPath);
            int i;
            while ((i=fl.read())!=-1){
                out.write(i);
            }
            fl.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "Bulkimport";
    }

   /* @RequestMapping(value = "/getCumulativeCourseCompCSV", method = RequestMethod.GET)
    @ResponseBody
    public String getCumulativeCourseCompletion(@PathVariable("state") String State,@PathVariable("district") String District,@PathVariable("block") String Block) throws ParseException, java.text.ParseException{
        adminService.getCumulativeCourseCompletion(State,District,Block);
        return "Bulkimport";
    }*/

    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST)
    @ResponseBody public Map resetPassword(@RequestBody PasswordDto passwordDto){
        //        String trackModification = mapper.convertValue(node.get("modification"), String.class);
//
//        ModificationTracker modification = new ModificationTracker();
//        modification.setModificationDate(new Date(System.currentTimeMillis()));
//        modification.setModificationType(ModificationType.UPDATE.getModificationType());
//        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
//        modification.setModifiedUserId(user);
//        modification.setModifiedField(trackModification);
//        modificationTrackerService.saveModification(modification);

//        return "redirect:http://localhost:8080/app/#!/";
        Map<Integer, String> map= userService.updatePassword(passwordDto);
        if(map.get(0).equals("Password changed successfully")){
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
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String createFolders() throws ParseException {
        createAllFiles();
        return "Created Folders";
    }

    public void createAllFiles(){
        adminService.createFiles(waCourse.getReportType());
        adminService.createFolders(ReportType.waAnonymous.getReportType());
        adminService.createFiles(ReportType.waInactive.getReportType());
        adminService.createFiles(ReportType.swcRejected.getReportType());
    }

    @RequestMapping(value = "/generateReports/{reportType}/{relativeMonth}", method = RequestMethod.GET)
    @ResponseBody
    public String getReportsByNameAndMonth(@PathVariable("reportType") String reportType, @PathVariable("relativeMonth") Integer relativeMonth) throws ParseException, java.text.ParseException{
//        User user=userService.getCurrentUser();
//        if(user==null || ! (user.getRoleName().equals(AccessType.MASTER_ADMIN.getAccessType()))) {
//            return "You are not authorised";
//        }

        ReportType tempReportType = ReportType.valueOf(reportType);
        Calendar aCalendar = Calendar.getInstance();
        Date fromDate=new Date();
        Date toDate;
        if(tempReportType.getReportType().equals(ReportType.swcRejected.getReportType())) {
            aCalendar.add( Calendar.DAY_OF_WEEK, -(aCalendar.get(Calendar.DAY_OF_WEEK)-1));
            aCalendar.add(Calendar.DATE,-(7*(relativeMonth-1)));
            toDate=aCalendar.getTime();
        }else {
            aCalendar.add(Calendar.MONTH, (-1) * relativeMonth);
            aCalendar.set(Calendar.DATE, 1);
            aCalendar.set(Calendar.MILLISECOND, 0);
            aCalendar.set(Calendar.SECOND, 0);
            aCalendar.set(Calendar.MINUTE, 0);
            aCalendar.set(Calendar.HOUR_OF_DAY, 0);

            fromDate = aCalendar.getTime();

            aCalendar.add(Calendar.MONTH, 1);

            toDate = aCalendar.getTime();
        }

        switch (tempReportType) {
            case waCourse: {
                adminService.getCumulativeCourseCompletionFiles(toDate);
                break;
            }
            case waAnonymous: {
                adminService.getCircleWiseAnonymousFiles(fromDate, toDate);
                break;
            }
            case waInactive:{
                adminService.getCumulativeInactiveFiles(toDate);
                break;
            }
            case swcRejected:{
                adminService.createSwcImportRejectedFiles(toDate);
                break;
            }

        }
        return "Reports Generated";
    }

    @RequestMapping(value = {"/state/{serviceType}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<State> getStatesByServiceType(@PathVariable("serviceType") String serviceType) {

        List<State> states= locationService.getStatesByServiceType(serviceType);

        return states;
    }

    @RequestMapping(value = {"/state/{serviceType}/{stateId}"}, method = RequestMethod.GET)
    public @ResponseBody
    Date getStateServiceStartDate(@PathVariable("serviceType") String serviceType,@PathVariable("stateId") Integer stateId) {


        return locationService.getServiceStartDateForState(stateId, serviceType);
    }


}
