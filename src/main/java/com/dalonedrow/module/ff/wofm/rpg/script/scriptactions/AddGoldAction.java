package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class AddGoldAction extends FFScriptAction {
	private int amount;
	/**
	 * Creates a new instance of {@link AddGoldAction}.
	 */
	public AddGoldAction() {
		super();
	}
	@Override
	public void execute() throws RPGException {
		if (super.getMaster() != null
				&& super.getMaster().getIO() != null
				&& super.getMaster().getIO().hasIOFlag(IoGlobals.IO_01_PC)) {
			super.getMaster().getIO().getPCData().adjustGold(amount);
		}
	}
}
