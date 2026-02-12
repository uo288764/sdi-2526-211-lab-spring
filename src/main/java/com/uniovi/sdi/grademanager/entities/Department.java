package com.uniovi.sdi.grademanager.entities;

import jakarta.persistence.*;

@Entity
public class Department {
    @Id
    @GeneratedValue
    private Long code;
    private String name;
    private String faculty;
    private String phone;
    private int professors;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getProfessors() {
        return professors;
    }

    public void setProfessors(int professors) {
        this.professors = professors;
    }

    public Department() {
    }

    public Department(Long code, String name, String faculty, String phone, int professors) {
        this.code = code;
        this.name = name;
        this.faculty = faculty;
        this.phone = phone;
        this.professors = professors;
    }


    @Override
    public String toString() {
        return "Department{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", faculty='" + faculty + '\'' +
                ", phone='" + phone + '\'' +
                ", professors=" + professors +
                '}';
    }
}