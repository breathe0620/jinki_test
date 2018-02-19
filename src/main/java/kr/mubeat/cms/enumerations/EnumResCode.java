package kr.mubeat.cms.enumerations;

/**
 * Created by doohwan.yoo on 2017. 5. 11..
 */
public enum EnumResCode {

    OK("0000" ,"정상처리"),
    E001("E001" , "해당 곡이 존재하지 않습니다"),
    E002("E002" , "해당 아티스트가 존재하지 않습니다"),
    E003("E003" , "변경 내역이 없습니다"),
    E004("E004" , "등록 할 수 없는 값 입니다"),
    E005("E005" , "삭제하는 도중 오류가 발생하였습니다"),
    E006("E006" , "존재하지 않는 관리자 입니다"),
    E007("E007" , "중복 된 값입니다 다시 확인해 주세요"),
    E008("E008" , "검색 결과가 없습니다"),

    E009("E009" , "해당 앨범이 존재하지 않습니다"),  // 6. 9. 추가 : 앨범이 존재하지 않을 경우

    E010("E010" , "이미 등록된 앨범입니다 다시 확인해 주세요."),  // 6. 23. 추가 : 앨범의 mid 가 존재 할 경우
    E011("E011" , "이미 등록된 아티스트입니다 다시 확인해 주세요."),  // 6. 23. 추가 : 아티스트의 mid 가 존재 할 경우
    E012("E012" , "이미 등록된 곡입니다 다시 확인해 주세요."),  // 6. 23. 추가 : 곡의 mid 가 존재 할 경우

    E013("E013" , "상세 클립정보 값과 아티스트 값을 확인해 주세요. 두 정보 모두 등록이 되어야 합니다."), // 7. 6. 추가

    E500("E500" , "파라미터 오류[agencyid]"),
    E501("E501" , "파라미터 오류[debutsongid]"),
    E502("E502" , "파라미터 오류[artisttypeid]"),
    E503("E503" , "파라미터 오류[albumtypeid]"),  // 6. 12. 추가 : 앨범 타입이 존재하지 않을 경우

    E504("E504" , "파라미터 오류"), // 7. 13. 추가 : 관리자 파라미터 오류

    E700("E700" , "데이터 베이스 업데이트 실패"),

    E800("E800" , "S3 파일 복사 실패"),

    E990("E990" , "파라미터 부족"),
    E991("E991" , "파라미터 변환 오류"),
    E996("E996" , "DB 커넥션이 최대치 입니다"),
    E997("E997" , "잘못 된 URL 입니다"),
    E998("E998" , "데이터베이스 에러"),
    E999("E999" , "시스템 에러");

    private String code;
    private String msg;

    EnumResCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.msg;
    }
}