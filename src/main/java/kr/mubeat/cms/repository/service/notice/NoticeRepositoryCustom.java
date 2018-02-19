package kr.mubeat.cms.repository.service.notice;

import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.service.notice.NoticeDetailDTO;
import kr.mubeat.cms.dto.service.notice.NoticeListDTO;
import kr.mubeat.cms.enumerations.LangType;
import org.springframework.data.domain.Page;

/**
 * Created by moonkyu.lee on 2017. 9. 27..
 */
public interface NoticeRepositoryCustom {

    Page<NoticeListDTO> getNoticeList(CustomSearchParam searchParam);
    NoticeDetailDTO getNoticeDetail(Integer noticeNo, LangType langType);
    void deleteNotice(Integer noticeNo);
    void deleteNoticeLangType(Integer noticeNo, LangType langType);

}
