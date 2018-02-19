package kr.mubeat.cms.service.service.notice;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.service.notice.NoticeDetailDTO;
import kr.mubeat.cms.enumerations.LangType;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
public interface NoticeService {
    /**
     * 1. List
     * 2. Detail
     * 3. Add
     * 4. Update
     * 5. Delete
     */

    CustomPageResult getNoticeList(CustomSearchParam searchParam);
    Result getNoticeDetail(Integer noticeNo, LangType langType);
    Result addNotice(NoticeDetailDTO param);
    Result updateNotice(Integer noticeNo, NoticeDetailDTO param);
    Result deleteNotice(Integer noticeNo);
    Result deleteNoticeLangType(Integer noticeNo, LangType langType);
}
