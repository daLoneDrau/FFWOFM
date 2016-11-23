package com.dalonedrow.module.barbarianprince.rpg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.BpInteractive;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

@SuppressWarnings("unchecked")
public class IOGroupTest {
	@Before
	public void before() throws RPGException {
		BpController.getInstance();
	}
	@Test
	public void canCreate() throws RPGException {
		BpIoGroup group = new BpIoGroup(0);
		assertNotNull(group);
	}
	@Test (expected = RPGException.class)
	public void willNotAddInvalid() throws RPGException, PooledException {
		BpIoGroup group = new BpIoGroup(0);
		group.addMember(-1);
	}
	@Test (expected = RPGException.class)
	public void willNotAddItself() throws RPGException, PooledException {
		BpIoGroup group = (BpIoGroup) 
				((BpInteractive) BpInteractive.getInstance()).newPlayerParty();
		group.addMember(group.getRefId());
	}
	@Test
	public void canChangeLocation() throws RPGException, PooledException {
		BpIoGroup group = (BpIoGroup) 
				((BpInteractive) BpInteractive.getInstance()).newPlayerParty();
		assertEquals(group.getLocation(), -1);
		group.setLocation(4);
		assertEquals(group.getLocation(), 4);
	}
	@Test
	public void canGetName() throws RPGException, PooledException {
		BpIoGroup group = (BpIoGroup) 
				((BpInteractive) BpInteractive.getInstance()).newPlayerParty();
		assertEquals(group.getName(), "");
		((BpInteractive) BpInteractive.getInstance()).newPlayer();
		int pio = ((BpInteractive) Interactive.getInstance()).getPlayerIO();
		pio = ((BpInteractive)
				BpInteractive.getInstance()).getPlayerIO();
		group.addMember(pio);
		assertTrue(group.hasMember(pio));
		assertEquals(group.getName(), "Cal Arath's Army");
	}
	@Test
	public void canAdd() throws RPGException, PooledException {
		BpIoGroup group = new BpIoGroup(0);
		((BpInteractive) BpInteractive.getInstance()).newPlayer();
		BpInteractiveObject io = (BpInteractiveObject)
				((BpInteractive) Interactive.getInstance()).newMob(4);
		group.addMember(io.getRefId());
		assertTrue(group.hasMember(io.getRefId()));
		io = (BpInteractiveObject)
				((BpInteractive) Interactive.getInstance()).newMob(4);
		assertFalse(group.hasMember(io.getRefId()));
		group.addMember(io.getRefId());
		assertTrue(group.hasMember(io.getRefId()));
		assertEquals(group.size(), 2);
	}
	@Test
	public void canRemove() throws RPGException, PooledException {
		BpIoGroup group = new BpIoGroup(0);
		assertEquals(group.size(), 0);
		group.removeMember(0);
		assertEquals(group.size(), 0);
		BpInteractiveObject io = (BpInteractiveObject)
				((BpInteractive) Interactive.getInstance()).newMob(4);
		group.addMember(io.getRefId());
		assertTrue(group.hasMember(io.getRefId()));
		io = (BpInteractiveObject)
				((BpInteractive) Interactive.getInstance()).newMob(4);
		assertFalse(group.hasMember(io.getRefId()));
		group.addMember(io.getRefId());
		assertTrue(group.hasMember(io.getRefId()));
		assertEquals(group.size(), 2);
		group.removeMember(io.getRefId());
		assertFalse(group.hasMember(io.getRefId()));
		assertEquals(group.size(), 1);
		group.removeMember(99);
		assertEquals(group.size(), 1);
	}
	@Test (expected = RPGException.class)
	public void willNotAddGroup() throws RPGException {
		BpIoGroup group = (BpIoGroup) 
				((BpInteractive) Interactive.getInstance()).newPlayerParty();
		BpIoGroup group2 = (BpIoGroup) 
				((BpInteractive) Interactive.getInstance()).newPlayerParty();
		group.addMember(group2.getRefId());
	}
	@Test
	public void canGetNumberOfMembers() throws RPGException, PooledException {
		String[] names = new String[] {
				"3 Mercenary Thugs", "3 Swordsmen", "3 Mercenary Leaders", 
				"3 Mercenaries", "3 Amazons", "3 Dwarf Warriors", 
				"3 Dwarf Younglings", "3 Elves", "3 Elf Apprentices", 
				"3 Halflings", "3 Starving Farmers", "3 Starving Women",
				"3 Starving Children", "3 Young Peasants", 
				"3 Tough Peasants", "3 Tough Farmers", 
				"3 Village Militia Leaders", "3 Village Militiamen",
				"3 Servant Retainers", "3 Wealthy Commoners",
				"3 Reaver Clan Leaders", "3 Reaver Clansmen",
				"3 Reaver Clan Elders", "3 Magicians", "3 Peasant Mob Leaders",
				"3 Angry Peasants", "3 Priests", "3 Hermit Monks",
				"3 Wandering Monks", "3 Warrior Monks", "3 Wizards", "3 Ghosts",
				"3 Wraiths", "3 Spectres", "3 Golems", "3 Dark Guardians",
				"3 Fugitive Swordsmen", "3 Fugitive Swordswomen",
				"3 Runaway Slaves", "3 Runaway Slave Girls",
				"3 Fugitive Priests", "3 Fugitive Magicians",
				"3 Fugitive Merchants", "3 Deserters", "3 Traveling Minstrels",
				"3 Constables", "3 Bandit Chiefs", "3 Bandits", "3 Hobgoblins",
				"3 Goblins", "3 Orc Alphas", "3 Orc Warpups", "3 Demi-Trolls",
				"3 Orc Wardogs", "3 Stone Trolls", "3 Dwarves",
				"3 Champion Warriors", "3 Elves",
				"3 Witches", "3 Giant Spiders", "3 Wolves", "3 Great Cats",
				"3 Mounted Soldiers", "3 Mounted Sergeants", "3 Wild Boars",
				"3 Bears", "3 Crocodiles", "3 Dragons", "3 Rocs", "3 Gryphons",
				"3 Harpies", "3 Falcons", "3 Hawkmen", "3 Eagles",
				"3 Giants", "3 Knights", "3 Bodyguards", "3 Undead Warriors",
				"3 Princesses", "3 Man-at-Arms", "3 Guardsmen", "3 Knights",
				"3 Slave Porters", "3 Slave Concubines", "3 Freeman Warriors",
				"3 Giant Lizards"
		};
		for (int i = 1, len = names.length; i <= len; i++) {
			BpIoGroup group = (BpIoGroup) 
					((BpInteractive) Interactive.getInstance()).newPlayerParty();
			group.addMember(((BpInteractive) Interactive.getInstance())
					.newMob(i).getRefId());
			group.addMember(((BpInteractive) Interactive.getInstance())
					.newMob(i).getRefId());
			group.addMember(((BpInteractive) Interactive.getInstance())
					.newMob(i).getRefId());
			assertEquals(group.size(), 3);
			assertEquals(group.getNumberOfMembers(), 3);
			assertEquals(group.getPartyList(), names[i - 1]);
		}
		BpIoGroup group = (BpIoGroup) 
				((BpInteractive) Interactive.getInstance()).newPlayerParty();
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(4).getRefId());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(4).getRefId());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(4).getRefId());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(5).getRefId());
		assertEquals(group.size(), 4);
		assertEquals(group.getNumberOfMembers(), 4);
		assertEquals(group.getPartyList(), "3 Mercenaries"
		+ System.lineSeparator() + "Amazon");
	}
	@Test
	public void canRide() throws RPGException, PooledException {
		BpIoGroup group = (BpIoGroup) 
				((BpInteractive) Interactive.getInstance()).newPlayerParty();
		group.addMember(((BpInteractive)
				Interactive.getInstance()).getPlayerIO());
		assertFalse(group.canTravelMounted());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newHorse().getRefId());
		assertEquals(group.getNumberOfHorses(), 1);
		assertTrue(group.canTravelMounted());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newHorse().getRefId());
		assertEquals(group.getNumberOfHorses(), 2);
		assertTrue(group.canTravelMounted());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(76).getRefId());
		assertTrue(group.canTravelMounted());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(76).getRefId());
		assertFalse(group.canTravelMounted());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newHorse().getRefId());
		assertEquals(group.getNumberOfHorses(), 3);
		assertTrue(group.canTravelMounted());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(71).getRefId());
		assertEquals(group.getNumberOfHorses(), 3);
		assertTrue(group.canTravelMounted());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(75).getRefId());
		assertFalse(group.canTravelMounted());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newHorse().getRefId());
		assertEquals(group.getNumberOfHorses(), 4);
		assertFalse(group.canTravelMounted());
	}
	@Test
	public void canFly() throws RPGException, PooledException {
		BpIoGroup group = (BpIoGroup) 
				((BpInteractive) Interactive.getInstance()).newPlayerParty();
		group.addMember(((BpInteractive)
				Interactive.getInstance()).getPlayerIO());
		assertFalse(group.canTravelAirborne());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newHorse().getRefId());
		assertEquals(group.getNumberOfHorses(), 1);
		assertEquals(group.getNumberOfWingedMounts(), 0);
		assertTrue(group.canTravelMounted());
		assertFalse(group.canTravelAirborne());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newWingedMount().getRefId());
		assertEquals(group.getNumberOfHorses(), 1);
		assertEquals(group.getNumberOfWingedMounts(), 1);
		assertTrue(group.canTravelMounted());
		assertTrue(group.canTravelAirborne());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(76).getRefId());
		assertFalse(group.canTravelAirborne());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(76).getRefId());
		assertFalse(group.canTravelAirborne());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newWingedMount().getRefId());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newWingedMount().getRefId());
		assertEquals(group.getNumberOfHorses(), 1);
		assertEquals(group.getNumberOfWingedMounts(), 3);
		assertTrue(group.canTravelAirborne());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(71).getRefId());
		assertEquals(group.getNumberOfHorses(), 1);
		assertEquals(group.getNumberOfWingedMounts(), 3);
		assertTrue(group.canTravelAirborne());
		group.addMember(((BpInteractive) Interactive.getInstance())
				.newMob(75).getRefId());
		assertFalse(group.canTravelAirborne());
		group.addMember(((BpInteractive)
				Interactive.getInstance()).newWingedMount().getRefId());
		assertEquals(group.getNumberOfHorses(), 1);
		assertEquals(group.getNumberOfWingedMounts(), 4);
		assertFalse(group.canTravelAirborne());
	}
}
