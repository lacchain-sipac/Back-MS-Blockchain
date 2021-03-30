package com.invest.honduras.util;

import com.invest.honduras.domain.model.project.Phase;
import com.invest.honduras.domain.model.project.Rule;
import com.invest.honduras.domain.model.project.Step;

public class FlowUtil {

	public static String getStepPrevius(Rule rule, String currentStep) {
		String codeStepPrevius = "";
		for (Phase phase : rule.getFases()) {
			
			for (Step step : phase.getStep()) {

				if (step.getCode().equalsIgnoreCase(currentStep)) {
					return codeStepPrevius;
				}
				codeStepPrevius = Constants.CODE_PHASE_1.equals(phase.getCode()) ?  phase.getCode() : step.getCode();
			}
		}
		return codeStepPrevius;
	}

}
