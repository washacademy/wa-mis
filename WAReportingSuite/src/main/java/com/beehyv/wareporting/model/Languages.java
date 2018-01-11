package com.beehyv.wareporting.model;

import javax.persistence.*;

@Entity
@Table(name = "languages")
public class Languages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", columnDefinition = "TINYINT(4)")
    private Integer languageId;

    @Column(name = "language", columnDefinition = "VARCHAR(45)")
    private String language;

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
}
