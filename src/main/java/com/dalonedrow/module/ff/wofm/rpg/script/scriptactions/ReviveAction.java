package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class ReviveAction extends FFScriptAction {
	private boolean reposition;
	/**
	 * Creates a new instance of {@link ReviveAction}.
	 */
	public ReviveAction() {
		super();
	}
	@Override
	public void execute() throws RPGException {
		if (super.getMaster() != null
				&& super.getMaster().getIO() != null
				&& super.getMaster().getIO().hasIOFlag(IoGlobals.IO_03_NPC)) {
			super.getMaster().getIO().getNPCData().ARX_NPC_Revive(reposition);
		}
	}
}
