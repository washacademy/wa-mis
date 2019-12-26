package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.*;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.entity.EmailInfo;
import com.beehyv.wareporting.entity.ReportRequest;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.enums.ReportType;
import com.beehyv.wareporting.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.util.*;

import static com.beehyv.wareporting.utils.ServiceFunctions.getMonthYear;

/**
 * Created by beehyv on 25/5/17.
 */

@Service("emailService")
@Transactional
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ServletContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private EmailTrackerService emailTrackerService;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private WACourseAttemptDao waCourseAttemptDao;

    @Autowired
    private SwachchagrahiDao SwachchagrahiDao;

    @Autowired
    private SwcImportRejectionDao swcImportRejectionDao;

    @Override
    public String sendMail(EmailInfo mailInfo) {
        try {
            final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
            Properties props = ms.getJavaMailProperties();
            final String username = ms.getUsername();
            final String password = ms.getPassword();
            //need authenticate to server
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailInfo.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailInfo.getTo()));
            message.setSubject(mailInfo.getSubject(), "UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailInfo.getBody());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String filename = mailInfo.getRootPath();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(mailInfo.getFileName());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            return "success";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "failure";
        }
    }

    private String sendMailWithStatistics(EmailInfo emailInfo) {
        try {
            final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
            Properties props = ms.getJavaMailProperties();
            final String username = ms.getUsername();
            final String password = ms.getPassword();
            //need authenticate to server
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailInfo.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailInfo.getTo()));
            message.setSubject(emailInfo.getSubject(),"UTF-8");
            message.setContent(emailInfo.getBody(),"text/html; charset=utf-8");
            Transport.send(message);
            return "success";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "failure";
        }
    }

    private String sendMailWithMultipleAttachments(EmailInfo mailInfo) {
        try {
            final JavaMailSenderImpl ms = (JavaMailSenderImpl) mailSender;
            Properties props = ms.getJavaMailProperties();
            final String username = ms.getUsername();
            final String password = ms.getPassword();
            //need authenticate to server
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailInfo.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailInfo.getTo()));
            message.setSubject(mailInfo.getSubject(),"UTF-8");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mailInfo.getBody());
            Multipart multipart = new MimeMultipart();
            List<String> filePaths=new ArrayList<>();
            filePaths.add(mailInfo.getRootPath());
            filePaths.add(mailInfo.getRootPath2());
            addAttachment(multipart,filePaths.get(0), mailInfo.getFileName());
            addAttachment(multipart,filePaths.get(1), mailInfo.getFileName2());
            message.setContent(multipart);
            Transport.send(message);
            return "success";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "failure";
        }
    }

    @Override
    public String getBody(String reportName,String place, String monthAndYear, String name) {
        String body = "";
        body+= "Dear "+name+",\n\n";
        /*body+= "This is the "+reportName+" Report of "+place+" for the month "+monthAndYear+ ".\n \n";
        body+= "Thank You.\n";
        body+= "NSP Support Team \n \n \n";
        body+= "P.S: This an auto-generated email. Please do not reply";*/
        if(reportName.equalsIgnoreCase(ReportType.waCourseCompletion.getReportName())) {
            body+="\tPlease find attached the list of Swachchagrahis who have completed the WASH Academy course.\n\n" +
                    "This is for your information.\n\n";
        }
        else if(reportName.equalsIgnoreCase(ReportType.waCircleWiseAnonymous.getReportName())) {
            body+="\tPlease find attached the list of anonymous callers to the WASH Academy course from the telecom " +
                    "circle of " + place + ". We presume that these numbers are used by Swachchagrahis working in your state/UT but " +
                    "have not been registered in MDWS Application. Please contact these numbers and if they belong " +
                    "to a registered Swachchagrahi in " + place + " then please tell them to either use their registered number " +
                    "to access the WASH Academy course or register their correct numbers in the MDWS Application so " +
                    "that they can access the WASH Academy course.\n\n" +
                    "This is for your information.\n\n";
        }
        else if(reportName.equalsIgnoreCase(ReportType.waInactive.getReportName())) {
            body+="\tPlease find attached the list of Swachchagrahis who have not yet started the WASH Academy course.\n\n" +
                    "\tYou are requested to kindly instruct your field level workers and ask them to start accessing " +
                    "the WASH Academy course and complete the course which has been designed to provide effective " +
                    "training for their operations.\n\n";
        }
        else if(reportName.equalsIgnoreCase(ReportType.swcRejected.getReportName()))
        {
            body+="\tPlease find attached the list of Swachchagrahis rejected due to invalid or duplicate mobile number and missing information.\n\n" +
                    "\tYou are requested to kindly instruct your field level workers and ask them to provide their mobile " +
                    "numbers through which they could call the WASH Academy course and update those mobile numbers.\n\n";
        }
        body+="Regards\n";
        body+= "NSP Support Team \n \n \n";
        body+= "P.S: This an auto-generated email. Please do not reply";
        return body;
    }

    private String getBodyForStateLevelUsers(String reportType, Integer stateId, String  name, Integer courseId) {
        String body= "Dear "+name +",<br>";
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH,1);

        Date toDate = calendar.getTime();
        if(reportType.equals(ReportType.swcRejected.getReportType())){
            calendar.add(Calendar.DAY_OF_MONTH, -7);
        } else {
            calendar.add(Calendar.MONTH, -1);
        }
        Date fromDate=calendar.getTime();

        List<District> districts=districtDao.getDistrictsOfState(stateId);
        if(reportType.equals(ReportType.waCourseCompletion.getReportType())){
            body+= "<pre>   </pre>Please find below the district wise count of Swachchagrahis who have successfully completed the WASH Academy" +
                    " course. The line listing of the Swachchagrahis have been sent to the respective district and block users.";

            body+=  "<br><br><table width='100%' border='1' align='center'>"
                    + "<tr align='center'>"
                    + "<td><b>District Name<b></td>"
                    + "<td><b>Count of Swachchagrahis who have successfully completed the course.<b></td>"
                    + "</tr>";
            for (District district:districts
                    ) {

                body=body+"<tr align='center'>"+"<td>" + district.getDistrictName() + "</td>"
                        + "<td>" +waCourseAttemptDao.getCountForGivenDistrict(toDate, district.getDistrictId(), courseId)+ "</td>"+"</tr>";
            }
        } else if(reportType.equals(ReportType.waInactive.getReportType())){
            body+="<pre>   </pre>Please find below the district wise count of Swachchagrahis who have not yet started the Any WASH Academy course." +
                    " The line listing of the Swachchagrahis have been sent to the respective district and block users.";

            body += "<br><br><pre>   </pre>You are requested to kindly instruct your field level workers and ask them to start accessing the WASH Academy " +
                    "courses and complete the courses which has been designed to provide effective training for their operations.";

            body += "<br><br><table width='100%' border='1' align='center'>"
                    + "<tr align='center'>"
                    + "<td><b>District Name<b></td>"
                    + "<td><b>Count of Inactive Swachchagrahis.<b></td>"
                    + "</tr>";

            for (District district:districts
                    ) {

                body=body+"<tr align='center'>"+"<td>" + district.getDistrictName() + "</td>"
                        + "<td>" + SwachchagrahiDao.getCountOfInactiveSwachchagrahisForGivenDistrict(toDate, district.getDistrictId())+ "</td>"+"</tr>";
            }
        } else if (reportType.equals(ReportType.swcRejected.getReportType())) {
            body+="<pre>   </pre>Please find below the district wise count of Swachchagrahis who were rejected for the following reasons -" +
                    "<br>invalid or duplicate mobile number and missing information. " +
                    "<br>The line listing of the Swachchagrahis have been sent to the respective district and block users.";

            body += "<br><br><pre>   </pre>You are requested to kindly instruct your field level workers and ask them to provide their mobile numbers" +
                    " through which they could call the WASH Academy course and update those mobile numbers in the RCH application.";

            body+= "<br><br><table width='100%' border='1' align='center'>"
                    + "<tr align='center'>"
                    + "<td><b>District Name<b></td>"
                    + "<td><b>Count of Rejected Swachchagrahis Records<b></td>"
                    + "</tr>";
            for (District district:districts
                    ) {
                body=body+"<tr align='center'>"+"<td>" + district.getDistrictName() + "</td>"
                        + "<td>" +swcImportRejectionDao.getCountOfSwcRejectedRecordsForDistrict(fromDate, toDate, district.getDistrictId())+ "</td>"
                        +"</tr>";
            }

        }

        body+="</table><br>Regards<br>";
        body+= "NSP Support Team <br><br>";
        body+= "P.S: This an auto-generated email. Please do not reply";
        return body;
    }
    /* for district users, we send mail with attachments and for state users, we send summary mails with district-wise details*/
    @Override
    public HashMap<String, String> sendAllMails(ReportType reportType) {
        List<User> users = userService.findAllActiveUsers();
        HashMap<String,String> errorSendingMail = new HashMap<>();
        for(User user: users){
            EmailInfo newMail = new EmailInfo();
            newMail.setFrom("nsp-reports@beehyv.com");
            newMail.setTo(user.getEmailId());
//                for(ReportType reportType: ReportType.values()){
            ReportRequest reportRequest = new ReportRequest();
            Calendar c = Calendar.getInstance();   // this takes current date

            /* if the report is rejection type, we need to find file name with previous sunday date(MM-DD-YY format)
            * else find file with previous month and year(MM-YY format) */

            /* previous sunday */
            if(reportType.getReportType().equals(ReportType.swcRejected.getReportType())) {
                c.add( Calendar.DAY_OF_WEEK, -(c.get(Calendar.DAY_OF_WEEK)-1));
                reportRequest.setToDate(c.getTime());

            }else {
                /* previous month */
                c.add(Calendar.MONTH, -1);
                c.set(Calendar.DATE, 1);
                reportRequest.setToDate(c.getTime());
            }
            reportRequest.setReportType(reportType.getReportType());
            String pathName = "",fileName = "",errorMessage = "",place = "";
            if(reportType.getReportType().equalsIgnoreCase(ReportType.waCircleWiseAnonymous.getReportType())){
                if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
                    List<Circle> stateCircleList = reportService.getUserCircles(user);
                    for(Circle circle: stateCircleList){
                        reportRequest.setCircleId(circle.getCircleId());
                        pathName = reportService.getReportPathName(reportRequest).get(1);
                        fileName = reportService.getReportPathName(reportRequest).get(0);
                        newMail.setSubject(fileName);
                        newMail.setFileName(fileName);
                        place = getStatesNamesByCircle(circle);
                        newMail.setBody(this.getBody(reportType.getReportName(),place,reportService.getMonthName(c.getTime()),user.getFullName()));
                        newMail.setRootPath(pathName);
                        errorMessage = this.sendMail(newMail);
                        EmailTracker emailTracker=new EmailTracker();
                        emailTracker.setEmailSuccessful(true);
                        if (errorMessage.equalsIgnoreCase("failure")){
                            errorMessage = this.sendMail(newMail);
                        }
                        if (errorMessage.equalsIgnoreCase("failure")){
                            errorMessage = this.sendMail(newMail);
                        }
                        if (errorMessage.equalsIgnoreCase("failure")) {
                            errorSendingMail.put(user.getUsername(), fileName);
                            emailTracker.setEmailSuccessful(false);
                        }
                        emailTracker.setFileName(fileName);
                        emailTracker.setReportType(reportType.getReportName());
                        emailTracker.setTime(new Date());
                        emailTracker.setUserId(user.getUserId());
                        emailTrackerService.saveEmailDetails(emailTracker);
                    }
                }
            } else {
                place = "NATIONAL";
                if (user.getStateId() == null)
                    reportRequest.setStateId(0);
                else {
                    reportRequest.setStateId(user.getStateId());
                    place = locationService.findStateById(user.getStateId()).getStateName()+" State";
                }
                if (user.getDistrictId() == null)
                    reportRequest.setDistrictId(0);
                else {
                    reportRequest.setDistrictId(user.getDistrictId());
                    place = locationService.findDistrictById(user.getDistrictId()).getDistrictName()+" District";
                }
                if (user.getBlockId() == null)
                    reportRequest.setBlockId(0);
                else {
                    reportRequest.setBlockId(user.getBlockId());
                    place = locationService.findBlockById(user.getBlockId()).getBlockName()+" Block";
                }
                List<String> fileDetails=reportService.getReportPathName(reportRequest);
                pathName = fileDetails.get(1);
                fileName = fileDetails.get(0);
                newMail.setSubject(fileName);
                newMail.setFileName(fileName);
                newMail.setBody(this.getBody(reportType.getReportName(), place, reportService.getMonthName(c.getTime()), user.getFullName()));
                newMail.setRootPath(pathName);
                if (user.getDistrictId() != null) {
                    errorMessage = this.sendMail(newMail);
                    EmailTracker emailTracker = new EmailTracker();
                    emailTracker.setEmailSuccessful(true);
                    emailTracker.setFileName(fileName);
                    emailTracker.setReportType(reportType.getReportName());
                    emailTracker.setTime(new Date());
                    emailTracker.setUserId(user.getUserId());
                    if (errorMessage.equalsIgnoreCase("failure")) {
                        errorMessage = this.sendMail(newMail);
                    }
                    if (errorMessage.equalsIgnoreCase("failure")) {
                        errorMessage = this.sendMail(newMail);
                    }
                    if (errorMessage.equalsIgnoreCase("failure")) {
                        emailTracker.setEmailSuccessful(false);
                    }
                    emailTrackerService.saveEmailDetails(emailTracker);

                } else if(user.getStateId()!=null)  {
                    newMail.setBody(this.getBodyForStateLevelUsers(reportType.getReportType(), user.getStateId(), user.getFullName(), reportRequest.getCourseId()));
                    errorMessage=sendMailWithStatistics(newMail);
                    EmailTracker emailTracker = new EmailTracker();
                    emailTracker.setEmailSuccessful(true);
                    emailTracker.setFileName(fileName);
                    emailTracker.setReportType(reportType.getReportName());
                    emailTracker.setTime(new Date());
                    emailTracker.setUserId(user.getUserId());
                    if (errorMessage.equalsIgnoreCase("failure")) {
                        errorMessage =sendMailWithStatistics(newMail);
                    }
                    if (errorMessage.equalsIgnoreCase("failure")) {
                        errorMessage = sendMailWithStatistics(newMail);
                    }
                    if (errorMessage.equalsIgnoreCase("failure")) {
                        emailTracker.setEmailSuccessful(false);
                    }
                    emailTrackerService.saveEmailDetails(emailTracker);
                }


            }
            if (errorMessage.equalsIgnoreCase("failure"))
                errorSendingMail.put(user.getUsername(), fileName);
        }
        return errorSendingMail;
    }

    private static void addAttachment(Multipart multipart, String filePath, String fileName) {
        DataSource source = new FileDataSource(filePath);
        BodyPart messageBodyPart = new MimeBodyPart();

        try {
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    private String getStatesNamesByCircle(Circle circle){
        List<State> states = locationService.getStatesOfCircle(circle);
        String placeNames = "";
        if(states.size()>1){
            placeNames += states.get(0).getStateName() + " and " + states.get(1).getStateName() + " regions";
        } else {
            placeNames += states.get(0).getStateName() + " region";
        }
        return placeNames;
    }
}