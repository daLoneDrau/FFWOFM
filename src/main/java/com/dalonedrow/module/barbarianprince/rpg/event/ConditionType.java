package com.dalonedrow.module.barbarianprince.rpg.event;

/**
 * 
 * @author drau
 *
 */
public final class ConditionType {
	/** a condition that passes when the player is in a specific location. */
	public static final int LOCATION_CONDITION = 0;
	/** an event that sets another event to happen in a specific time phase. */
	public static final int ROLL_CONDITION = 1;
	/** Hidden constructor. */
	private ConditionType() {
		super();
	}
}
