package com.dalonedrow.module.barbarianprince.turn;

/**
 * 
 * @author drau
 *
 */
public enum Day {
	/** day one. */
	One(1, "1st"),
	/** day two. */
	Two(2, "2nd"),
	/** day three. */
	Three(3, "3rd"),
	/** day four. */
	Four(4, "4th"),
	/** day five. */
	Five(5, "5th"),
	/** day six. */
	Six(6, "6th"),
	/** day seven. */
	Seven(7, "7th");
	/** the {@link Day}'s int value. */
	private final int value;
	/** a description of the {@link Day}. */
	private final String adjective;
	/**
	 * Creates a new instance of {@link Day}.
	 * @param v the {@link Day}'s value
	 * @param a {@link Day}'s adjective
	 */
	Day(final int v, final String a) {
		value = v;
		adjective = a;
	}
	/**
	 * Gets the adjective describing the {@link Day}.
	 * @return {@link String}
	 */
	public String getAdjective() {
		return adjective;
	}
	/**
	 * Gets the {@link Day}'s numeric value.
	 * @return {@link int}
	 */
	public int getValue() {
		return value;
	}
}
