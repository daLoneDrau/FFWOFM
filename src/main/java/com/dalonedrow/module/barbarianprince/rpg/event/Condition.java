package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * 
 * @author drau
 *
 */
public abstract class Condition {
	/** the condition's name. */
	private char[] name;
	public Condition() {
		super();
	}
	public abstract int getType();
	public abstract boolean passes() throws RPGException;
	/**
	 * Gets the name.
	 * @return {@link String}
	 */
	public final String getName() {
		return new String(name);
	}
	/**
	 * Sets the value of the name.
	 * @param val the new value to set
	 */
	public final void setName(final char[] val) {
		name = val;
	}
	/**
	 * Sets the value of the name.
	 * @param val the new value to set
	 */
	public final void setName(final String val) {
		if (val != null) {
			name = val.toCharArray();
		}
	}
}
