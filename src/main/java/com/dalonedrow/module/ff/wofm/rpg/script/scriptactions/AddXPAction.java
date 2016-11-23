package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class AddXPAction extends FFScriptAction {
	private int amount;
	/**
	 * Creates a new instance of {@link AddXPAction}.
	 */
	public AddXPAction() {
		super();
	}
	@Override
	public void execute() throws RPGException {
		if (super.getMaster() != null
				&& super.getMaster().getIO() != null
				&& super.getMaster().getIO().hasIOFlag(IoGlobals.IO_01_PC)) {
			super.getMaster().getIO().getPCData().adjustXp(amount);
		}
	}
}
