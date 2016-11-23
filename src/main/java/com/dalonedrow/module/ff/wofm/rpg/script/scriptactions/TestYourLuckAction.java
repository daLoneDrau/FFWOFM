package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;

/**
 * @author drau
 */
@SuppressWarnings("rawtypes")
public final class TestYourLuckAction extends FFScriptAction {
	/** the list of scripts executed when the action fails. */
	private final ScriptAction[]	failScripts;
	/** the list of scripts executed when the action passes. */
	private final ScriptAction[]	passScripts;
	public TestYourLuckAction(final ScriptAction[] passScripts,
			final ScriptAction[] failScripts) {
		super();
		this.passScripts = passScripts;
		this.failScripts = failScripts;
	}
	@Override
	public void execute() throws RPGException {
		FFInteractiveObject playerIO =
				(FFInteractiveObject) Interactive.getInstance().getIO(
						FFWoFMController.getInstance().getPlayer());
		if (playerIO.getPCData().testYourLuck(false)) {
			for (int i = 0, len = passScripts.length; i < len; i++) {
				passScripts[i].execute();
			}
		} else {
			for (int i = 0, len = failScripts.length; i < len; i++) {
				failScripts[i].execute();
			}
		}
		
	}
}
