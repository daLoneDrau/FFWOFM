package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * 
 * @author drau
 *
 */
public final class LocationCondition extends Condition {
	/** the list of locations where this condition passes. */
	private int[] locations;
	/** Creates a new instance of {@link LocationCondition}. */
	public LocationCondition() {
		locations = new int[0];
	}
	/**
	 * Adds a location to the list of locations where this condition passes.
	 * @param loc the location
	 */
	public void addLocation(final int loc) {
		locations = ArrayUtilities.getInstance().extendArray(loc, locations);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getType() {
		return ConditionType.LOCATION_CONDITION;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean passes() throws RPGException {
		boolean passes = false;
		for (int i = locations.length - 1; i >= 0; i--) {
			if (BpController.getInstance().getPartyLocation() == locations[i]) {
				passes = true;
				break;
			}
		}
		return passes;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append("[locations = [");
		for (int i = 0, len = locations.length; i < len; i++) {
			sb.append(locations[i]);
			if (i + 1 < len) {
				sb.append(", ");
			}
		}
		sb.append("]]");
		String s = sb.toString();
		sb = null;
		return s;
	}
}
