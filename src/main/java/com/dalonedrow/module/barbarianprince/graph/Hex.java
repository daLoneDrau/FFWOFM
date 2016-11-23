package com.dalonedrow.module.barbarianprince.graph;

import com.dalonedrow.module.barbarianprince.constants.TurnActions;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.graph.Hexagon;
import com.dalonedrow.rpg.graph.PhysicalGraphNode;

/**
 * @author drau
 */
public final class Hex extends PhysicalGraphNode {
	/** the {@link Hex}'s features. */
	private long	features;
	/** the internal {@link Hexagon} instance representing this location. */
	private Hexagon	hexagon;
	/** the {@link Hex}'s type. */
	private HexType	type;
	/**
	 * Creates a new instance of {@link Hex}.
	 * @throws RPGException if an error occurs
	 */
	private Hex() throws RPGException {
		super("", 0, 0, 0);
	}
	/**
	 * Adds a feature.
	 * @param feature the feature
	 */
	public void addFeature(final HexFeature feature) {
		features |= feature.getFlag();
	}
	/**
	 * Determines if a specific action is allowed.
	 * @param action the action id
	 * @return <tt>true</tt> if the action is allowed; <tt>false</tt> otherwise
	 * @throws RPGException if an error occurs
	 */
	public boolean canPerformAction(final int action) throws RPGException {
		long actions = getPossibleActions();
		return (actions & action) == action;
	}
	/** Clears all features that were set. */
	public void clearFeatures() {
		features = 0;
	}
	/**
	 * Gets the value for the hexagon.
	 * @return {@link Hexagon}
	 */
	public Hexagon getHexagon() {
		return hexagon;
	}
	/**
	 * Gets the location's name.
	 * @return {@link String}
	 */
	public String getLocationName() {
		String name = type.getTitle();
		if (super.getName() != null
				&& super.getName().length > 0) {
			name = new String(super.getName());
		}
		return name;
	}
	/**
	 * Gets all possible actions allowed in this hex.
	 * @return {@link long}
	 * @throws RPGException if an error occurs
	 */
	public long getPossibleActions() throws RPGException {
		long actions = 0;
		actions |= TurnActions.ACTION_REST;
		actions |= TurnActions.ACTION_LAND_TRAVEL;
		if (HexMap.getInstance().hasRiverNode(super.getIndex())) {
			actions |= TurnActions.ACTION_RIVER_TRAVEL;
		}
		// TODO Auto-generated method stub
		// must check for airborne
		
		// TODO Auto-generated method stub
		// must check for caches
		
		if (hasFeature(HexFeature.TOWN)
				|| hasFeature(HexFeature.CASTLE)
				|| hasFeature(HexFeature.TEMPLE)) {
			actions |= TurnActions.ACTION_NEWS;
		}
		
		if (hasFeature(HexFeature.TOWN)
				|| hasFeature(HexFeature.CASTLE)) {
			actions |= TurnActions.ACTION_HIRE;
		}
		
		if (hasFeature(HexFeature.TOWN)
				|| hasFeature(HexFeature.CASTLE)
				|| hasFeature(HexFeature.TEMPLE)) {
			actions |= TurnActions.ACTION_AUDIENCE;
		}
		
		if (hasFeature(HexFeature.TEMPLE)) {
			actions |= TurnActions.ACTION_OFFERING;
		}
		
		if (hasFeature(HexFeature.RUINS)) {
			actions |= TurnActions.ACTION_SEARCH_RUINS;
		}
		return actions;
	}
	/**
	 * Gets the type.
	 * @return {@link HexType}
	 */
	public HexType getType() {
		return type;
	}
	/**
	 * Determines if the {@link Hex} has a specific feature.
	 * @param feature the feature
	 * @return true if the {@link Hex} has the feature; false otherwise
	 */
	public boolean hasFeature(final HexFeature feature) {
		return (features & feature.getFlag()) == feature.getFlag();
	}
	/**
	 * Removes a feature.
	 * @param feature the feature
	 */
	public void removeFeature(final HexFeature feature) {
		features &= ~feature.getFlag();
	}
	/**
	 * Sets the value of the hexagon.
	 * @param h the new value to set
	 */
	public void setHexagon(final Hexagon h) {
		hexagon = h;
	}
	/**
	 * Sets the hex's type.
	 * @param t the type
	 * @throws RPGException if the type is invalid
	 */
	public void setType(final int t) throws RPGException {
		type = HexType.typeOf(t);
		if (type == null) {
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid type " + t);
		}
	}
	/**
	 * Gets a formatted {@link String} for this hex.
	 * @return {@link String}
	 */
	public String toMsgString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getLocationName());
		sb.append(" (");
		sb.append((int) super.getLocation().getX());
		sb.append(", ");
		sb.append((int) super.getLocation().getY());
		sb.append(')');
		String s = sb.toString();
		sb = null;
		return s;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[index = ");
		sb.append(super.getIndex());
		sb.append(", location = ");
		sb.append(super.getLocation());
		sb.append(", name = \"");
		sb.append(super.getName());
		sb.append("\", type = ");
		sb.append(type);
		sb.append(", features = ");
		sb.append(features);
		sb.append("]");
		String s = sb.toString();
		sb = null;
		return s;
	}
}
