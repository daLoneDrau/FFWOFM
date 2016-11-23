package com.dalonedrow.module.barbarianprince.graph;

public class RiverCrossing {
	private int from;
	private int to;
	private String riverName;
	/**
	 * Gets the value for the from.
	 * @return {@link int}
	 */
	public int getFrom() {
		return from;
	}
	/**
	 * Sets the value of the from.
	 * @param from the new value to set
	 */
	public void setFrom(int from) {
		this.from = from;
	}
	/**
	 * Gets the value for the to.
	 * @return {@link int}
	 */
	public int getTo() {
		return to;
	}
	/**
	 * Sets the value of the to.
	 * @param to the new value to set
	 */
	public void setTo(int to) {
		this.to = to;
	}
	/**
	 * Gets the value for the riverName.
	 * @return {@link String}
	 */
	public String getRiverName() {
		return riverName;
	}
	/**
	 * Sets the value of the riverName.
	 * @param riverName the new value to set
	 */
	public void setRiverName(String riverName) {
		this.riverName = riverName;
	}
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[from = ");
		sb.append(from);
		sb.append(", to = ");
		sb.append(to);
		sb.append(", name = \"");
		sb.append(riverName);
		sb.append("\"]");
		String s = sb.toString();
		sb = null;
		return s;
	}   
}
