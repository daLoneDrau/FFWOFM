package com.dalonedrow.module.basic_dnd.rpg.systems;

import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.basic_dnd.rpg.constants.BDDEquipmentGlobals;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDIO;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.ScriptVariables;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class BDDController extends ProjectConstants<BDDIO> {
	/** Creates a new instance of {@link BDDController}. 
	 * @throws RPGException */
	public BDDController() throws RPGException {
		super.setInstance(this);
		new BDDInteractive();
		new BDDScriptMaster();
		// init globals
		BDDScriptMaster.getInstance().setGlobalVariable(
				ScriptVariables.FIGHTING.toString(), 0);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getMaxEquipped() {
		// TODO Auto-generated method stub
		return BDDEquipmentGlobals.MAX_EQUIPPED;
	}
	@Override
	public int getMaxSpells() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getPlayer() throws RPGException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getNumberEquipmentElements() {
		return BDDEquipmentGlobals.MAX_EQUIP_ELEMENTS;
	}
}
