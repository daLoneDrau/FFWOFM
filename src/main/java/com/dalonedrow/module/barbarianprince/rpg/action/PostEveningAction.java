package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class PostEveningAction implements TurnAction {
	/** the singleton instance. */
	private static PostEveningAction instance;
	/**
	 * Gets the one and only instance of {@link PostEveningAction}.
	 * @return {@link PostEveningAction}
	 */
	public static PostEveningAction getInstance() {
		if (PostEveningAction.instance == null) {
			PostEveningAction.instance = new PostEveningAction();
		}
		return PostEveningAction.instance;
	}
	/** flag indicating the action was resolved. */
	private boolean resolved;
	/** Hidden constructor. */
	private PostEveningAction() {
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
