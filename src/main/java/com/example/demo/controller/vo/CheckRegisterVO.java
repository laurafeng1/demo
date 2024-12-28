package com.example.demo.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@ToString
public class CheckRegisterVO {
    private boolean hasSignedIn;

    private BaseVO baseVO;
}
