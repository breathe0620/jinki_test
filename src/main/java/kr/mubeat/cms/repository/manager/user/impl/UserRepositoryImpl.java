package kr.mubeat.cms.repository.manager.user.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.manager.user.QUser;
import kr.mubeat.cms.domain.manager.user.User;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.manager.user.UserDTO;
import kr.mubeat.cms.repository.manager.user.UserRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by moonkyu.lee on 2017. 8. 21..
 */
@Repository
public class UserRepositoryImpl extends QueryDslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryImpl() { super(User.class); }

    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Page<UserDTO> getUserList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QUser user = QUser.user;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getStart() != null) {
            booleanBuilder
                    .and(user.createDt.gt(searchParam.getStart()));
        }
        if (searchParam.getEnd() != null) {
            booleanBuilder
                    .and(user.createDt.lt(searchParam.getEnd()));
        }
        if (searchParam.getType() != null) {
            if (searchParam.getType() == SearchType.SEARCH_BY_USER_ID) {
                booleanBuilder
                        .and(user.userId.like('%' + searchParam.getSearchText() + '%'));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_USER_NAME) {
                booleanBuilder
                        .and(user.userName.like('%' + searchParam.getSearchText() + '%'));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_USER_NO) {
                booleanBuilder
                        .and(user.userNo.eq(Long.valueOf(searchParam.getSearchText())));
            }
        }

        QueryResults<UserDTO> result = queryFactory.select(
                Projections.constructor(
                        UserDTO.class,
                        user.userNo,
                        user.userId,
                        user.userType,
                        user.userStatus,
                        user.userName,
                        user.userEmail,
                        user.createDt,
                        user.lastLoginDt
                ))
                .from(user)
                .where(booleanBuilder)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(user.userNo.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Long updateUserState(Long userNo, String stateCode) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QUser user = QUser.user;

        return queryFactory.update(user).where(user.userNo.eq(userNo))
                .set(user.userStatus, stateCode)
                .execute();
    }
}
