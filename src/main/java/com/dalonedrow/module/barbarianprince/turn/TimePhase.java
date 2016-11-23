package com.dalonedrow.module.barbarianprince.turn;

/**
 * 
 * @author drau
 *
 */
public enum TimePhase {
	/** before dawn. */
	PHASE_00_PRE_ACTION("Dawn"),
	/** action. */
	PHASE_01_ACTION("Noon"),
	/** after action. */
	PHASE_02_POST_ACTION("Noon"),
	/** before evening. */
	PHASE_03_PRE_EVENING("Noon"),
	/** evening - food. */
	PHASE_04_EVENING_FOOD("Dusk"),
	/** evening - lodging. */
	PHASE_05_EVENING_LODGING("Dusk"),
	/** end of day. */
	PHASE_06_POST_EVENING("Midnight");
	/** the title of each phase. */
	private final String title;
	/**
	 * Creates a new instance of Phase.
	 * @param name the title of each phase
	 */
	TimePhase(final String name) {
		title = name;
	}
	/**
	 * Advances the current position, and get the next Phase.
	 * @return {@link TimePhase}
	 */
	public TimePhase advance() {
		int next = ordinal();
		if (next >= TimePhase.values().length - 1) {
			next = 0;
		} else {
			next++;
		}
		return TimePhase.values()[next];
	}
	/**
	 * Gets the <code>TimePhase</code>'s title.
	 * @return <code>String</code>
	 */
	public final String getTitle() { 
		return title;
	}

}
