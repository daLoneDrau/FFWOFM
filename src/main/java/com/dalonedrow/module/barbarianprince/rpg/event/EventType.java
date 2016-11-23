package com.dalonedrow.module.barbarianprince.rpg.event;

/**
 * 
 * @author drau
 *
 */
public final class EventType {
	/** an event that teleports the party to a random location. */
	public static final int RANDOM_TELEPORT_EVENT = 0;
	/** an event that sets another event to happen in a specific time phase. */
	public static final int DELAYED_EVENT = 1;
	/** an event that spawns an encounter. */
	public static final int MOB_ENCOUNTER_EVENT = 2;
	/** Hidden constructor. */
	private EventType() {
		super();
	}
}
