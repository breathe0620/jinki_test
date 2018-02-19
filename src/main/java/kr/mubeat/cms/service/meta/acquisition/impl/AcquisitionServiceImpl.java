package kr.mubeat.cms.service.meta.acquisition.impl;

import kr.mubeat.cms.config.ClipState;
import kr.mubeat.cms.config.Constants;
import kr.mubeat.cms.domain.meta.clip.Transcode;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ClipAcquisitionDetailDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.repository.meta.acquisition.AcquisitionRepository;
import kr.mubeat.cms.repository.meta.program.ProgramMetaRepository;
import kr.mubeat.cms.service.meta.acquisition.AcquisitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
@Service
public class AcquisitionServiceImpl implements AcquisitionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AcquisitionRepository acquisitionRepository;
    private RedisTemplate redisTemplate;

    @Autowired
    public AcquisitionServiceImpl(
            AcquisitionRepository acquisitionRepository,
            RedisTemplate redisTemplate
    ) {
        this.acquisitionRepository = acquisitionRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getAcquisitionList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        logger.info(">> pageResult 1: "+pageResult);
        logger.info(">> searchParam : "+searchParam);
        pageResult.setPageResult(acquisitionRepository.getAcquisitionList(searchParam));
        logger.info(">> pageResult 2: "+pageResult);
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getAcquisitionStateList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(acquisitionRepository.getAcquisitionStateList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = true)
    public ClipAcquisitionDetailDTO getAcquisitionDetail(Long metaId) {
        ClipAcquisitionDetailDTO dto = new ClipAcquisitionDetailDTO();

        dto.setClipDTO(acquisitionRepository.getClip(metaId));
        dto.setClipMetaDTO(acquisitionRepository.getClipMeta(metaId));
        dto.setClipAcquisitionDTO(acquisitionRepository.getClipAcquisition(metaId));
        dto.setContentDTO(acquisitionRepository.getContent(metaId));
        dto.setClipMediaList(acquisitionRepository.getClipMediaList(metaId));
        dto.setOriginalVideo(acquisitionRepository.getOriginalClipPath(metaId));

        return dto;
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateUploadState(List<Long> id) {

        // 방송 장르 02 (예능) 번 이고 입수 상태가 N 인 것은 uploadyn 를 W 로 처리
        // 방송 장르 03 (음악) 번 이고 입수 상태가 F 인 것은 uploadyn 를 N 로 처리 (다만 수동 확인 필요)

        redisTemplate.delete("noRemainAwaitingUpload");
        redisTemplate.delete("noRemainAwaitingTranscode");

        List<Transcode> transcodeList = acquisitionRepository.getUploadState(id);

        List<Long> entertainmentList = new ArrayList<>();
        List<Long> musicList = new ArrayList<>();

        for (Transcode transcode : transcodeList) {
            /**
             * 기존은 현재 클립 상태를 보고 트랜스코딩 요청을 하도록 하였으나,
             * 트랜스코딩의 알 수 없는 오류로 인하여
             * 클립 상태가 무엇이든 트랜스코딩을 요청할 수 있도록 변경
             */
            if (
                transcode.getClipCategory().equalsIgnoreCase(Constants.ENTERTAINMENT_PROGRAM)
            ) {
                entertainmentList.add(transcode.getClipMetaId());
            }
            /**
             * 음악인 경우에는 실패든 미변환이든 재입수 요청이 필요하기 때문에 하단부분 주석처리
             */
//            /**
//             * 음악인 경우는 실패(F) 일 때만 처리
//             */
//            if (
//                transcode.getClipCategory().equalsIgnoreCase(Constants.MUSIC_PROGRAM)
//                    &&
//                transcode.getUploadYn().equalsIgnoreCase(ClipState.FAIL)
//            ) {
//                musicList.add(transcode.getClipMetaId());
//            }
            if (
                transcode.getClipCategory().equalsIgnoreCase(Constants.MUSIC_PROGRAM)
            ) {
                musicList.add(transcode.getClipMetaId());
            }
        }

        if (entertainmentList.size() > 0) {
            if (acquisitionRepository.updateTranscode(entertainmentList, Constants.ENTERTAINMENT_PROGRAM) > 0) {
                return new Result(EnumResCode.OK);
            }
        }
        if (musicList.size() > 0) {
            if (acquisitionRepository.updateTranscode(musicList, Constants.MUSIC_PROGRAM) > 0) {
                return new Result(EnumResCode.OK);
            }
        }
        return new Result(EnumResCode.E003);
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateAcquisitionInformation(Long id, String clipTitle) {
        Long result = acquisitionRepository.updateAcquisitionInformation(id, clipTitle);
        if (result != null && result > 0) {
            return new Result(EnumResCode.OK);
        }
        return new Result(EnumResCode.E003);
    }
}
