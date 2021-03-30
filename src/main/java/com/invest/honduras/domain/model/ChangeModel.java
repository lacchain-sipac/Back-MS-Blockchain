package com.invest.honduras.domain.model;

import java.math.BigInteger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeModel {
    private String hash;
    private String user;
    private String roleUser;
    private Boolean isFinal;
    private BigInteger timestamp;
}
