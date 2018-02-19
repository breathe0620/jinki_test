package kr.mubeat.cms.repository.service.faq;

import kr.mubeat.cms.domain.service.faq.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by moonkyu.lee on 2017. 9. 27..
 */
@Repository
public interface FAQRepository extends JpaRepository<FAQ, Integer>, FAQRepositoryCustom {
}
