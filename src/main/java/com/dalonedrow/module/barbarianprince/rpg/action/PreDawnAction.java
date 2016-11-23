package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class PreDawnAction implements TurnAction {
	/** the singleton instance. */
	private static PreDawnAction instance;
	/**
	 * Gets the one and only instance of {@link PreDawnAction}.
	 * @return {@link PreDawnAction}
	 */
	public static PreDawnAction getInstance() {
		if (PreDawnAction.instance == null) {
			PreDawnAction.instance = new PreDawnAction();
		}
		return PreDawnAction.instance;
	}
	/** flag indicating the action was resolved. */
	private boolean resolved;
	/** Hidden constructor. */
	private PreDawnAction() {
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
