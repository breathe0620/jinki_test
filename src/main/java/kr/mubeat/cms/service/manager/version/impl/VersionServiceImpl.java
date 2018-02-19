package kr.mubeat.cms.service.manager.version.impl;

import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.domain.manager.version.Version;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.repository.manager.version.VersionRepository;
import kr.mubeat.cms.service.manager.version.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 7. 14..
 */
@Service
public class VersionServiceImpl implements VersionService {

    private VersionRepository versionRepository;

    @Autowired
    public VersionServiceImpl(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Version> getVersions() {
        return versionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateVersion(Version version) {
        versionRepository.save(version);
        return new Result(EnumResCode.OK);
    }
}
