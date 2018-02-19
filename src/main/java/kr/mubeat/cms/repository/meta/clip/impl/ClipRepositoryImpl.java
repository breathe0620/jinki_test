package kr.mubeat.cms.repository.meta.clip.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import kr.mubeat.cms.config.ClipState;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.config.ContentsType;
import kr.mubeat.cms.config.SearchType;
import kr.mubeat.cms.domain.meta.album.QAlbum;
import kr.mubeat.cms.domain.meta.artist.QArtist;
import kr.mubeat.cms.domain.meta.artist.QArtistLang;
import kr.mubeat.cms.domain.meta.clip.*;
import kr.mubeat.cms.domain.meta.program.QProgramMeta;
import kr.mubeat.cms.domain.meta.song.QSong;
import kr.mubeat.cms.domain.meta.song.QSongLang;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.meta.clip.*;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.meta.clip.ClipRepositoryCustom;
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
 * Created by moonkyu.lee on 2017. 6. 26..
 */
@Repository
public class ClipRepositoryImpl extends QueryDslRepositorySupport implements ClipRepositoryCustom {

    public ClipRepositoryImpl() {
        super(ClipMeta.class);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) { super.setEntityManager(entityManager); }

    @Override
    public Page<ClipDTO> getClipList(CustomSearchParam searchParam) {

        Pageable pageable = new PageRequest(searchParam.getPage(), searchParam.getSize());

        String where = whereBuilder(searchParam);

        Query result = this.getEntityManager().createNativeQuery(
                "SELECT " +
                        "cm.clipmetaid, " +
                        "cm.corporator, " +
                        "p.programtitle, " +
                        "cml.cliptitle, " +
                        "cm.clipcategory, " +
                        "cm.modifydt, " +
                        "cm.enableyn, " +
                        "cm.clipclassify, " +
                        "(SELECT " +
                        "COUNT(*) " +
                        "FROM tbl_clip_artist ca " +
                        "WHERE ca.clipmetaid = cm.clipmetaid) AS artistregist, " +
                        "(SELECT " +
                        "COUNT(*) " +
                        "FROM tbl_clip_song cs " +
                        "WHERE cs.clipmetaid = cm.clipmetaid) AS songregist " +
                        "FROM tbl_clip_meta cm " +
                        "INNER JOIN tbl_clip_acquisition cac " +
                        "ON cm.clipmetaid = cac.clipmetaid " +
                        "AND (cm.enableyn = 'Y' " +
                        "OR (cac.uploadyn = 'Y' AND cac.transcodeyn = 'Y')) " +
                        "LEFT OUTER JOIN tbl_program_meta p " +
                        "ON cm.programid = p.programid " +
                        "LEFT OUTER JOIN tbl_clip_meta_lang cml " +
                        "ON cm.clipmetaid = cml.clipmetaid " +
                        "AND cml.langtype = 'KO' " +
                        where +
                        "ORDER BY cm.clipmetaid DESC " +
                        "LIMIT "+pageable.getPageSize()+" OFFSET "+pageable.getOffset()+";",
                "ClipDTO"
        );

        Query count = this.getEntityManager().createNativeQuery(
                "SELECT " +
                        "COUNT(*) `count` " +
                        "FROM tbl_clip_meta cm " +
                        "INNER JOIN tbl_clip_acquisition cac " +
                        "ON cm.clipmetaid = cac.clipmetaid " +
                        "AND (cm.enableyn = 'Y' " +
                        "OR (cac.uploadyn = 'Y' AND cac.transcodeyn = 'Y')) " +
                        "LEFT OUTER JOIN tbl_program_meta p " +
                        "ON cm.programid = p.programid " +
                        "LEFT OUTER JOIN tbl_clip_meta_lang cml " +
                        "ON cm.clipmetaid = cml.clipmetaid " +
                        "AND cml.langtype = 'KO' " +
                        where
        );

        Long countResult = Long.parseLong(String.valueOf(count.getSingleResult()));

        return new PageImpl<>(result.getResultList(), pageable, countResult);
    }

    @Override
    public List<ClipExcelDTO> getClipsForExcel(CustomSearchParam searchParam) {

        String where = whereBuilder(searchParam);

        Query result = this.getEntityManager().createNativeQuery(
                "SELECT " +
                        "cm.clipmetaid, " +
                        "cm.corporator, " +
                        "p.programtitle, " +
                        "p.programtitle_en AS programtitleen, " +
                        "(SELECT cml.cliptitle FROM tbl_clip_meta_lang cml " +
                        "WHERE cml.clipmetaid = cm.clipmetaid AND cml.langtype = 'ko') AS cliptitle, " +
                        "(SELECT cml.cliptitle FROM tbl_clip_meta_lang cml " +
                        "WHERE cml.clipmetaid = cm.clipmetaid AND cml.langtype = 'en') AS cliptitleen, " +
                        "cm.clipcategory, " +
                        "cm.clipclassify, " +
                        "cm.modifydt, " +
                        "cm.enableyn, " +
                        "GROUP_CONCAT(DISTINCT al.artistname SEPARATOR ', ') AS artistname, " +
                        "GROUP_CONCAT(DISTINCT sl.songname SEPARATOR ', ') AS songname " +
                        "FROM tbl_clip_meta cm " +
                        "INNER JOIN tbl_clip_acquisition cac ON cm.clipmetaid = cac.clipmetaid " +
                        "AND (cm.enableyn = 'Y' OR (cac.uploadyn = 'Y' AND cac.transcodeyn = 'Y')) " +
                        "LEFT OUTER JOIN tbl_program_meta p ON cm.programid = p.programid " +
                        "LEFT OUTER JOIN tbl_clip_meta_lang cml ON cm.clipmetaid = cml.clipmetaid AND cml.langtype='ko' " +
                        "LEFT OUTER JOIN tbl_clip_artist ca ON cm.clipmetaid = ca.clipmetaid " +
                        "LEFT OUTER JOIN tbl_artist a ON ca.artistid = a.artistid " +
                        "LEFT OUTER JOIN tbl_artist_lang al ON a.artistid = al.artistid AND (al.langtype = 'en') " +
                        "LEFT OUTER JOIN tbl_clip_song cs ON cm.clipmetaid = cs.clipmetaid " +
                        "LEFT OUTER JOIN tbl_song s ON cs.songid = s.songid " +
                        "LEFT OUTER JOIN tbl_song_lang sl ON s.songid = sl.songid AND sl.langtype = 'en' " +
                        where +
                        "GROUP BY cm.clipmetaid;",
                "ClipExcelDTO"
        );

        return result.getResultList();
    }

    @Override
    public ClipDTO getClipDetail(Long clipMetaId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMeta clipMeta = QClipMeta.clipMeta;
        QClipMetaLang clipMetaLang = QClipMetaLang.clipMetaLang;
        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;
        QClip clip = QClip.clip;
        QProgramMeta programMeta = QProgramMeta.programMeta;
        QContent content = QContent.content;

        ClipDTO clipDTO = queryFactory.select(
                Projections.constructor(
                        ClipDTO.class,
                        clipMeta.clipMetaId,
                        clipMeta.corporator,
                        clipAcquisition.originClipId,
                        clipMeta.programId,
                        programMeta.programTitle,
                        programMeta.programTitleEn,
                        JPAExpressions.select(clipMetaLang.clipTitle)
                        .from(clipMetaLang)
                        .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId))
                        .where(clipMetaLang.langType.eq(LangType.KO.name())),
                        JPAExpressions.select(clipMetaLang.clipTitle)
                        .from(clipMetaLang)
                        .where(clipMetaLang.clipMetaId.eq(clipMeta.clipMetaId))
                        .where(clipMetaLang.langType.eq(LangType.EN.name())),
                        clipMeta.clipCategory,
                        content.contentNumber,
                        clipMeta.modifyDt,
                        clipMeta.enableYn,
                        content.broadDate,
                        clipMeta.isoriginal,
                        clipMeta.playTime,
                        clipMeta.clipClassify,
                        clipMeta.thumbImgUrl,
                        clip.mediaDomain.concat(clip.filePath)
                ))
                .from(clipMeta)
                .innerJoin(clipMeta.clipAcquisition, clipAcquisition)
                .innerJoin(clipAcquisition.clip, clip)
                .leftJoin(clipMeta.programMeta, programMeta)
                .leftJoin(clip.content, content)
                .where(
                    clipAcquisition.uploadYn.eq(ClipState.SUCCESS)
                        .and(clipAcquisition.transcodeYn.eq(ClipState.SUCCESS))
                        .or(clipMeta.enableYn.eq(ClipState.SUCCESS)
                            .and(clipAcquisition.uploadYn.eq(ClipState.SUCCESS)))
                )
                .where(clipMeta.clipMetaId.eq(clipMetaId)).fetchOne();

        if (clipDTO != null) {
            QArtist artist = QArtist.artist;
            QArtistLang artistLang = QArtistLang.artistLang;
            QClipArtist clipArtist = QClipArtist.clipArtist;
            QSong song = QSong.song;
            QSongLang songLang = QSongLang.songLang;
            QAlbum album = QAlbum.album;
            QClipSong clipSong = QClipSong.clipSong;
            QClipMedia clipMedia = QClipMedia.clipMedia;

            clipDTO.setArtistList(queryFactory.select(
                    Projections.constructor(
                            ClipArtist.class,
                            clipArtist.clipMetaId,
                            artist.artistId,
                            artist.originId,
                            JPAExpressions.select(artistLang.artistName)
                            .from(artistLang)
                            .where(artistLang.langType.eq(LangType.KO.name()))
                            .where(artistLang.artistId.eq(artist.artistId)),
                            JPAExpressions.select(artistLang.artistName)
                            .from(artistLang)
                            .where(artistLang.langType.eq(LangType.EN.name()))
                            .where(artistLang.artistId.eq(artist.artistId)),
                            artist.artistMainImgUrl,
                            artist.artistProfileImgUrl))
                    .from(clipArtist)
                    .leftJoin(clipArtist.artist, artist)
                    .where(clipArtist.clipMetaId.eq(clipMetaId))
                    .fetch());
            clipDTO.setSongList(queryFactory.select(
                    Projections.constructor(
                            ClipSong.class,
                            clipSong.clipMetaId,
                            song.songId,
                            song.originId,
                            JPAExpressions.select(songLang.songName)
                            .from(songLang)
                            .where(songLang.langType.eq(LangType.KO.name()))
                            .where(songLang.songId.eq(song.songId)),
                            JPAExpressions.select(songLang.songName)
                            .from(songLang)
                            .where(songLang.langType.eq(LangType.EN.name()))
                            .where(songLang.songId.eq(song.songId)),
                            album.publishDt,
                            song.genreId,
                            album.albumImgUrl))
                    .from(clipSong)
                    .leftJoin(clipSong.song, song)
                    .leftJoin(song.album, album)
                    .leftJoin(song.songLang, songLang)
                    .where(clipSong.clipMetaId.eq(clipMetaId))
                    .where(songLang.langType.eq(LangType.KO.name()))
                    .fetch());
            clipDTO.setClipMediaList(queryFactory.select(
                    Projections.constructor(
                            ClipMedia.class,
                            clipMedia.clipMetaId,
                            clipMedia.mediaType,
                            clipMedia.mediaPath,
                            clipMedia.baseUrl))
                    .from(clipMedia)
                    .where(clipMedia.clipMetaId.eq(clipMetaId))
                    .fetch());
        }

        return clipDTO;
    }

    @Override
    public List<ClipMedia> getClipMedia(Long clipMetaId) {

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
    public List<ClassifyDTO> getClassify() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClassify classify = QClassify.classify;

        return queryFactory.select(
                Projections.constructor(
                        ClassifyDTO.class,
                        classify.clipClassify,
                        classify.name
                ))
                .from(classify).fetchResults().getResults();
    }

    @Override
    public Long updateClipInfo(Long clipMetaId, ClipMeta clipMeta) {
        QClipMeta meta = QClipMeta.clipMeta;

        return new JPAUpdateClause(this.getEntityManager(), meta)
                    .where(meta.clipMetaId.eq(clipMetaId))
                    .set(meta.enableYn, clipMeta.getEnableYn())
                    .set(meta.isoriginal, clipMeta.getIsoriginal())
                    .set(meta.clipClassify, clipMeta.getClipClassify())
                    .execute();
    }

    @Override
    public TranscodeDTO getTranscodeStates() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipAcquisition clipAcquisition = QClipAcquisition.clipAcquisition;
        QClipMeta clipMeta = QClipMeta.clipMeta;

        return queryFactory.select(
                Projections.constructor(
                        TranscodeDTO.class,
                        JPAExpressions.select(clipAcquisition.count())
                                .from(clipAcquisition)
                                .innerJoin(clipAcquisition.clipMeta, clipMeta)
                                .where(
                                    clipMeta.enableYn.eq(ClipState.SUCCESS)
                                    .or(clipAcquisition.transcodeYn.eq(ClipState.SUCCESS)
                                    .and(clipAcquisition.uploadYn.eq(ClipState.SUCCESS)))
                                ),
                        JPAExpressions.select(clipMeta.count())
                                .from(clipMeta)
                                .where(clipMeta.enableYn.eq(ClipState.SUCCESS)
                                        .or(clipMeta.enableYn.eq(ClipState.TRASH))),
                        JPAExpressions.select(clipAcquisition.count())
                                .from(clipAcquisition)
                                .where(clipAcquisition.uploadYn.eq(ClipState.SUCCESS))
                                .where(clipAcquisition.transcodeYn.eq(ClipState.SUCCESS)),
                        JPAExpressions.select(clipAcquisition.count())
                                .from(clipAcquisition)
                                .where(clipAcquisition.uploadYn.eq(ClipState.SUCCESS))
                                .where(clipAcquisition.transcodeYn.eq(ClipState.NORMAL)),
                        JPAExpressions.select(clipAcquisition.count())
                                .from(clipAcquisition)
                                .where(clipAcquisition.uploadYn.eq(ClipState.FAIL)),
                        JPAExpressions.select(clipAcquisition.count())
                                .from(clipAcquisition)
                                .where(clipAcquisition.transcodeYn.eq(ClipState.FAIL)),
                        JPAExpressions.select(clipAcquisition.count())
                                .from(clipAcquisition)
                                .innerJoin(clipAcquisition.clipMeta, clipMeta)
                                .where(clipAcquisition.uploadYn.eq(ClipState.SUCCESS))
                                .where(clipAcquisition.transcodeYn.eq(ClipState.SUCCESS))
                                .where(clipMeta.clipCategory.eq(ContentsType.MUSIC)),
                        JPAExpressions.select(clipAcquisition.count())
                                .from(clipAcquisition)
                                .innerJoin(clipAcquisition.clipMeta, clipMeta)
                                .where(clipAcquisition.uploadYn.eq(ClipState.SUCCESS))
                                .where(clipAcquisition.transcodeYn.eq(ClipState.SUCCESS))
                                .where(clipMeta.clipCategory.eq(ContentsType.ENTERTAINMENT))
                ))
                .from(clipAcquisition)
                .limit(1)
                .fetch().get(0);
    }

    @Override
    public List<ClipEsDTO> getAllClipForElasticSearch(int page) {

        Query query = this.getEntityManager().createNativeQuery(
            "SELECT " +
                        "dummy.clipmetaid AS metaid, " +
                        "dummy.cliptitle AS title, " +
                        "GROUP_CONCAT(DISTINCT dummy.artistname SEPARATOR '---') AS artists, " +
                        "GROUP_CONCAT(DISTINCT dummy.songname SEPARATOR '---') AS songs " +
                        "FROM ( " +
                        "SELECT " +
                        "m.clipmetaid, " +
                        "(SELECT cml.cliptitle FROM tbl_clip_meta_lang cml WHERE cml.clipmetaid = m.clipmetaid AND cml.langtype = 'KO') cliptitle, " +
                        "GROUP_CONCAT(DISTINCT al.artistname SEPARATOR '---') artistname, " +
                        "GROUP_CONCAT(DISTINCT sl.songname SEPARATOR '---') songname " +
                        "FROM tbl_clip_meta m " +
                        "LEFT OUTER JOIN tbl_clip_artist ca ON m.clipmetaid = ca.clipmetaid " +
                        "LEFT OUTER JOIN tbl_artist a ON ca.artistid = a.artistid " +
                        "LEFT OUTER JOIN tbl_artist_lang al ON a.artistid = al.artistid AND al.langtype = 'KO' " +
                        "LEFT OUTER JOIN tbl_clip_song cs ON m.clipmetaid = cs.clipmetaid " +
                        "LEFT OUTER JOIN tbl_song s ON cs.songid = s.songid " +
                        "LEFT OUTER JOIN tbl_song_lang sl ON s.songid = sl.songid AND sl.langtype = 'KO' " +
                        "WHERE m.enabled = 'Y' " +
                        "GROUP BY m.clipmetaid " +
                        "ORDER BY m.clipmetaid ASC " +
                        ") AS dummy " +
                        "GROUP BY dummy.clipmetaid;",
                "ClipEsDTO"
        );

        return query.getResultList();
    }

    @Override
    public List<Long> getAllDeletedClipForElasticSearch(int page) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.getEntityManager());

        QClipMeta clipMeta = QClipMeta.clipMeta;

        return queryFactory.select(
                Projections.constructor(
                        Long.class,
                        clipMeta.clipMetaId
                ))
                .from(clipMeta)
                .where(clipMeta.enableYn.eq(Constants.FLAG_T))
                .fetchResults().getResults();
    }

    @Override
    public List<ClipEsDTO> getClipForElasticSearch(Long clipMetaId) {

        Query query = this.getEntityManager().createNativeQuery(
                "SELECT " +
                            "dummy.clipmetaid AS metaid, " +
                            "dummy.cliptitle AS title, " +
                            "dummy.cliptitleen AS titleen, " +
                            "GROUP_CONCAT(DISTINCT dummy.artistname SEPARATOR '---') AS artists, " +
                            "GROUP_CONCAT(DISTINCT dummy.artistnameen SEPARATOR '---') AS artistsen, " +
                            "GROUP_CONCAT(DISTINCT dummy.songname SEPARATOR '---') AS songs, " +
                            "GROUP_CONCAT(DISTINCT dummy.songnameen SEPARATOR '---') AS songsen " +
                            "FROM ( " +
                            "SELECT " +
                            "m.clipmetaid, " +
                            "(SELECT cml.cliptitle FROM tbl_clip_meta_lang cml WHERE cml.clipmetaid = m.clipmetaid AND cml.langtype = 'KO') cliptitle, " +
                            "(SELECT cml.cliptitle FROM tbl_clip_meta_lang cml WHERE cml.clipmetaid = m.clipmetaid AND cml.langtype = 'EN') cliptitleen, " +
                            "GROUP_CONCAT(DISTINCT (SELECT al.artistname FROM tbl_artist_lang al WHERE al.artistid = a.artistid AND al.langtype = 'KO') SEPARATOR '---') artistname, " +
                            "GROUP_CONCAT(DISTINCT (SELECT al.artistname FROM tbl_artist_lang al WHERE al.artistid = a.artistid AND al.langtype = 'EN') SEPARATOR '---') artistnameen, " +
                            "GROUP_CONCAT(DISTINCT (SELECT sl.songname FROM tbl_song_lang sl WHERE sl.songid = s.songid AND sl.langtype = 'KO') SEPARATOR '---') songname, " +
                            "GROUP_CONCAT(DISTINCT (SELECT sl.songname FROM tbl_song_lang sl WHERE sl.songid = s.songid AND sl.langtype = 'EN') SEPARATOR '---') songnameen " +
                            "FROM tbl_clip_meta m " +
                            "LEFT OUTER JOIN tbl_clip_artist ca ON m.clipmetaid = ca.clipmetaid " +
                            "LEFT OUTER JOIN tbl_artist a ON ca.artistid = a.artistid " +
                            "LEFT OUTER JOIN tbl_clip_song cs ON m.clipmetaid = cs.clipmetaid " +
                            "LEFT OUTER JOIN tbl_song s ON cs.songid = s.songid " +
                            "WHERE m.clipmetaid = " + clipMetaId + " " +
                            "GROUP BY m.clipmetaid " +
                            "ORDER BY m.clipmetaid ASC " +
                            ") AS dummy " +
                            "GROUP BY dummy.clipmetaid;",
                "ClipEsDTO"
        );

        return query.getResultList();
    }

    private String whereBuilder(CustomSearchParam searchParam) {

        StringBuilder stringBuilder = new StringBuilder();

        if (searchParam.getCorporator() != null && searchParam.getCorporator() != "") {
            if (stringBuilder.toString().length() > 0) {
                stringBuilder.append("AND \n");
            }
            stringBuilder.append("cm.corporator = '" + searchParam.getCorporator() + "' \n");
        }
        if (searchParam.getProgram() != null && searchParam.getProgram() != "") {
            if (stringBuilder.toString().length() > 0) {
                stringBuilder.append("AND \n");
            }
            stringBuilder.append("p.programid = '" + searchParam.getProgram() + "' \n");
        }
        if (searchParam.getClipType() != null && searchParam.getClipType() != "") {
            if (stringBuilder.toString().length() > 0) {
                stringBuilder.append("AND \n");
            }
            stringBuilder.append("cm.clipcategory = '" + searchParam.getClipType() + "' \n");
        }
        if (searchParam.getClipClassify() != null) {
            if (searchParam.getClipClassify() == 0) {
                if (stringBuilder.toString().length() > 0) {
                    stringBuilder.append("AND \n");
                }
                stringBuilder.append("cm.clipclassify IS NOT NULL \n");
            } else {
                if (stringBuilder.toString().length() > 0) {
                    stringBuilder.append("AND \n");
                }
                stringBuilder.append("cm.clipclassify = " + searchParam.getClipClassify() + " \n");
            }
        }
        if (searchParam.getEnableState() != null && searchParam.getEnableState() != "") {
            if (stringBuilder.toString().length() > 0) {
                stringBuilder.append("AND \n");
            }
            stringBuilder.append("cm.enableyn = '" + searchParam.getEnableState() + "' \n");
        }
        if (searchParam.getType() != null) {
            if (searchParam.getType() == SearchType.SEARCH_BY_CLIP_NAME) {
                if (stringBuilder.toString().length() > 0) {
                    stringBuilder.append("AND \n");
                }
                stringBuilder.append("cml.cliptitle LIKE '%" + searchParam.getSearchText()+"%' \n");
            }
            if (searchParam.getType() == SearchType.SEARCH_BY_ID) {
                if (stringBuilder.toString().length() > 0) {
                    stringBuilder.append("AND \n");
                }
                stringBuilder.append("cm.clipmetaid = " + Long.parseLong(searchParam.getSearchText()) + " \n");
            }
        }

        String where = "";
        if (stringBuilder.toString().length() > 0) {
            where = "WHERE " + stringBuilder.toString();
        }

        return where;
    }
}
