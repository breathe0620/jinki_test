package kr.mubeat.cms.security;

import kr.mubeat.cms.config.URLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by doohwan.yoo on 2017. 4. 19..
 * 로그아웃 성공 핸들러
 */
@Component
@Qualifier("adminSignoutSuccessHandler")
public class AdminSignoutSuccessHandler implements LogoutSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        logger.debug("Admin Logout");

        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie) ;
        }

        response.sendRedirect(URLConfig.DEFAULT_SIGNIN_PAGE_URL);
    }
}