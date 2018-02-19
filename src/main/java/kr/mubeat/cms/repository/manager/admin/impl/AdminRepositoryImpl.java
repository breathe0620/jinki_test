package kr.mubeat.cms.repository.manager.admin.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.Constants;

import kr.mubeat.cms.domain.manager.admin.Admin;
import kr.mubeat.cms.domain.manager.admin.QAdmin;
import kr.mubeat.cms.dto.manager.admin.AdminDTO;
import kr.mubeat.cms.repository.manager.admin.AdminRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by doohwan.yoo on 2017. 4. 18..
 */
@Repository
public class AdminRepositoryImpl extends QueryDslRepositorySupport implements AdminRepositoryCustom {

    public AdminRepositoryImpl() {
        super(Admin.class);
    }

    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public long getSAdminCount() {
        QAdmin admin = QAdmin.admin;

        return from(admin).where(admin.adminType.eq(Constants.ADMIN_TYPE_SUPER)).fetchCount();
    }

    @Override
    public Page<AdminDTO> getAdminList(Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QAdmin admin = QAdmin.admin;

        QueryResults<AdminDTO> result = queryFactory.select(Projections.constructor(AdminDTO.class,
                admin.adminNo, admin.adminName, admin.adminId, admin.phoneNum, admin.adminType, admin.createDt))
                .from(admin)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(admin.adminNo.desc()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
