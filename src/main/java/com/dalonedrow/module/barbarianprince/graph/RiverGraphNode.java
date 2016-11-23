package com.dalonedrow.module.barbarianprince.graph;

import com.dalonedrow.engine.sprite.base.SimplePoint;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.graph.GraphNode;

/**
 * 
 * @author drau
 *
 */
public final class RiverGraphNode extends GraphNode {
	/** node representing a hex location. */
	public static final int RIVER_BANK = 0;
	/** node representing a river side. */
	public static final int RIVER_RAFT = 1;
    /**
     * an integer representing the one of the hex nodes to which the river 
     * connects.
     */
    private int bank0;
    /**
     * an integer representing the one of the hex nodes to which the river 
     * connects.
     */
    private int bank1;
	/** the id of the hex node this node represents. */
    private int hexId;
	/** the type of node. */
	private int type;
	/**
	 * Gets the value for the bank0.
	 * @return {@link int}
	 */
	public int getBank0() {
		return bank0;
	}
	/**
	 * Gets the value for the bank1.
	 * @return {@link int}
	 */
	public int getBank1() {
		return bank1;
	}
	/**
	 * Gets the value for the hexId.
	 * @return {@link int}
	 */
	public int getHexId() {
		return hexId;
	}
	/**
	 * Gets the value for the type.
	 * @return {@link int}
	 */
	public int getType() {
		return type;
	}
	/**
	 * Sets the value of the bank0.
	 * @param val the new value to set
	 */
	public void setBank0(final int val) {
		bank0 = val;
	}
	/**
	 * Sets the value of the bank0.
	 * @param val the new value to set
	 * @throws RPGException if an error occurs
	 */
	public void setBank0(final SimplePoint val) throws RPGException {
		bank0 = HexMap.getInstance().getHex(val).getIndex();
	}
	/**
	 * Sets the value of the bank1.
	 * @param bank1 the new value to set
	 */
	public void setBank1(int bank1) {
		this.bank1 = bank1;
	}
	/**
	 * Sets the value of the bank1.
	 * @param val the new value to set
	 * @throws RPGException if an error occurs
	 */
	public void setBank1(final SimplePoint val) throws RPGException {
		bank1 = HexMap.getInstance().getHex(val).getIndex();
	}
	/**
	 * Sets the value of the bank1.
	 * @param val the new value to set
	 * @throws RPGException if an error occurs
	 */
	public void setHexId(final SimplePoint val) throws RPGException {
		hexId = HexMap.getInstance().getHex(val).getIndex();
	}
	/**
	 * Sets the value of the type.
	 * @param val the new value to set
	 */
	public void setType(final int val) {
		type = val;
	}
	/**
	 * Sets the value of the hexId.
	 * @param hexId the new value to set
	 */
	public void setHexId(int hexId) {
		this.hexId = hexId;
	}
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[index = ");
		sb.append(super.getIndex());
		switch (type) {
		case RIVER_BANK:
			sb.append(", type = RIVER_BANK, name = \"");
			sb.append(super.getName());
			sb.append("\", hex = ");
			sb.append(hexId);
			break;
		default:
			sb.append(", type = RIVER_RAFT, name = \"");
			sb.append(super.getName());
			sb.append("\", bank0 = ");
			sb.append(bank0);
			sb.append(", bank1 = ");
			sb.append(bank1);
			break;
		}
		sb.append("]");
		String s = sb.toString();
		sb = null;
		return s;
	}    
}
