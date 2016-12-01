/**
 *
 */
package com.dalonedrow.module.basic_dnd.rpg.scripts;

import com.dalonedrow.rpg.base.constants.ScriptConsts;

/**
 * @author 588648
 */
public class MageScript extends BDDPCScript {
	/* (non-Javadoc)
	 * @see com.dalonedrow.module.basic_dnd.rpg.scripts.BDDPCScript#getMaxRollArmorClass(int)
	 */
	@Override
	protected int getMaxRollArmorClass(final int level) {
		// at level 0, a roll of 20 is required to hit AC0.
		int maxRollAC = 0;
		int l = level;
		if (l > 24) { // mage attack tables stop at level 24
			l = 24;
		}
		switch (l) {
		case 24:
			maxRollAC--;
		case 21:
		case 22:
		case 23:
			maxRollAC--;
		case 19:
		case 20:
			maxRollAC--;
		case 16:
		case 17:
		case 18:
			maxRollAC--;
		case 14:
		case 15:
			maxRollAC--;
		case 13:
			maxRollAC--;
		case 11:
		case 12:
			maxRollAC--;
		case 8:
		case 9:
		case 10:
			maxRollAC--;
		case 4:
		case 5:
		case 6:
		case 7:
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
