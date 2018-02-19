package kr.mubeat.cms.repository.service.notice;

import kr.mubeat.cms.domain.service.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 27..
 */
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer>, NoticeRepositoryCustom {
}
