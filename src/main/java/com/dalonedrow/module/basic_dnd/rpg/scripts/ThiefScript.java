/**
 *
 */
package com.dalonedrow.module.basic_dnd.rpg.scripts;

import com.dalonedrow.rpg.base.constants.ScriptConsts;

/**
 * @author 588648
 */
public class ThiefScript extends BDDPCScript {
	/* (non-Javadoc)
	 * @see com.dalonedrow.module.basic_dnd.rpg.scripts.BDDPCScript#getMaxRollArmorClass(int)
	 */
	@Override
	protected int getMaxRollArmorClass(final int level) {
		// at level 0, a roll of 20 is required to hit AC0.
		int maxRollAC = 0;
		int l = level;
		if (l > 21) { // thief attack tables stop at level 21
			l = 21;
		}
		switch (l) {
		case 21:
			maxRollAC--;
		case 19:
		case 20:
			maxRollAC--;
		case 17:
		case 18:
			maxRollAC--;
		case 15:
		case 16:
			maxRollAC--;
		case 13:
		case 14:
			maxRollAC--;
		case 12:
			maxRollAC--;
		case 11:
			maxRollAC--;
		case 9:
		case 10:
			maxRollAC--;
		case 6:
		case 7:
		case 8:
			maxRollAC--;
		case 4:
		case 5:
			maxRollAC--;
		case 1:
		case 2:
		case 3:
			maxRollAC--;
		default:
			break;
		}
		return maxRollAC;
	}
}
