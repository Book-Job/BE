package com.bookjob.email.domain;

import org.springframework.stereotype.Component;

@Component
public class EmailBuilder {
    public static String buildEmail(String code) {
        String content =
                "<div style='font-family: Arial, Helvetica, sans-serif; color: #333; background-color: #ffffff; padding: 40px; border-radius: 15px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); text-align: center;'>"
                        + "<h2 style='color: #4a7c59; font-size: 22px;'>🎉 안녕하세요! 복잡한 출판 구인구직을 한번에, 북잡입니다! 🎉</h2>"
                        + "<p style='font-size: 16px;'>5분 내에 아래 <strong>인증번호</strong>를 복사하여 인증번호 확인란에 입력해주세요.</p>"
                        + "<div style='margin: 30px auto; padding: 20px; background-color: #e6f9d4; display: inline-block;'>"
                        + "<h3 style='color: #333; font-size: 18px;'>인증번호입니다.</h3>"
                        + "<p style='background-color: #d4f7c5; color: #4a7c59; font-size: 24px; padding: 10px 20px; border-radius: 10px; display: inline-block; margin: 0;'>" + code + "</p>"
                        + "</div>"
                        + "<p style='font-size: 16px; margin-top: 40px;'>이 코드를 요청하지 않은 경우, 이 이메일을 무시해도 됩니다.<br>다른 사용자가 실수로 이메일 주소를 입력했을 수 있습니다.</p>"
                        + "</div>";

        return content;
    }
}
