package kr.mubeat.cms.security;

import kr.mubeat.cms.config.URLConfig;
import kr.mubeat.cms.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    private AuthenticationSuccessHandler adminSigninSuccessHandler;
    private AuthenticationFailureHandler adminSigninFailHandler;
    private LogoutSuccessHandler adminSignoutSuccessHandler;
    private CommonService commonService;

    @Autowired
    public SecurityConfigAdapter(AuthenticationSuccessHandler adminSigninSuccessHandler,
                                 AuthenticationFailureHandler adminSigninFailHandler,
                                 CommonService commonService,
                                 LogoutSuccessHandler adminSignoutSuccessHandler) {
        this.adminSigninSuccessHandler = adminSigninSuccessHandler;
        this.adminSigninFailHandler = adminSigninFailHandler;
        this.commonService = commonService;
        this.adminSignoutSuccessHandler = adminSignoutSuccessHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(URLConfig.DEFAULT_WEB_IGNORE_MATCHER);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(commonService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .accessDeniedPage("/permission-error")
            .and()
            .headers()
                .defaultsDisabled()
                .cacheControl()
            .and()
                .defaultsDisabled()
                .contentTypeOptions()
            .and()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable()
                .xssProtection().block(true)
            .and()
                .contentSecurityPolicy("img-src * data:; default-src * blob:; object-src 'self'; style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval'")
                .and()
                .addHeaderWriter(new StaticHeadersWriter("P3P", "CP=\"ALL DSP COR MON LAW IVDi HIS IVAi DELi SAMi OUR LEG PHY UNI ONL DEM STA INT NAV PUR FIN OTC GOV\""))
                .addHeaderWriter(new StaticHeadersWriter("Pragma", "no-cache"))
                .addHeaderWriter(new StaticHeadersWriter("Cache-Control", "no-cache"))
            .and()
//                .exceptionHandling().accessDeniedHandler(memberAccessDeniedHandler).and()
                .csrf().disable()
            .antMatcher("/**")
            .authorizeRequests()
                .antMatchers(URLConfig.DEFAULT_SIGNIN_PAGE_URL).anonymous()
                .antMatchers(URLConfig.AUTHENTICATION_WHITE_LIST).permitAll()
                .antMatchers(URLConfig.REQUIRED_AUTHENTICATION_LIST).access("hasRole('ADMIN')")
            .and()
                .formLogin()
                .loginPage(URLConfig.DEFAULT_SIGNIN_PAGE_URL)
                .loginProcessingUrl(URLConfig.DEFAULT_SIGNIN_PROCESS_URL)
                .usernameParameter("adminId")
                .passwordParameter("adminPwd")
                .successHandler(adminSigninSuccessHandler)
                .failureHandler(adminSigninFailHandler)
            .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutUrl(URLConfig.DEFAULT_SIGNOUT_PROCESS_URL)
                .logoutSuccessHandler(adminSignoutSuccessHandler);

    }
}
