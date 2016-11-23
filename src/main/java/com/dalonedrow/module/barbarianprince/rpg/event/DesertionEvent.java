package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.log.HeroLog;
import com.dalonedrow.module.barbarianprince.log.LogEntry;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.module.barbarianprince.ui.console.GUI;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * Event for a single NPC leaving the party.
 * @author drau
 */
public final class DesertionEvent extends Event {
	/** the cause of the event is a guide getting lost. */
	public static final int	LOST_GUIDE	= 0;
	/** the cause of the desertion. */
	private int					cause;
	/** the reference id of the IO leaving the party. */
	private int					ioid;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fire() throws RPGException {
		BpController.getInstance().removeIOFromParty(ioid);
		// TODO generate message to player
		log();
		super.setResolved();
	}
	/**
	 * Creates a log string for the event.
	 * @throws RPGException should not happen
	 */
	private void log() throws RPGException {
		String s;
		BpInteractiveObject io =
				(BpInteractiveObject) Interactive.getInstance().getIO(ioid);
		switch (cause) {
		case LOST_GUIDE:
			s = WebServiceClient.getInstance().loadText("move_msg_lost_guide");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					(String[]) null,
					s);
			break;
		default:
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid cause " + cause);
		}
		HeroLog.getInstance().addEntry(new LogEntry(null, null, null, s));
		GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
	}
	/**
	 * Sets the cause of the desertion.
	 * @param val the new value to set
	 */
	public void setCause(final int val) {
		cause = val;
	}
	/**
	 * Sets the reference id of the IO leaving the party.
	 * @param val the new value to set
	 */
	public void setIoid(final int val) {
		ioid = val;
	}
}
