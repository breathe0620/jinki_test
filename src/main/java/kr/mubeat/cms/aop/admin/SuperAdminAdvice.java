package kr.mubeat.cms.aop.admin;

import kr.mubeat.cms.annotation.admin.SuperAdmin;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.domain.manager.admin.AuthToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by moonkyu.lee on 2017. 7. 13..
 */
@Aspect
@Component
public class SuperAdminAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(superAdmin)")
    public Object superAdmin(
            ProceedingJoinPoint joinPoint,
            SuperAdmin superAdmin
    ) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        AuthToken authToken = (AuthToken)session.getAttribute(Constants.SESSION_IDENTIFIER);

        if (authToken.getAdminType().equalsIgnoreCase(Constants.ADMIN_TYPE_SUPER)) {
            return joinPoint.proceed();
        } else {
            session.invalidate();
            return "redirect:/";
        }
    }
}
