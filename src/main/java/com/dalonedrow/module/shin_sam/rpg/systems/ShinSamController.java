package com.dalonedrow.module.shin_sam.rpg.systems;

import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.shin_sam.rpg.base.flyweights.ShinSamIO;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class ShinSamController extends ProjectConstants<ShinSamIO> {
	/** Creates a new instance of {@link ShinSamController}. */
	public ShinSamController() {
		super.setInstance(this);
		new ShinSamInteractive();
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
		// TODO Auto-generated method stub
		return 0;
	}
}
