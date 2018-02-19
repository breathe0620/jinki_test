package kr.mubeat.cms.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ExcelUtil {

    public byte[] writeToExcel(
            Workbook workbook
    ) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byte[] datas = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            workbook.close();
            return datas;
        } catch (IOException e) {

        } finally {

        }
        return null;
    }

    public byte[] writeToXlsx(
            SXSSFWorkbook sxssfWorkbook
    ) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            sxssfWorkbook.write(byteArrayOutputStream);
            byte[] datas = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            sxssfWorkbook.close();
            sxssfWorkbook.dispose();
            return datas;
        } catch (IOException e) {

        } finally {

        }
        return null;
    }

    public Long escapeLongNull(Object data) {
        if (data == null) {
            return 0L;
        }
        return Long.parseLong(String.valueOf(data));
    }

    public Integer escapeIntegerNull(Object data) {
        if (data == null) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(data));
    }

    public String escapeNull(Object data) {
        if (data == null) {
            return "";
        }
        return String.valueOf(data);
    }
}
