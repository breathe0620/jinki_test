package kr.mubeat.cms.service.recommend.theme.impl;

import kr.mubeat.cms.domain.recommend.theme.Theme;
import kr.mubeat.cms.domain.recommend.theme.ThemeItem;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.theme.ThemeDTO;
import kr.mubeat.cms.dto.recommend.theme.ThemePosition;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.repository.recommend.home.HomeRepository;
import kr.mubeat.cms.repository.recommend.theme.ThemeItemRepository;
import kr.mubeat.cms.repository.recommend.theme.ThemeRepository;
import kr.mubeat.cms.service.recommend.theme.ThemeService;
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

/**
 * Created by moonkyu.lee on 2017. 11. 3..
 */
@Service
public class ThemeServiceImpl implements ThemeService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.aws.cloudfront-url}")
    private String cloudFrontUrl;

    private HomeRepository homeRepository;
    private ThemeRepository themeRepository;
    private ThemeItemRepository themeItemRepository;

    private AWSUtils awsUtils;
    private TimeStampUtil timeStampUtil;

    @Autowired
    public ThemeServiceImpl(
        HomeRepository homeRepository,
        ThemeRepository themeRepository,
        ThemeItemRepository themeItemRepository,
        AWSUtils awsUtils,
        TimeStampUtil timeStampUtil
    ) {
        this.homeRepository = homeRepository;
        this.themeRepository = themeRepository;
        this.themeItemRepository = themeItemRepository;
        this.awsUtils = awsUtils;
        this.timeStampUtil = timeStampUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThemeDTO> getThemes() {
        return themeRepository.getThemes();
    }

    @Override
    @Transactional(readOnly = true)
    public Theme getTheme(Integer recommendThemeId) {
        return themeRepository.findOne(recommendThemeId);
    }

    @Override
    @Transactional(readOnly = false)
    public Result saveTheme(ThemeDTO themeDTO, MultipartFile img) {

        Theme theme = new Theme(
            themeDTO.getPosition(),
            themeDTO.getTitle(),
            themeDTO.getTitleEn(),
            themeDTO.getThemeImgUrl(),
            themeDTO.getEnableYn()
        );

        Integer themeId = null;

        if (themeDTO.getRecommendThemeId() == null) {
            themeId = themeRepository.save(theme).getRecommendThemeId();
        } else if (themeDTO.getRecommendThemeId() != 0) {
            theme.setRecommendThemeId(themeDTO.getRecommendThemeId());
            themeId = themeRepository.save(theme).getRecommendThemeId();
            if (themeId != themeDTO.getRecommendThemeId()) {
                throw new RuntimeException("Database Exception");
            }
        }

        if (img != null) {
//            awsUtils.deleteObjectFromS3(
//                "recommend/theme/" + themeId + "_theme_img"
//            );
            String outputKey = awsUtils.uploadObjectToS3(
                img,
                "recommend/theme/" + themeId + "/"+ timeStampUtil.timeStamp(),
                "image/png"
            );
            if (outputKey != null) {
                theme.setThemeImgUrl(cloudFrontUrl + outputKey);
            }
//            awsUtils.invalidateCachedObjectFromCloudfront(
//                "resize/recommend/theme/" + themeId + "_theme_img.png*"
//            );
        }

        themeRepository.save(theme);
        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteTheme(Integer recommendThemeId) {
        themeItemRepository.deleteByThemeId(recommendThemeId);
        themeRepository.delete(recommendThemeId);
        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getSearchClipList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(homeRepository.getSearchClipList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = false)
    public Result updatePosition(List<ThemePosition> themePositions) {

        for (ThemePosition themePosition : themePositions) {
            themeRepository.updatePosition(
                themePosition.getRecommendThemeId(),
                themePosition.getPosition()
            );
        }
        return new Result(EnumResCode.OK);
    }
}
