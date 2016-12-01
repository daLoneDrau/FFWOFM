/**
 *
 */
package com.dalonedrow.module.basic_dnd.rpg.flyweights;

import com.dalonedrow.engine.systems.base.Diceroller;

/**
 * @author 588648
 */
public enum Dice {
	/** 1D2. */
	ONE_D2(1, Die.D2),
	/** 1D3. */
	ONE_D3(1, Die.D3),
	/** 1D4. */
	ONE_D4(1, Die.D4),
	/** 1D6. */
	ONE_D6(1, Die.D6),
	/** 3D6. */
	THREE_D6(3, Die.D6);
	private Die die;
	private int num;
	/**
	 * Creates a new instance of {@link Dice}.
	 * @param num
	 * @param die
	 */
	private Dice(int num, Die die) {
		this.num = num;
		this.die = die;
	}
	/**
	 * Gets the index of the Dice, so it can be referenced later when rolled.
	 * @return <code>int</code>
	 */
	public int index() {
		Dice[] list = Dice.values();
		int i = list.length - 1;
		for (; i >= 0; i--) {
			if (list[i].equals(this)) {
				break;
			}
		}
		return i;
	}
	/**
	 * Rolls the dice.
	 * @return <code>int</code>
	 */
	public int roll() {
		int val = 0;
		if (num > 1) {
			val = Diceroller.getInstance().rollXdY(num, die.getFaces());
		} else {
			val = Diceroller.getInstance().rolldX(die.getFaces());
		}
		return val;
	}
}
