package com.dalonedrow.module.basic_dnd.rpg.flyweights;

import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;

/**
 * 
 * @author Donald
 *
 */
public class BDDIO
extends BaseInteractiveObject<BDDItem, BDDInventory, BDDPC, BDDNPC, BDDScript> {
	/**
	 * Creates a new instance of {@link BDDIO}.
	 * @param id the reference id
	 */
	public BDDIO(final int id) {
		super(id);
	}
}
