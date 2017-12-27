package com.springapp.appcfg;

/**
 * Created by Mendor on 25.12.2017.
 */
public class AppProperties {

    private String userDefaultRole;
    private String userDefaultState;
    private String userDisabledState;
    private String accessEnableLink;
    private String defaultState;

    public void setUserDefaultRole(String userDefaultRole) {
        this.userDefaultRole = userDefaultRole;
    }

    public String getUserDefaultRole() {
        return userDefaultRole;
    }

    public void setUserDefaultState(String userDefaultState) {
        this.userDefaultState = userDefaultState;
    }

    public String getUserDefaultState() {
        return userDefaultState;
    }

    public void setUserDisabledState(String userDisabledState) {
        this.userDisabledState = userDisabledState;
    }

    public String getUserDisabledState() {
        return userDisabledState;
    }

    public void setAccessEnableLink(String accessEnableLink) {
        this.accessEnableLink = accessEnableLink;
    }

    public String getAccessEnableLink() {
        return accessEnableLink;
    }

    public void setDefaultState(String defaultState) {
        this.defaultState = defaultState;
    }

    public String getDefaultState() {
        return defaultState;
    }
}
