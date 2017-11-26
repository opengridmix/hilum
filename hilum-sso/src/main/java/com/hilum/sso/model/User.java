package com.hilum.sso.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    @Id
    private long id;         //id
    private String realm;    //realm
    private String userName; //用户名
    private String password; //密码
    private String mobileNo; //手机号码
    private boolean enabled; //用户是否被禁用

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUserName(final String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(simpleGrantedAuthority);
        return authorityList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm == null ? null : realm.trim();
    }
}
