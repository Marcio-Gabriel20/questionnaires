package com.hiro.questionnaires.entity;

import com.hiro.questionnaires.enums.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long roleId;

    private String name;
    private RoleType roleType;
    
    public Role() {
    }

    public Role(Long roleId, String name, RoleType roleType) {
        this.roleId = roleId;
        this.name = name;
        this.roleType = roleType;
    }

    public Role(String name, RoleType roleType) {
        this.name = name;
        this.roleType = roleType;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
    
}