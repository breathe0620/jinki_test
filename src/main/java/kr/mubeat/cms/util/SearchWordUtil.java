package kr.mubeat.cms.util;

import org.springframework.stereotype.Service;

/**
 * Created by moonkyu.lee on 2017. 7. 27..
 */
@Service
public class SearchWordUtil {

    // ㄱ      ㄲ      ㄴ      ㄷ      ㄸ      ㄹ      ㅁ      ㅂ      ㅃ      ㅅ      ㅆ      ㅇ      ㅈ      ㅉ      ㅊ      ㅋ      ㅌ      ㅍ      ㅎ
    final static char[] first   = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };
    // ㅏ      ㅐ      ㅑ      ㅒ      ㅓ      ㅔ      ㅕ      ㅖ      ㅗ      ㅘ      ㅙ      ㅚ      ㅛ      ㅜ      ㅝ      ㅞ      ㅟ      ㅠ      ㅡ      ㅢ      ㅣ
    final static char[] middle = { 0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163 };
    //         ㄱ      ㄲ      ㄳ      ㄴ      ㄵ      ㄶ      ㄷ      ㄹ      ㄺ      ㄻ      ㄼ      ㄽ      ㄾ      ㄿ      ㅀ      ㅁ      ㅂ      ㅄ      ㅅ      ㅆ      ㅇ      ㅈ      ㅊ      ㅋ      ㅌ      ㅍ      ㅎ
    final static char[] last  = { 0,      0x3131, 0x3132, 0x3133, 0x3134, 0x3135, 0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

    /**
     * 1. 띄어쓰기 구분
     * 2. 단어 앞뒤에 * 붙이기
     * 예)
     * *피* OR *땀* OR *눈*
     * @param data
     * @return
     */
    public String convertKeyword(String data) {
        if (data == null || data.length() == 0) {
            return "";
        }
        String words[] = data.split(" ");

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (sb.toString().length() > 0) {
                sb.append(" AND ");
            }
            sb.append("*");
            sb.append(word);
            sb.append("*");
        }
        return sb.toString();
    }

    public String jasoSplit(String data) {
        StringBuilder sb = new StringBuilder();

        data = data.replaceAll(" ", "");

        int size = data.length();
        int i = 0;
        int a,b,c;

        for (; i<size; i++) {
            String result = "";
            char ch = data.charAt(i);
            if (ch >= 0xAC00 && ch <= 0xD7A3) {
                c = ch - 0xAC00;
                a = c / (21 * 28);
                c = c % (21 * 28);
                b = c / 28;
                c = c % 28;

                result = result + first[a] + middle[b];
                if (c != 0) result = result + last[c];
            } else {
                result = result + ch;
            }
            sb.append(result);
        }

        return sb.toString();
    }

    public String jasoSplitForKeyword(String data) {
        StringBuilder sb = new StringBuilder();
        StringBuilder fir = new StringBuilder();

        data = data.replaceAll(" ", "");

        int size = data.length();
        int i = 0;
        int a,b,c;

        for (; i<size; i++) {
            String result = "";
            char ch = data.charAt(i);
            if (ch >= 0xAC00 && ch <= 0xD7A3) {
                c = ch - 0xAC00;
                a = c / (21 * 28);
                c = c % (21 * 28);
                b = c / 28;
                c = c % 28;

                fir.append(first[a]);
                result = result + first[a] + middle[b];
                if (c != 0) result = result + last[c];
            } else {
                result = result + ch;
            }
            sb.append(result);
        }

        return sb.toString()+fir.toString();
    }

    public String returnKoreanEnglishNumber(String data) {
        StringBuilder sb = new StringBuilder();

        int size = data.length();
        int i = 0;

        for (; i < size; i++) {
            int cp = Character.codePointAt(data, i);
            int group = checkGroup(cp);

            if (
                (i > 1) &&
                (checkGroup(Character.codePointAt(data, i))
                    !=
                checkGroup(Character.codePointAt(data, i-1))) &&
                (checkGroup(Character.codePointAt(data, i-1)) != 0)
            ) {
                sb.append(" ");
            }

            if (group != 0) {
                sb.append(data.substring(i, i+1));
            } else {
                if (
                    sb.toString().length() > 0 &&
                    Character.codePointAt(
                                sb.toString(),
                                sb.toString().length()-1
                    ) != 32
                ) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    private int checkGroup(int cp) {
        int group = 0;
        if (
            (cp >= 0x61 && cp <= 0x7a) ||   // 영소문자
            (cp >= 0x41 && cp <= 0x5a)      // 영대문자
        ) {
            group = 1;
        } else if (
            (cp >= 0x30 && cp <= 0x39)      // 숫자
        ) {
            group = 2;
        } else if (
            (cp >= 0xac00 && cp <= 0xd7a3)  // 한글
        ) {
            group = 3;
        } else if (cp == 0x32) {
            group = 0;
        }
        return group;
    }

}
