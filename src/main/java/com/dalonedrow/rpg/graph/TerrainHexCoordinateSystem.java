package com.dalonedrow.rpg.graph;

public final class TerrainHexCoordinateSystem extends HexCoordinateSystem {
	/**
	 * Creates a new instance of {@link TerrainHexCoordinateSystem}.
	 * @param config the system's offset configuration
	 */
	public TerrainHexCoordinateSystem(final int config) {
		super(config);
	}
	/**
	 * Gets a hexagon associated with a specific clearing.
	 * @param clearingId the clearing id
	 * @return {@link Hexagon}
	 */
	public Hexagon getHexagonForClearing(final int clearingId) {
		Hexagon hex = null;
		for (int i = super.length() - 1; i >= 0; i--) {
			Hexagon hex0 = super.getHexagon(i);
			if (hex0 instanceof TerrainHexagon) {
				if (((TerrainHexagon) hex0).getClearingId() == clearingId) {
					hex = hex0;
					break;
				}
			}
		}
		return hex;
	}
}
