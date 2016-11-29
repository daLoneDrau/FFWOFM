package com.dalonedrow.module.basic_dnd.rpg.systems;

import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDIO;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class BDDController extends ProjectConstants<BDDIO> {
	/** Creates a new instance of {@link BDDController}. */
	public BDDController() {
		super.setInstance(this);
		new BDDInteractive();
		new BDDScriptMaster();
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getMaxEquipped() {
		// TODO Auto-generated method stub
		return 0;
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
		return 31;
	}
}
