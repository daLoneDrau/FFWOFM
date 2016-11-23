package com.dalonedrow.module.barbarianprince.rpg;

import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;

/**
 * 
 * @author drau
 *
 */
public class BpInteractiveObject
		extends BaseInteractiveObject<BpItem, BpInventory, BpCharacter, BpNpc,
		BpScriptable> {
	/**
	 * Creates a new instance of {@link BpInteractiveObject}.
	 * @param id the IO id
	 */
	public BpInteractiveObject(final int id) {
		super(id);
        super.setInventory(new BpInventory());
        super.getInventory().setIo(this);
        super.setItemData(new BpItem());
	}
}
