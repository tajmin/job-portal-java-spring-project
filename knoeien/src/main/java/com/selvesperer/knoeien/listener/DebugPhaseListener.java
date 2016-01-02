package com.selvesperer.knoeien.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8428987840479511029L;

	private static final Logger log = (Logger)LoggerFactory.getLogger(DebugPhaseListener.class);

	@Override
	public void afterPhase(PhaseEvent event) {
		if (log.isDebugEnabled()) {
			// log.debug("After phase: " + event.getPhaseId());
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		if (log.isDebugEnabled()) {
			// log.debug("Before phase: " + event.getPhaseId());
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
