package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class PreEveningAction implements TurnAction {
	/** the singleton instance. */
	private static PreEveningAction instance;
	/**
	 * Gets the one and only instance of {@link PreEveningAction}.
	 * @return {@link PreEveningAction}
	 */
	public static PreEveningAction getInstance() {
		if (PreEveningAction.instance == null) {
			PreEveningAction.instance = new PreEveningAction();
		}
		return PreEveningAction.instance;
	}
	/** flag indicating the action was resolved. */
	private boolean resolved;
	/** Hidden constructor. */
	private PreEveningAction() {
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
		// TODO Auto-generated method stub

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
