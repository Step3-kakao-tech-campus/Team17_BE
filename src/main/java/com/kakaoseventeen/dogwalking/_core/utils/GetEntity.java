package com.kakaoseventeen.dogwalking._core.utils;

import com.kakaoseventeen.dogwalking.application.domain.Application;
import com.kakaoseventeen.dogwalking.match.domain.Match;
import com.kakaoseventeen.dogwalking.member.domain.Member;
import com.kakaoseventeen.dogwalking.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class GetEntity {

    public static Member getMaster1(){
        return Member.builder()
                .email("yardyard@likelion.org")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("견주유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }


    public static Member getMaster2(){
        return Member.builder()
                .email("yardyard@likelion")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("견주유저2")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }


    public static Member getWalker1(){
        return Member.builder()
                .email("yardyard@naver.com")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("알바유저1")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }

    public static Member getWalker2(){
        return Member.builder()
                .email("yardyard@daum.com")
                .password("asd1111")
                .profileContent("반갑다리요")
                .dogBowl(50)
                .nickname("알바유저2")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }

    public static Notification getNotification(){
        return Notification.builder()
                .title("우리 강아지 좀 봐주세요")
                .lat(2.123123)
                .lng(31.12312)
                .startTime(LocalDateTime.MIN)
                .endTime(LocalDateTime.MAX)
                .significant("요크 셔테리어")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }

    public static Notification getNotification2(){
        return Notification.builder()
                .title("우리 강아지 좀 봐주세요")
                .lat(2.123123)
                .lng(31.12312)
                .startTime(LocalDateTime.MIN)
                .endTime(LocalDateTime.MAX)
                .significant("요크 셔테리어")
                .coin(BigDecimal.valueOf(3000))
                .build();
    }

    public static Application getApplication(){
        return Application.builder()
                .appMemberId(getWalker1())
                .aboutMe("저는 이승건입니다.")
                .build();
    }

    public static Match getMatch(){
        return Match.builder()
                .applicationId(getApplication())
                .notificationId(getNotification())
                .build();
    }
}
