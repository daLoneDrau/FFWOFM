package com.dalonedrow.module.ff.systems.wofm;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.ff.constants.FFIo;
import com.dalonedrow.module.ff.rpg.FFCharacter;
import com.dalonedrow.module.ff.rpg.FFDoorData;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFInventory;
import com.dalonedrow.module.ff.rpg.FFIoGroup;
import com.dalonedrow.module.ff.rpg.FFItem;
import com.dalonedrow.module.ff.rpg.FFNpc;
import com.dalonedrow.module.ff.rpg.FFRoomData;
import com.dalonedrow.module.ff.wofm.rpg.script.DoorScript;
import com.dalonedrow.module.ff.wofm.rpg.script.ItemScript;
import com.dalonedrow.module.ff.wofm.rpg.script.MasterScript;
import com.dalonedrow.module.ff.wofm.rpg.script.NpcScript;
import com.dalonedrow.module.ff.wofm.rpg.script.PartyScript;
import com.dalonedrow.module.ff.wofm.rpg.script.PcScript;
import com.dalonedrow.module.ff.wofm.rpg.script.RoomScript;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.InventoryData;
import com.dalonedrow.rpg.base.flyweights.InventorySlot;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.Spell;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.base.systems.SpellController;
import com.dalonedrow.rpg.base.systems.WebServiceClient;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class FFInteractive extends Interactive<FFInteractiveObject> {
	@Override
	public FFInteractiveObject addItem(String item, long flags) throws RPGException {		
		FFInteractiveObject io = ((FFWebServiceClient)
				WebServiceClient.getInstance()).getItemByName(item);
		if (io != null) {
			// add additional flags, such as GOLD or MOVABLE
			if ((flags & IoGlobals.NO_ON_LOAD) != IoGlobals.NO_ON_LOAD) {
				Script.getInstance().sendIOScriptEvent(io, ScriptConsts.SM_41_LOAD, null, "");
			}
			// TODO - remove spellcasting data
			// io->spellcast_data.castingspell = -1;
			// TODO -set texture and position
		}
		return io;
	}
	/** the next available id. */
	private int						nextId;
	/** the list of {@link FFInteractiveObject}s. */
	private FFInteractiveObject[]	objs;
	/** the player's reference id. */
	private int						playerRefId;
	/**
	 * Creates a new instance of {@link FFInteractive}.
	 */
	FFInteractive() {
		objs = new FFInteractiveObject[0];
		playerRefId = -1;
		super.setInstance(this);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAnimation(final int id, final int animId)
			throws RPGException {
		FFInteractiveObject io = getIO(id);
		if (io == null) {
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"ERROR! Interactive.addAnimation() - id '"
							+ id + "' does not exist");
		}
		// TODO Auto-generated method stub
	}
	@Override
	public void ARX_INTERACTIVE_ForceIOLeaveZone(final FFInteractiveObject io,
			final long flags) {
	}
	/**
	 * Gets the master script object.
	 * @return {@link FFInteractiveObject}
	 */
	public FFInteractiveObject getMasterScript() {
		FFInteractiveObject io = getNewIO();
		io.setScript(new MasterScript());
		io.addIOFlag(FFIo.IO_16_IMMORTAL);
		return io;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxIORefId() {
		return nextId;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FFInteractiveObject getNewIO() {
		// step 1 - find the next id
		int id = nextId++;
		FFInteractiveObject io = null;
		try {
			io = new FFInteractiveObject(id);
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		// step 2 - find the next available index in the objs array
		int index = -1;
		for (int i = objs.length - 1; i >= 0; i--) {
			if (objs[i] == null) {
				index = i;
				break;
			}
		}
		// step 3 - put the new object into the arrays
		if (index < 0) {
			objs = ArrayUtilities.getInstance().extendArray(io, objs);
		} else {
			objs[index] = io;
		}
		return io;
	}
	/**
	 * Gets the player's reference id.
	 * @return {@link int}
	 * @throws RPGException should never happen
	 */
	public int getPlayerRefId() throws RPGException {
		if (playerRefId < 0) {
			newPlayer();
		}
		return playerRefId;
	}
	/**
	 * Creates a new door.
	 * @param doorData the door's data
	 * @return {@link FFInteractiveObject}
	 * @throws RPGException if an error occurs
	 */
	public FFInteractiveObject newDoor(final FFDoorData doorData) {
		FFInteractiveObject io = getNewIO();
		io.setScript(new DoorScript(io));
		io.setDoorData(doorData);
		io.addIOFlag(FFIo.IO_18_DOOR);
		return io;
	}
	/**
	 * Creates a new item IO.
	 * @param item the item data for the IO
	 * @return {@link FFInteractiveObject}
	 */
	public FFInteractiveObject newItem(final FFItem item) {
		FFInteractiveObject io = getNewIO();
		io.setScript(new ItemScript(io));
		io.setItemData(item);
		io.addIOFlag(IoGlobals.IO_02_ITEM);
		return io;
	}
	/**
	 * Creates a new NPC.
	 * @param npcData the NPC's data
	 * @return {@link FFInteractiveObject}
	 * @throws RPGException if an error occurs
	 */
	public FFInteractiveObject newMob(final FFNpc npcData) {
		FFInteractiveObject io = getNewIO();
		io.setScript(new NpcScript(io));
		io.setNPCData(npcData);
		io.addIOFlag(IoGlobals.IO_03_NPC);
		return io;
	}
	/**
	 * Creates a new mob.
	 * @param id the mob's data reference id
	 * @return {@link FFInteractiveObject}
	 * @throws RPGException if an error occurs
	 */
	public FFInteractiveObject newMob(final int id) throws RPGException {
		FFInteractiveObject io = getNewIO();
		io.setScript(new NpcScript(io));
		io.setNPCData(new FFNpc());
		/*
		 * Script.getInstance().sendIOScriptEvent( io, ScriptConsts.SM_001_INIT,
		 * WebServiceClient.getInstance().getCharacter(id), "init");
		 */
		io.addIOFlag(IoGlobals.IO_03_NPC);
		return io;
	}
	/**
	 * Creates a new player.
	 * @throws RPGException should never happen
	 */
	public void newPlayer() throws RPGException {
		FFInteractiveObject io = getNewIO();
		io.setScript(new PcScript(io));
		io.setPCData(new FFCharacter());
		Script.getInstance().sendIOScriptEvent(
				io,
				ScriptConsts.SM_001_INIT,
				null,
				"init");
		io.addIOFlag(IoGlobals.IO_01_PC);
		playerRefId = io.getRefId();
		io = null;
	}
	/**
	 * {@inheritDoc}
	 */
	public FFInteractiveObject newPlayerParty() {
		// step 1 - find the next id
		int id = nextId++;
		FFIoGroup io = new FFIoGroup(id);
		io.addIOFlag(IoGlobals.IO_14_PARTY);
		io.setScript(new PartyScript(io));
		// step 2 - find the next available index in the objs array
		int index = -1;
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] == null) {
				index = i;
				break;
			}
		}
		// step 3 - put the new object into the arrays
		if (index < 0) {
			objs = ArrayUtilities.getInstance().extendArray(io, objs);
		} else {
			objs[index] = io;
		}
		return io;
	}
	/**
	 * Creates a new room.
	 * @param roomData the room's data
	 * @return {@link FFInteractiveObject}
	 * @throws RPGException if an error occurs
	 */
	public FFInteractiveObject newRoom(final FFRoomData roomData)
			throws RPGException {
		FFInteractiveObject io = getNewIO();
		io.setScript(new RoomScript(io));
		io.setRoomData(roomData);
		io.addIOFlag(FFIo.IO_17_ROOM);
		return io;
	}
	@Override
	protected FFInteractiveObject[] getIOs() {
		return objs;
	}
}
