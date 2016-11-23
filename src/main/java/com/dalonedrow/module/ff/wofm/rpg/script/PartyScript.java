/**
 *
 */
package com.dalonedrow.module.ff.wofm.rpg.script;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFIoGroup;
import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptConstants;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.graph.WeightedGraphEdge;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class PartyScript extends FFScriptable {
	/**
	 * Creates a new instance of {@link PartyScript}.
	 * @param io the IO associated with this script
	 */
	public PartyScript(final FFInteractiveObject io) {
		super(io);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onMovement() throws RPGException {
		FFIoGroup io = (FFIoGroup) super.getIO();
		return ScriptConstants.ACCEPT;
	}
}
