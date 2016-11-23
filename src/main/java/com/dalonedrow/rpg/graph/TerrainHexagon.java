package com.dalonedrow.rpg.graph;

import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.TextLoader;

/**
 * @author drau
 */
public class TerrainHexagon extends Hexagon {
	/** flag indicating ANSI display has been turned on. */
	private static boolean		ansiIsOn;
	/** the black shade used. */
	private static final int	BLACK					= 234;
	/** the blackest shade used. */
	private static final int	BLACKEST				= 0;
	/** a clearing hex. */
	public static final int		CLEARING				= 64;
	/** the value of the escape character. */
	private static final int	ESCAPE					= 27;
	/** a forest hex. */
	public static final int		FOREST					= 128;
	/** a forest hex. */
	public static final int		FOREST_GOLD				= 132;
	/** a forest hex. */
	public static final int		FOREST_GOLD_GREY_PURPLE	= 134;
	/** a forest hex. */
	public static final int		FOREST_GREY				= 131;
	/** a forest hex. */
	public static final int		FOREST_PURPLE			= 133;
	/** the gold shade used. */
	private static final int	GOLD					= 220;
	/** a grass hex. */
	public static final int		GRASS					= 135;
	/** a grass hex. */
	public static final int		GRASS_GREY				= 136;
	/** the grey shade used. */
	private static final int	GREY					= 8;
	/** a forest hex. */
	public static final int		MOUNTAIN				= 129;
	/** the number of sides in a hexagon. */
	private static final int	NUM_SIDES				= 6;
	/** the purple shade used. */
	private static final int	PURPLE					= 13;
	/** a forest hex. */
	public static final int		RUINS					= 130;
	/** the white shade used. */
	private static final int	WHITE					= 15;
	/**
	 * Sets the value of the ansiOn.
	 * @param flag the value to set
	 */
	public static void setAnsiOn(final boolean flag) {
		TerrainHexagon.ansiIsOn = flag;
	}
	/** the hex's clearing id. */
	private int	clearingId	= -1;
	/** the hex's terrain value. */
	private int	terrain		= -1;
	/**
	 * Creates a new instance of {@link TerrainHexagon}.
	 * @param isFlat flag indicating whether the hexagon is flat on top or
	 *            pointy
	 * @param refId the hexagon's reference id
	 */
	public TerrainHexagon(final boolean isFlat, final int refId) {
		super(isFlat, refId);
	}
	/**
	 * Creates a new instance of {@link TerrainHexagon}.
	 * @param isFlat flag indicating whether the hexagon is flat on top or
	 *            pointy
	 * @param refId the hexagon's reference id
	 * @param newSize the hexagon's new size
	 */
	public TerrainHexagon(final boolean isFlat, final int refId,
			final float newSize) {
		super(isFlat, refId, newSize);
	}
	/**
	 * Creates a new instance of {@link TerrainHexagon}.
	 * @param refId the hexagon's reference id
	 */
	public TerrainHexagon(final int refId) {
		super(refId);
	}
	/**
	 * Creates a new instance of {@link TerrainHexagon}.
	 * @param refId the hexagon's reference id
	 * @param newSize the hexagon's new size
	 */
	public TerrainHexagon(final int refId, final float newSize) {
		super(refId, newSize);
	}
	/**
	 * Adds a road edge to the terrain.
	 * @param side the side the loads leads out
	 * @throws Exception if the side is invalid
	 */
	public final void addRoadEdge(final int side) throws Exception {
		if (side < 0 || side > 5) {
			throw new Exception("Invalid edge - " + side);
		}
		if (terrain < TerrainHexagon.FOREST) {
			terrain += 1 << side;
		} else {
			terrain = 0;
			terrain += 1 << side;
		}
	}
	public void copyOf(final TerrainHexagon hex) {
		((Hexagon) this).copyOf(hex);
		clearingId = hex.clearingId;
		terrain = hex.terrain;
	}
	/**
	 * Gets the ANSI codes for black text on a grey background.
	 * @return {@link String}
	 */
	private String getANSIBlackOnGrey() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append(";48;5;");
		sb.append(TerrainHexagon.GREY);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for black text on a light brown background.
	 * @return {@link String}
	 */
	private String getANSIBlackOnLightBrown() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append(";48;5;172m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for black text on a white background.
	 * @return {@link String}
	 */
	private String getANSIBlackOnWhite() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append(";48;5;15m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets clean Ascii art for a bones clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getANSIBones(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		sb.append(getANSIWhiteOnBlack());
		sb.append(" ___ ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIBlackOnWhite());
		sb.append("(^.^)");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xx xx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIWhiteOnBlack());
		sb.append(" ");
		sb.append(getANSIBlackOnWhite());
		sb.append("|||");
		sb.append(getANSIWhiteOnBlack());
		sb.append(" ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI codes for bright green text on a black background.
	 * @return {@link String}
	 */
	private String getANSIBrightGreenOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;10;48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets clean Ascii art for a campfire clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getANSICampfire(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		sb.append(getANSIGreyOnBlack());
		sb.append("  )  ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIRedOnBlack());
		sb.append(" (");
		sb.append(getANSIYellowOnBlack());
		sb.append(",");
		sb.append(getANSIRedOnBlack());
		sb.append(") ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xx xx", sb.toString());

		sb.setLength(0);
		sb.append(getANSILightBrownOnBlack());
		sb.append(".-");
		sb.append(getANSIYellowOnBlack());
		sb.append("'");
		sb.append(getANSILightBrownOnBlack());
		sb.append("`.");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets clean ANSI art for a cave clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getANSICaveClearing(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		// remove box on top
		sb.append(getANSIWhiteOnBlack());
		sb.append("__");
		sb.append(getANSILightBrownOnBlack());
		sb.append("|");
		sb.append(getANSIWhiteOnBlack());
		sb.append("__");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("__\\|__", sb.toString());
		// 2nd possible box line
		sb.setLength(0);
		sb.append(getANSIWhiteOnBlack());
		sb.append("_____");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("_____", sb.toString());
		// 1st line
		sb.setLength(0);
		sb.append(getANSIWhiteOnBlack());
		sb.append("|");
		sb.append(getANSIWhiteOnBlackest());
		sb.append("   ");
		sb.append(getANSIWhiteOnBlack());
		sb.append("|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		// name line
		sb.setLength(0);
		sb.append(getANSIWhiteOnBlack());
		sb.append("|");
		sb.append(getANSIWhiteOnBlackest());
		sb.append(clearing.getName());
		sb.append(getANSIWhiteOnBlack());
		sb.append("|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xx xx", sb.toString());
		// last line
		sb.setLength(0);
		sb.append(getANSIWhiteOnBlack());
		sb.append("|");
		sb.append(getANSIWhiteOnBlackest());
		sb.append("___");
		sb.append(getANSIWhiteOnBlack());
		sb.append("|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets clean Ascii art for a chapel clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getANSIChapel(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");

		sb.setLength(0);
		sb.append(getANSIGreyOnBlack());
		sb.append(" _");
		sb.append(getANSIYellowOnBlack());
		sb.append("+");
		sb.append(getANSIGreyOnBlack());
		sb.append("_ ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIGreyOnBlack());
		sb.append("/\\__\\");
		sb.append(getANSILightBrownOnBlack());
		data = data.replace("xx xx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIWhiteOnBlack());
		sb.append("||[]|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets clean ANSI art for a woods/mountain clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getANSIClearing(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		// remove box on top
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		// 1st line
		sb.append(getANSIBlackOnLightBrown());
		sb.append("     ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		// name line
		sb.setLength(0);
		sb.append(getANSIBlackOnLightBrown());
		sb.append(' ');
		sb.append(clearing.getName());
		sb.append(' ');
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xx xx", sb.toString());
		// last line
		sb.setLength(0);
		sb.append(getANSIBlackOnLightBrown());
		sb.append("     ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI closing sequence, followed by a line break.
	 * @return {@link String}
	 */
	private String getANSICloseSequence() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[00;00m\n");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for dark brown text on a black background.
	 * @return {@link String}
	 */
	private String getANSIDarkBrownOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;88;48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for dark green text on a black background.
	 * @return {@link String}
	 */
	private String getANSIDarkGreenOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;2;48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI art for the FOREST terrain.
	 * @return {@link String}
	 */
	private String getANSIForest() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();

		sb.append(getANSIDarkGreenOnBlack());
		sb.append("      \\");
		sb.append(getANSICloseSequence());

		sb.append(getANSIDarkGreenOnBlack());
		sb.append("   \\  / \\");
		sb.append(getANSICloseSequence());

		sb.append(getANSIDarkGreenOnBlack());
		sb.append("  / \\");
		sb.append(getANSIDarkBrownOnBlack());
		sb.append("  | ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIDarkBrownOnBlack());
		sb.append("  |    ");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI art for the FOREST_GOLD terrain.
	 * @return {@link String}
	 */
	private String getANSIForestGold() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();

		sb.append(getANSIGoldOnBlack());
		sb.append(" *    \\");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGoldOnBlack());
		sb.append("   \\  / \\");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGoldOnBlack());
		sb.append("* / \\  ");
		sb.append(getANSIDarkBrownOnBlack());
		sb.append("| ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIDarkBrownOnBlack());
		sb.append("  |  ");
		sb.append(getANSIGoldOnBlack());
		sb.append("* ");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI art for the FOREST_GOLD_GREY_PURPLE terrain.
	 * @return {@link String}
	 */
	private String getANSIForestGoldGreyPurple() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();

		sb.append(getANSIGoldOnBlack());
		sb.append(" *");
		sb.append(getANSIGreyOnBlack());
		sb.append("   \\|");
		sb.append(getANSICloseSequence());

		sb.append(getANSIPurpleOnBlack());
		sb.append("   \\  ");
		sb.append(getANSIGreyOnBlack());
		sb.append("\\|/");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGoldOnBlack());
		sb.append("* ");
		sb.append(getANSIPurpleOnBlack());
		sb.append("/ \\  ");
		sb.append(getANSIGreyOnBlack());
		sb.append("| ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIDarkBrownOnBlack());
		sb.append("  |  ");
		sb.append(getANSIGoldOnBlack());
		sb.append("* ");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI art for the FOREST_GREY terrain.
	 * @return {@link String}
	 */
	private String getANSIForestGrey() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();

		sb.append(getANSIGreyOnBlack());
		sb.append(" *   \\|");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append("   |/ \\|/");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append("* \\|/  | ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append("  |  * ");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI art for the FOREST_PURPLE terrain.
	 * @return {@link String}
	 */
	private String getANSIForestPurple() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();

		sb.append(getANSIPurpleOnBlack());
		sb.append(" *    \\");
		sb.append(getANSICloseSequence());

		sb.append(getANSIPurpleOnBlack());
		sb.append("   \\  / \\");
		sb.append(getANSICloseSequence());

		sb.append(getANSIPurpleOnBlack());
		sb.append("* / \\  ");
		sb.append(getANSIDarkBrownOnBlack());
		sb.append("| ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIDarkBrownOnBlack());
		sb.append("  |  ");
		sb.append(getANSIPurpleOnBlack());
		sb.append("* ");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI codes for gold text on a black background.
	 * @return {@link String}
	 */
	private String getANSIGoldOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;");
		sb.append(TerrainHexagon.GOLD);
		sb.append(";48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI art for the GRASS terrain.
	 * @return {@link String}
	 */
	private String getANSIGrass() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(getANSIBrightGreenOnBlack());
		sb.append(" .   . ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIBrightGreenOnBlack());
		sb.append(" ;  .  . ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIBrightGreenOnBlack());
		sb.append("  .   .; ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIBrightGreenOnBlack());
		sb.append("   ; . ");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI art for the GRASS_GREY terrain.
	 * @return {@link String}
	 */
	private String getANSIGrassGrey() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(getANSIGreyOnBlack());
		sb.append("*.   . ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append(" ;  .  * ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append("  .   .; ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append("   ;*. ");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI codes for grey text on a black background.
	 * @return {@link String}
	 */
	private String getANSIGreyOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;");
		sb.append(TerrainHexagon.GREY);
		sb.append(";48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for grey text on a white background.
	 * @return {@link String}
	 */
	private String getANSIGreyOnWhite() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;");
		sb.append(TerrainHexagon.GREY);
		sb.append(";48;5;");
		sb.append(TerrainHexagon.WHITE);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets clean Ascii art for a guardhouse clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getANSIGuardhouse(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");

		sb.setLength(0);
		sb.append(getANSIBlackOnGrey());
		sb.append("|^^^|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIBlackOnGrey());
		sb.append("| +[|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xx xx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIBlackOnGrey());
		sb.append("|+");
		sb.append(getANSIGreyOnBlack());
		sb.append("[]");
		sb.append(getANSIBlackOnGrey());
		sb.append("|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets clean Ascii art for a house clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getANSIHouse(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");

		sb.setLength(0);
		sb.append(getANSIYellowOnBlack());
		sb.append(" _");
		sb.append(getANSIRedOnBlack());
		sb.append("m");
		sb.append(getANSIYellowOnBlack());
		sb.append("_ ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIYellowOnBlack());
		sb.append("/\\__\\");
		sb.append(getANSILightBrownOnBlack());
		data = data.replace("xx xx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIRedOnBlack());
		sb.append("|_|");
		sb.append(getANSIWhiteOnBlack());
		sb.append('"');
		sb.append(getANSIRedOnBlack());
		sb.append("|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets clean Ascii art for an inn clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getANSIInn(final String text, final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");

		sb.setLength(0);
		sb.append(getANSIDarkBrownOnBlack());
		sb.append(" m");
		sb.append(getANSIRedOnBlack());
		sb.append("__ ");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIRedOnBlack());
		sb.append("/\\__\\");
		sb.append(getANSILightBrownOnBlack());
		data = data.replace("xx xx", sb.toString());

		sb.setLength(0);
		sb.append(getANSIYellowOnBlack());
		sb.append("||[]|");
		sb.append(getANSILightBrownOnBlack());
		data = data.replaceFirst("xxxxx", sb.toString());
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI codes for light brown text on a black background.
	 * @return {@link String}
	 */
	private String getANSILightBrownOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;172;48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for light brown text on a white background.
	 * @return {@link String}
	 */
	private String getANSILightBrownOnWhite() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;172;48;5;");
		sb.append(TerrainHexagon.WHITE);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI art for the MOUNTAIN terrain.
	 * @return {@link String}
	 */
	private String getANSIMountain() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(getANSIWhiteOnBlack());
		sb.append("   _   ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append("   /");
		sb.append(getANSIWhiteOnBlack());
		sb.append("^");
		sb.append(getANSIGreyOnBlack());
		sb.append("\\   ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append("  /");
		sb.append(getANSIWhiteOnBlack());
		sb.append("^");
		sb.append(getANSIGreyOnBlack());
		sb.append("  \\  ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIGreyOnBlack());
		sb.append("/    ");
		sb.append(getANSIWhiteOnBlack());
		sb.append("^");
		sb.append(getANSIGreyOnBlack());
		sb.append("\\");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI codes for orange text on a black background.
	 * @return {@link String}
	 */
	private String getANSIOrangeOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;214;48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for purple text on a black background.
	 * @return {@link String}
	 */
	private String getANSIPurpleOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;");
		sb.append(TerrainHexagon.PURPLE);
		sb.append(";48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for red text on a black background.
	 * @return {@link String}
	 */
	private String getANSIRedOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;9;48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for red text on a yellow background.
	 * @return {@link String}
	 */
	private String getANSIRedOnYellow() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;9;48;5;11m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI art for the RUINS terrain.
	 * @return {@link String}
	 */
	private String getANSIRuins() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(getANSIWhiteOnBlack());
		sb.append("    ___");
		sb.append(getANSICloseSequence());

		sb.append(getANSIWhiteOnBlack());
		sb.append(" .; !___!");
		sb.append(getANSICloseSequence());

		sb.append(getANSIWhiteOnBlack());
		sb.append(" ! \\ ! ! ");
		sb.append(getANSICloseSequence());

		sb.append(getANSIWhiteOnBlack());
		sb.append("| | | |");
		sb.append(getANSICloseSequence());
		String data = sb.toString();
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets the ANSI codes for white text on a black background.
	 * @return {@link String}
	 */
	private String getANSIWhiteOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;15;48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for white text on the blackest background.
	 * @return {@link String}
	 */
	private String getANSIWhiteOnBlackest() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;15;48;5;");
		sb.append(TerrainHexagon.BLACKEST);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for white text on a white background.
	 * @return {@link String}
	 */
	private String getANSIWhiteOnWhite() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;15;48;5;15m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ANSI codes for yellow text on a black background.
	 * @return {@link String}
	 */
	private String getANSIYellowOnBlack() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((char) TerrainHexagon.ESCAPE);
		sb.append("[38;5;11;48;5;");
		sb.append(TerrainHexagon.BLACK);
		sb.append("m");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * Gets the ASCII art for the hex.
	 * @return {@link String}
	 * @throws Exception if there is an error
	 */
	public final String getAsciiArt() throws RPGException {
		String data = loadTerrainArt();
		if (terrain >= TerrainHexagon.CLEARING
				&& terrain < TerrainHexagon.FOREST) {
			System.out.println("print clearing " + clearingId);
			System.out.println(toString());
			TileClearing clearing =
					MagicRealmMap.getInstance().getClearing(clearingId);
			System.out.println(clearing);
			HexTile hexTile =
					MagicRealmMap.getInstance().getHexTileForClearing(
							clearingId);
			System.out.println(hexTile);
			System.out.println("****\n");
			int partyId = -1;/*
								 * ((MagicRealmController)
								 * MagicRealmController.getInstance())
								 * .getPartyIdForIO(
								 * MagicRealmController.getInstance().getPcId())
								 * ;
								 */
			// set flag indicating the clearing was already redrawn
			boolean redrawn = false;
			if (hexTile.wasWarningRevealed(partyId)) {
				if (clearing.getClearingNumber() == 5) {
					switch (hexTile.getWarning()) {
					case MagicRealmConsts.WARNING_BONES:
						if (TerrainHexagon.ansiIsOn) {
							data = getANSIBones(data, clearing);
						} else {
							data = getAsciiBones(data, clearing);
						}
						redrawn = true;
						break;
					case MagicRealmConsts.WARNING_SMOKE:
						switch (hexTile.getType()) {
						case MagicRealmConsts.TILE_TYPE_WOODS:
							if (TerrainHexagon.ansiIsOn) {
								data = getANSICampfire(data, clearing);
							} else {
								data = getAsciiCampfire(data, clearing);
							}
							redrawn = true;
							break;
						case MagicRealmConsts.TILE_TYPE_VALLEY:
							if (TerrainHexagon.ansiIsOn) {
								data = getANSIHouse(data, clearing);
							} else {
								data = getAsciiHouse(data, clearing);
							}
							redrawn = true;
							break;
						default:
							break;
						}
						break;
					case MagicRealmConsts.WARNING_STINK:
						switch (hexTile.getType()) {
						case MagicRealmConsts.TILE_TYPE_WOODS:
							if (TerrainHexagon.ansiIsOn) {
								data = getANSICampfire(data, clearing);
							} else {
								data = getAsciiCampfire(data, clearing);
							}
							redrawn = true;
							break;
						case MagicRealmConsts.TILE_TYPE_VALLEY:
							if (TerrainHexagon.ansiIsOn) {
								data = getANSIInn(data, clearing);
							} else {
								data = getAsciiInn(data, clearing);
							}
							redrawn = true;
							break;
						default:
							break;
						}
						break;
					case MagicRealmConsts.WARNING_DANK:
						switch (hexTile.getType()) {
						case MagicRealmConsts.TILE_TYPE_VALLEY:
							if (TerrainHexagon.ansiIsOn) {
								data = getANSIChapel(data, clearing);
							} else {
								data = getAsciiChapel(data, clearing);
							}
							redrawn = true;
							break;
						default:
							break;
						}
						break;
					case MagicRealmConsts.WARNING_RUINS:
						switch (hexTile.getType()) {
						case MagicRealmConsts.TILE_TYPE_VALLEY:
							if (TerrainHexagon.ansiIsOn) {
								data =
										getANSIGuardhouse(data,
												clearing);
							} else {
								data =
										getAsciiGuardhouse(data,
												clearing);
							}
							redrawn = true;
							break;
						default:
							break;
						}
						break;
					default:
						break;
					}
				}
			}
			if (!redrawn) {
				switch (clearing.getFlag()) {
				case MagicRealmConsts.CLEARING_TYPE_CAVE:
					if (TerrainHexagon.ansiIsOn) {
						data = getANSICaveClearing(data, clearing);
					} else {
						data = getAsciiCaveClearing(data, clearing);
					}
					break;
				default:
					if (TerrainHexagon.ansiIsOn) {
						data = getANSIClearing(data, clearing);
					} else {
						data = getAsciiClearing(data, clearing);
					}
					break;
				}
			}
		}
		if (TerrainHexagon.ansiIsOn) {
			switch (terrain) {
			case TerrainHexagon.FOREST:
				data = getANSIForest();
				break;
			case TerrainHexagon.GRASS:
				data = getANSIGrass();
				break;
			case TerrainHexagon.GRASS_GREY:
				data = getANSIGrassGrey();
				break;
			case TerrainHexagon.FOREST_GREY:
				data = getANSIForestGrey();
				break;
			case TerrainHexagon.FOREST_GOLD:
				data = getANSIForestGold();
				break;
			case TerrainHexagon.FOREST_PURPLE:
				data = getANSIForestPurple();
				break;
			case TerrainHexagon.FOREST_GOLD_GREY_PURPLE:
				data = getANSIForestGoldGreyPurple();
				break;
			case TerrainHexagon.MOUNTAIN:
				data = getANSIMountain();
				break;
			case TerrainHexagon.RUINS:
				data = getANSIRuins();
				break;
			default:
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				String[] split = data.split("\n");
				for (int i = 0, len = split.length; i < len; i++) {
					sb.append(getANSILightBrownOnBlack());
					sb.append(split[i]);
					sb.append(getANSICloseSequence());
				}
				data = sb.toString();
				sb.returnToPool();
				sb = null;
				break;
			}
		}
		return data;
	}
	/**
	 * Gets clean Ascii art for a bones clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getAsciiBones(final String text,
			final TileClearing clearing) {
		String data = text;
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		data = data.replaceFirst("xxxxx", " .-. ");
		data = data.replaceFirst("xx xx", "(^.^)");
		data = data.replaceFirst("xxxxx", " ||| ");
		return data;
	}
	/**
	 * Gets clean Ascii art for a campfire clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getAsciiCampfire(final String text,
			final TileClearing clearing) {
		String data = text;
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		data = data.replaceFirst("xxxxx", "  )  ");
		data = data.replaceFirst("xx xx", " (,) ");
		data = data.replaceFirst("xxxxx", ".-'`.");
		return data;
	}
	/**
	 * Gets clean Ascii art for a cave clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getAsciiCaveClearing(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		data = data.replaceFirst("xxxxx", "|   |");
		sb.setLength(0);
		sb.append('|');
		sb.append(clearing.getName());
		sb.append('|');
		data = data.replaceFirst("xx xx", sb.toString());
		data = data.replaceFirst("xxxxx", "|___|");
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets clean Ascii art for a chapel clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getAsciiChapel(final String text,
			final TileClearing clearing) {
		String data = text;
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		data = data.replaceFirst("xxxxx", " _+_ ");
		data = data.replace("xx xx", "/\\__\\");
		data = data.replaceFirst("xxxxx", "||[]|");
		return data;
	}
	/**
	 * Gets clean Ascii art for a woods/mountain clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getAsciiClearing(final String text,
			final TileClearing clearing) {
		String data = text;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		data = data.replaceFirst("xxxxx", "     ");
		sb.setLength(0);
		sb.append(' ');
		sb.append(clearing.getName());
		sb.append(' ');
		data = data.replaceFirst("xx xx", sb.toString());
		data = data.replaceFirst("xxxxx", "     ");
		sb.returnToPool();
		sb = null;
		return data;
	}
	/**
	 * Gets clean Ascii art for a guardhouse clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getAsciiGuardhouse(final String text,
			final TileClearing clearing) {
		String data = text;
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		data = data.replaceFirst("xxxxx", "|^^^|");
		data = data.replaceFirst("xx xx", "| +[|");
		data = data.replaceFirst("xxxxx", "|+[]|");
		return data;
	}
	/**
	 * Gets clean Ascii art for a house clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getAsciiHouse(final String text,
			final TileClearing clearing) {
		String data = text;
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		data = data.replaceFirst("xxxxx", " _m_ ");
		data = data.replace("xx xx", "/\\__\\");
		data = data.replaceFirst("xxxxx", "|_|\"|");
		return data;
	}
	/**
	 * Gets clean Ascii art for an inn clearing.
	 * @param text the initial text
	 * @param clearing the {@link TileClearing}
	 * @return {@link String}
	 */
	private String getAsciiInn(final String text, final TileClearing clearing) {
		String data = text;
		data = data.replaceFirst("__\\|__", "  |  ");
		data = data.replaceFirst("_____", "     ");
		data = data.replaceFirst("xxxxx", " m__ ");
		data = data.replace("xx xx", "/\\__\\");
		data = data.replaceFirst("xxxxx", "||[]|");
		return data;
	}
	/**
	 * Gets the hex's clearing id. If the hex isn't associated with a clearing,
	 * -1 is returned.
	 * @return <code>int</code>
	 */
	public final int getClearingId() {
		return clearingId;
	}
	/**
	 * Gets the value of the terrain.
	 * @return <code>int</code>
	 */
	public final int getTerrain() {
		return terrain;
	}
	/**
	 * Loads the terrain art from file.
	 * @return {@link String}
	 * @throws Exception if there is an error loading the art
	 */
	private String loadTerrainArt() throws Exception {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append("terrain_");
		if (terrain < TerrainHexagon.CLEARING) {
			sb.append("ROAD");
			for (int i = 0, len = TerrainHexagon.NUM_SIDES; i < len; i++) {
				if ((terrain & 1 << i) == 1 << i) {
					sb.append('_');
					sb.append(i);
				}
			}
		} else if (terrain == TerrainHexagon.CLEARING) {
			sb.append("CLEARING");
		} else if (terrain > TerrainHexagon.CLEARING
				&& terrain < TerrainHexagon.FOREST) {
			sb.append("CLEARING_ROAD");
			for (int i = 0, len = TerrainHexagon.NUM_SIDES; i < len; i++) {
				if ((terrain & 1 << i) == 1 << i) {
					sb.append('_');
					sb.append(i);
				}
			}
		} else if (terrain == TerrainHexagon.FOREST
				|| terrain == TerrainHexagon.FOREST_GREY
				|| terrain == TerrainHexagon.FOREST_GOLD
				|| terrain == TerrainHexagon.FOREST_PURPLE
				|| terrain == TerrainHexagon.FOREST_GOLD_GREY_PURPLE) {
			sb.append("FOREST");
		} else if (terrain == TerrainHexagon.GRASS
				|| terrain == TerrainHexagon.GRASS_GREY) {
			sb.append("GRASS");
		} else if (terrain == TerrainHexagon.MOUNTAIN) {
			sb.append("MOUNTAIN");
		} else if (terrain == TerrainHexagon.RUINS) {
			sb.append("RUINS");
		}
		String s =
				TextLoader.getInstance().loadText("ascii_art.txt",
						sb.toString());
		sb.returnToPool();
		sb = null;
		return s;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.module.magicrealm.graph.Hexagon#rotate()
	 */
	@Override
	public final void rotate() throws Exception {
		super.rotate();
		int edges = 0;
		if (terrain >= TerrainHexagon.CLEARING
				&& terrain < TerrainHexagon.FOREST) {
			edges = TerrainHexagon.CLEARING;
		}
		if (terrain < TerrainHexagon.FOREST) {
			for (int i = 0, len = TerrainHexagon.NUM_SIDES; i < len; i++) {
				if ((terrain & 1 << i) == 1 << i) {
					if (i < TerrainHexagon.NUM_SIDES - 1) {
						edges += 1 << i + 1;
					} else {
						edges++;
					}
				}
			}
			terrain = edges;
		}
	}
	/**
	 * Sets the value of the clearing id. -1 means no clearing.
	 * @param val the value to set
	 */
	public final void setClearingId(final int val) {
		clearingId = val;
	}
	/**
	 * Sets the value of the terrain.
	 * @param val the value to set
	 */
	public final void setTerrain(final int val) {
		terrain = val;
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append("[id=");
		sb.append(super.getId());
		sb.append(", x=");
		sb.append(super.getX());
		sb.append(", y=");
		sb.append(super.getY());
		sb.append(", z=");
		sb.append(super.getZ());
		sb.append(", terrain=");
		sb.append(terrain);
		sb.append(", clearingId=");
		sb.append(clearingId);
		sb.append("]");
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
}
