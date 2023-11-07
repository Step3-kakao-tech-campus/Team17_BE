package com.kakaoseventeen.dogwalking._core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Error : 에러의 메시지와 상태를 전달하는 DTO
 *
 * @author 승건 이
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Error {

    private String message;

    private String status;

}
