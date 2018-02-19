package kr.mubeat.cms.repository.meta.acquisition.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import kr.mubeat.cms.config.ClipState;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.meta.clip.*;
import kr.mubeat.cms.domain.meta.program.QProgramMeta;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.acquisition.*;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.meta.acquisition.AcquisitionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 9. 5..
 */
@Repository
public class AcquisitionRepositoryImpl extends QueryDslRepositorySupport implements AcquisitionRepositoryCustom {

    public AcquisitionRepositoryImpl() {
        super(ClipAcquisition.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Page<AcquisitionListDTO> getAcquisitionList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMeta clipMeta = QClipMeta.clipMeta;
        QClipMetaLang clipMetaLang = QClipMetaLang.clipMetaLang;
        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;
        QProgramMeta programMeta = QProgramMeta.programMeta;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getCorporator() != null && searchParam.getCorporator() != "") {
            booleanBuilder
                .and(clipMeta.corporator.eq(searchParam.getCorporator()));
        }
        if (searchParam.getProgram() != null && searchParam.getProgram() != "") {
            booleanBuilder
                .and(clipMeta.programId.eq(searchParam.getProgram()));
        }
        if (searchParam.getClipType() != null && searchParam.getClipType() != "") {
            booleanBuilder
                .and(clipMeta.clipCategory.eq(searchParam.getClipType()));
        }
        if (searchParam.getType() != null) {
            if (searchParam.getType() == SearchType.SEARCH_BY_CLIP_NAME) {
                booleanBuilder
                    .and(clipMetaLang.clipTitle.like('%'+searchParam.getSearchText()+'%'));
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_ID) {
                booleanBuilder
                    .and(clipMeta.clipMetaId.eq(Long.parseLong(searchParam.getSearchText())));
            }
        }

        QueryResults<AcquisitionListDTO> result = queryFactory.select(
            Projections.constructor(
                AcquisitionListDTO.class,
                clipMeta.clipMetaId,
                clipMeta.corporator,
                programMeta.programTitle,
                clipMeta.clipCategory,
                clipMetaLang.clipTitle,
                clipAcquisition.uploadYn,
                clipAcquisition.transcodeYn,
                clipMeta.broadDate))
            .from(clipMeta)
            .innerJoin(clipMeta.clipAcquisition, clipAcquisition)
            .leftJoin(clipMeta.programMeta, programMeta)
            .leftJoin(clipMeta.clipMetaLang, clipMetaLang)
                .on(clipMetaLang.langType.eq(LangType.KO.name()))
            .where(booleanBuilder)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(clipMeta.regDate.desc(), clipMeta.broadDate.desc()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<AcquisitionListDTO> getAcquisitionStateList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMeta clipMeta = QClipMeta.clipMeta;
        QClipMetaLang clipMetaLang = QClipMetaLang.clipMetaLang;
        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;
        QProgramMeta programMeta = QProgramMeta.programMeta;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (searchParam.getUploadState() != null && searchParam.getUploadState() != "") {
            booleanBuilder.and(clipAcquisition.uploadYn.eq(searchParam.getUploadState()));
        }
        if (searchParam.getTranscodeState() != null && searchParam.getTranscodeState() != "") {
            booleanBuilder.and(clipAcquisition.transcodeYn.eq(searchParam.getTranscodeState()));
        }

        QueryResults<AcquisitionListDTO> result = queryFactory.select(
            Projections.constructor(
                AcquisitionListDTO.class,
                clipMeta.clipMetaId,
                clipMeta.corporator,
                programMeta.programTitle,
                clipMeta.clipCategory,
                clipMetaLang.clipTitle,
                clipAcquisition.uploadYn,
                clipAcquisition.transcodeYn,
                clipMeta.broadDate))
            .from(clipMeta)
            .innerJoin(clipMeta.clipAcquisition, clipAcquisition)
            .leftJoin(clipMeta.programMeta, programMeta)
            .leftJoin(clipMeta.clipMetaLang, clipMetaLang)
                .on(clipMetaLang.langType.eq(LangType.KO.name()))
            .where(booleanBuilder)
            .limit(pageable.getPageSize())
            .offset(pageable.getOffset())
            .orderBy(clipMeta.clipMetaId.desc()).fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Long updateOriginS3Location(Long id, String originS3Location) {

        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;

        return new JPAUpdateClause(this.getEntityManager(), clipAcquisition)
                    .where(clipAcquisition.clipMetaId.eq(id))
                    .set(clipAcquisition.originS3Location, originS3Location)
                    .set(clipAcquisition.uploadYn, ClipState.NORMAL)
                    .set(clipAcquisition.transcodeYn, ClipState.NORMAL)
                    .execute();
    }

    @Override
    public Long updateAcquisitionInformation(Long id, String clipTitle) {
        QClipMetaLang metaLang = QClipMetaLang.clipMetaLang;

        return new JPAUpdateClause(this.getEntityManager(), metaLang)
            .where(metaLang.clipMetaId.eq(id))
            .where(metaLang.langType.eq(LangType.KO.name()))
            .set(metaLang.clipTitle, clipTitle)
            .execute();
    }

    @Override
    public List<Transcode> getUploadState(List<Long> id) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMeta clipMeta = QClipMeta.clipMeta;
        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;

        return queryFactory.select(
            Projections.constructor(
                Transcode.class,
                clipMeta.clipMetaId,
                clipMeta.clipCategory,
                clipAcquisition.uploadYn
            ))
            .from(clipMeta)
            .innerJoin(clipMeta.clipAcquisition, clipAcquisition)
            .where(clipMeta.clipMetaId.in(id)).fetchResults().getResults();
    }

    @Override
    public Long updateTranscode(List<Long> id, String type) {

        String upload = null;
        String trans = null;

//        if (type.equalsIgnoreCase(Constants.ENTERTAINMENT_PROGRAM)) {
//            upload = ClipState.WAIT;
//            trans = ClipState.NORMAL;
//        } else if (type.equalsIgnoreCase(Constants.MUSIC_PROGRAM)) {
//            upload = ClipState.NORMAL;
//            trans = ClipState.NORMAL;
//        }

        upload = ClipState.WAIT;
        trans = ClipState.NORMAL;

        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;

        return new JPAUpdateClause(this.getEntityManager(), clipAcquisition)
            .where(clipAcquisition.clipMetaId.in(id))
            .set(clipAcquisition.uploadYn, upload)
            .set(clipAcquisition.transcodeYn, trans)
            .execute();
    }

    @Override
    public ClipDTO getClip(Long clipMetaId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;
        QClip clip = QClip.clip;

        return queryFactory.select(
            Projections.constructor(
                ClipDTO.class,
                clip.clipId,
                clip.title,
                clip.synopsis,
                clip.searchKeyword,
                clip.clipCategory,
                clip.contentImg,
                clip.platformIsUse,
                clip.isUse,
                clip.regDate,
                clip.modfyDate,
                clip.fileModifyDate,
                clip.broadDate
            ))
            .from(clipAcquisition)
            .leftJoin(clipAcquisition.clip, clip)
            .where(clipAcquisition.clipMetaId.eq(clipMetaId))
            .fetchOne();
    }

    @Override
    public ClipMetaDTO getClipMeta(Long clipMetaId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMeta clipMeta = QClipMeta.clipMeta;
        QClipMetaLang clipMetaLang = QClipMetaLang.clipMetaLang;
        QProgramMeta programMeta = QProgramMeta.programMeta;

        return queryFactory.select(
            Projections.constructor(
                ClipMetaDTO.class,
                clipMeta.clipMetaId,
                clipMetaLang.clipTitle,
                clipMeta.clipCategory,
                clipMeta.clipClassify,
                clipMeta.playTime,
                clipMeta.broadDate,
                clipMeta.corporator,
                programMeta.programTitle,
                clipMeta.enableYn,
                clipMeta.isoriginal,
                clipMeta.hit
            ))
            .from(clipMeta)
            .leftJoin(clipMeta.programMeta, programMeta)
            .leftJoin(clipMeta.clipMetaLang, clipMetaLang)
                .on(clipMetaLang.langType.eq(LangType.KO.name()))
            .where(clipMeta.clipMetaId.eq(clipMetaId))
            .fetchOne();
    }

    @Override
    public ClipAcquisitionDTO getClipAcquisition(Long clipMetaId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;

        return queryFactory.select(
            Projections.constructor(
                ClipAcquisitionDTO.class,
                clipAcquisition.clipMetaId,
                clipAcquisition.providerType,
                clipAcquisition.originClipId,
                clipAcquisition.uploadYn,
                clipAcquisition.transcodeYn,
                clipAcquisition.originS3Location,
                clipAcquisition.createDt
            ))
            .from(clipAcquisition)
            .where(clipAcquisition.clipMetaId.eq(clipMetaId))
            .fetchOne();
    }

    @Override
    public ContentDTO getContent(Long clipMetaId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;
        QClip clip = QClip.clip;
        QContent content = QContent.content;

        return queryFactory.select(
            Projections.constructor(
                ContentDTO.class,
                content.contentNumber,
                content.broadDate
            ))
            .from(clipAcquisition)
            .leftJoin(clipAcquisition.clip, clip)
            .leftJoin(clip.content, content)
            .where(clipAcquisition.clipMetaId.eq(clipMetaId))
            .fetchOne();
    }

    @Override
    public List<ClipMedia> getClipMediaList(Long clipMetaId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMedia clipMedia = QClipMedia.clipMedia;

        return queryFactory.select(
            Projections.constructor(
                ClipMedia.class,
                clipMedia.clipMetaId,
                clipMedia.mediaType,
                clipMedia.mediaPath,
                clipMedia.baseUrl
            ))
            .from(clipMedia)
            .where(clipMedia.clipMetaId.eq(clipMetaId))
            .fetch();
    }

    @Override
    public String getOriginalClipPath(Long clipMetaId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;
        QClip clip = QClip.clip;

        return queryFactory.select(
            Projections.constructor(
                String.class,
                clip.mediaDomain.concat(clip.filePath)
            ))
            .from(clipAcquisition)
            .leftJoin(clipAcquisition.clip, clip)
            .where(clipAcquisition.clipMetaId.eq(clipMetaId))
            .fetchOne();
    }
}
