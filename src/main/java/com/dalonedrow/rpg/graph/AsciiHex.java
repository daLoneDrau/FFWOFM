package com.dalonedrow.rpg.graph;

import com.dalonedrow.engine.sprite.base.SimpleVector3;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * Class to represent the physical layout of a hex tile.
 * @author drau
 */
public class AsciiHex extends Hexagon {
	/** the list of tiles that make up the hex. */
	private Hexagon[]	hexes	= new Hexagon[0];
	/** the number of rotations applied to the {@link Hexagon}. */
	private int			rotations;
	/**
	 * Creates a new instance of {@link AsciiHex}.
	 */
	private AsciiHex(final boolean isFlat, final int refId) {
		super(isFlat, refId);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Creates a new instance of {@link AsciiHex}.
	 * @param refId the hexagon's reference id
	 */
	protected AsciiHex(final int refId) {
		super(false, refId);
	}
	/**
	 * Adds a hexagon.
	 * @param hexagon the hexagon
	 */
	public final void addHex(final Hexagon hexagon) {
		if (hexagon != null) {
			hexes = ArrayUtilities.getInstance().extendArray(
					hexagon, hexes);
		}
	}
	public void copyOf(final AsciiHex hex) {
		((Hexagon) this).copyOf(hex);
		rotations = hex.rotations;
		hexes = new Hexagon[hex.hexes.length];
		for (int i = 0, len = hexes.length; i < len; i++) {
			Hexagon h = new Hexagon(
					hex.hexes[i].isFlat(), hex.hexes[i].getId(),
					hex.hexes[i].getSize());
			h.copyOf(hex.hexes[i]);
			hexes[i] = h;
			h = null;
		}
	}
	/**
	 * Gets the center hex for this ascii hex.
	 * @return {@link Hexagon}
	 */
	public final Hexagon getCenterHexagon() {
		int maxx = 0, minx = 0, maxy = 0, miny = 0, maxz = 0, minz = 0;
		for (int i = hexes.length - 1; i >= 0; i--) {
			maxx = Math.max(maxx, hexes[i].getX());
			minx = Math.min(minx, hexes[i].getX());
			maxy = Math.max(maxy, hexes[i].getY());
			miny = Math.min(miny, hexes[i].getY());
			maxz = Math.max(maxz, hexes[i].getZ());
			minz = Math.min(minz, hexes[i].getZ());
		}
		return getHexagon((maxx + minx) / 2, (maxy + miny) / 2,
				(maxz + minz) / 2);
	}
	/**
	 * Gets the {@link Hexagon} found at a specific index.
	 * @param index the index
	 * @return {@link Hexagon}
	 */
	public final Hexagon getHexagon(final int index) {
		return hexes[index];
	}
	/**
	 * Gets a hexagon at a specific set of coordinates.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 * @return {@link Hexagon}
	 */
	public final Hexagon getHexagon(final int x, final int y, final int z) {
		Hexagon hex = null;
		for (int i = hexes.length - 1; i >= 0; i--) {
			if (hexes[i].equals(x, y, z)) {
				hex = hexes[i];
			}
		}
		return hex;
	}
	/**
	 * Gets a hexagon at a specific set of coordinates.
	 * @param v3 the set of coordinates
	 * @return {@link Hexagon}
	 */
	public final Hexagon getHexagon(final SimpleVector3 v3) {
		return getHexagon((int) v3.getX(), (int) v3.getY(), (int) v3.getZ());
	}
	/**
	 * gets the number of hexes that make up this ascii hex.
	 * @return <code>int</code>
	 */
	public final int getNumberOfHexes() {
		return hexes.length;
	}
	/**
	 * Gets the number of rotations applied to the hex tile.
	 * @return <code>int</code>
	 */
	public final int getRotations() {
		return rotations;
	}
	/**
	 * Rotates the hex tile.
	 * @throws Exception if an error occurs
	 */
	@Override
	public void rotate() throws Exception {
		for (int i = hexes.length - 1; i >= 0; i--) {
			hexes[i].rotate();
		}
		rotations++;
		if (rotations > 5) {
			rotations = 0;
		}
	}
}
