package kr.mubeat.cms.repository.recommend.weekprogram;

import kr.mubeat.cms.dto.recommend.program.ProgramDTO;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 11. 9..
 */
public interface WeekProgramRepositoryCustom {

    List<ProgramDTO> getPrograms();
    ProgramDTO getProgram(Integer recommendProgramId);

}
