package kr.mubeat.cms.util;

import kr.mubeat.cms.config.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moonkyu.lee on 2017. 10. 30..
 */
@Service
public class CSVUtil {

    public Map<String, String> parse(
            MultipartFile multipartFile,
            int id,
            int eng
    ) {
        Map<String, String> returnData = new HashMap<>();

        BufferedReader bufferedReader = null;
        String line;
        String delimiter = "\t";
        Long lineNumber = 0L;

        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            multipartFile.getInputStream(),
                            Charset.forName(Constants.CHARSET_UTF8)
                    )
            );
            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * 첫줄은 타이틀 이므로 파싱 하지 않는다
                 */
                if (lineNumber == 0) {
                    lineNumber++;
                    continue;
                }
                String[] field = line.split(delimiter);
                if (field.length > eng) {
                    returnData.put(field[id].trim(), field[eng].trim());
                    lineNumber++;
                }
            }
            return returnData;
        } catch (ArrayIndexOutOfBoundsException |
                IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Wrong Data Format");
        }
    }
}
