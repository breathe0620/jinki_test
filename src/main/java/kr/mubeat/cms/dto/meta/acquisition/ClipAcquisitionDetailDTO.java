package kr.mubeat.cms.dto.meta.acquisition;

import kr.mubeat.cms.domain.meta.clip.ClipMedia;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 9. 5..
 */
public class ClipAcquisitionDetailDTO implements Serializable {

    private static final long serialVersionUID = 4109394210197766263L;

    /**               SMR       ARCHIVE     AGENCY
     * 클립 정보          O         O           O
     * 프로그램 정보       O         △           X
     * 회차 정보          O         △           X
     * CDN 정보          O         O           O
     * OriginalData     O         X           ?
     *
     * ARCHIVE 는
     * MBC>
     * 제목(방송이름, 회차)
     * 부제목(방송일자, 메인 출연진)
     * 회차
     * 영상종류(시:분:초:ms)
     * 전송영상길이
     * 본편시작시간
     * 본편종료시간
     * 본편영상길이
     * 회차ID
     * 방송일
     * 출연진
     * 줄거리
     * 의 데이터를 내려준다
     *

     MBC Archive XML Data

     <?xml version="1.0" encoding="utf-8" standalone="yes"?>
     <EPISODE_META>
     <제목><![CDATA[쇼! 음악중심, 437회]]></제목>
     <부제목><![CDATA[[2014년 12월 27일]  동방신기 . BEAST . 인피니트 . 태민 . SISTAR . EXO]]></부제목>
     <회차><![CDATA[437회]]></회차>
     <영상종류><![CDATA[PART]]></영상종류>
     <전송영상길이><![CDATA[00:04:04:11]]></전송영상길이>
     <본편시작시간><![CDATA[]]></본편시작시간>
     <본편종료시간><![CDATA[]]></본편종료시간>
     <본편영상길이><![CDATA[]]></본편영상길이>
     <회차ID><![CDATA[EP201612191153]]></회차ID>
     <방송일><![CDATA[2014-12-27]]></방송일>
     <출연진><![CDATA[MC: 민호,소현,지코출연: 동방신기,비스트,인피니트,태민, 씨스타,EXO,걸스데이,틴탑, 에이핑크,블락비,에일리,B1A4, 빅스,비투비,방탄소년단,레드벨벳, 마마무,러블리즈,제>이켠]]></출연진>
     <줄거리><![CDATA[]]></줄거리>
     </EPISODE_META>
     */

    private ClipDTO clipDTO;
    private ClipMetaDTO clipMetaDTO;
    private ClipAcquisitionDTO clipAcquisitionDTO;
    private ContentDTO contentDTO;
    private List<ClipMedia> clipMediaList;
    private String originalVideo;

    public ClipDTO getClipDTO() {
        return clipDTO;
    }

    public void setClipDTO(ClipDTO clipDTO) {
        this.clipDTO = clipDTO;
    }

    public ClipMetaDTO getClipMetaDTO() {
        return clipMetaDTO;
    }

    public void setClipMetaDTO(ClipMetaDTO clipMetaDTO) {
        this.clipMetaDTO = clipMetaDTO;
    }

    public ClipAcquisitionDTO getClipAcquisitionDTO() {
        return clipAcquisitionDTO;
    }

    public void setClipAcquisitionDTO(ClipAcquisitionDTO clipAcquisitionDTO) {
        this.clipAcquisitionDTO = clipAcquisitionDTO;
    }

    public ContentDTO getContentDTO() {
        return contentDTO;
    }

    public void setContentDTO(ContentDTO contentDTO) {
        this.contentDTO = contentDTO;
    }

    public List<ClipMedia> getClipMediaList() {
        return clipMediaList;
    }

    public void setClipMediaList(List<ClipMedia> clipMediaList) {
        this.clipMediaList = clipMediaList;
    }

    public String getOriginalVideo() {
        return originalVideo;
    }

    public void setOriginalVideo(String originalVideo) {
        this.originalVideo = originalVideo;
    }
}
