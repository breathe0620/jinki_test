package kr.mubeat.cms.security;

import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.config.URLConfig;
import kr.mubeat.cms.domain.manager.admin.AuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 * 로그인 성공 처리 핸들러
 */
@Component
@Qualifier("adminSigninSuccessHandler")
public class AdminSigninSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        logger.debug("login success");

        if (authentication.getPrincipal() instanceof AuthToken) {

            AuthToken authToken = (AuthToken)authentication.getPrincipal();

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(60*60*24);
            session.setAttribute(Constants.SESSION_IDENTIFIER, authToken);

            Cookie cookie = new Cookie(Constants.COOKIE_TYPE, authToken.getAdminType());
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);

            String referer = (String)request.getSession().getAttribute("referer");

            logger.debug("referer : " + referer);
            logger.debug("main url : " + URLConfig.MAIN_URL);

            if(referer != null && !referer.isEmpty()) {
                logger.debug("here");
                setDefaultTargetUrl(referer);
            }
            else {
                logger.debug("default target url");
                logger.debug("get : " + this.getDefaultTargetUrl());
                setDefaultTargetUrl(URLConfig.MAIN_URL);
            }
            
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
