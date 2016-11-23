package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class FoodAction implements TurnAction {
	/** the singleton instance. */
	private static FoodAction instance;
	/**
	 * Gets the one and only instance of {@link FoodAction}.
	 * @return {@link FoodAction}
	 */
	public static FoodAction getInstance() {
		if (FoodAction.instance == null) {
			FoodAction.instance = new FoodAction();
		}
		return FoodAction.instance;
	}
	private boolean	debug;
	/** flag indicating the action was resolved. */
	private boolean	resolved;
	/** Hidden constructor. */
	private FoodAction() {
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
