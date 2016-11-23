package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.rpg.BpNpc;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.BpInteractive;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * 
 * @author drau
 *
 */
public final class MobSpawnEvent extends Event {
	/** the mob's reference id. */
	private int mobId;
	/** the list of the numbers of mobs to create. */
	private int[] numMobs;
	/** Creates a new instance of {@link MobSpawnEvent}. */
	public MobSpawnEvent() {
		numMobs = new int[0];
		mobId = -1;
	}
	/**
	 * Adds a random number of mobs to create.
	 * @param val the value
	 */
	public void addRandomSpawnNumber(final int val) {
		numMobs = ArrayUtilities.getInstance().extendArray(val, numMobs);
	}
	@Override
	public void fire() throws RPGException {
		if (mobId == -1) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, 
					"mobId was never set");
		}
		if (numMobs.length == 0) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, 
					"no number of mobs set");
		}
		if (super.happens()) {
			int num = Diceroller.getInstance().getRandomIndex(numMobs);
			BpIoGroup g = (BpIoGroup) 
					((BpInteractive) Interactive.getInstance()).newPlayerParty();
			for (int i = num; i > 0; i--) {
				g.addMember(((BpInteractive)
						Interactive.getInstance()).newMob(mobId).getRefId());
			}
			BpController.getInstance().setEncounterGroup(g.getRefId());
		}
		super.setResolved();
	}
	/**
	 * Sets the mob's reference id.
	 * @param val the new value to set
	 */
	public void setMobId(final int val) {
		mobId = val;
	}

}
