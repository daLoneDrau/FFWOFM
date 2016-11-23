package com.dalonedrow.module.citadelofchaos.rpg;

import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;

public class CitadelInteractiveObject
		extends
		BaseInteractiveObject<ITEM, INVENTORY, CitadelPC, CitadelNPC, SCRIPT> {

	public CitadelInteractiveObject(final int id) {
		super(id);
	}

}
