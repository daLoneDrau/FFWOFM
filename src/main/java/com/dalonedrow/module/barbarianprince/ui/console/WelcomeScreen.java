package com.dalonedrow.module.barbarianprince.ui.console;

import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.GameState;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.rpg.base.consoleui.ConsoleView;
import com.dalonedrow.rpg.base.consoleui.InputProcessor;
import com.dalonedrow.rpg.base.consoleui.OutputEvent;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class WelcomeScreen extends ConsoleView {
	/** the one and only instance of the <code>WelcomeScreen</code> class. */
	private static WelcomeScreen instance;
	/**
	 * Gives access to the singleton instance of {@link WelcomeScreen}.
	 * @return {@link WelcomeScreen}
	 */
	public static WelcomeScreen getInstance() {
		if (WelcomeScreen.instance == null) {
			WelcomeScreen.instance = new WelcomeScreen();
		}
		return WelcomeScreen.instance;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render() throws RPGException {
		OutputEvent.getInstance().print(
				TextProcessor.getInstance().wrapText(
						WebServiceClient.getInstance().loadText("welcome_text"),
						0,
						false,
						false),
				this);
		try {
			InputProcessor.getInstance().setInputAction(
					ProjectConstants.getInstance(), // object
					BpController.class.getMethod(
							"goToState",
							new Class[] { int.class }),
					new Integer[] { GameState.STATE_01_IN_PLAY });
		} catch (NoSuchMethodException | SecurityException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
}
