package com.kakaoseventeen.dogwalking.member.valid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorTest {
    @DisplayName("비밀번호 길이 정규식 테스트")
    @Test
    public void password_length_test() {
        // given
        String regex = "^.{8,16}$"; // 정규식
        String input = "gksp950034#"; // 테스트할 문자열

        // when
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // then
        Assertions.assertTrue(matcher.matches());

    }

    @DisplayName("비밀번호 패턴 정규식 테스트")
    @Test
    public void password_pattern_test() {
        // given
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]+$";
        String input = "gksp950034#"; // 테스트할 문자열

        // when
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // then
        Assertions.assertTrue(matcher.matches());
    }
}
