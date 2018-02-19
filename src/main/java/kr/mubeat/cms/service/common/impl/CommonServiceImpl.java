package kr.mubeat.cms.service.common.impl;

import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.enumerations.Roles;
import kr.mubeat.cms.domain.manager.admin.Admin;
import kr.mubeat.cms.domain.manager.admin.AdminPermission;
import kr.mubeat.cms.domain.manager.admin.AuthToken;
import kr.mubeat.cms.dto.manager.admin.AdminDTO;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.repository.manager.admin.AdminPermissionRepository;
import kr.mubeat.cms.repository.manager.admin.AdminRepository;
import kr.mubeat.cms.repository.manager.admin.AdminSecretRepository;
import kr.mubeat.cms.service.common.CommonService;
import kr.mubeat.cms.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 * CommonService
 */
@Service
public class CommonServiceImpl implements CommonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AdminRepository adminRepository;
    private AdminSecretRepository adminSecretRepository;
    private AdminPermissionRepository adminPermissionRepository;

    @Autowired
    public CommonServiceImpl(
            AdminRepository adminRepository,
            AdminSecretRepository adminSecretRepository,
            AdminPermissionRepository adminPermissionRepository
    ) {
        this.adminRepository = adminRepository;
        this.adminSecretRepository = adminSecretRepository;
        this.adminPermissionRepository = adminPermissionRepository;
    }

    @Transactional(readOnly = true)
    public boolean isExistSuperUser() {
        return (adminRepository.getSAdminCount() > 0);
    }

    @Override
    @Transactional(rollbackFor=Exception.class, readOnly = false)
    public Result signupSuperAdmin(AdminDTO input) {

        if (adminSecretRepository.findOne(input.getSecretKey()) == null) {
            return new Result(EnumResCode.E504);
        }

        Result result = new Result(EnumResCode.OK);

        Date curDate = new Date();

        Admin admin = new Admin();

        admin.setAdminName(input.getAdminName());
        admin.setAdminId(input.getAdminId());
        admin.setAdminPwd(CommonUtil.encryptPassword(new BCryptPasswordEncoder(), input.getAdminPwd()));
        admin.setAdminType(Constants.ADMIN_TYPE_SUPER);
        admin.setPhoneNum(input.getPhoneNum());
        admin.setCreateDt(curDate);
        admin.setModifyDt(curDate);

        adminRepository.save(admin);

        return result;
    }

    @Override
    @Transactional(rollbackFor=Exception.class, readOnly = false)
    public Result signupAdmin(Admin input) {

        if (adminSecretRepository.findOne(input.getSecretKey()) == null) {
            return new Result(EnumResCode.E504);
        }

        Result result = new Result(EnumResCode.OK);

        Date curDate = new Date();

        Admin admin = new Admin();

        admin.setAdminName(input.getAdminName());
        admin.setAdminId(input.getAdminId());
        admin.setAdminPwd(CommonUtil.encryptPassword(new BCryptPasswordEncoder(), input.getAdminPwd()));
        admin.setAdminType(Constants.ADMIN_TYPE_NORMAL);
        admin.setPhoneNum(input.getPhoneNum());
        admin.setCreateDt(curDate);
        admin.setModifyDt(curDate);

        Long adminNo = adminRepository.save(admin).getAdminNo();
        AdminPermission permission = input.getAdminPermission();
        permission.setAdminNo(adminNo);
        adminPermissionRepository.save(permission);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.debug("Admin Login Process");

        Admin admin = adminRepository.findByAdminId(username);

        if(admin == null)
            throw new UsernameNotFoundException("Admin Not Found");

        logger.debug(admin.getPhoneNum());

        AuthToken authToken = new AuthToken();
        authToken.setUserName(admin.getAdminId());
        authToken.setPassword(admin.getAdminPwd());
        authToken.setAdminType(admin.getAdminType());
        authToken.setAdminNo(admin.getAdminNo());

        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        grantedAuths.add(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.getRole()));

        AdminPermission permission = admin.getAdminPermission();

        if(permission.getMeta().equals(Constants.FLAG_Y))
            grantedAuths.add(new SimpleGrantedAuthority(Roles.ROLE_MENU_META.getRole()));

        if(permission.getMember().equals(Constants.FLAG_Y))
            grantedAuths.add(new SimpleGrantedAuthority(Roles.ROLE_MENU_MEMBER.getRole()));

        authToken.setAuthorities(grantedAuths);

        return authToken;
    }
}
