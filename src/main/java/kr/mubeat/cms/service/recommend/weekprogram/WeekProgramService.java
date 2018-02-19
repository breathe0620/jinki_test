package kr.mubeat.cms.service.recommend.weekprogram;

import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.recommend.program.ProgramDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 10. 31..
 */
public interface WeekProgramService {

    List<ProgramDTO> getPrograms();
    ProgramDTO getProgram(Integer recommendProgramId);
    Result updateProgram(ProgramDTO programDTO, MultipartFile file);

}
