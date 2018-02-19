package kr.mubeat.cms.service.recommend.weekprogram.impl;

import kr.mubeat.cms.domain.recommend.weekprogram.WeekProgram;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.program.ProgramDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.repository.recommend.weekprogram.WeekProgramItemRepository;
import kr.mubeat.cms.repository.recommend.weekprogram.WeekProgramRepository;
import kr.mubeat.cms.service.recommend.weekprogram.WeekProgramService;
import kr.mubeat.cms.util.AWSUtils;
import kr.mubeat.cms.util.TimeStampUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Service
public class WeekProgramServiceImpl implements WeekProgramService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.aws.cloudfront-url}")
    private String cloudFrontUrl;

    private WeekProgramRepository weekProgramRepository;
    private WeekProgramItemRepository weekProgramItemRepository;

    private AWSUtils awsUtils;
    private TimeStampUtil timeStampUtil;

    @Autowired
    public WeekProgramServiceImpl(
        WeekProgramRepository weekProgramRepository,
        WeekProgramItemRepository weekProgramItemRepository,
        AWSUtils awsUtils,
        TimeStampUtil timeStampUtil
    ) {
        this.weekProgramRepository = weekProgramRepository;
        this.weekProgramItemRepository = weekProgramItemRepository;
        this.awsUtils = awsUtils;
        this.timeStampUtil = timeStampUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramDTO> getPrograms() {
        return weekProgramRepository.getPrograms();
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramDTO getProgram(Integer recommendProgramId) {
        return weekProgramRepository.getProgram(recommendProgramId);
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateProgram(ProgramDTO programDTO, MultipartFile img) {

        String currentProgram = weekProgramRepository.findOne(programDTO.getRecommendProgramId()).getProgramId();

        WeekProgram weekProgram = new WeekProgram(
            programDTO.getRecommendProgramId(),
            programDTO.getProgramId(),
            programDTO.getBannerImgUrl()
        );

        if (!Objects.equals(currentProgram, programDTO.getProgramId())) {
            weekProgramItemRepository.deleteByRecommendProgramId(programDTO.getRecommendProgramId());
        }

        if (img != null) {
//            awsUtils.deleteObjectFromS3(
//                    "recommend/weekprogram/" + programDTO.getRecommendProgramId() + "/"
//            );
            String outputKey = awsUtils.uploadObjectToS3(
                img,
                "recommend/weekprogram/"+ programDTO.getRecommendProgramId() +"/"+ timeStampUtil.timeStamp() ,
                "image/png"
            );
            if (outputKey != null) {
                weekProgram.setBannerImgUrl(cloudFrontUrl + outputKey);
            }
//            awsUtils.invalidateCachedObjectFromCloudfront(
//                "resize/recommend/weekprogram/" + programDTO.getRecommendProgramId() + "_week_program_img.png*"
//            );
        }

        weekProgramRepository.save(weekProgram);

        return new Result(EnumResCode.OK);
    }
}
