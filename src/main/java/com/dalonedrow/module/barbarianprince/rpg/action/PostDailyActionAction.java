package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class PostDailyActionAction implements TurnAction {
	/** the singleton instance. */
	private static PostDailyActionAction instance;
	/**
	 * Gets the one and only instance of {@link PostDailyActionAction}.
	 * @return {@link PostDailyActionAction}
	 */
	public static PostDailyActionAction getInstance() {
		if (PostDailyActionAction.instance == null) {
			PostDailyActionAction.instance = new PostDailyActionAction();
		}
		return PostDailyActionAction.instance;
	}
	/** flag indicating the action was resolved. */
	private boolean resolved;
	/** Hidden constructor. */
	private PostDailyActionAction() {
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
