package kr.mubeat.cms.repository.recommend.home.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.meta.clip.QClipMeta;
import kr.mubeat.cms.domain.meta.clip.QClipMetaLang;
import kr.mubeat.cms.domain.recommend.home.Home;
import kr.mubeat.cms.domain.recommend.home.QHome;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.recommend.home.ClipDTO;
import kr.mubeat.cms.dto.recommend.home.HomeDTO;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.recommend.home.HomeRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Repository
public class HomeRepositoryImpl extends QueryDslRepositorySupport implements HomeRepositoryCustom {

    public HomeRepositoryImpl() { super(Home.class); }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Page<ClipDTO> getSearchClipList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        if (searchParam.getType() != null
            &&
            searchParam.getSearchText() != null
            &&
            !searchParam.getSearchText().equalsIgnoreCase("")
        ) {

            String where = "";

            if (searchParam.getType() == SearchType.SEARCH_BY_CLIP_NAME) {
                where = "AND cmlk.cliptitle LIKE '%" + searchParam.getSearchText() + "%' " +
                        "OR cmle.cliptitle LIKE '%" + searchParam.getSearchText() + "%' ";
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_ID) {
                where = "AND cm.clipmetaid = " + Long.parseLong(searchParam.getSearchText()) + " ";
            }

            Query result = this.getEntityManager().createNativeQuery(
            "SELECT cm.clipmetaid, cmlk.cliptitle AS cliptitle, cmle.cliptitle AS cliptitleen, cm.enableyn " +
                    "FROM tbl_clip_meta cm " +
                    "LEFT OUTER JOIN tbl_clip_meta_lang cmlk " +
                    "ON cm.clipmetaid = cmlk.clipmetaid AND cmlk.langtype = 'KO' " +
                    "LEFT OUTER JOIN tbl_clip_meta_lang cmle " +
                    "ON cm.clipmetaid = cmle.clipmetaid AND cmle.langtype = 'EN' " +
                    "WHERE cm.enableyn = 'Y' " +
                    "AND cmlk.clipmetaid = cmle.clipmetaid " +
                    where +
                    "ORDER BY cm.clipmetaid DESC " +
                    "LIMIT "+pageable.getPageSize()+" OFFSET "+pageable.getOffset()+";",
                "HomeClipDTO"
            );

            Query count = this.getEntityManager().createNativeQuery(
            "SELECT COUNT(*) `count` " +
                    "FROM tbl_clip_meta cm " +
                    "LEFT OUTER JOIN tbl_clip_meta_lang cmlk " +
                    "ON cm.clipmetaid = cmlk.clipmetaid AND cmlk.langtype = 'KO' " +
                    "LEFT OUTER JOIN tbl_clip_meta_lang cmle " +
                    "ON cm.clipmetaid = cmle.clipmetaid AND cmle.langtype = 'EN' " +
                    "WHERE cm.enableyn = 'Y' " +
                    "AND cmlk.clipmetaid = cmle.clipmetaid " +
                    where
            );

            Long countResult = Long.parseLong(String.valueOf(count.getSingleResult()));

            return new PageImpl<>(result.getResultList(), pageable, countResult);

        } else {
            return null;
        }
    }

    @Override
    public List<HomeDTO> getHomes() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QHome home = QHome.home;
        QClipMeta clipMeta = QClipMeta.clipMeta;
        QClipMetaLang clipMetaLang = QClipMetaLang.clipMetaLang;

        return queryFactory.select(
            Projections.constructor(
                HomeDTO.class,
                home.recommendHomeId,
                clipMeta.clipMetaId,
                JPAExpressions.select(clipMetaLang.clipTitle)
                    .from(clipMetaLang)
                    .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId))
                    .where(clipMetaLang.langType.eq(LangType.KO.name())),
                JPAExpressions.select(clipMetaLang.clipTitle)
                    .from(clipMetaLang)
                    .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId))
                    .where(clipMetaLang.langType.eq(LangType.EN.name())),
                clipMeta.enableYn
            ))
            .from(home)
            .innerJoin(home.clipMeta, clipMeta)
            .orderBy(home.recommendHomeId.asc())
            .fetch();
    }

    @Override
    public void homeSave(Integer recommendHomeId, Long clipMetaId) {

        Query query = this.getEntityManager().createNativeQuery(
            "INSERT INTO tbl_recommend_home (recommendhomeid, clipmetaid) " + 
                        "VALUES (?, ?);");
        query.setParameter(1, recommendHomeId);
        query.setParameter(2, clipMetaId);
        query.executeUpdate();
    }
}
