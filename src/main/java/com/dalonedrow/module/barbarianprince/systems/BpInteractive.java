package com.dalonedrow.module.barbarianprince.systems;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.constants.BpIo;
import com.dalonedrow.module.barbarianprince.rpg.BpCharacter;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.BpInventory;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.rpg.BpNpc;
import com.dalonedrow.module.barbarianprince.rpg.script.MasterScript;
import com.dalonedrow.module.barbarianprince.rpg.script.MountScript;
import com.dalonedrow.module.barbarianprince.rpg.script.NpcScript;
import com.dalonedrow.module.barbarianprince.rpg.script.PartyScript;
import com.dalonedrow.module.barbarianprince.rpg.script.PcScript;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class BpInteractive extends Interactive<BpInteractiveObject> {
	/** the next available id. */
	private int						nextId;
	/** the list of {@link BpInteractiveObject}s. */
	private BpInteractiveObject[]	objs;
	/** the player's reference id. */
	private int						playerIO;
	/**
	 * Creates a new instance of {@link BpInteractive}.
	 */
	BpInteractive() {
		objs = new BpInteractiveObject[0];
		playerIO = -1;
		super.setInstance(this);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAnimation(final int id, final int animId)
			throws RPGException {
		BpInteractiveObject io = getIO(id);
		if (io == null) {
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"ERROR! Interactive.addAnimation() - id '"
							+ id + "' does not exist");
		}
		// TODO Auto-generated method stub
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BpInteractiveObject getIO(final int id) throws RPGException {
		BpInteractiveObject io = null;
		if (hasIO(id)) {
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null
						&& objs[i].getRefId() == id) {
					io = objs[i];
					break;
				}
			}
		} else {
			throw new RPGException(
					ErrorMessage.INTERNAL_BAD_ARGUMENT, "IO does not exist");
		}
		return io;
	}
	/**
	 * Gets the master script object.
	 * @return {@link BpInteractiveObject}
	 */
	public BpInteractiveObject getMasterScript() {
		BpInteractiveObject io = getNewIO();
		io.setScript(new MasterScript());
		io.addIOFlag(BpIo.IO_16_IMMORTAL);
		return io;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BpInteractiveObject getNewIO() {
		// step 1 - find the next id
		int id = nextId++;
		BpInteractiveObject io = new BpInteractiveObject(id);
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
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberIOs() {
		int num = 0;
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null) {
				num++;
			}
		}
		return num;
	}
	/**
	 * Gets the player's reference id.
	 * @return {@link int}
	 * @throws RPGException should never happen
	 */
	public int getPlayerIO() throws RPGException {
		if (playerIO < 0) {
			newPlayer();
		}
		return playerIO;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasIO(final int id) {
		boolean has = false;
		for (int i = 0; i < objs.length; i++) {
			if (objs[i] != null
					&& id == objs[i].getRefId()) {
				has = true;
				break;
			}
		}
		return has;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSameObject(final BpInteractiveObject io0,
			final BpInteractiveObject io1) {
		if (io0 == null
				|| io1 == null
				|| io0.hasIOFlag(IoGlobals.IO_13_UNIQUE)
		// || (io0.getd->durability != ioo->durability)
		// || (io->max_durability != ioo->max_durability))
		) {
			return false;
		}

		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Creates a new horse.
	 * @throws PooledException should never happen
	 * @return {@link BpInteractiveObject}
	 */
	public BpInteractiveObject newHorse() throws PooledException {
		BpInteractiveObject io = getNewIO();
		io.addIOFlag(IoGlobals.IO_04_HORSE);
		io.setScript(new MountScript());
		return io;
	}
	/**
	 * Creates a new mob.
	 * @param id the mob's data reference id
	 * @return {@link BpInteractiveObject}
	 * @throws RPGException if an error occurs
	 */
	public BpInteractiveObject newMob(final int id) throws RPGException {
		BpInteractiveObject io = getNewIO();
		io.setScript(new NpcScript());
		io.setNPCData(new BpNpc());
		Script.getInstance().sendIOScriptEvent(
				io,
				ScriptConsts.SM_001_INIT,
				WebServiceClient.getInstance().getCharacter(id),
				"init");
		io.addIOFlag(IoGlobals.IO_03_NPC);
		return io;
	}
	/**
	 * Creates a new player.
	 * @throws RPGException should never happen
	 */
	public void newPlayer() throws RPGException {
		BpInteractiveObject io = getNewIO();
		io.setScript(new PcScript());
		io.setPCData(new BpCharacter());
		Script.getInstance().sendIOScriptEvent(
				io,
				ScriptConsts.SM_001_INIT,
				WebServiceClient.getInstance().getCharacter(0),
				"init");
		io.addIOFlag(IoGlobals.IO_01_PC);
		playerIO = io.getRefId();
		io = null;
	}
	/**
	 * {@inheritDoc}
	 */
	public BpInteractiveObject newPlayerParty() {
		// step 1 - find the next id
		int id = nextId++;
		BpIoGroup io = new BpIoGroup(id);
		io.addIOFlag(IoGlobals.IO_14_PARTY);
		io.setScript(new PartyScript());
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
	 * Creates a new winged mount.
	 * @throws PooledException should never happen
	 * @return {@link BpInteractiveObject}
	 */
	public BpInteractiveObject newWingedMount() throws PooledException {
		BpInteractiveObject io = getNewIO();
		io.addIOFlag(IoGlobals.IO_15_WINGED_MOUNT);
		io.setScript(new MountScript());
		return io;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void releaseIO(final BpInteractiveObject io) {
		if (io != null
				&& !io.hasIOFlag(BpIo.IO_16_IMMORTAL)) {
			if (io.getInventory() != null) {
				BpInventory inventory = io.getInventory();
				if (inventory != null) {
					for (int j = 0; j < inventory.getNumInventorySlots(); j++) {
						if (io.equals(inventory.getSlot(j).getIo())) {
							inventory.getSlot(j).setIo(null);
							inventory.getSlot(j).setShow(true);
						}
					}
				}
			}
			// release script timers and spells
			// release from groups
			//
			Script.getInstance().timerClearForIO(io);
			// MagicRealmSpells.getInstance().removeAllSpellsOn(io);
			Script.getInstance().releaseScript(io.getScript());
			Script.getInstance().releaseScript(io.getOverscript());
			Script.getInstance().releaseAllGroups(io);
			int id = io.getRefId();
			int index = -1;
			for (int i = 0; i < objs.length; i++) {
				if (objs[i] != null
						&& id == objs[i].getRefId()) {
					index = i;
					break;
				}
			}
			if (index > -1) {
				objs[index] = null;
			}
		}
	}
}
