package com.dalonedrow.module.barbarianprince.turn;

/**
 * 
 * @author drau
 *
 */
public enum Week {
	/** week one. */
	One(1, "1st"),
	/** week two. */
	Two(2, "2nd"),
	/** week three. */
	Three(3, "3rd"),
	/** week four. */
	Four(4, "4th"),
	/** week five. */
	Five(5, "5th"),
	/** week six. */
	Six(6, "6th"),
	/** week seven. */
	Seven(7, "7th"),
	/** week eight. */
	Eight(8, "8th"),
	/** week nine. */
	Nine(9, "9th"),
	/** week ten. */
	Ten(10, "10th");
	/** the <code>Week</code>'s int value. */
	private final int value;
	/** a description of the <code>Week</code>. */
	private final String adjective;
	/**
	 * Creates a new instance of <code>Week</code>.
	 * @param val the <code>Week</code>'s int value
	 * @param adj the description of the <code>Week</code>
	 */
	Week(final int val, final String adj) {
		value = val;
		adjective = adj;
	}
	/**
	 * Gets a description of the <code>Week</code>.
	 * @return <code>String</code>
	 */
	public String getAdjective() {
		return adjective;
	}
	/**
	 * Gets the <code>Week</code>'s int value.
	 * @return int
	 */
	public int getValue() {
		return value;
	}	
}
