package com.invest.honduras.response;

import com.everis.blockchain.honduras.Project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectItem {

    private Boolean finalized;
    private Boolean initialized;
    private String currentStep;
    private String creator;
    
    public ProjectItem(Project project) {
    	this.finalized = project.finalized;
    	this.initialized = project.initialized;
    	this.currentStep = project.currentStep;
    	this.creator = project.creator;
    }
    
    public ProjectItem() {}
}
