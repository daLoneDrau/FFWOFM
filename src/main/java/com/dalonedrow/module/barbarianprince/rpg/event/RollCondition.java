package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * 
 * @author drau
 *
 */
public final class RollCondition extends Condition {
	/** the condition modifier. */
	private int conditionModifier;
	/** the number of d6 rolled. */
	private int numD6;
	/** the roll modifier. */
	private int rollModifier;
	/** the roll minimum needed to pass. */
	private int rollMin;
	/** the additional condition that applies a roll modifier. */
	private Condition condition;
	/** Creates a new instance of {@link RollCondition}. */
	public RollCondition() {
		super();
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
		int roll = Diceroller.getInstance().rollXdY(numD6, Dice.D6);
		roll += rollModifier;
		if (condition != null && condition.passes()) {
			roll += conditionModifier;
		}
		if (roll >= rollMin) {
			passes = true;
		}
		return passes;
	}
	/**
	 * Sets the additional condition that modifies the roll.
	 * @param val the new value to set
	 */
	public void setCondition(final Condition val) {
		condition = val;
	}
	/**
	 * Sets the condition modifier.
	 * @param val the new value to set
	 */
	public void setConditionModifier(final int val) {
		conditionModifier = val;
	}
	/**
	 * Sets the number of d6 rolled.
	 * @param val the new value to set
	 */
	public void setNumD6(final int val) {
		numD6 = val;
	}
	/**
	 * Sets the roll minimum needed to pass.
	 * @param val the new value to set
	 */
	public void setRollMin(final int val) {
		rollMin = val;
	}
	/**
	 * Sets the roll modifier.
	 * @param val the new value to set
	 */
	public void setRollModifier(final int val) {
		rollModifier = val;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName());
		sb.append("[numD6 = ");
		sb.append(numD6);
		sb.append(", rollMin = ");
		sb.append(rollMin);
		sb.append(", rollModifier = ");
		sb.append(rollModifier);
		sb.append(", conditionModifier = ");
		sb.append(conditionModifier);
		sb.append(", condition = ");
		sb.append(condition);
		sb.append("]");
		String s = sb.toString();
		sb = null;
		return s;
	}
}
