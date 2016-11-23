package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * 
 * @author drau
 *
 */
public final class LinkedCondition extends Condition {
	/** the list of child conditions. */
	private Condition[] children;
	/** Creates a new instance of {@link LinkedCondition}. */
	public LinkedCondition() {
		children = new Condition[0];
	}
	/**
	 * Adds a child to the list of conditions.
	 * @param condition the child condition
	 */
	public void addChild(final Condition condition) {
		if (condition != null) {
			children = ArrayUtilities.getInstance().extendArray(
					condition, children);
		}
	}
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean passes() throws RPGException {
		boolean passes = true;
		for (int i = 0, len = children.length; i < len; i++) {
			if (!children[i].passes()) {
				passes = false;
				break;
			}
		}
		return passes;
	}
}
