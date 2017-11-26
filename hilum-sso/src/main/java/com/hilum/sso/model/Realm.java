package com.hilum.sso.model;

import javax.persistence.Id;

public class Realm {
    @Id
    private String id;

    private String name; //realm name

    private String description; //real desc
}
