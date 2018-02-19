package kr.mubeat.cms.service.manager.version;

import kr.mubeat.cms.domain.manager.version.Version;
import kr.mubeat.cms.dto.Result;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 7. 14..
 */
public interface VersionService {

    List<Version> getVersions();
    Result updateVersion(Version version);

}
