package kr.mubeat.cms.repository.meta.acquisition;

import kr.mubeat.cms.domain.meta.clip.ClipAcquisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 5..
 */
@Repository
public interface AcquisitionRepository extends JpaRepository<ClipAcquisition, Long>, AcquisitionRepositoryCustom {
}
