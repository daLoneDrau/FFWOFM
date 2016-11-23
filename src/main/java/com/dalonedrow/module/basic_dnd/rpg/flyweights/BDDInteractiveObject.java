package com.dalonedrow.module.basic_dnd.rpg.flyweights;

import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;

public class BDDInteractiveObject
        extends BaseInteractiveObject<ITEM, INVENTORY, BDDCharacter, NPC, SCRIPT> {

	public BDDInteractiveObject(final int id) {
		super(id);
	}

}
