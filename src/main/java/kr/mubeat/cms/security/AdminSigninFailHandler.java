package kr.mubeat.cms.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 * 로그인 실패 핸들러
 */
@Component
@Qualifier("adminSigninFailHandler")
public class AdminSigninFailHandler implements AuthenticationFailureHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AdminSigninFailHandler() {
    }

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.debug(request.getRequestURI());
        logger.debug("Authentication Failure : " + exception.getMessage());
        logger.debug("cause : "+ exception.getCause());

        String redirectUrl = "/";

        if(exception.getClass().isAssignableFrom(BadCredentialsException.class)) {

            logger.debug("암호 불일치");

            // ToDo 로그인 실패 로그 남기는 것 추가 해야 함
        }

//        request.setAttribute("result", result.getResult());

        request.getRequestDispatcher(redirectUrl).forward(request, response);
    }
}
