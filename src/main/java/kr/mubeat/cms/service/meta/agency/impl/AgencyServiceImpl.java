package kr.mubeat.cms.service.meta.agency.impl;

import kr.mubeat.cms.dto.meta.agency.AgencyDTO;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.domain.meta.agency.Agency;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.repository.meta.agency.AgencyRepository;
import kr.mubeat.cms.repository.meta.artist.ArtistRepository;
import kr.mubeat.cms.service.meta.agency.AgencyService;
import kr.mubeat.cms.util.CSVUtil;
import kr.mubeat.cms.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by moonkyu.lee on 2017. 6. 13..
 */
@Service
public class AgencyServiceImpl implements AgencyService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ArtistRepository artistRepository;
    private AgencyRepository agencyRepository;

    private ExcelUtil excelUtil;
    private CSVUtil csvUtil;

    public AgencyServiceImpl(
            ArtistRepository artistRepository,
            AgencyRepository agencyRepository,
            ExcelUtil excelUtil,
            CSVUtil csvUtil
    ) {
        this.artistRepository = artistRepository;
        this.agencyRepository = agencyRepository;
        this.excelUtil = excelUtil;
        this.csvUtil = csvUtil;
    }

    @Override
    public List<Agency> getAllAgency() {
        return agencyRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Result getAgency(Pageable pageable) {
        Result result = new Result(EnumResCode.OK);
        result.setData(agencyRepository.findAll(pageable));

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Result findAllByAgencyName(String agencyName) {
        List<Agency> listAgency = agencyRepository.findAllByAgencyNameLike('%' + agencyName + '%');

        if(listAgency.size() == 0) {
            return new Result(EnumResCode.E008);
        }

        Result result = new Result(EnumResCode.OK);
        result.setData(listAgency);

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public Result addAgency(Agency param) {
        if(agencyRepository.findByAgencyName(param.getAgencyName()) != null) {
            return new Result(EnumResCode.E007);
        }
        param.setAgencyName(param.getAgencyName().trim());
        param.setAgencyNameEn(param.getAgencyNameEn().trim());
        agencyRepository.save(param);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result updateAgency(Integer agencyId, AgencyDTO param) {
        Agency agency = new Agency();
        agency.setAgencyId(agencyId);
        agency.setAgencyName(param.getAgencyName().trim());
        agency.setAgencyNameEn(param.getAgencyNameEn().trim());
        agencyRepository.save(agency);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = false)
    public Result deleteAgency(Integer agencyId) {
        agencyRepository.delete(agencyId);
        artistRepository.removeAgencyByAgencyId(agencyId);

        return new Result(EnumResCode.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public Result getSameAgencyArtist(Integer agencyId) {
        Result result = new Result(EnumResCode.OK);
        result.setData(artistRepository.findSameAgencyArtist(agencyId));

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getAgencyForExcel() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(-1);
        Sheet sheet = sxssfWorkbook.createSheet("Original");

        int rowNum = 0;

        Row row = null;
        Cell cell = null;

        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("고유번호(숫자)");
        cell = row.createCell(1);
        cell.setCellValue("소속사명");
        cell = row.createCell(2);
        cell.setCellValue("소속사명-영문");

        List<Agency> agencies = agencyRepository.findAll();
        for (Agency agency : agencies) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(excelUtil.escapeIntegerNull(agency.getAgencyId()));
            cell = row.createCell(1);
            cell.setCellValue(excelUtil.escapeNull(agency.getAgencyName()));
            cell = row.createCell(2);
            cell.setCellValue(excelUtil.escapeNull(agency.getAgencyNameEn()));
        }

        return excelUtil.writeToXlsx(sxssfWorkbook);
    }

    @Override
    @Transactional(readOnly = false)
    public Result xlsxToUpdateAgencyMeta(MultipartFile file) {

        try {

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet xssfSheet = xssfWorkbook.getSheet("Original");

            int lastRowNum = xssfSheet.getLastRowNum();

            Integer agencyId;
            String agencyNameEn;

            int rowNum = 1;
            for (; rowNum <= lastRowNum; rowNum++) {
                try {
                    agencyId = (int)xssfSheet.getRow(rowNum).getCell(0).getNumericCellValue();
                    agencyNameEn = xssfSheet.getRow(rowNum).getCell(2).getRichStringCellValue().getString();

                    if (
                        (agencyId != null && agencyId > 0)
                            &&
                        (agencyNameEn != null && !agencyNameEn.equalsIgnoreCase("null") && agencyNameEn.length() > 0)
                    ) {
                        agencyRepository.updateAgencyTitleEn(agencyId, agencyNameEn);
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
}
