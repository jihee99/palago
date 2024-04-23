package com.ex.ticket.mail.service;

import com.ex.ticket.group.domain.entity.GroupUserRole;
import com.ex.ticket.mail.dto.EmailUserInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.ex.ticket.common.util.EmailContents.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupUserInvitationEmailService {

    private final JavaMailSender mailSender;
    private final StringBuilder stringBuilder = new StringBuilder();

    public void execute(EmailUserInfo toEmailUserInfo, String groupName, GroupUserRole role) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.addRecipients(MimeMessage.RecipientType.TO, toEmailUserInfo.getEmail());
            message.setFrom(SENDER_ADDRESS);
            message.setSubject(MESSAGE_SUBJECT);
            message.setText(getMessage(toEmailUserInfo.getName(), groupName), "utf-8", "html");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


    private String getMessage(String userName, String groupName){
        stringBuilder.setLength(0);
        return String.valueOf(stringBuilder.append(MESSAGE_PREFIX)
            .append(userName + " 님, 티켓히어로 "+groupName + " 그룹 초대 알림입니다.")
            .append(MESSAGE_SUFFIX));
    }

}
