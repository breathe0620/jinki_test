package kr.mubeat.cms.repository.meta.agency;

import kr.mubeat.cms.domain.meta.agency.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by doohwan.yoo on 2017. 6. 8..
 */
public interface AgencyRepository extends JpaRepository<Agency, Integer>, AgencyRepositoryCustom {

    Agency findByAgencyName(String agencyName);
    List<Agency> findAllByAgencyNameLike(String agencyName);
    Page<Agency> findAll(Pageable pageable);

}
