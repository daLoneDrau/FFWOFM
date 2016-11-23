package com.dalonedrow.module.shin_sam.rpg.base.flyweights;

import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;

/**
 * 
 * @author Donald
 *
 */
public class ShinSamIO
extends BaseInteractiveObject<ShinSamItem, ShinSamInventory, ShinSamPC,
ShinSamNPC, ShinSamScript> {
	/**
	 * Creates a new instance of {@link ShinSamIO}.
	 * @param id the reference id
	 */
	public ShinSamIO(final int id) {
		super(id);
	}
}
