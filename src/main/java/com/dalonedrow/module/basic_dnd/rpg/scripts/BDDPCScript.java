/**
 * 
 */
package com.dalonedrow.module.basic_dnd.rpg.scripts;

import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDIO;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDScriptable;

/**
 * @author 588648
 *
 */
public abstract class BDDPCScript extends BDDScriptable {
	/**
	 * Gets the roll needed to hit a specific Armor Class.
	 * @param ac the armor class
	 * @return {@link int}
	 */
	public final int getTHAC(final int ac) {
		int maxRollAC = getMaxRollArmorClass(super.getIO().getLevel());
		// maxRollAC is now the lowest AC at which
		// the character needs to roll a 20 to hit
		int roll = 20;
		// roll is going to be the max AC minus the AC trying to hit.
		// if max AC is 0 and the AC trying to hit is 6, the roll needed is
		// going to be 20 + (0 - 6) = 14
		roll += (maxRollAC - ac);
		if (roll > 20) {
			roll = 20;
		}
		if (roll < 2) {
			roll = 2;
		}
		return roll;
	}
	/**
	 * Gets the lowest armor class at which the character needs to make the
	 * highest roll (20) to hit.
	 * @return <code>int</code>
	 */
	protected abstract int getMaxRollArmorClass(int level);
	/**
	 * @param io
	 * @return
	 */
	public abstract boolean isItemRestricted(BDDIO io);
}
