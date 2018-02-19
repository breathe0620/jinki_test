package kr.mubeat.cms.service.meta.program;

import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;
import kr.mubeat.cms.dto.meta.program.ProgramMetaDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by moonkyu.lee on 2017. 9. 22..
 */
public interface ProgramService {

    /**
     * 1. List
     * 2. Add
     * 3. Edit
     * 4. Delete
     */

    CustomPageResult getProgramList(CustomSearchParam searchParam);
    Result updateProgram(String programId, ProgramMetaDTO param);

    byte[] getProgramForExcel();
    Result xlsxToUpdateProgramMeta(MultipartFile file);

    List<String> getCorporators();
    List<ProgramSearchDTO> getPrograms(String corporator);

}
