/**
 *
 */
package com.dalonedrow.rpg.graph;

import java.util.Arrays;

import com.dalonedrow.engine.sprite.base.SimpleVector3;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.consoleui.ConsoleLogger;
import com.dalonedrow.utils.ArrayUtilities;
import com.dalonedrow.utils.TextLoader;

/**
 * @author drau
 */
public final class HexTile extends AsciiHex {
	/** the set of clearings on the tile. */
	private int[]			clearings;
	/**
	 * a multi-dimensional array containing the reference ids for all parties
	 * that discovered the tile's sites or sounds.
	 */
	private int[][]			partySiteOrSound;
	/**
	 * an array containing the reference ids for all parties that discovered the
	 * tile warning.
	 */
	private int[]			partyWarnings;
	/**
	 * the set of clearings that have roadways running off the edge of the tile.
	 */
	private final int[]		sides;
	/** the secret site or sound assigned to the tile. */
	private int[]			sitesOrSounds;
	/** constants. */
	private final int		six	= 6, four = 4, three = 3;
	/** the tile's name. */
	private final char[]	tileName;
	/** the tile type (Cave, Mountain, Valley, or Woods). */
	private final int		type;
	/** the secret warning assigned to the tile. */
	private int				warning;
	public HexTile(final HexTile hexTile) {
		this(hexTile.getId(), hexTile.tileName, hexTile.type);
		this.copyOf(hexTile);
	}
	/**
	 * Creates a new instance of {@link HexTile}.
	 * @param refId the hexagon's reference id
	 * @param name the tile's name
	 * @param tileType the tile's type
	 */
	public HexTile(final int refId, final char[] name, final int tileType) {
		super(refId);
		tileName = name;
		type = tileType;
		clearings = new int[0];
		sides = new int[six];
		Arrays.fill(sides, -1);
		warning = -1;
		sitesOrSounds = new int[0];
	}
	/**
	 * Adds a clearing to the tile.
	 * @param v the clearing's id
	 */
	public void addClearing(final int v) {
		clearings = ArrayUtilities.getInstance().extendArray(v, clearings);
	}
	/**
	 * Adds a secret site or sound assigned to the tile.
	 * @param val the value to add
	 */
	public void addSiteOrSound(final int val) {
		boolean found = false;
		for (int i = sitesOrSounds.length - 1; i >= 0; i--) {
			if (sitesOrSounds[i] == val) {
				found = true;
				break;
			}
		}
		if (!found) {
			sitesOrSounds =
					ArrayUtilities.getInstance()
							.extendArray(val, sitesOrSounds);
		}
	}
	public void copyOf(final HexTile hex) {
		((AsciiHex) this).copyOf(hex);
		clearings = Arrays.copyOf(hex.clearings, hex.clearings.length);
		if (hex.partySiteOrSound != null) {
			partySiteOrSound = Arrays.copyOf(hex.partySiteOrSound,
					hex.partySiteOrSound.length);
		}
		if (hex.partyWarnings != null) {
			partyWarnings = Arrays.copyOf(hex.partyWarnings,
					hex.partyWarnings.length);
		}
		for (int i = 0, len = hex.sides.length; i < len; i++) {
			sides[i] = hex.sides[i];
		}
		if (hex.sitesOrSounds != null) {
			sitesOrSounds = Arrays.copyOf(hex.sitesOrSounds,
					hex.sitesOrSounds.length);
		}
		for (int i = 0, len = hex.tileName.length; i < len; i++) {
			tileName[i] = hex.tileName[i];
		}
		warning = hex.warning;
	}
	/**
	 * Draws the tile.
	 * @return {@link String}
	 * @throws Exception
	 */
	public String draw() throws Exception {
		String art = "";
		if (Arrays.equals(tileName, "LINDEN WOODS".toCharArray())) {
			art = getArtForLindenWoods();
			art = fixWoodsWarnings(art);
		} else if (Arrays.equals(tileName, "MAPLE WOODS".toCharArray())) {
			art = getArtForMapleWoods();
			art = fixValleyWarnings(art);
		} else if (Arrays.equals(tileName, "NUT WOODS".toCharArray())) {
			art = getArtForNutWoods();
			art = fixValleyWarnings(art);
		} else if (Arrays.equals(tileName, "AWFUL VALLEY".toCharArray())) {
			art = getArtForAwfulValley();
			art = fixValleyWarnings(art);
		} else if (Arrays.equals(tileName, "BAD VALLEY".toCharArray())) {
			art = getArtForBadValley();
			art = fixValleyWarnings(art);
		} else if (Arrays.equals(tileName, "CURST VALLEY".toCharArray())) {
			art = getArtForCurstValley();
			art = fixValleyWarnings(art);
		} else if (Arrays.equals(tileName, "DARK VALLEY".toCharArray())) {
			art = getArtForDarkValley();
			art = fixValleyWarnings(art);
		} else if (Arrays.equals(tileName, "EVIL VALLEY".toCharArray())) {
			art = getArtForEvilValley();
			art = fixValleyWarnings(art);
		}
		art = TextLoader.getInstance().loadText("ascii_art.txt", "empty_hex");
		return art;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		boolean equals = false;
		if (obj instanceof HexTile) {
			if (Arrays.equals(tileName, ((HexTile) obj).tileName)) {
				equals = true;
			}
		}
		return equals;
	}
	private String fixRoadDestinations(final String art,
			final int[] replaceOrder) throws Exception {
		String s = art;
		for (int i = 0, len = replaceOrder.length; i < len; i++) {
			TileClearing clearing = getAdjacentClearing(replaceOrder[i]);
			if (clearing == null) {
				s = s.replaceFirst("to DW5 ", "NOWHERE");
			} else {
				s = s.replaceFirst("DW5", new String(clearing.getName()));
			}
			clearing = null;
		}
		return s;
	}
	/**
	 * Fixes the warnings drawn in valley tiles.
	 * @param art the ascii art
	 * @return {@link String}
	 * @throws Exception if an error occurs
	 */
	private String fixValleyWarnings(final String art) throws Exception {
		int partyId =
				((MagicRealmController) ProjectConstants.getInstance())
						.getPartyForIO(ProjectConstants.getInstance().getPcId())
						.getRefId();
		String s = art;
		if (wasWarningRevealed(partyId)) {
			String warningArt = "";
			switch (warning) {
			case MagicRealmConsts.WARNING_BONES:
				warningArt =
						TextLoader.getInstance().loadText("ascii_art.txt",
								"bones");
				break;
			case MagicRealmConsts.WARNING_DANK:
				warningArt =
						TextLoader.getInstance().loadText("ascii_art.txt",
								"chapel");
				break;
			case MagicRealmConsts.WARNING_RUINS:
				warningArt =
						TextLoader.getInstance().loadText("ascii_art.txt",
								"guardhouse");
				break;
			case MagicRealmConsts.WARNING_SMOKE:
				warningArt =
						TextLoader.getInstance().loadText("ascii_art.txt",
								"house");
				break;
			case MagicRealmConsts.WARNING_STINK:
				warningArt =
						TextLoader.getInstance().loadText("ascii_art.txt",
								"inn");
				break;
			default:
				throw new Exception("Invalid warning " + warning);
			}
			String[] split = warningArt.split("\n");
			for (int i = 0, len = split.length; i < len; i++) {
				s = s.replaceFirst("XXXXXXX", split[i].replace("\\", "\\\\"));
			}
		} else {
			s = s.replaceAll("XXXXXXX", "       ");
		}
		return s;
	}
	/**
	 * Fixes the warnings drawn in valley tiles.
	 * @param art the ascii art
	 * @return {@link String}
	 * @throws Exception if an error occurs
	 */
	private String fixWoodsWarnings(final String art) throws Exception {
		int partyId =
				((MagicRealmController) ProjectConstants.getInstance())
						.getPartyForIO(ProjectConstants.getInstance().getPcId())
						.getRefId();
		String s = art;
		if (wasWarningRevealed(partyId)
				&& warning != MagicRealmConsts.WARNING_DANK
				&& warning != MagicRealmConsts.WARNING_RUINS) {
			String warningArt = "";
			switch (warning) {
			case MagicRealmConsts.WARNING_BONES:
				warningArt =
						TextLoader.getInstance().loadText("ascii_art.txt",
								"bones");
				break;
			case MagicRealmConsts.WARNING_SMOKE:
			case MagicRealmConsts.WARNING_STINK:
				warningArt =
						TextLoader.getInstance().loadText("ascii_art.txt",
								"campfire");
				break;
			default:
				throw new Exception("Invalid warning " + warning);
			}
			String[] split = warningArt.split("\n");
			for (int i = 0, len = split.length; i < len; i++) {
				s = s.replaceFirst("XXXXXXX", split[i].replace("\\", "\\\\"));
			}
		} else {
			s = s.replaceAll("XXXXXXX", "       ");
		}
		return s;
	}
	/**
	 * Gets the clearing adjacent to a particular side.
	 * @param side the side
	 * @return {@link TileClearing}
	 * @throws Exception if an error occurs
	 */
	private TileClearing getAdjacentClearing(final int side) throws Exception {
		SimpleVector3 v3 =
				MagicRealmMap.getInstance().getOverworldGrid()
						.getNeighborCoordinates(this, side);
		TileClearing clearing = null;
		HexTile tile = MagicRealmMap.getInstance().getHexTileAtPosition(v3);
		if (tile != null) {
			int[] adjacencies =
					MagicRealmMap.getInstance().getGraph().getAdjacencies(
							sides[side]);
			for (int i = adjacencies.length - 1; i >= 0; i--) {
				if (tile.hasClearing(adjacencies[i])) {
					clearing =
							MagicRealmMap.getInstance().getClearing(
									adjacencies[i]);
					break;
				}
			}
			adjacencies = null;
		}
		tile = null;
		return clearing;
	}
	/**
	 * Gets the ascii art for Awful Valley.
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	private String getArtForAwfulValley() throws Exception {
		String art = null;
		TileClearing clearing;
		// the side order in which the "to DW5" text gets replaced
		int[] replaceOrder = null;
		String section = null;
		if (sides[0] == -1) {
			section = "3";
			replaceOrder = new int[] { 5, 1, 4, 2, 3 };
		} else {
			clearing = MagicRealmMap.getInstance().getClearing(sides[0]);
			if (clearing.getClearingNumber() == 5) {
				section = "0";
				replaceOrder = new int[] { 0, 5, 1, 4, 2 };
			} else if (clearing.getClearingNumber() == 1) {
				section = "1";
				replaceOrder = new int[] { 0, 5, 1, 2, 3 };
			} else if (clearing.getClearingNumber() == 2) {
				section = "2";
				replaceOrder = new int[] { 0, 1, 4, 2, 3 };
			} else if (clearing.getClearingNumber() == 4) {
				if (sides[0] == sides[5]) {
					section = "4";
					replaceOrder = new int[] { 0, 5, 4, 2, 3 };
				} else {
					section = "5";
					replaceOrder = new int[] { 0, 5, 1, 4, 3 };
				}
			}
		}
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(new String(tileName).replaceAll(" ", "_").toLowerCase());
		sb.append(".txt");
		art = TextLoader.getInstance().loadText(sb.toString(), section);
		art = fixRoadDestinations(art, replaceOrder);
		clearing = null;
		replaceOrder = null;
		section = null;
		sb.returnToPool();
		section = null;
		return art;
	}
	/**
	 * Gets the ascii art for Bad Valley.
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	private String getArtForBadValley() throws Exception {
		String art = null;
		TileClearing clearing;
		// the side order in which the "to DW5" text gets replaced
		int[] replaceOrder = null;
		;
		String section = null;
		if (sides[0] == -1) {
			section = "3";
			replaceOrder = new int[] { 5, 1, 4, 2, 3 };
		} else {
			clearing = MagicRealmMap.getInstance().getClearing(sides[0]);
			if (clearing.getClearingNumber() == 5) {
				section = "5";
				replaceOrder = new int[] { 0, 5, 1, 4, 3 };
			} else if (clearing.getClearingNumber() == 1) {
				section = "4";
				replaceOrder = new int[] { 0, 5, 4, 2, 3 };
			} else if (clearing.getClearingNumber() == 2) {
				section = "2";
				replaceOrder = new int[] { 0, 1, 4, 2, 3 };
			} else if (clearing.getClearingNumber() == 4) {
				if (sides[0] == sides[5]) {
					section = "0";
					replaceOrder = new int[] { 0, 5, 1, 4, 2 };
				} else {
					section = "1";
					replaceOrder = new int[] { 0, 5, 1, 2, 3 };
				}
			}
		}
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(new String(tileName).replaceAll(" ", "_").toLowerCase());
		sb.append(".txt");
		art = TextLoader.getInstance().loadText(sb.toString(), section);
		art = fixRoadDestinations(art, replaceOrder);
		clearing = null;
		replaceOrder = null;
		section = null;
		sb.returnToPool();
		section = null;
		return art;
	}
	/**
	 * Gets the ascii art for Curst Valley.
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	private String getArtForCurstValley() throws Exception {
		String art = null;
		TileClearing clearing;
		// the side order in which the "to DW5" text gets replaced
		int[] replaceOrder = null;
		;
		String section = null;
		if (sides[0] == -1) {
			section = "3";
			replaceOrder = new int[] { 5, 1, 4, 2, 3 };
		} else {
			clearing = MagicRealmMap.getInstance().getClearing(sides[0]);
			if (clearing.getClearingNumber() == 5) {
				section = "5";
				replaceOrder = new int[] { 0, 5, 1, 4, 3 };
			} else if (clearing.getClearingNumber() == 1) {
				section = "0";
				replaceOrder = new int[] { 0, 5, 1, 4, 2 };
			} else if (clearing.getClearingNumber() == 2) {
				section = "1";
				replaceOrder = new int[] { 0, 5, 1, 2, 3 };
			} else if (clearing.getClearingNumber() == 4) {
				if (sides[0] == sides[4]) {
					section = "2";
					replaceOrder = new int[] { 0, 1, 4, 2, 3 };
				} else {
					section = "4";
					replaceOrder = new int[] { 0, 5, 4, 2, 3 };
				}
			}
		}
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(new String(tileName).replaceAll(" ", "_").toLowerCase());
		sb.append(".txt");
		art = TextLoader.getInstance().loadText(sb.toString(), section);
		art = fixRoadDestinations(art, replaceOrder);
		clearing = null;
		replaceOrder = null;
		section = null;
		sb.returnToPool();
		section = null;
		return art;
	}
	/**
	 * Gets the ascii art for Dark Valley.
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	private String getArtForDarkValley() throws Exception {
		String art = null;
		TileClearing clearing;
		// the side order in which the "to DW5" text gets replaced
		int[] replaceOrder = null;
		String section = null;
		if (sides[0] == -1) {
			section = "3";
			replaceOrder = new int[] { 5, 1, 4, 2, 3 };
		} else {
			clearing = MagicRealmMap.getInstance().getClearing(sides[0]);
			if (clearing.getClearingNumber() == 5) {
				section = "4";
				replaceOrder = new int[] { 0, 5, 4, 2, 3 };
			} else if (clearing.getClearingNumber() == 1) {
				section = "5";
				replaceOrder = new int[] { 0, 5, 1, 4, 3 };
			} else if (clearing.getClearingNumber() == 2) {
				section = "0";
				replaceOrder = new int[] { 0, 5, 1, 4, 2 };
			} else if (clearing.getClearingNumber() == 4) {
				if (sides[0] == sides[5]) {
					section = "1";
					replaceOrder = new int[] { 0, 5, 1, 2, 3 };
				} else {
					section = "2";
					replaceOrder = new int[] { 0, 1, 4, 2, 3 };
				}
			}
		}
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(new String(tileName).replaceAll(" ", "_").toLowerCase());
		sb.append(".txt");
		art = TextLoader.getInstance().loadText(sb.toString(), section);
		art = fixRoadDestinations(art, replaceOrder);
		clearing = null;
		replaceOrder = null;
		section = null;
		sb.returnToPool();
		section = null;
		return art;
	}
	/**
	 * Gets the ascii art for Evil Valley.
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	private String getArtForEvilValley() throws Exception {
		String art = null;
		TileClearing clearing;
		// the side order in which the "to DW5" text gets replaced
		int[] replaceOrder = null;
		String section = null;
		if (sides[0] == -1) {
			section = "3";
			replaceOrder = new int[] { 5, 1, 4, 2, 3 };
		} else {
			clearing = MagicRealmMap.getInstance().getClearing(sides[0]);
			if (clearing.getClearingNumber() == 5) {
				section = "4";
				replaceOrder = new int[] { 0, 5, 4, 2, 3 };
			} else if (clearing.getClearingNumber() == 1) {
				section = "2";
				replaceOrder = new int[] { 0, 1, 4, 2, 3 };
			} else if (clearing.getClearingNumber() == 2) {
				section = "1";
				replaceOrder = new int[] { 0, 5, 1, 2, 3 };
			} else if (clearing.getClearingNumber() == 4) {
				if (sides[0] == sides[1]) {
					section = "0";
					replaceOrder = new int[] { 0, 5, 1, 4, 2 };
				} else {
					section = "5";
					replaceOrder = new int[] { 0, 5, 1, 4, 3 };
				}
			}
		}
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(new String(tileName).replaceAll(" ", "_").toLowerCase());
		sb.append(".txt");
		art = TextLoader.getInstance().loadText(sb.toString(), section);
		art = fixRoadDestinations(art, replaceOrder);
		clearing = null;
		replaceOrder = null;
		section = null;
		sb.returnToPool();
		section = null;
		return art;
	}
	/**
	 * Gets the ascii art for Linden Woods.
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	private String getArtForLindenWoods() throws Exception {
		String art = null;
		TileClearing clearing;
		// the side order in which the "to DW5" text gets replaced
		int[] replaceOrder = null;
		String section = null;
		if (sides[0] == -1) {
			section = "3";
			replaceOrder = new int[] { 5, 1, 4, 2, 3 };
		} else {
			clearing = MagicRealmMap.getInstance().getClearing(sides[0]);
			if (clearing.getClearingNumber() == 4) {
				section = "1";
				replaceOrder = new int[] { 0, 5, 1, 2, 3 };
			} else if (clearing.getClearingNumber() == 2) {
				if (sides[0] == sides[4]) {
					section = "2";
					replaceOrder = new int[] { 0, 1, 4, 2, 3 };
				} else {
					section = "4";
					replaceOrder = new int[] { 0, 5, 4, 2, 3 };
				}
			} else if (clearing.getClearingNumber() == 5) {
				if (sides[0] == sides[5]) {
					section = "5";
					replaceOrder = new int[] { 0, 5, 1, 4, 3 };
				} else {
					section = "0";
					replaceOrder = new int[] { 0, 5, 1, 4, 2 };
				}
			}
		}
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(new String(tileName).replaceAll(" ", "_").toLowerCase());
		sb.append(".txt");
		art = TextLoader.getInstance().loadText(sb.toString(), section);
		art = fixRoadDestinations(art, replaceOrder);
		clearing = null;
		replaceOrder = null;
		section = null;
		sb.returnToPool();
		section = null;
		return art;
	}
	/**
	 * Gets the ascii art for Maple Woods.
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	private String getArtForMapleWoods() throws Exception {
		String art = null;
		TileClearing clearing;
		// the side order in which the "to DW5" text gets replaced
		int[] replaceOrder = null;
		String section = null;
		if (sides[0] == -1) {
			section = "3";
			replaceOrder = new int[] { 5, 1, 4, 2, 3 };
		} else {
			clearing = MagicRealmMap.getInstance().getClearing(sides[0]);
			if (clearing.getClearingNumber() == 4) {
				section = "4";
				replaceOrder = new int[] { 0, 5, 4, 2, 3 };
			} else if (clearing.getClearingNumber() == 5) {
				if (sides[0] == sides[5]) {
					section = "1";
					replaceOrder = new int[] { 0, 5, 1, 2, 3 };
				} else {
					section = "2";
					replaceOrder = new int[] { 0, 1, 4, 2, 3 };
				}
			} else if (clearing.getClearingNumber() == 2) {
				if (sides[0] == sides[5]) {
					section = "5";
					replaceOrder = new int[] { 0, 5, 1, 4, 3 };
				} else {
					section = "0";
					replaceOrder = new int[] { 0, 5, 1, 4, 2 };
				}
			}
		}
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(new String(tileName).replaceAll(" ", "_").toLowerCase());
		sb.append(".txt");
		art = TextLoader.getInstance().loadText(sb.toString(), section);
		art = fixRoadDestinations(art, replaceOrder);
		clearing = null;
		replaceOrder = null;
		section = null;
		sb.returnToPool();
		section = null;
		return art;
	}
	/**
	 * Gets the ascii art for Nut Woods.
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	private String getArtForNutWoods() throws Exception {
		String art = null;
		TileClearing clearing;
		// the side order in which the "to DW5" text gets replaced
		int[] replaceOrder = null;
		String section = null;
		if (sides[0] == -1) {
			section = "3";
			replaceOrder = new int[] { 5, 1, 4, 2, 3 };
		} else {
			clearing = MagicRealmMap.getInstance().getClearing(sides[0]);
			if (clearing.getClearingNumber() == 4) {
				section = "5";
				replaceOrder = new int[] { 0, 5, 1, 4, 3 };
			} else if (clearing.getClearingNumber() == 2) {
				if (sides[0] == sides[5]) {
					section = "0";
					replaceOrder = new int[] { 0, 5, 1, 4, 2 };
				} else {
					section = "1";
					replaceOrder = new int[] { 0, 5, 1, 2, 3 };
				}
			} else if (clearing.getClearingNumber() == 5) {
				if (sides[0] == sides[4]) {
					section = "2";
					replaceOrder = new int[] { 0, 1, 4, 2, 3 };
				} else {
					section = "4";
					replaceOrder = new int[] { 0, 5, 4, 2, 3 };
				}
			}
		}
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(new String(tileName).replaceAll(" ", "_").toLowerCase());
		sb.append(".txt");
		art = TextLoader.getInstance().loadText(sb.toString(), section);
		art = fixRoadDestinations(art, replaceOrder);
		clearing = null;
		replaceOrder = null;
		section = null;
		sb.returnToPool();
		section = null;
		return art;
	}
	/**
	 * Gets the vertex id representing a specific clearing.
	 * @param number the clearing's assigned number
	 * @return <code>int</code>
	 * @throws Exception if an error occurs
	 */
	public int getClearing(final int number) throws Exception {
		final int hexNodeLen = clearings.length, max = 1000;
		int min = max, clearingId = -1;
		for (int i = hexNodeLen - 1; i >= 0; i--) {
			min = Math.min(clearings[i], min);
		}
		switch (hexNodeLen) {
		case six: // the hex has six clearings, each numbered 1-six
			clearingId = min + number - 1;
			break;
		case four: // the hex has four clearings, numbered 1-2, four-5
			if (number < three) {
				clearingId = min + number - 1;
			} else if (number > three && number < six) {
				clearingId = min + number - 2;
			}
			break;
		case three: // the hex has three clearings, numbered 2, four-5
			if (number == 2) {
				clearingId = min;
			} else if (number > three && number < six) {
				clearingId = min + number - three;
			}
			break;
		default:
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Invalid number of clearings in the hex - ");
			sb.append(hexNodeLen);
			Exception ex = new Exception(sb.toString());
			sb.returnToPool();
			throw ex;
		}
		return clearingId;
	}
	/**
	 * Gets the vertex id of a clearing that leads out of a specific side.
	 * @param side the side (0-5)
	 * @return <code>int</code>
	 */
	public int getClearingForSide(final int side) {
		return sides[side];
	}
	/**
	 * Gets the id.
	 * @return <code>char</code>[]
	 */
	public char[] getName() {
		return tileName;
	}
	/**
	 * Gets the number of sites or sounds assigned to the tile.
	 * @return <code>int</code>
	 */
	public int getNumberOfSitesOrSounds() {
		return sitesOrSounds.length;
	}
	/**
	 * Gets the secret site or sound assigned to the tile.
	 * @param index the array index
	 * @return <code>int</code>
	 */
	public int getSiteOrSound(final int index) {
		return sitesOrSounds[index];
	}
	/**
	 * Gets the tile type (Cave, Mountain, Valley, or Woods).
	 * @return <code>int</code>
	 */
	public int getType() {
		return type;
	}
	/**
	 * Gets the secret warning assigned to the tile.
	 * @return <code>int</code>
	 */
	public int getWarning() {
		return warning;
	}
	/**
	 * Determines if the hex contains a specific clearing vertex.
	 * @param clearingId the vertex's id
	 * @return <tt>true</tt> if the tile contains the vertex, <tt>false</tt>
	 *         otherwise
	 */
	public boolean hasClearing(final int clearingId) {
		boolean found = false;
		for (int i = clearings.length - 1; i >= 0; i--) {
			if (clearings[i] == clearingId) {
				found = true;
				break;
			}
		}
		return found;
	}
	public boolean hasSiteAtClearing(final int clearingId) throws Exception {
		int clearingNum;
		for (int i = 1, len = 6; i <= len; i++) {
			if (getClearing(i) == clearingId) {
				clearingNum = i;
				break;
			}
		}
		return false;
	}
	public void moveTo(final int side) {
		// side 0 x= 3 y= 4 z=-7
		// side 1 x= 7 y=-3 z=-4
		// side 2 x= 4 y=-7 z= 3
		// side 3 x=-3 y=-4 z= 7
		// side 4 x=-7 y= 3 z= 4
		// side 5 x=-4 y= 7 z=-3

	}
	/**
	 * Reveals one of the tile's sites or sounds chit to a specific party.
	 * @param siteOrSound the site or sound's id
	 * @param partyId the player party's reference id
	 * @throws Exception
	 */
	public void revealSiteOrSound(final int siteOrSound, final int partyId)
			throws Exception {
		ConsoleLogger.getInstance().debug(
				"reveal site or sound " + warning + " to party " + partyId);
		if (partySiteOrSound == null) {
			partySiteOrSound = new int[sitesOrSounds.length][];
			for (int i = sitesOrSounds.length - 1; i >= 0; i--) {
				partySiteOrSound[i] = new int[] { sitesOrSounds[i] };
			}
		}
		for (int i = partySiteOrSound.length - 1; i >= 0; i--) {
			int[] arr = partySiteOrSound[i];
			if (arr[0] == siteOrSound) {
				ConsoleLogger.getInstance().debug(
						"SOS " + siteOrSound + " found at index " + i);
				boolean found = false;
				for (int j = 1, len = arr.length; j < len; j++) {
					if (arr[j] == partyId) {
						ConsoleLogger.getInstance().debug(
								"party " + partyId + " found at index " + j);
						found = true;
						arr = null;
						break;
					}
				}
				if (!found) {
					ConsoleLogger.getInstance().debug(
							"adding party IO " + partyId + " to list");
					arr =
							ArrayUtilities.getInstance().extendArray(partyId,
									arr);
					partySiteOrSound[i] = arr;
				}
			}
			arr = null;
		}
	}
	/**
	 * Reveals the tile's warning chit to a specific party.
	 * @param partyId the player party's reference id
	 * @throws Exception
	 */
	public void revealWarning(final int partyId) throws Exception {
		ConsoleLogger.getInstance().debug(
				"reveal warning " + warning + " to party " + partyId);
		if (partyWarnings == null) {
			partyWarnings = new int[0];
		}
		boolean warned = false;
		for (int i = partyWarnings.length - 1; i >= 0; i--) {
			if (partyWarnings[i] == partyId) {
				warned = true;
				break;
			}
		}
		if (!warned) {
			partyWarnings =
					ArrayUtilities.getInstance().extendArray(partyId,
							partyWarnings);
		}
	}
	/**
	 * Rotates the hex.
	 * @throws Exception
	 */
	@Override
	public void rotate() throws Exception {
		// save the first value erased
		int savedVal = sides[sides.length - 1];
		for (int i = sides.length - 1; i > 0; i--) {
			sides[i] = sides[i - 1];
		}
		sides[0] = savedVal;
		super.rotate();
	}
	public void setEdgeToClearing(final int side, final int clearing) {
		sides[side] = clearing;
	}
	/**
	 * Sets the secret warning assigned to the tile.
	 * @param val the value to set
	 */
	public void setWarning(final int val) {
		warning = val;
	}
	public String toHexPrintString(final UndirectedGraph g) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		// side 0
		if (sides[0] > -1) {
			sb.append("\t");
			sb.append(g.getVertex(sides[0]).getName());
			sb.append("\t\t");
		}
		sb.append("\n");
		// side 5
		if (sides[5] > -1) {
			sb.append(g.getVertex(sides[5]).getName());
		}
		sb.append("\t\t");
		// side 1
		if (sides[1] > -1) {
			sb.append(g.getVertex(sides[1]).getName());
		}
		sb.append("\n");
		sb.append("\t");
		sb.append(tileName);
		sb.append("\n");
		// side four
		if (sides[four] > -1) {
			sb.append(g.getVertex(sides[four]).getName());
		}
		sb.append("\t\t");
		// side 2
		if (sides[2] > -1) {
			sb.append(g.getVertex(sides[2]).getName());
		}
		sb.append("\n");
		// side three
		if (sides[three] > -1) {
			sb.append("\t");
			sb.append(g.getVertex(sides[three]).getName());
		}
		sb.append("\n\t\t\t4");
		String s = sb.toString();
		sb.returnToPool();
		return s;
	}
	public String toHexPrintString(final UndirectedGraph g,
			final int lineNumber) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		switch (lineNumber) {
		case 0:
			// side 0
			sb.append("|----------");
			if (sides[0] > -1) {
				sb.append(g.getVertex(sides[0]).getName());
			} else {
				sb.append("---");
			}
			sb.append("---------|");
			break;
		case 1:
			sb.append("|");
			// side 5
			if (sides[5] > -1) {
				sb.append(g.getVertex(sides[5]).getName());
			}
			sb.append("\t\t    ");
			// side 1
			if (sides[1] > -1) {
				sb.append(g.getVertex(sides[1]).getName());
			} else {
				sb.append("   ");
			}
			sb.append("|");
			break;
		case 2:
			// name
			sb.append("|");
			int spaces = 22 - tileName.length;
			for (int i = spaces / 2; i > 0; i--) {
				sb.append(' ');
			}
			sb.append(tileName);
			for (int i = spaces - spaces / 2; i > 0; i--) {
				sb.append(' ');
			}
			sb.append("|");
			break;
		case three:
			// position
			sb.append("|\t ");
			// sb.append((int) position.getX());
			sb.append(",");
			// sb.append((int) position.getY());
			sb.append("\t       |");
			break;
		case four:
			sb.append("|");
			// side four
			if (sides[four] > -1) {
				sb.append(g.getVertex(sides[four]).getName());
			}
			sb.append("\t\t    ");
			// side 2
			if (sides[2] > -1) {
				sb.append(g.getVertex(sides[2]).getName());
			} else {
				sb.append("   ");
			}
			sb.append("|");
			break;
		case 5:
			// side three
			sb.append("|__________");
			if (sides[three] > -1) {
				sb.append(g.getVertex(sides[three]).getName());
			} else {
				sb.append("___");
			}
			sb.append("_________|");
			break;
		}
		String s = sb.toString();
		sb.returnToPool();
		return s;
	}
	/**
	 * Determines if one of the tile's site or sound chits was revealed to a
	 * specific player party.
	 * @param siteOrSound the site or sound's id
	 * @param partyId the player party's reference id
	 * @return <tt>true</tt> if the warning was already revealed; <tt>false</tt>
	 *         otherwise
	 * @throws Exception
	 */
	public boolean wasSiteOrSoundRevealed(final int siteOrSound,
			final int partyId) throws Exception {
		ConsoleLogger.getInstance().debug(
				"check tile " + new String(getName()) + " for SOS "
						+ siteOrSound + " revealed to " + partyId);
		if (partySiteOrSound == null) {
			partySiteOrSound = new int[sitesOrSounds.length][];
			for (int i = sitesOrSounds.length - 1; i >= 0; i--) {
				partySiteOrSound[i] = new int[] { sitesOrSounds[i] };
			}
		}
		boolean found = false;
		for (int i = partySiteOrSound.length - 1; i >= 0; i--) {
			int[] arr = partySiteOrSound[i];
			if (arr[0] == siteOrSound) {
				ConsoleLogger.getInstance().debug(
						"SOS " + siteOrSound + " found at index " + i);
				for (int j = 1, len = arr.length; j < len; j++) {
					if (arr[j] == partyId) {
						ConsoleLogger.getInstance().debug(
								"party " + partyId + " found at index " + j);
						found = true;
						arr = null;
						break;
					}
				}
			}
			arr = null;
		}
		if (found) {
			ConsoleLogger.getInstance().debug(
					"site or sound " + siteOrSound + " was revealed to party "
							+ partyId);
		} else {
			ConsoleLogger.getInstance().debug(
					"site or sound " + siteOrSound
							+ " was not revealed to party " + partyId);
		}
		return found;
	}
	/**
	 * Determines if the tile's warning chit was revealed to a specific player
	 * party.
	 * @param partyId the player party's reference id
	 * @return <tt>true</tt> if the warning was already revealed; <tt>false</tt>
	 *         otherwise
	 * @throws Exception if an error occurs
	 */
	public boolean wasWarningRevealed(final int partyId) throws Exception {
		if (partyWarnings == null) {
			partyWarnings = new int[0];
		}
		boolean warned = false;
		for (int i = partyWarnings.length - 1; i >= 0; i--) {
			if (partyWarnings[i] == partyId) {
				warned = true;
				break;
			}
		}
		if (warned) {
			ConsoleLogger.getInstance().debug(
					"warning " + warning + " was revealed to party " + partyId);
		} else {
			ConsoleLogger.getInstance().debug(
					"warning " + warning + " was not revealed to party "
							+ partyId);
		}
		return warned;
	}
}
