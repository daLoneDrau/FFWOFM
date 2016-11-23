package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class DmgPlayerAction extends FFScriptAction {
	/** the attribute being damaged. */
	private final String	attribute;
	/** the amount to damage the player. */
	private final int		dmgAmount;
	/**
	 * Creates a new instance of {@link DmgPlayerAction}.
	 * @param attr the attribute being damaged
	 * @param amt the amount to damage the player
	 */
	public DmgPlayerAction(final String attr, final int amt) {
		attribute = attr;
		dmgAmount = amt;
	}
	@Override
	public void execute() throws RPGException {
		FFInteractiveObject playerIO =
				(FFInteractiveObject) Interactive.getInstance().getIO(
						FFWoFMController.getInstance().getPlayer());
		Script.getInstance().stackSendIOScriptEvent(
				playerIO, // the IO
				ScriptConsts.SM_016_HIT, // the message
				new Object[] { "hit_attribute", attribute,
						"hit_amount", dmgAmount }, // params
				"hit"); // event name
		playerIO = null;
	}
}
