package com.beehyv.wareporting.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dim_language")
public class Languages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "TINYINT(4)")
    private Integer languageId;

    @Column(name = "name", columnDefinition = "VARCHAR(45)")
    private String language;

    @Column(name = "code", columnDefinition = "BIGINT(20)")
    private String code;

    @Column(name = "creation_date", columnDefinition = "DATETIME")
    private Date creationDate;

    @Column(name = "last_modified", columnDefinition = "TIMESTAMP")
    private Date lastModified;

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
