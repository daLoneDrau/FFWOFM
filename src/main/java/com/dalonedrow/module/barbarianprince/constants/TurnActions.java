package com.dalonedrow.module.barbarianprince.constants;

/**
 * @author drau
 */
public class TurnActions {
	/** airborne travel. */
	public static final int	ACTION_AIR_TRAVEL	= 8;
	/** seek audience with local lord in town, castle or temple. */
	public static final int	ACTION_AUDIENCE		= 128;
	/** seek to hire followers in town or castle. */
	public static final int	ACTION_HIRE			= 64;
	/** travel overland. */
	public static final int	ACTION_LAND_TRAVEL	= 2;
	/** seek news & information in town, castle, or temple. */
	public static final int	ACTION_NEWS			= 32;
	/** submit offering at temple. */
	public static final int	ACTION_OFFERING		= 256;
	/** Daily actions. */
	/** rest in current hex, to heal wounds and improve hunting. */
	public static final int	ACTION_REST			= 1;
	/** waterborne travel. */
	public static final int	ACTION_RIVER_TRAVEL	= 4;
	/** search for a previously placed cache. */
	public static final int	ACTION_SEARCH_CACHE	= 16;
	/** search in a ruins. */
	public static final int	ACTION_SEARCH_RUINS	= 512;
	/** leaves items or gold in a "hidden cache". */
	public static final int	ACTION_MAKE_CACHE	= 1024;
	/** Hidden constructor. */
	private TurnActions() {
		super();
	}
}
