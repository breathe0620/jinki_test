package kr.mubeat.cms.service.meta.program.impl;

import kr.mubeat.cms.annotation.redis.RedisExpire;
import kr.mubeat.cms.annotation.redis.RedisHashExpire;
import kr.mubeat.cms.domain.meta.program.ProgramMeta;
import kr.mubeat.cms.dto.CustomPageResult;
import kr.mubeat.cms.dto.CustomSearchParam;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.dto.meta.acquisition.ProgramSearchDTO;
import kr.mubeat.cms.dto.meta.program.ProgramMetaDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.repository.meta.program.ProgramMetaRepository;
import kr.mubeat.cms.service.meta.program.ProgramService;
import kr.mubeat.cms.util.CSVUtil;
import kr.mubeat.cms.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by moonkyu.lee on 2017. 9. 22..
 */
@Service
public class ProgramServiceImpl implements ProgramService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProgramMetaRepository programMetaRepository;

    private ExcelUtil excelUtil;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ProgramServiceImpl(
        ProgramMetaRepository programMetaRepository,
        ExcelUtil excelUtil
    ) {
        this.programMetaRepository = programMetaRepository;
        this.excelUtil = excelUtil;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResult getProgramList(CustomSearchParam searchParam) {
        CustomPageResult pageResult = new CustomPageResult(searchParam);
        pageResult.setPageResult(programMetaRepository.getProgramMetaList(searchParam));
        return pageResult;
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateProgram(String programId, ProgramMetaDTO param) {
        ProgramMeta programMeta = programMetaRepository.findOne(programId);
        programMeta.setProgramId(programId);
        programMeta.setProgramTitleEn(param.getProgramTitleEn());
        programMeta.setLimitnation(param.getLimitnation());
        programMetaRepository.save(programMeta);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getProgramForExcel() {

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(-1);
        Sheet sheet = sxssfWorkbook.createSheet("Original");

        int rowNum = 0;

        Row row = null;
        Cell cell = null;

        row = sheet.createRow(rowNum);
        cell = row.createCell(0);
        cell.setCellValue("고유번호");
        cell = row.createCell(1);
        cell.setCellValue("방송사");
        cell = row.createCell(2);
        cell.setCellValue("프로그램 타이틀");
        cell = row.createCell(3);
        cell.setCellValue("프로그램 타이틀 - 영문");
        cell = row.createCell(4);
        cell.setCellValue("국가제한");

        List<ProgramMetaDTO> programMetaList = programMetaRepository.getProgramForExcel();
        for (ProgramMetaDTO programMeta : programMetaList) {
            row = sheet.createRow(++rowNum);
            cell = row.createCell(0);
            cell.setCellValue(programMeta.getProgramId());
            cell = row.createCell(1);
            cell.setCellValue(programMeta.getCorporator());
            cell = row.createCell(2);
            cell.setCellValue(programMeta.getProgramTitle());
            cell = row.createCell(3);
            cell.setCellValue(programMeta.getProgramTitleEn());
            cell = row.createCell(4);
            cell.setCellValue(programMeta.getLimitnation());
        }

        return excelUtil.writeToExcel(sxssfWorkbook);
    }

    @Override
    @Transactional(readOnly = false)
    public Result xlsxToUpdateProgramMeta(MultipartFile file) {

        try {

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheet("Original");

            int lastRowNum = xssfSheet.getLastRowNum();

            String programId;
            String programTitleEn;

            int rowNum = 1;
            for (; rowNum <= lastRowNum; rowNum++) {
                try {
                    programId = xssfSheet.getRow(rowNum).getCell(0).getRichStringCellValue().getString();
                    programTitleEn = xssfSheet.getRow(rowNum).getCell(3).getRichStringCellValue().getString();

                    if (
                        (programId != null && !programId.equalsIgnoreCase("null") && programId.length() > 0)
                            &&
                        (programTitleEn != null && !programTitleEn.equalsIgnoreCase("null") && programTitleEn.length() > 0)
                    ) {
                        programMetaRepository.updateProgramTitleEn(programId, programTitleEn);
                    }

                } catch (NullPointerException e) {

                }
            }

            return new Result(EnumResCode.OK);

        } catch (IOException |
                NumberFormatException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Wrong Data Format");
        }
    }

    @Override
    @RedisExpire(key = "acquisition:corporators", expireTime = 60L)
    @Transactional(readOnly = true)
    public List<String> getCorporators() {
        return programMetaRepository.getCorporators();
    }

    @Override
    @RedisHashExpire(key = "acquisition:programs", expireTime = 60L)
    @Transactional(readOnly = true)
    public List<ProgramSearchDTO> getPrograms(String corporator) {
        return programMetaRepository.getPrograms(corporator);
    }
}
