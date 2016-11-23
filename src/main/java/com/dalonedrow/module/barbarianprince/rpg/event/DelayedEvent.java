package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.module.barbarianprince.turn.TimePhase;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * Event that sets another event to happen later.
 * @author drau
 */
public final class DelayedEvent extends Event {
	/** the id of the delayed event that happens. */
	private int	eventId;
	/** the turn phase where the delayed event happens. */
	private int	turnPhase;
	/** Creates a new instance of {@link DelayedEvent}. */
	public DelayedEvent() {
		eventId = -1;
		turnPhase = -1;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire() throws RPGException {
		if (eventId == -1) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, 
					"eventId was never set");
		}
		if (turnPhase == -1) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, 
					"turnPhase was never set");
		}
		if (super.happens()) {
			try {
				BpController.getInstance().addEventToTurn(
						WebServiceClient.getInstance().getEvent(eventId),
						TimePhase.values()[turnPhase]);
			} catch (PooledException e) {
				e.printStackTrace();
			}
		}
		super.setResolved();
	}
	/**
	 * Sets the event id.
	 * @param val the new value to set
	 */
	public void setEventId(final int val) {
		eventId = val;
	}
	/**
	 * Sets the turn phase.
	 * @param val the new value to set
	 */
	public void setTurnPhase(final int val) {
		turnPhase = val;
	}
}
