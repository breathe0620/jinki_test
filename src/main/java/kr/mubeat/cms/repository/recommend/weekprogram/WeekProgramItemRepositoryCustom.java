package kr.mubeat.cms.repository.recommend.weekprogram;

import kr.mubeat.cms.dto.recommend.program.ClipDTO;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 9..
 */
public interface WeekProgramItemRepositoryCustom {

    Long deleteByRecommendProgramId(Integer recommendProgramId);

    List<ClipDTO> getProgramItems(Integer recommendProgramId);

    List<String> getProgramBroadDates(String programId);
    List<ClipDTO> getProgramsByBroadDate(
            String programId,
            String broadDate,
            Integer recommendProgramId
    );

}
