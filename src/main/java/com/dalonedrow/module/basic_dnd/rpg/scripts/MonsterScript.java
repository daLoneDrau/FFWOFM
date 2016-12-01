/**
 * 
 */
package com.dalonedrow.module.basic_dnd.rpg.scripts;

/**
 * @author 588648
 *
 */
public class MonsterScript extends BDDNPCScript {

	/* (non-Javadoc)
	 * @see com.dalonedrow.module.basic_dnd.rpg.scripts.BDDNPCScript#getMaxRollArmorClass(int)
	 */
	@Override
	protected int getMaxRollArmorClass(int level) {
		// at level 1 or less, a roll of 20 is required to hit AC-1.
		int maxRollAC = -1;
		int l = level;
		if (l > 22) { // monster attack tables stop at level 22
			l = 22;
		}
		switch (l) {
		case 22:
			maxRollAC--;
		case 20:
		case 21:
			maxRollAC--;
		case 18:
		case 19:
			maxRollAC--;
		case 16:
		case 17:
			maxRollAC--;
		case 14:
		case 15:
			maxRollAC--;
		case 12:
		case 13:
			maxRollAC--;
		case 10:
		case 11:
			maxRollAC--;
		case 8:
		case 9:
			maxRollAC--;
		case 7:
			maxRollAC--;
		case 6:
			maxRollAC--;
		case 5:
			maxRollAC--;
		case 4:
			maxRollAC--;
		case 3:
			maxRollAC--;
		case 2:
			maxRollAC--;
		case 1:
			maxRollAC--;
		default:
			break;
		}
		return maxRollAC;
	}

}
