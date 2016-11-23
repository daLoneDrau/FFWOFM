package com.dalonedrow.module.shin_sam.rpg.systems;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.shin_sam.rpg.base.flyweights.ShinSamIO;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

public class ShinSamInteractive extends Interactive<ShinSamIO> {
	/** the next available id. */
	private int						nextId;
	/** the list of {@link ShinSamIO}s. */
	private ShinSamIO[]	objs;

	@Override
	public void addAnimation(int id, int animId) throws RPGException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ShinSamIO addItem(String item, long flags) throws RPGException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void ARX_INTERACTIVE_ForceIOLeaveZone(ShinSamIO io, long flags) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxIORefId() {
		return nextId;
	}
	ShinSamInteractive() {
		super.setInstance(this);
	}

	@Override
	protected ShinSamIO getNewIO() {
		// step 1 - find the next id
		int id = nextId++;
		ShinSamIO io = null;
		//try {
			io = new ShinSamIO(id);
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
	protected ShinSamIO[] getIOs() {
		return objs;
	}
}
