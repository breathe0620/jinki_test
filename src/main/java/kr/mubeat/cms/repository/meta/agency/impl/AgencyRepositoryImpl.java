package kr.mubeat.cms.repository.meta.agency.impl;

import com.querydsl.jpa.impl.JPAUpdateClause;
import kr.mubeat.cms.domain.meta.agency.Agency;
import kr.mubeat.cms.domain.meta.agency.QAgency;
import kr.mubeat.cms.repository.meta.agency.AgencyRepositoryCustom;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by moonkyu.lee on 2017. 10. 23..
 */
@Repository
public class AgencyRepositoryImpl extends QueryDslRepositorySupport implements AgencyRepositoryCustom {

    public AgencyRepositoryImpl() {
        super(Agency.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Long updateAgencyTitleEn(Integer agencyId, String agencyNameEn) {

        QAgency agency = QAgency.agency;

        return new JPAUpdateClause(this.getEntityManager(), agency)
                .where(agency.agencyId.eq(agencyId))
                .set(agency.agencyNameEn, agencyNameEn)
                .execute();
    }
}
