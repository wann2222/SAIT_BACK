package com.ssafy.trip.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionMailService {

    private final JavaMailSender mailSender;
    private final String ADMIN_EMAIL = "kimjy8027@naver.com"; // 운영자 이메일(수정 가능)

    public void sendQuestionMail(String fromEmail, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(ADMIN_EMAIL);
        message.setSubject("[사이트문의] " + title);
        message.setText(
                "문의자 이메일: " + fromEmail + "\n" +
                "제목: " + title + "\n\n" +
                "내용:\n" + content
        );
        message.setReplyTo(fromEmail); // 답장시 문의자 이메일로
        mailSender.send(message);
    }
}
