package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class EncounterAction implements TurnAction {
	/** the singleton instance. */
	private static EncounterAction instance;
	/**
	 * Gets the one and only instance of {@link EncounterAction}.
	 * @return {@link EncounterAction}
	 */
	public static EncounterAction getInstance() {
		if (EncounterAction.instance == null) {
			EncounterAction.instance = new EncounterAction();
		}
		return EncounterAction.instance;
	}
	private boolean	debug;
	/** flag indicating the action was resolved. */
	private boolean	resolved;
	/** Hidden constructor. */
	private EncounterAction() {
		super();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws RPGException {
		// TODO Auto-generated method stub

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResolved() {
		return resolved;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() throws RPGException {
		resolved = false;

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDebug(final boolean val) {
		debug = true;
	}
	/**
	 * Sets the value of the flag indicating the action was resolved.
	 * @param val the new value to set
	 */
	@Override
	public void setResolved(final boolean val) {
		resolved = val;
	}
}
