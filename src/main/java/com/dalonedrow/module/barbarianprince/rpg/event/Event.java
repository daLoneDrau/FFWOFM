package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.script.MasterScript;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.Scriptable;

/**
 * 
 * @author drau
 *
 */
public abstract class Event {
	/** the {@link Condition} tied to this {@link Event}. */
	private Condition condition;
	/** the event's name. */
	private char[] name;
	/** the parent. */
	private MasterScript parent;
	/** flag indicating the event has been resolved. */
	private boolean resolved;
	private int code;
	/** Creates a new instance of {@link Event}. */
	protected Event() {
		resolved = false;
	}
	/**
	 * Gets the name.
	 * @return {@link String}
	 */
	public final String getName() {
		return new String(name);
	}
	/**
	 * Determines if the event has been resolved.
	 * @return <tt>true</tt> if the {@link Event} has been resolved;
	 * <tt>false</tt> otherwise
	 */
	public final boolean isResolved() {
		return resolved;
	}
	/**
	 * Fires the event. 
	 * @throws RPGException if an error occurs
	 */
	public abstract void fire() throws RPGException;
	/**
	 * Determines if the {@link Event} happens.  An {@link Event} with no 
	 * {@link Condition} will always happen.
	 * @return <code>boolean</code>
	 * @throws RPGException if an error occurs
	 */
	protected final boolean happens() throws RPGException {
		boolean happens = true;
		if (condition != null) {
			if (!condition.passes()) {
				happens = false;
			}
		}
		return happens;
	}
	/**
	 * Sets the {@link Condition} tied to this {@link Event}.
	 * @param c the {@link Condition} to set
	 */
	public final void setCondition(final Condition c) {
		condition = c;
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
	/**
	 * Sets the event parent.
	 * @param flow the parent instance
	 */
	public final void setParent(final Scriptable flow) {
		parent = (MasterScript) flow;
	}
	/** Sets the flag indicating the event has been resolved. 
	 * @throws RPGException if an error occurs
	 */
	public final void setResolved() throws RPGException {
		if (parent == null) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT,
					"Orphan event set to resolved");
		}
		resolved = true;
		parent.onFire();
	}
	public int getCode() {
		return code;
	}
	/**
	 * Sets the value of the code.
	 * @param code the new value to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
}
