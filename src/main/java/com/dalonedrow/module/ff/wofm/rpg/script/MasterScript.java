/**
 *
 */
package com.dalonedrow.module.ff.wofm.rpg.script;

import java.util.Arrays;

import com.dalonedrow.engine.sprite.base.SimplePoint;
import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author drau
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class MasterScript extends FFScriptable {
	/**
	 * Creates a new instance of {@link MasterScript}.
	 */
	public MasterScript() {
		super(null);
	}
	/* (non-Javadoc)
	 * @see com.dalonedrow.module.ff.rpg.FFScriptable#onEnterDungeon()
	 */
	@Override
	public int onEnterDungeon() {
		try {
			FFWoFMController.getInstance().setCurrentRoom(1);
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		return super.onEnterDungeon();
	}
	
}
