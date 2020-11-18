package com.example.xiangfengtea.entity;

import javax.persistence.*;

@Entity(name = "manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int managerId;
    @Column(nullable = false,length = 20,unique = true)
    private String managerName;
    @Column(nullable = false,length = 20)
    private String managerPassword;
    @Column()
    private String managerHeadImage;

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public String getManagerHeadImage() {
        return managerHeadImage;
    }

    public void setManagerHeadImage(String managerHeadImage) {
        this.managerHeadImage = managerHeadImage;
    }
}
