package com.dalonedrow.module.shin_sam.rpg.base.flyweights;

import com.dalonedrow.rpg.base.flyweights.IoNpcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class ShinSamNPC extends IoNpcData<ShinSamIO> {

	public ShinSamNPC() throws RPGException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void adjustLife(float dmg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void adjustMana(float dmg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ARX_NPC_ManagePoison() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void defineAttributes() throws RPGException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getBaseLife() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getBaseMana() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean hasLifeRemaining() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void moveToInitialPosition() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void restoreLifeToMax() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stopActiveAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stopIdleAnimation() {
		// TODO Auto-generated method stub
		
	}

}
