package com.dalonedrow.module.barbarianprince.turn;

import com.dalonedrow.module.barbarianprince.rpg.event.EventContainer;

public class TurnPhase extends EventContainer{
	/**
	 * Gets the value for the phase.
	 * @return {@link TimePhase}
	 */
	public TimePhase getPhase() {
		return phase;
	}

	private final TimePhase phase;

	public TurnPhase(TimePhase p) {
		phase = p;
	}
}
