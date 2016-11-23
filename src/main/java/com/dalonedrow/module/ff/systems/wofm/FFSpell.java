package com.dalonedrow.module.ff.systems.wofm;

import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.Spell;
import com.dalonedrow.rpg.base.systems.SpellController;

public class FFSpell extends SpellController {
	FFSpell() throws RPGException {
		super.setInstance(this);
		spells = new Spell[ProjectConstants.getInstance().getMaxSpells()];
	}
	private Spell[] spells;
	public Spell getSpell(final int index) {
		Spell spell = null;
		if (index >= 0
				&& index < spells.length) {
			spell = spells[index];
		}
		return spell;
	}
}
