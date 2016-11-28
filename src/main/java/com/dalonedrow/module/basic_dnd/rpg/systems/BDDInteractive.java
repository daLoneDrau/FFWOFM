package com.dalonedrow.module.basic_dnd.rpg.systems;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDIO;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

public class BDDInteractive extends Interactive<BDDIO> {
	/** the next available id. */
	private int						nextId;
	/** the list of {@link BDDIO}s. */
	private BDDIO[]	objs;

	@Override
	public void addAnimation(int id, int animId) throws RPGException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BDDIO addItem(String item, long flags) throws RPGException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void ARX_INTERACTIVE_ForceIOLeaveZone(BDDIO io, long flags) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxIORefId() {
		return nextId;
	}
	BDDInteractive() {
		super.setInstance(this);
	}

	@Override
	protected BDDIO getNewIO() {
		// step 1 - find the next id
		int id = nextId++;
		BDDIO io = null;
		//try {
			io = new BDDIO(id);
		//} catch (RPGException e) {
		//	JOGLErrorHandler.getInstance().fatalError(e);
		//}
		// step 2 - find the next available index in the objs array
		int index = -1;
		for (int i = objs.length - 1; i >= 0; i--) {
			if (objs[i] == null) {
				index = i;
				break;
			}
		}
		// step 3 - put the new object into the arrays
		if (index < 0) {
			objs = ArrayUtilities.getInstance().extendArray(io, objs);
		} else {
			objs[index] = io;
		}
		return io;
	}
	@Override
	protected BDDIO[] getIOs() {
		return objs;
	}
}
