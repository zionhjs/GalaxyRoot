package com.galaxy.ucenter.module.model;

import javax.persistence.Table;

@Table(name = "t_user")
public class User {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
