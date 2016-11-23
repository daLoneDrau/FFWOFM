package com.dalonedrow.module.ff.systems.wofm;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.engine.systems.base.Time;
import com.dalonedrow.module.ff.constants.FFIo;
import com.dalonedrow.module.ff.constants.FFScriptConsts;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFIoGroup;
import com.dalonedrow.module.ff.wofm.ui.console.RoomScreen;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * The main class for running the game.
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class FFWoFMController
		extends ProjectConstants<FFInteractiveObject> {
	/** the singleton instance. */
	private static FFWoFMController	instance;
	/** the maximum number of equipment slots. */
	public static final int			MAX_EQUIPPED	= 8;
	/** the maximum number of equipment slots. */
	public static final int			MAX_SPELLS		= 1;
	/**
	 * Gets the one and only instance of {@link FFWoFMController}.
	 * @return {@link FFWoFMController}
	 * @throws RPGException if an error occurs
	 */
	public static FFWoFMController getInstance() throws RPGException {
		if (FFWoFMController.instance == null) {
			FFWoFMController.instance = new FFWoFMController();
		}
		return FFWoFMController.instance;
	}
	/** the current room the hero is in. */
	private int		currentRoom;
	private boolean	gameOver;
	/** the player's party. */
	private int		party	= -1;
	/**
	 * Hidden constructor.
	 * @throws RPGException if an error occurs
	 */
	private FFWoFMController() throws RPGException {
		super.setInstance(this);
		new FFInteractive();
		new FFScript();
		new FFWebServiceClient();
		new FFSpell();
		Time.getInstance().init();
		((FFWebServiceClient) WebServiceClient.getInstance()).loadAllRooms();
	}
	/**
	 * Gets the current room the hero is in.
	 * @return {@link int}
	 */
	public int getCurrentRoom() {
		return currentRoom;
	}
	/**
	 * Gets a door IO by its name.
	 * @param name the door's name
	 * @return {@link FFInteractiveObject}
	 * @throws RPGException if an error occurs
	 */
	public FFInteractiveObject getDoorIOByName(final String name)
			throws RPGException {
		FFInteractiveObject io = null;
		for (int i =
				Interactive.getInstance().getNumberIOs() - 1; i >= 0; i--) {
			FFInteractiveObject door =
					(FFInteractiveObject) Interactive.getInstance().getIO(i);
			if (door.hasIOFlag(FFIo.IO_18_DOOR)
					&& door.getDoorData().getName().equalsIgnoreCase(name)) {
				io = door;
				break;
			}
		}
		return io;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxEquipped() {
		return MAX_EQUIPPED;
	}
	@Override
	public int getMaxSpells() {
		return MAX_SPELLS;
	}
	/**
	 * Gets the markup table for a NPC's status.
	 * @return {@link String}
	 * @throws RPGException if an error occurs
	 * @throws PooledException if an error occurs
	 */
	public String getNPCStatusMarkup(final FFInteractiveObject io)
			throws RPGException, PooledException {
		io.getNPCData().doComputeFullStats();
		String sk = "", st = "";
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		System.out.println(new String(io.getNPCData().getName()));
		System.out.println(io.getNPCData().getBaseAttributeScore("SK"));
		System.out.println(io.getNPCData().getFullAttributeScore("SK"));
		sb.append((int) io.getNPCData().getFullAttributeScore("SK"));
		sb.append('/');
		sb.append((int) io.getNPCData().getFullAttributeScore("MSK"));
		sk = sb.toString();
		sb.setLength(0);
		sb.append((int) io.getNPCData().getFullAttributeScore("ST"));
		sb.append('/');
		sb.append((int) io.getNPCData().getFullAttributeScore("MST"));
		st = sb.toString();
		sb.returnToPool();
		sb = null;
		return TextProcessor.getInstance().processText(null,
				null,
				new String[] { sk, st },
				((FFWebServiceClient) WebServiceClient.getInstance())
						.getTextByName(
								"npc_stats_table"));
	}
	/**
	 * Gets the markup table for the party's status.
	 * @return {@link String}
	 * @throws RPGException if an error occurs
	 * @throws PooledException if an error occurs
	 */
	public String getPartyStatusTable() throws RPGException, PooledException {
		FFInteractiveObject io =
				(FFInteractiveObject) Interactive.getInstance().getIO(
						getPlayer());
		io.getPCData().doComputeFullStats();
		String sk = "", st = "", lk = "";
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append((int) io.getPCData().getFullAttributeScore("SK"));
		sb.append('/');
		sb.append((int) io.getPCData().getFullAttributeScore("MSK"));
		sk = sb.toString();
		sb.setLength(0);
		sb.append((int) io.getPCData().getFullAttributeScore("ST"));
		sb.append('/');
		sb.append((int) io.getPCData().getFullAttributeScore("MST"));
		st = sb.toString();
		sb.setLength(0);
		sb.append((int) io.getPCData().getFullAttributeScore("LK"));
		sb.append('/');
		sb.append((int) io.getPCData().getFullAttributeScore("MLK"));
		lk = sb.toString();
		sb.returnToPool();
		sb = null;
		return TextProcessor.getInstance().processText(null,
				null,
				new String[] { sk, st, lk },
				((FFWebServiceClient) WebServiceClient.getInstance())
						.getTextByName(
								"stats_table"));
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPlayer() throws RPGException {
		return ((FFIoGroup) Interactive.getInstance().getIO(party))
				.getMember(0);

	}
	/**
	 * Gets the player IO.
	 * @return {@link int}
	 * @throws RPGException if an error occurs
	 */
	public FFInteractiveObject getPlayerIO() throws RPGException {
		return (FFInteractiveObject) Interactive.getInstance().getIO(
				((FFIoGroup) Interactive.getInstance().getIO(party))
						.getMember(0));

	}
	/**
	 * Gets the reference id of the player's party.
	 * @return {@link int}
	 */
	public int getPlayerParty() {
		return party;
	}
	/**
	 * Gets a room's IO by its code.
	 * @param roomCode the room's code
	 * @return {@link FFInteractiveObject}
	 */
	public FFInteractiveObject getRoomIOByCode(final int roomCode) {
		FFInteractiveObject roomIO = null;
		int len = Interactive.getInstance().getNumberIOs();
		for (int i = len - 1; i >= 0; i--) {
			try {
				FFInteractiveObject io =
						(FFInteractiveObject) Interactive.getInstance()
								.getIO(i);
				if (io.getRoomData() != null
						&& io.getRoomData().getCode() == roomCode) {
					roomIO = io;
					break;
				}
			} catch (RPGException e) {
				JOGLErrorHandler.getInstance().fatalError(e);
			}
		}
		return roomIO;
	}
	public void goToState(final int state) {
		GameState.getInstance().setCurrentState(state);
	}
	/**
	 * Gets the gameOver.
	 * @return <code>boolean</code>
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	/**
	 * Sets the current room the hero is in.
	 * @param roomCode the new value to set
	 */
	public void setCurrentRoom(final int roomCode) {
		FFInteractiveObject io = getRoomIOByCode(roomCode);
		currentRoom = io.getRefId();
		RoomScreen.getInstance().setRoom(currentRoom);
	}
	/**
	 * Sets the value for the gameOver.
	 * @param val the value to set
	 */
	public void setGameOver(final boolean val) {
		gameOver = val;
	}
	/**
	 * Starts the game.
	 * @throws RPGException if an error occurs
	 */
	public void startGame() throws RPGException {
		FFIoGroup group =
				(FFIoGroup) ((FFInteractive) Interactive.getInstance())
						.newPlayerParty();
		((FFInteractive) Interactive.getInstance()).newPlayer();
		group.addMember(
				((FFInteractive) Interactive.getInstance()).getPlayerRefId());
		party = group.getRefId();
		group = null;
		Script.getInstance().eventStackClear();
		Script.getInstance().stackSendIOScriptEvent(
				((FFScript) Script.getInstance()).getMasterScript(),
				FFScriptConsts.SM_301_ENTER_DUNGEON, null, null);
	}
	@Override
	public void update() {
		/*
		 * if (isTurnActionResolved()) { // TODO did control go to an event
		 * screen? try { System.out.println("advancing timetrack"); turnAction =
		 * null; TimeTrack.getInstance().nextPhase(); } catch (RPGException e) {
		 * e.printStackTrace(); JOGLErrorHandler.getInstance().fatalError(e); }
		 * }
		 */
	}
}
