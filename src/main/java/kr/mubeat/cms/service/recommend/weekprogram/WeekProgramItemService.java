package kr.mubeat.cms.service.recommend.weekprogram;

import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.program.WeekProgramItemDTO;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 9..
 */
public interface WeekProgramItemService {

    Result getProgramBroadDates(String programId);
    Result getProgramsByBroadDate(String programId, String broadDt, Integer recommendProgramId);

    Result getProgramitems(Integer recommendProgramId);

    Result addClip(List<WeekProgramItemDTO> weekProgramItems);
    Result deleteClip(List<WeekProgramItemDTO> weekProgramItems);

}
