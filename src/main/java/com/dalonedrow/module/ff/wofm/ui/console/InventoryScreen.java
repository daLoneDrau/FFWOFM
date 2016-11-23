package com.dalonedrow.module.ff.wofm.ui.console;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFInventory;
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
public final class InventoryScreen extends ConsoleView implements Watcher {
	/** the one and only instance of the <code>WelcomeScreen</code> class. */
	private static InventoryScreen	instance;
	/** display 10 lines at most to describe a room. */
	private static final int	MAX_ROOM_LINES	= 10;
	/**
	 * Gives access to the singleton instance of {@link InventoryScreen}.
	 * @return {@link InventoryScreen}
	 */
	public static InventoryScreen getInstance() {
		if (InventoryScreen.instance == null) {
			InventoryScreen.instance = new InventoryScreen();
		}
		return InventoryScreen.instance;
	}
	/** the panel displaying the error messages. */
	private final Panel	errorPanel;
	/** the messages displayed in the message panel. */
	private String		message;
	/** the current index. */
	private int			nextSection;
	/** the panel displaying the equipped items. */
	private final Panel	equipPanel;
	/** the panel displaying the backpack. */
	private final Panel	slotsPanel;
	/** Hidden constructor. */
	private InventoryScreen() {
		slotsPanel = new Panel(100, true, 10, "", "BACKPACK");
		errorPanel = new Panel(100, true, 6);
		equipPanel = new Panel(100, false, 6);
		message = "";
	}
	/**
	 * Processes user input to go to the next screen.
	 * @param s not used
	 * @throws Exception if an error occurs
	 */
	public void actionProcessInput(final String s) {
		try {
			FFWoFMActionHelper.getInstance().processInput(s);
		} catch (RPGException | PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
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
			equipPanel.setContent(TextProcessor.getInstance().getCenteredText(
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
			equipPanel.setContent(sb.toString());
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
		// get all items in inventory
		FFInteractiveObject playerIO =
				FFWoFMController.getInstance().getPlayerIO();
		FFInventory inv = playerIO.getInventory();
		for (int i = 0, len = inv.getNumInventorySlots(); i < len; i++) {
			
		}
		inv.getNumInventorySlots()
		slotsPanel.setContent(
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
			equipPanel.join(slotsPanel, Panel.LEFT, Panel.TOP);
			viewPanel.join(equipPanel, Panel.TOP, Panel.CENTER);
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
