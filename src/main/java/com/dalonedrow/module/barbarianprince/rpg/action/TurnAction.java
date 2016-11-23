package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/** interface for all turn actions. */
public interface TurnAction {
	/**
	 * Executes the action.
	 * @throws RPGException if an error occurs
	 */
	void execute() throws RPGException;
	/**
	 * Determines if this action is finished.
	 * @return <tt>true</tt> if the action is finished; <tt>false</tt> otherwise
	 */
	boolean isResolved();
	/**
	 * Resets all settings.
	 * @throws RPGException if an error occurs
	 */
	void reset() throws RPGException;
	/**
	 * Sets the debugging flag.
	 * @param val the flag to set
	 */
	void setDebug(final boolean val);
	/**
	 * Sets the value of the flag indicating the action was resolved.
	 * @param val the new value to set
	 */
	void setResolved(boolean val);
}
