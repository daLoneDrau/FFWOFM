package com.dalonedrow.module.basic_dnd.rpg.systems;

import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDIO;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDScriptable;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDStacked;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDTimer;
import com.dalonedrow.rpg.base.systems.Script;

public final class BDDScriptMaster
extends Script<BDDIO, BDDTimer, BDDScriptable, BDDStacked> {
	/** Creates a new instance of {@link BDDScriptMaster}. */
	protected BDDScriptMaster() {
		super.setInstance(this);
	}

	@Override
	protected void clearAdditionalEventStacks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clearAdditionalEventStacksForIO(BDDIO io) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void destroyScriptTimers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventStackInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void executeAdditionalStacks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BDDTimer getScriptTimer(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BDDTimer[] getScriptTimers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BDDStacked getStackedEvent(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initScriptTimers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setScriptTimer(int index, BDDTimer timer) {
		// TODO Auto-generated method stub
		
	}
}
