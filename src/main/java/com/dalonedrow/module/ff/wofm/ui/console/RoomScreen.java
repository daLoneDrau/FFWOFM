package com.dalonedrow.module.ff.wofm.ui.console;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFRoomData;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMActionHelper;
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
import com.dalonedrow.utils.Watchable;
import com.dalonedrow.utils.Watcher;

/**
 * @author drau
 */
public final class RoomScreen extends ConsoleView implements Watcher {
	/** the one and only instance of the <code>WelcomeScreen</code> class. */
	private static RoomScreen	instance;
	/** display 10 lines at most to describe a room. */
	private static final int	MAX_ROOM_LINES	= 10;
	/**
	 * Gives access to the singleton instance of {@link RoomScreen}.
	 * @return {@link RoomScreen}
	 */
	public static RoomScreen getInstance() {
		if (RoomScreen.instance == null) {
			RoomScreen.instance = new RoomScreen();
		}
		return RoomScreen.instance;
	}
	/** the panel displaying the error messages. */
	private final Panel	errorPanel;
	/** flag indicating the game needs to go to combat. */
	private boolean		goToCombat;
	private int			lastStartingIndex;
	/** the messages displayed in the message panel. */
	private String		message;
	/** the current index. */
	private int			nextSection;
	/** the panel displaying the actions. */
	private final Panel	optionsPanel;
	/** the room's IO reference id. */
	private int			roomIoId;
	/** the width of the stats table, with borders. */
	private final int	STATS_TABLE_WIDTH;
	/** the panel displaying the stats. */
	private final Panel	statsPanel;
	/** the room text. */
	private String[]	text;
	/** the panel displaying the room view. */
	private final Panel	viewPanel;
	/** Hidden constructor. */
	private RoomScreen() {
		roomIoId = -1;
		viewPanel = new Panel(100, false, MAX_ROOM_LINES);
		STATS_TABLE_WIDTH = "Stamina: 99/99".length() + 4;
		statsPanel = new Panel(STATS_TABLE_WIDTH, true, 5, "", "STATUS");
		errorPanel = new Panel(100, true, 6);
		optionsPanel =
				new Panel(100 - STATS_TABLE_WIDTH + 1, true, 6, "", "ACTIONS");
		message = "";
	}
	/**
	 * Processes user input to go to the next screen.
	 * @param s not used
	 * @throws Exception if an error occurs
	 */
	public void actionProcessInput(final String s) {
		if (nextSection < text.length - 1) {

		} else {
			try {
				FFWoFMActionHelper.getInstance().processInput(s);
			} catch (RPGException | PooledException e) {
				JOGLErrorHandler.getInstance().fatalError(e);
			}
		}
	}
	@Override
	public void addErrorMessage(final String msg) {
		try {
			PooledStringBuilder sb1 =
					StringBuilderPool.getInstance().getStringBuilder();
			sb1.append(message);
			sb1.append(TextProcessor.getInstance().wrapText(
					msg, 96, false, true));
			message = sb1.toString();
			sb1.returnToPool();
			sb1 = null;
		} catch (PooledException | RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	@Override
	public void addMessage(final String msg) {
		try {
			PooledStringBuilder sb1 =
					StringBuilderPool.getInstance().getStringBuilder();
			sb1.append(message);
			sb1.append(TextProcessor.getInstance().wrapText(msg, 96));
			message = sb1.toString();
			sb1.returnToPool();
			sb1 = null;
		} catch (PooledException | RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getErrorMessage() {
		return message;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.consoleui.ConsoleView#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
	/**
	 * Renders the top half of the screen, which is the room description.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void processErrorView() throws PooledException, RPGException {
		errorPanel.setContent("\n ");
		if (message.length() > 0) {
			errorPanel.setContent(message);
			message = "";
		}
	}
	/**
	 * Renders the top half of the screen, which is the room description.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void processOptionsView() throws PooledException, RPGException {
		if (goToCombat
				|| nextSection < text.length - 1) {
			optionsPanel.setContent(TextProcessor.getInstance().getCenteredText(
					WebServiceClient.getInstance().getTextByName("continue"),
					97 - STATS_TABLE_WIDTH));
		} else {
			String[] actions =
					FFWoFMActionHelper.getInstance().getPossibleActions(
							((FFInteractiveObject) Interactive.getInstance()
									.getIO(
											roomIoId)).getRoomData());
			String t = TextProcessor.getInstance().getSelectionsAsColumns(
					Math.min(actions.length, 4), actions, "  ");
			// FIX MENU ITEMS WIDTH
			String[] split = t.split("\n");
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append(split[0]);
			int i = 97 - STATS_TABLE_WIDTH - split[0].length();
			for (; i > 0; i--) {
				sb.append(' ');
			}
			i = 1;
			for (int len = split.length; i < len; i++) {
				sb.append("\n");
				sb.append(split[i]);
			}
			optionsPanel.setContent(sb.toString());
			sb.returnToPool();
			sb = null;
			actions = null;
			t = null;
		}
	}
	/**
	 * Renders the top half of the screen, which is the room description.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void processPartyView() {
		try {
			// show up to 10 lines
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			int numLines = 0;
			if (nextSection < text.length) {
				lastStartingIndex = nextSection;
			} else {
				nextSection = lastStartingIndex;
			}
			do {
				String[] split = text[nextSection].split("\n");
				numLines += split.length;
				if (numLines < MAX_ROOM_LINES) {
					for (int i = 0, len = split.length; i < len; i++) {
						sb.append(split[i]);
						sb.append('\n');
					}
					nextSection++;
				}
			} while (numLines < MAX_ROOM_LINES && nextSection < text.length);
			viewPanel.setContent(sb.toString());
			sb.returnToPool();
			sb = null;
		} catch (SecurityException | PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	/**
	 * Renders the top half of the screen, which is the room description.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void processStatsView() throws PooledException, RPGException {
		statsPanel.setContent(
				FFWoFMController.getInstance().getPartyStatusTable());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render() throws RPGException {
		try {
			processPartyView();
			processOptionsView();
			processStatsView();
			processErrorView();
			optionsPanel.join(statsPanel, Panel.LEFT, Panel.TOP);
			viewPanel.join(optionsPanel, Panel.TOP, Panel.CENTER);
			viewPanel.join(errorPanel, Panel.BOTTOM, Panel.CENTER);
			OutputEvent.getInstance().print(viewPanel.getDisplayText(), this);
			if (nextSection >= text.length - 1) {
				OutputEvent.getInstance().print(
						WebServiceClient.getInstance().getTextByName("choice"),
						this);
			}
			if (goToCombat) {
				// after next input, go straight to combat screen
				goToCombat = false;
				InputProcessor.getInstance().setInputAction(
						ProjectConstants.getInstance(), // object
						FFWoFMController.class.getMethod(
								"goToState",
								new Class[] { int.class }),
						new Integer[] { GameState.STATE_02_IN_COMBAT });
			} else {
				InputProcessor.getInstance().setInputAction(
						this, // object
						getClass().getMethod("actionProcessInput",
								new Class[] { String.class }), // method
						null); // arguments to be read from system.in
			}
		} catch (NoSuchMethodException | PooledException
				| SecurityException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	private void resetText() throws RPGException {
		nextSection = 0;
		lastStartingIndex = 0;
		FFRoomData roomData =
				((FFInteractiveObject) Interactive.getInstance().getIO(
						roomIoId)).getRoomData();
		String roomTextId = roomData.getTextid();
		roomData.addWatcher(this);
		text = WebServiceClient.getInstance().getTextByName(
				roomTextId).split("\r\n");
		for (int i = text.length - 1; i >= 0; i--) {
			text[i] = TextProcessor.getInstance().wrapText(
					text[i], 0, false, false);
		}
	}
	/**
	 * Sets the flag indicating the game needs to go to combat.
	 * @param flag the flag value
	 */
	public void setGoToCombat(final boolean flag) {
		goToCombat = flag;
	}
	/**
	 * Sets the room being displayed.
	 * @param room the room id
	 */
	public void setRoom(final int room) {
		try {
			if (roomIoId > -1) {
				FFRoomData roomData =
						((FFInteractiveObject) Interactive.getInstance().getIO(
								roomIoId)).getRoomData();
				roomData.removeWatcher(this);
				roomData = null;
			}
			roomIoId = room;
			FFRoomData roomData =
					((FFInteractiveObject) Interactive.getInstance().getIO(
							roomIoId)).getRoomData();
			roomData.addWatcher(this);
			roomData = null;
			resetText();
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	@Override
	public void watchUpdated(final Watchable data) {
		try {
			FFRoomData roomData = (FFRoomData) data;
			String roomTextId = roomData.getTextid();
			String[] newText = WebServiceClient.getInstance().getTextByName(
					roomTextId).split("\r\n");
			for (int i = newText.length - 1; i >= 0; i--) {
				newText[i] = TextProcessor.getInstance().wrapText(
						newText[i], 0, false, false);
			}
			// compare new text and old text
			boolean needsUpdate = false;
			if (newText.length == text.length) {
				for (int i = text.length - 1; i >= 0; i--) {
					if (!newText[i].equalsIgnoreCase(text[i])) {
						needsUpdate = true;
						break;
					}
				}
			} else {
				needsUpdate = true;
			}
			if (needsUpdate) {
				resetText();
			}
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
}
