package kr.mubeat.cms.repository.manager.version;

import kr.mubeat.cms.domain.manager.version.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 7. 14..
 */
@Repository
public interface VersionRepository extends JpaRepository<Version, String> {

    Version findByOsCode(String osCode);

}
