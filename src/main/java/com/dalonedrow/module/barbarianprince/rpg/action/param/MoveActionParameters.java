package com.dalonedrow.module.barbarianprince.rpg.action.param;

/**
 * Parameter class for {@link MoveAction}.
 * @author drau
 */
public final class MoveActionParameters {
	/** flag indicating whether the move was taken while airborne. */
	private final boolean	airborne;
	/** the move destination. */
	private final int		destination;
	/** flag indicating the party drifts when lost and airborne. */
	private final boolean	drift;
	/** the local guide set for the move. */
	private final int		localGuide;
	/** flag indicating the local guide deserts the group. */
	private final boolean	localGuideDeserts;
	/** flag indicating the party gets lost. */
	private final boolean	lost;
	/** flag indicating the party is rafting. */
	private final boolean	rafting;
	/** flag indicating a travel event happens. */
	private final boolean	travelEvent;
	/**
	 * Creates an instance of {@link MoveActionParameters} used for testing.
	 * @param a flag indicating whether the move was taken while airborne
	 * @param dest the move destination
	 * @param dr flag indicating the party drifts when lost and airborne
	 * @param lg the local guide set for the move
	 * @param lgd flag indicating the local guide deserts the group
	 * @param l flag indicating the party gets lost
	 * @param r flag indicating the party is rafting
	 * @param te flag indicating a travel event happens
	 */
	public MoveActionParameters(final boolean a, final int dest,
			final boolean dr, final int lg,
			final boolean lgd, final boolean l, final boolean r,
			final boolean te) {
		airborne = a;
		destination = dest;
		drift = dr;
		localGuide = lg;
		localGuideDeserts = lgd;
		lost = l;
		rafting = r;
		travelEvent = te;
	}
	/**
	 * Creates a standard instance of {@link MoveActionParameters}.
	 * @param a flag indicating whether the move was taken while airborne
	 * @param dest the move destination
	 * @param lg the local guide set for the move
	 * @param r flag indicating the party is rafting
	 */
	public MoveActionParameters(final boolean a, final int dest, final int lg,
			final boolean r) {
		airborne = a;
		destination = dest;
		localGuide = lg;
		rafting = r;
		drift = false;
		localGuideDeserts = false;
		lost = false;
		travelEvent = false;
	}
	/**
	 * Gets the flag indicating the local guide deserts the group.
	 * @return {@link boolean}
	 */
	public boolean doesLocalGuideDesert() {
		return localGuideDeserts;
	}
	/**
	 * Gets the move destination.
	 * @return {@link int}
	 */
	public int getDestination() {
		return destination;
	}
	/**
	 * Gets the reference id for the local guide set for the move.
	 * @return {@link int}
	 */
	public int getLocalGuide() {
		return localGuide;
	}
	/**
	 * Gets the flag indicating whether the move was taken while airborne.
	 * @return {@link boolean}
	 */
	public boolean isAirborne() {
		return airborne;
	}
	/**
	 * Gets the flag indicating the party drifts when lost and airborne.
	 * @return {@link boolean}
	 */
	public boolean isDrifting() {
		return drift;
	}
	/**
	 * Gets the flag indicating the party gets lost.
	 * @return {@link boolean}
	 */
	public boolean isLost() {
		return lost;
	}
	/**
	 * Gets the flag indicating the party is rafting.
	 * @return {@link boolean}
	 */
	public boolean isRafting() {
		return rafting;
	}
	/**
	 * Gets the flag indicating a travel event happens.
	 * @return {@link boolean}
	 */
	public boolean hasTravelEvent() {
		return travelEvent;
	}
}
