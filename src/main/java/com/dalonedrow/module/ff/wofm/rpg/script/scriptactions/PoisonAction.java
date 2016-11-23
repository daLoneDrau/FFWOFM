package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class PoisonAction extends FFScriptAction {
	private int poisonous_count;
	private int poisonous;
	/**
	 * Creates a new instance of {@link PoisonAction}.
	 */
	public PoisonAction() {
		super();
	}
	@Override
	public void execute() throws RPGException {
		if (poisonous_count == 0) {
			super.getMaster().getIO().setPoisonCharges(poisonous_count);
		} else {
			super.getMaster().getIO().setPoisonLevel(poisonous);
			super.getMaster().getIO().setPoisonCharges(poisonous_count);	
		}
	}
}
