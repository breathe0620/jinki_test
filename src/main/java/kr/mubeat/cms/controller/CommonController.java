package kr.mubeat.cms.controller;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.mubeat.cms.enumerations.EnumResCode;
import kr.mubeat.cms.dto.manager.admin.AdminDTO;
import kr.mubeat.cms.dto.meta.artist.ArtistDTO;
import kr.mubeat.cms.dto.Result;
import kr.mubeat.cms.service.common.CommonService;
import kr.mubeat.cms.util.AWSUtils;
import kr.mubeat.cms.util.AWSV4RequestUtil;
import kr.mubeat.cms.util.ElasticsearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by doohwan.yoo on 2017. 4. 17..
 * CommonController
 */
@Controller
public class CommonController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CommonService commonService;
    private AWSUtils awsUtils;
    private ElasticsearchUtil elasticsearchUtil;
    private AWSV4RequestUtil awsv4RequestUtil;
    private AmazonSimpleEmailServiceClient amazonSimpleEmailServiceClient;

    @Autowired
    public CommonController(
            CommonService commonService,
            AWSUtils awsUtils,
            ElasticsearchUtil elasticsearchUtil,
            AWSV4RequestUtil awsv4RequestUtil,
            AmazonSimpleEmailServiceClient amazonSimpleEmailServiceClient
    ) {
        this.commonService = commonService;
        this.awsUtils = awsUtils;
        this.elasticsearchUtil = elasticsearchUtil;
        this.awsv4RequestUtil = awsv4RequestUtil;
        this.amazonSimpleEmailServiceClient = amazonSimpleEmailServiceClient;
    }

    /**
     * CMS 메인 페이지
     * @return
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String mainView() {
        return "cms_main";
    }

    /**
     * signin 페이지 요청
     * @return
     */
    @RequestMapping(value="/signin", method = RequestMethod.GET)
    public String signin() {

        if(commonService.isExistSuperUser()) {
            return "signin";
        }

        return "redirect:signup-super";

    }

    /**
     * 슈퍼 관리자 생성 페이지 요청
     * @return
     */
    @RequestMapping(value="/signup-super", method = RequestMethod.GET)
    public String signupSuperView() {
        return "signup_super";
    }

    /**
     * 슈퍼 관리자 생성
     * @param input
     * @return
     */
    @RequestMapping(value="/signup-super", method = RequestMethod.POST)
    public @ResponseBody Result signupSuper(@ModelAttribute AdminDTO input) {
        return commonService.signupSuperAdmin(input);
    }

    /**
     * 권한 없음 페이지
     * @return
     */
    @RequestMapping(value="/permission-error", method = RequestMethod.GET)
    public String permissionError() {
        return "errors/permission_error";
    }

    /**
     * 이곳부터 아래는 front 에서의 원할한 기능 테스트 목적으로 만들어 졌으며,
     * front 테스트 이후 삭제 하면 됩니다.
     */

    /**
     * sample
     * @return
     */
    @RequestMapping(value="/sample", method = RequestMethod.GET)
    public String sample() {
        return "sample";
    }

    /**
     * 데이터 전달 테스트
     * @param mainImg
     * @param param
     * @return
     */
    @RequestMapping(value="/sample2", method = RequestMethod.POST)
    public @ResponseBody Result sample2(@RequestPart("mainImg") MultipartFile mainImg, @RequestPart("test") String param) {

        try {
            ArtistDTO jsonAd = new ObjectMapper().readValue(param, ArtistDTO.class);
        }
        catch(IOException e) {
            return new Result(EnumResCode.E999);
        }

        return new Result(EnumResCode.OK);
    }

    /**
     * 이미지 크롭 테스트
     * @return
     */
    @RequestMapping(value = "/sampleCrop", method = RequestMethod.GET)
    public String cropImageEditor() {
        return "sample";
    }

    /**
     * 이미지 전달 테스트
     * @param img
     * @return
     */
    @RequestMapping(value = "/sample3", method = RequestMethod.POST)
    public @ResponseBody
    Result sample3(
            @RequestPart("img") MultipartFile img
    ) {
        try {
            File file = new File(System.currentTimeMillis()+".png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(img.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            return new Result(EnumResCode.E999);
        }
        return new Result(EnumResCode.OK);
    }

    /**
     * SES 테스트
     * @return
     */
    @RequestMapping(value = "/ses", method = RequestMethod.GET)
    public @ResponseBody
    String ses(){
        String to = "moonkyu@vlending.co.kr";
        String from = "dhyoo@vlending.co.kr";
        Destination destination = new Destination().withToAddresses(new String[]{to});

        Content subject = new Content().withData("Mail Test - 한글");
        Content textBody = new Content().withData("Mail Body - 한글");
        Body body = new Body().withText(textBody);

        Message message = new Message().withSubject(subject).withBody(body);

        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message);
        amazonSimpleEmailServiceClient.setRegion(Region.getRegion(Regions.US_EAST_1));
        SendEmailResult result = amazonSimpleEmailServiceClient.sendEmail(request);
        return result.toString();
    }
}
