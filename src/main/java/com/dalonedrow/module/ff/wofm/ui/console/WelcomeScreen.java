package com.dalonedrow.module.ff.wofm.ui.console;

import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.module.ff.systems.wofm.GameState;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.consoleui.ConsoleView;
import com.dalonedrow.rpg.base.consoleui.InputProcessor;
import com.dalonedrow.rpg.base.consoleui.OutputEvent;
import com.dalonedrow.rpg.base.consoleui.Panel;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * @author drau
 */
public final class WelcomeScreen extends ConsoleView {
	/** the one and only instance of the <code>WelcomeScreen</code> class. */
	private static WelcomeScreen	instance;
	/** the maximum number of lines to show. */
	private static final int		MAX_LINES	= 13;
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
	/* (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.consoleui.ConsoleView#setMessage(java.lang.String)
	 */
	@Override
	public void addMessage(String text) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.consoleui.ConsoleView#getMessage()
	 */
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	/** the current index. */
	private int			index;
	/** the panel displayed. */
	private Panel		panel;
	/** the intro text. */
	private String[]	text;
	/** Hidden constructor. */
	private WelcomeScreen() {
		try {
			text = WebServiceClient.getInstance().getTextByName(
					"START").split("\r\n");
			for (int i = text.length - 1; i >= 0; i--) {
				text[i] = TextProcessor.getInstance().wrapText(
						text[i], 100);
			}
			panel = new Panel(100, false, 15);
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	/**
	 * Processes user input to go to the next screen.
	 * @param s not used
	 * @throws Exception if an error occurs
	 */
	public void actionProcessInput(final String s) {
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render() throws RPGException {
		try {
			// show up to 13 lines
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			int numLines = 0;
			do {
				String[] split = text[index].split("\n");
				numLines += split.length;
				if (numLines < MAX_LINES) {
					for (int i = 0, len = split.length; i < len; i++) {
						sb.append(split[i]);
						sb.append('\n');
					}
					index++;
				}
			} while (numLines < MAX_LINES && index < text.length);
			sb.append('\n');
			sb.append(TextProcessor.getInstance().getCenteredText(
					WebServiceClient.getInstance().getTextByName("continue"),
					100));
			panel.setContent(sb.toString());
			sb.returnToPool();
			sb = null;

			OutputEvent.getInstance().print(panel.getDisplayText(), this);
			InputProcessor.getInstance().setInputAction(
					this, // object
					getClass().getMethod("actionProcessInput",
							new Class[] { String.class }), // method
					null); // arguments to be read from system.in
			if (index >= text.length) {
				InputProcessor.getInstance().setInputAction(
						ProjectConstants.getInstance(), // object
						FFWoFMController.class.getMethod(
								"goToState",
								new Class[] { int.class }),
						new Integer[] { GameState.STATE_01_IN_PLAY });
			}
		} catch (NoSuchMethodException | SecurityException
				| PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	/* (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.consoleui.ConsoleView#getErrorMessage(java.lang.String)
	 */
	@Override
	public String getErrorMessage() {
		return null;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addErrorMessage(final String msg) {
		// TODO Auto-generated method stub
		
	}
}
