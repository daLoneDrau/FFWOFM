package com.dalonedrow.module.barbarianprince.turn;

import com.dalonedrow.module.barbarianprince.rpg.event.EventContainer;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * @author drau
 */
public final class Turn {
	/** the current phase. */
	private TimePhase					currentPhase;
	/** the list of event for each phase. */
	private final EventContainer[][]	phases;
	/** Creates a new instance of {@link Turn}. */
	public Turn() {
		phases = new EventContainer[TimePhase.values().length][];
		for (int i = phases.length - 1; i >= 0; i--) {
			phases[i] = new EventContainer[0];
		}
		currentPhase = TimePhase.PHASE_00_PRE_ACTION;
	}
	/**
	 * Adds an event to the current turn phase.
	 * @param e the event
	 */
	public void addEvent(final EventContainer e) {
		addEvent(e, currentPhase);
	}
	/**
	 * Adds an event to a specific turn phase.
	 * @param e the event
	 * @param tp the turn phase
	 */
	public void addEvent(final EventContainer e, final TimePhase tp) {
		phases[tp.ordinal()] = ArrayUtilities.getInstance().extendArray(
				e, phases[tp.ordinal()]);
	}
	/**
	 * Adds an event to the beginning specific turn phase.
	 * @param e the event
	 * @param tp the turn phase
	 */
	public void addEventToBeginning(final EventContainer e,
			final TimePhase tp) {
		phases[tp.ordinal()] = ArrayUtilities.getInstance().prependArray(
				e, phases[tp.ordinal()]);
	}
	/** Clears all events. */
	public void clear() {
		for (int i = phases.length - 1; i >= 0; i--) {
			phases[i] = new EventContainer[0];
		}
	}
	/**
	 * Fires all actions until control is returned to the user.
	 * @throws RPGException if an error occurs
	 */
	private void fireActions() throws RPGException {
		// TODO CHECK FOR END OF GAME
		int index = currentPhase.ordinal();
		if (hasEventsInPhase(currentPhase)) {
			// get the first event.
			EventContainer event = phases[index][0];
			// if the event is resolved, remove it, and continue the next action
			if (!event.hasActions()) {
				phases[index] = ArrayUtilities.getInstance().removeIndex(
						0, phases[index]);
				fireActions();
			} else { // fire the event
				event.fireActions();
			}
		}
	}
	/**
	 * Gets the value for the current phase.
	 * @return {@link TimePhase}
	 */
	public TimePhase getCurrentPhase() {
		return currentPhase;
	}
	public EventContainer getFirstEventForPhase(final TimePhase tp) {
		EventContainer ec = null;
		if (phases[tp.ordinal()].length > 0) {
			ec = phases[tp.ordinal()][0];
		}
		return ec;
	}
	public boolean hasEventsInPhase(final TimePhase tp) {
		return phases[tp.ordinal()].length > 0;
	}
	public void nextPhase() throws RPGException {
		while (hasEventsInPhase(currentPhase)) {
			fireActions();
		}
		currentPhase = currentPhase.advance();
		BpController.getInstance().completeTurnPhase();
	}
}
