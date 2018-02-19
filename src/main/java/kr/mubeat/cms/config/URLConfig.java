package kr.mubeat.cms.config;

/**
 * Created by doohwan.yoo on 2016. 10. 21..
 * URL 관련 설정
 */
public class URLConfig {

    public final static String MAIN_URL = "/";
    public final static String DEFAULT_SIGNIN_PAGE_URL = "/signin";
    public final static String DEFAULT_SIGNIN_PROCESS_URL = "/signin";
    public final static String DEFAULT_SIGNUP_SUPER_ADMIN = "/signup-super";
    public final static String DEFAULT_SIGNOUT_PROCESS_URL = "/signout";

    public final static String PERMISSION_ERROR_PAGE_URL = "/permission-error";

    public final static String [] DEFAULT_WEB_IGNORE_MATCHER = {"/css/**", "/js/**", "/font/**", "/data/**", "/vendor/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/webjars/**"};
    public final static String [] REQUIRED_AUTHENTICATION_LIST = {"/**"};
    public final static String [] AUTHENTICATION_WHITE_LIST = {DEFAULT_SIGNUP_SUPER_ADMIN, PERMISSION_ERROR_PAGE_URL, DEFAULT_SIGNOUT_PROCESS_URL, "/sample", "/sample2", "/sample3"};
}
