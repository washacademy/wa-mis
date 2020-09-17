package com.beehyv.wareporting.business.impl;

import com.beehyv.wareporting.business.ReportService;
import com.beehyv.wareporting.dao.*;
import com.beehyv.wareporting.entity.ReportRequest;
import com.beehyv.wareporting.enums.AccessLevel;
import com.beehyv.wareporting.enums.ReportType;
import com.beehyv.wareporting.model.Circle;
import com.beehyv.wareporting.model.Course;
import com.beehyv.wareporting.model.StateCircle;
import com.beehyv.wareporting.model.User;
import com.beehyv.wareporting.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.*;

import static com.beehyv.wareporting.utils.Global.retrieveCourseIdsFromFileLocationProperties;
import static com.beehyv.wareporting.utils.Global.retrieveDocuments;
import static com.beehyv.wareporting.utils.ServiceFunctions.StReplace;

/**
 * Created by beehyv on 25/5/17.
 */
@Service("reportService")
@Transactional
public class ReportServiceImpl implements ReportService{

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private CircleDao circleDao;

    @Autowired
    private StateCircleDao stateCircleDao;

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Autowired
    private CourseDao courseDao;



    private final String documents = retrieveDocuments();
    private final String reports = documents+"Reports/";

    @Override
    public ArrayList<Course> getCourses(){
        Integer[] courseIds = retrieveCourseIdsFromFileLocationProperties();
        ArrayList<Course> courses= new ArrayList<Course>();
        for(int i=0;i<courseIds.length;i++){
            Course course =  courseDao.findByCourseId(courseIds[i]);
            course.setPassingScore(null);
            course.setContent(null);
            course.setNoOfChapters(null);
            courses.add(course);
        }
        return courses;
    }

    @Override
    public Course getCourseByCourseId(Integer courseId) {
        return courseDao.findByCourseId(courseId);
    }


    @Override
    public List<String> getReportPathName(ReportRequest reportRequest) {
        String rootPath = "";
        String place = "NATIONAL";

        if(reportRequest.getReportType().equals(ReportType.waCircleWiseAnonymous.getReportType())){
            if(reportRequest.getCircleId()!=0){
                place=StReplace(circleDao.getByCircleId(reportRequest.getCircleId()).getCircleFullName());
                rootPath+=place+"/";
            }
        }
        else {
            if (reportRequest.getStateId() != 0) {
                place = StReplace(stateDao.findByStateId(reportRequest.getStateId()).getStateName());
                rootPath += place + "/";
            }

            if (reportRequest.getDistrictId() != 0) {
                place = StReplace(districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName());
                rootPath += place + "/";
            }

            if (reportRequest.getBlockId() != 0) {
                place = StReplace(blockDao.findByBlockId(reportRequest.getBlockId()).getBlockName());
                rootPath += place + "/";
            }
        }
        String filename= reportRequest.getReportType()+"_"+place+"_"+getMonthYear(reportRequest.getToDate())+".xlsx";
        if(reportRequest.getReportType().equals(ReportType.swcRejected.getReportType())) {
            filename=reportRequest.getReportType()+"_"+place+"_"+this.getDateMonthYear(reportRequest.getToDate())+".xlsx";
        }
        rootPath = reports+reportRequest.getReportType()+"/"+rootPath+filename;
        List<String> extras = new ArrayList<>();
        extras.add(filename);
        extras.add(rootPath);
        return extras;
    }


    @Override
    public List<Circle> getUserCircles(User user){
        List<StateCircle> list = new ArrayList<>();
        if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            list = stateCircleDao.getRelByStateId(null);
        }
        else if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
            list = stateCircleDao.getRelByStateId(user.getStateId());
        }else if(user.getAccessLevel().equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())){
            StateCircle stateCircle = new StateCircle();
            stateCircle.setCircleId(districtDao.findByDistrictId(user.getDistrictId()).getCircleOfDistrict());
            stateCircle.setStateId(user.getStateId());
            list.add(stateCircle);
        }
        else{
            StateCircle stateCircle = new StateCircle();
            stateCircle.setCircleId(districtDao.findByDistrictId(blockDao.getDistrictOfBlock(user.getBlockId())).getCircleOfDistrict());
            stateCircle.setStateId(blockDao.findByBlockId(user.getBlockId()).getStateOfBlock());
            list.add(stateCircle);
        }

        List<Circle> circleList = new ArrayList<>();
        for(StateCircle item : list){
            if(!circleList.contains(circleDao.getByCircleId(item.getCircleId())))
            circleList.add(circleDao.getByCircleId(item.getCircleId()));
        }
        return circleList;
    }

    @Override
    public ReportType getReportTypeByName(String reportName) {
        return reportTypeDao.getReportTypeByName(reportName);
    }

    @Override
    public ReportType getReportTypeByReportEnum(String reportEnum) {
        return reportTypeDao.getReportTypeByReportEnum(reportEnum);
    }

    @Override
    public String getMonthName(Date toDate) {
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
        int month=c.get(Calendar.MONTH)+1;
//        String monthString = "";
        int year=(c.get(Calendar.YEAR))%100;
        String monthString = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
//        if(month<10){
//            monthString="0"+String.valueOf(month);
//        }
//        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+"_"+yearString;
    }

    private String getMonthYear(Date toDate){
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
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

    @Override
    public String getDateMonthYear(Date toDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(toDate);
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
}
