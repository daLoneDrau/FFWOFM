/**
 *
 */
package com.dalonedrow.module.basic_dnd.rpg.scripts.items;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.basic_dnd.rpg.constants.BDDEquipmentGlobals;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDIO;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDItem;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDScriptable;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.Dice;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.Groups;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.ScriptVariables;
import com.dalonedrow.module.basic_dnd.rpg.scripts.BDDPCScript;
import com.dalonedrow.rpg.base.constants.EquipmentGlobals;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author 588648
 */
public class BattleAxe extends BDDScriptable {
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.Scriptable#onEquip()
	 */
	@Override
	public int onEquip() throws RPGException {
		// play sound file "equip_nosword"
		// PLAY "equip_nosword"
		return super.onEquip();
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.Scriptable#onInit()
	 */
	@Override
	public int onInit() throws RPGException {
		BDDIO io = super.getIO();
		BDDItem item = io.getItemData();
		item.setItemName("Battle Axe");
		item.setDescription("An arm-length weapon borne in both hands");
		item.setPrice(6);
		item.setWeight(6);
		item.ARX_EQUIPMENT_SetObjectType(EquipmentGlobals.OBJECT_TYPE_2H, true);
		item.getEquipitem().getElement(
		        BDDEquipmentGlobals.EQUIPITEM_ELEMENT_DAMAGES).setValue(
		                Dice.ONE_D8.index());
		io.addGroup(Groups.EDGED_WEAPON.toString());
		io.addGroup(Groups.HEAVY_WEAPON.toString());
		io = null;
		item = null;
		return super.onInit();
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.Scriptable#onInventoryUse()
	 */
	@Override
	public int onInventoryUse() throws RPGException {
		int fighting = Script.getInstance().getGlobalIntVariableValue(
		        ScriptVariables.FIGHTING.toString());
		if (fighting == 0) { // game is not in combat mode
			int ioid = super.getLocalIntVariableValue(
			        ScriptVariables.TARGET_IO.toString());
			if (Interactive.getInstance().hasIO(ioid)) {
				BDDIO io = super.getIO();
				// check to see if weapon is restricted
				BDDIO tio = (BDDIO) Interactive.getInstance().getIO(ioid);
				if (tio.hasIOFlag(IoGlobals.IO_01_PC)) {
					if (((BDDPCScript) tio.getScript())
					        .isWeaponRestricted(io)) {
						// TODO - send message that weapon is restricted
					} else {
						io.getItemData().ARX_EQUIPMENT_Equip(tio);
					}
				}
				// if player isn't strong enough to wield
				// send a message
				// else
				// have player equip the item
				io = null;
				tio = null;
			}
		}
		return super.onInventoryUse();
	}
}
