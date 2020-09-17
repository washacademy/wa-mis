package com.beehyv.wareporting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wash_course")
public class Course {

    @Id
    @Column(name="courseId", columnDefinition = "INT(11)", nullable = false,unique = true)
    private Integer courseId;

    @Column(name="name", columnDefinition = "VARCHAR(45)",nullable = false,unique = true)
    private String name;

    @Column(name="content", columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(name="noOfChapters", columnDefinition = "INT(11)")
    private String noOfChapters;

    public Course() {
    }

    public Course(Integer courseId, String name, String content, String noOfChapters, String passingScore) {
        this.courseId = courseId;
        this.name = name;
        this.content = content;
        this.noOfChapters = noOfChapters;
        this.passingScore = passingScore;
    }

    @Column(name="passingScore", columnDefinition = "INT(11)")
    private String passingScore;

    public Integer getCourseId() {        return courseId;    }

    public void setCourseId(Integer courseId) {        this.courseId = courseId;    }

    public String getName() {       return name;    }

    public void setName(String name) {       this.name = name;    }

    public String getContent() {        return content;  }

    public void setContent(String content) {        this.content = content;    }

    public String getNoOfChapters() {        return noOfChapters;    }

    public void setNoOfChapters(String noOfChapters) {       this.noOfChapters = noOfChapters;  }

    public String getPassingScore() {       return passingScore; }

    public void setPassingScore(String passingScore) {     this.passingScore = passingScore;  }
}
