package com.dalonedrow.module.barbarianprince.graph;

/**
 * 
 * @author drau
 *
 */
public enum HexType {
	/** Countryside. */
	COUNTRY(0, "Countryside"),
	/** Farmland. */
	FARM(1, "Farmland"),
	/** Forest. */
	FOREST(2, "Forest"),
	/** Badlands. */
	HILL(3, "Badlands"),
	/** Mountains. */
	MOUNTAIN(4, "Mountains"),
	/** Desert. */
	DESERT(5, "Desert"),
	/** Swamp. */
	SWAMP(6, "Swamp");
	/**
	 * Gets the type by its value.
	 * @param val the value
	 * @return {@link HexType}
	 */
	public static HexType typeOf(final long val) {
		HexType type = null;
		HexType[] vals = HexType.values();
		for (int i = vals.length - 1; i >= 0; i--) {
			if (vals[i].getType() == val) {
				type = vals[i];
				break;
			}
		}
		return type;
	}
	/** the type. */
	private final int type;
	/** the title. */
	private final char[] title;
	/**
	 * Creates a new instance of {@link HexType}.
	 * @param t the type
	 * @param l the title
	 */
	HexType(final int t, final String l) {
		type = t;
		title = l.toCharArray();
	}
	/**
	 * Gets the value for the type.
	 * @return {@link int}
	 */
	public int getType() {
		return type;
	}
	/**
	 * Gets the value for the title.
	 * @return {@link String}
	 */
	public String getTitle() {
		return new String(title);
	}
}
