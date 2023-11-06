package com.kakaoseventeen.dogwalking.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {

    private static final String EMAIL_PATTERN = "^[\\w_]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$";

    @NotEmpty
    @Pattern(regexp = EMAIL_PATTERN, message = "이메일 형식으로 작성해주세요.")
    String email;

    @NotEmpty
    @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
    @Pattern(regexp = PASSWORD_PATTERN,
            message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~20자 이내여야 합니다.")
    String password;
}
