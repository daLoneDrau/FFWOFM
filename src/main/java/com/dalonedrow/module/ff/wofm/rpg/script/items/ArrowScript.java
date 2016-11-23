package com.dalonedrow.module.ff.wofm.rpg.script.items;

import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFItem;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * S
 * @author drau
 *
 */
public final class ArrowScript extends WeaponScript {
	/**
	 * Creates a new instance of {@link ArrowScript}.
	 * @param io the IO associated with the script
	 */
	public ArrowScript(final FFInteractiveObject io) {
		super(io);
	}
	/* (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.Scriptable#onInit()
	 */
	@Override
	public int onInit() throws RPGException {
		FFInteractiveObject io = super.getIO();
		FFItem item = io.getItemData();
		item.setItemName("Arrow");
		io.addGroup("ARMORY");
		// SET_MATERIAL WOOD
		// SET_WEAPON_MATERIAL DAGGER
		item.setPrice(1);
		item.setStackSize(100);
		//item.setStealvalue(50);
		return super.onInit();
	}	
}
