package com.invest.honduras.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectModel {
    private String flow;
    private String roles;
    private Boolean finalized;
    private Boolean initialized;
    private String currentStep;
    private String creator;
}
