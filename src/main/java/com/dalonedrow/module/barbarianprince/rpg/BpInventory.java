package com.dalonedrow.module.barbarianprince.rpg;

import com.dalonedrow.module.barbarianprince.constants.BpIo;
import com.dalonedrow.rpg.base.flyweights.InventoryData;

/**
 * 
 * @author drau
 *
 */
public final class BpInventory 
extends InventoryData<BpInteractiveObject, BPInventorySlot> {
	/** Creates a new instance of {@link BpInventory}. */
	public BpInventory() {
		BPInventorySlot[] slots =
                new BPInventorySlot[BpIo.INV_SLOTS];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new BPInventorySlot();
        }
        super.setSlots(slots);

	}
}
