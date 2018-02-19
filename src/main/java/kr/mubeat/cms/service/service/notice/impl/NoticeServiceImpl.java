package kr.mubeat.cms.service.service.notice.impl;

import kr.mubeat.cms.domain.service.notice.Notice;
import kr.mubeat.cms.domain.service.notice.NoticeLang;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.service.notice.NoticeDetailDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.enumerations.LangType;
import kr.mubeat.cms.repository.service.notice.NoticeLangRepository;
import kr.mubeat.cms.repository.service.notice.NoticeRepository;
import kr.mubeat.cms.service.service.notice.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by moonkyu.lee on 2017. 9. 25..
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private NoticeRepository noticeRepository;
    private NoticeLangRepository noticeLangRepository;

    @Autowired
    public NoticeServiceImpl(
        NoticeRepository noticeRepository,
        NoticeLangRepository noticeLangRepository
    ) {
        this.noticeRepository = noticeRepository;
        this.noticeLangRepository = noticeLangRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getNoticeList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(noticeRepository.getNoticeList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public Result getNoticeDetail(Integer noticeNo, LangType langType) {
        Result result = new Result(EnumResCode.OK);
        result.setData(noticeRepository.getNoticeDetail(noticeNo, langType));
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result addNotice(NoticeDetailDTO param) {
        Notice notice = new Notice();
        notice.setCategory(param.getCategory());
        notice.setIsImportant(param.getIsImportant());

        Integer createdId = noticeRepository.save(notice).getNoticeNo();

        if (createdId != null) {
            NoticeLang noticeLang = new NoticeLang();
            noticeLang.setNoticeNo(createdId);
            noticeLang.setLangType(param.getLangType().name());
            noticeLang.setSubject(param.getSubject());
            noticeLang.setContent(param.getContent());

            noticeLangRepository.save(noticeLang);

            return new Result(EnumResCode.OK);
        } else {
            return new Result(EnumResCode.E998);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateNotice(Integer noticeNo, NoticeDetailDTO param) {
        Notice notice = new Notice();
        notice.setNoticeNo(noticeNo);
        notice.setCategory(param.getCategory());
        notice.setIsImportant(param.getIsImportant());

        noticeRepository.save(notice);

        if (
            (param.getSubject() != null && param.getSubject().length() > 0)
            &&
            (param.getContent() != null && param.getContent().length() > 0)
        ) {
            NoticeLang noticeLang = new NoticeLang();
            noticeLang.setNoticeNo(noticeNo);
            noticeLang.setLangType(param.getLangType().name());
            noticeLang.setSubject(param.getSubject());
            noticeLang.setContent(param.getContent());

            noticeLangRepository.save(noticeLang);
        }

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteNotice(Integer noticeNo) {
        noticeRepository.deleteNotice(noticeNo);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteNoticeLangType(Integer noticeNo, LangType langType) {
        noticeRepository.deleteNoticeLangType(noticeNo, langType);

        return new Result(EnumResCode.OK);
    }
}
