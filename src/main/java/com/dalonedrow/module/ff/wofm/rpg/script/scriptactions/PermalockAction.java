package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;

/**
 * @author drau
 */
public final class PermalockAction extends FFScriptAction {
	/** the room where the exit is being created. */
	private final String doorId;
	/**
	 * Creates a new instance of {@link PermalockAction}.
	 * @param params the parameters
	 */
	public PermalockAction(final String params) {
		doorId = params;
	}
	@Override
	public void execute() throws RPGException {
		FFWoFMController.getInstance().getDoorIOByName(
				doorId).getScript().setLocalVariable("perma_locked", 1);
	}
}
