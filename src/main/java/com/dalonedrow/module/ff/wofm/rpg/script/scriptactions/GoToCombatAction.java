package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import java.util.List;

import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.module.ff.systems.wofm.GameState;
import com.dalonedrow.module.ff.wofm.ui.console.CombatScreen;
import com.dalonedrow.module.ff.wofm.ui.console.RoomScreen;
import com.dalonedrow.rpg.base.consoleui.InputProcessor;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * 
 * @author drau
 *
 */
public class GoToCombatAction extends FFScriptAction {
	/** the list of mobs attacking. */
	private final List<String>	mobs;
	/** the text to display when combat begins. */
	private final String		textId;
	/**
	 * Creates a new instance of {@link GoToCombatAction}.
	 * @param list the list of mobs attacking
	 * @param id the text to display
	 */
	public GoToCombatAction(final List<String> list, final String id) {
		super();
		mobs = list;
		textId = id;
	}
	@Override
	public void execute() throws RPGException {
		RoomScreen.getInstance().setGoToCombat(true);
		CombatScreen.getInstance().reset();
		for (int i = 0, len = mobs.size(); i < len; i++) {
			CombatScreen.getInstance().addMob(
					((FFWebServiceClient) WebServiceClient.getInstance())
							.getNPCByName(
									mobs.get(i)));
		}
		if (textId != null) {
			CombatScreen.getInstance().addMessage(
					((FFWebServiceClient) WebServiceClient.getInstance())
							.getTextByName(
									textId));
		}
		try {
			InputProcessor.getInstance().setInputAction(
					ProjectConstants.getInstance(), // object
					FFWoFMController.class.getMethod(
							"goToState",
							new Class[] { int.class }),
					new Integer[] { GameState.STATE_02_IN_COMBAT });
		} catch (NoSuchMethodException | SecurityException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
}
