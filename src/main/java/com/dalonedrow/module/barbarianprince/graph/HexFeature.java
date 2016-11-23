package com.dalonedrow.module.barbarianprince.graph;

/**
 * 
 * @author drau
 *
 */
public enum HexFeature {
	/** Temple. */
	TEMPLE(1, "Temple"),
	/** Ruins. */
	RUINS(2, "Ruins"),
	/** Castle. */
	CASTLE(4, "Castle"),
	/** Town. */
	TOWN(8, "Town"),
	/** River. */
	RIVER(16, "River"),
	/** Oasis. */
	OASIS(32, "Oasis"),
	/** Oasis. */
	CACHE(64, "Cache");
	/**
	 * Gets the type by its value.
	 * @param val the value
	 * @return {@link HexType}
	 */
	public static HexFeature typeOf(final long val) {
		HexFeature type = null;
		HexFeature[] vals = HexFeature.values();
		for (int i = vals.length - 1; i >= 0; i--) {
			if (vals[i].getFlag() == val) {
				type = vals[i];
				break;
			}
		}
		return type;
	}
	/** the type. */
	private final long flag;
	/** the title. */
	private final char[] title;
	/**
	 * Creates a new instance of {@link HexFeature}.
	 * @param t the type
	 * @param l the title
	 */
	HexFeature(final long t, final String l) {
		flag = t;
		title = l.toCharArray();
	}
	/**
	 * Gets the bit-flag for the {@link HexFeature}.
	 * @return {@link long}
	 */
	public long getFlag() {
		return flag;
	}
	/**
	 * Gets the value for the title.
	 * @return {@link String}
	 */
	public String getTitle() {
		return new String(title);
	}
}
