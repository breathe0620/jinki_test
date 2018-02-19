package kr.mubeat.cms.enumerations;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 *
 * Edited by moonkyu.lee on 2017. 6. 9..
 * class to enum
 */
public enum Roles {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MENU_META("ROLE_MENU_META"),
    ROLE_MENU_MEMBER("ROLE_MENU_MEMBER");

    private String role;
    Roles(String role) {
        this.role = role;
    }

    public String getRole() { return this.role; }

}
