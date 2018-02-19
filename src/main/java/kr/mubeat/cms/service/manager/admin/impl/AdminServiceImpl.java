package kr.mubeat.cms.service.manager.admin.impl;

import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.domain.manager.admin.Admin;
import kr.mubeat.cms.dto.manager.admin.AdminDTO;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.repository.manager.admin.AdminPermissionRepository;
import kr.mubeat.cms.repository.manager.admin.AdminRepository;
import kr.mubeat.cms.service.manager.admin.AdminService;
import kr.mubeat.cms.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by doohwan.yoo on 2017. 5. 26..
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AdminRepository adminRepository;
    private AdminPermissionRepository adminPermissionRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, AdminPermissionRepository adminPermissionRepository) {
        this.adminRepository = adminRepository;
        this.adminPermissionRepository = adminPermissionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AdminDTO> getAdminList(Pageable pageable) {
        return adminRepository.getAdminList(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Admin getAdminDetail(Long adminNo) {
        return adminRepository.findByAdminNo(adminNo);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor=Exception.class)
    public Result updateAdminInfo(Long adminNo, Admin input) {

        Admin curAdmin = adminRepository.findByAdminNo(adminNo);

        if(curAdmin == null)
            return new Result(EnumResCode.E006);

        input.setAdminNo(adminNo);
        input.setAdminType(curAdmin.getAdminType());
        input.setModifyDt(new Date());
        if (input.getAdminPwd().length() > 6) {
            input.setAdminPwd(CommonUtil.encryptPassword(new BCryptPasswordEncoder(), input.getAdminPwd()));
        } else {
            input.setAdminPwd(curAdmin.getAdminPwd());
        }
        input.getAdminPermission().setAdminNo(adminNo);

        adminRepository.save(input);

        return new Result(EnumResCode.OK);
    }
}
