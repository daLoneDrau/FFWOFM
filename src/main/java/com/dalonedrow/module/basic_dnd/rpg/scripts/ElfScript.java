/**
 *
 */
package com.dalonedrow.module.basic_dnd.rpg.scripts;

import com.dalonedrow.rpg.base.constants.ScriptConsts;

/**
 * @author 588648
 */
public class ElfScript extends BDDPCScript {
	/* (non-Javadoc)
	 * @see com.dalonedrow.module.basic_dnd.rpg.scripts.BDDPCScript#getMaxRollArmorClass(int)
	 */
	@Override
	protected int getMaxRollArmorClass(final int level) {
		// at level 0, a roll of 20 is required to hit AC0.
		int maxRollAC = 0;
		int l = level;
		if (l > 19) { // dwarf attack tables stop at level 19
			l = 19;
		}
		switch (l) {
		case 19:
			maxRollAC--;
		case 18:
			maxRollAC--;
		case 17:
			maxRollAC--;
		case 16:
			maxRollAC--;
		case 15:
			maxRollAC--;
		case 14:
			maxRollAC--;
		case 13:
			maxRollAC--;
		case 12:
			maxRollAC--;
		case 10:
		case 11:
			maxRollAC--;
		case 9:
			maxRollAC--;
		case 7:
		case 8:
			maxRollAC--;
		case 6:
			maxRollAC--;
		case 5:
			maxRollAC--;
		case 4:
			maxRollAC--;
		case 3:
			maxRollAC--;
		case 1:
		case 2:
			maxRollAC--;
		default:
			break;
		}
		return maxRollAC;
	}
}
