/**
 *
 */
package com.dalonedrow.module.basic_dnd.rpg.scripts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDIO;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDPC;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.Dice;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.ScriptVariables;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.armours.BandedMail;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.BastardSword;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.BattleAxe;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Club;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Dagger;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Dart;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Flail;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Hammer;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.HandAxe;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.HeavyCrossbow;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.HeavyFlail;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.HeavyHammer;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.HeavyPick;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Javelin;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Lance;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.LightCrossbow;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Longbow;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Longsword;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Mace;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.MorningStar;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Polearm;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Quarterstaff;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Scimitar;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Shortbow;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Shortsword;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.SilverDagger;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Sling;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Spear;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.Trident;
import com.dalonedrow.module.basic_dnd.rpg.scripts.items.weapons.TwoHandedSword;
import com.dalonedrow.module.basic_dnd.rpg.systems.BDDController;
import com.dalonedrow.module.basic_dnd.rpg.systems.BDDInteractive;
import com.dalonedrow.rpg.base.constants.EquipmentGlobals;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author 588648
 */
public class ClericScriptTest {
	private ClericScript script;
	@Before
	public void before() throws RPGException {
		new BDDController();
		script = new ClericScript();
		script.setIO(
		        ((BDDInteractive) Interactive.getInstance()).getNewPlayer());
		script.getIO().setLevel(1);
		script.getIO().setScript(script);
	}
	@Test
	public void canApplyDexACModifier() {
		
	}
	@Test
	public void canEquipBandedMail() throws RPGException {
		BDDPC pc = script.getIO().getPCData();
		pc.setBaseAttributeScore("DEX", 9f);
		assertEquals(9f, pc.getFullAttributeScore("AC"), .01f);
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new BandedMail());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_TORSO));
		//script.getIO().getPCData().computeFullStats();
		assertEquals(4f, pc.getFullAttributeScore("AC"), .01f);
	}
	@Test
	public void canEquipClub() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Club());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D4,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canEquipFlail() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Flail());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D6,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canEquipHammer() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Hammer());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D4,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canEquipHeavyFlail() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new HeavyFlail());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D8,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canEquipHeavyHammer() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new HeavyHammer());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D6,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canEquipMace() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Mace());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D6,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canEquipMorningStar() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new MorningStar());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D6,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canEquipQuarterstaff() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Quarterstaff());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D6,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canEquipSling() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Sling());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
		script.getIO().getPCData().computeFullStats();
		assertEquals(Dice.ONE_D4,
		        Dice.values()[(int) script.getIO().getPCData()
		                .getFullAttributeScore("DMG")]);
	}
	@Test
	public void canGetTHAC() {
		assertEquals("at level 1, need to roll 14 to hit AC 5",
		        14, script.getTHAC(5));
		assertEquals("at level 1, need to roll 20 to hit AC -6",
		        20, script.getTHAC(-6));
		assertEquals("at level 1, need to roll 9 to hit AC 10",
		        9, script.getTHAC(10));
		script.getIO().setLevel(3);
		assertEquals("at level 3, need to roll 19 to hit AC 0",
		        19, script.getTHAC(0));
		assertEquals("at level 3, need to roll 16 to hit AC 3",
		        16, script.getTHAC(3));
		assertEquals("at level 3, need to roll 13 to hit AC 6",
		        6, script.getTHAC(13));
		script.getIO().setLevel(4);
		assertEquals("at level 4, need to roll 18 to hit AC 0",
		        18, script.getTHAC(0));
		assertEquals("at level 4, need to roll 15 to hit AC 3",
		        15, script.getTHAC(3));
		script.getIO().setLevel(5);
		assertEquals("at level 5, need to roll 13 to hit AC 5",
		        13, script.getTHAC(5));
		assertEquals("at level 5, need to roll 20 to hit AC -6",
		        20, script.getTHAC(-6));
		script.getIO().setLevel(6);
		assertEquals("at level 6, need to roll 17 to hit AC 0",
		        17, script.getTHAC(0));
		assertEquals("at level 6, need to roll 14 to hit AC 3",
		        14, script.getTHAC(3));
		script.getIO().setLevel(8);
		assertEquals("at level 5, need to roll 12 to hit AC 5",
		        12, script.getTHAC(5));
		assertEquals("at level 5, need to roll 20 to hit AC -6",
		        20, script.getTHAC(-6));
		script.getIO().setLevel(9);
		assertEquals("at level 9, need to roll 16 to hit AC 0",
		        16, script.getTHAC(0));
		assertEquals("at level 9, need to roll 13 to hit AC 3",
		        13, script.getTHAC(3));
		script.getIO().setLevel(10);
		assertEquals("at level 10, need to roll 11 to hit AC 5",
		        11, script.getTHAC(5));
		assertEquals("at level 10, need to roll 20 to hit AC -6",
		        20, script.getTHAC(-6));
		script.getIO().setLevel(11);
		assertEquals("at level 11, need to roll 15 to hit AC 0",
		        15, script.getTHAC(0));
		assertEquals("at level 11, need to roll 12 to hit AC 3",
		        12, script.getTHAC(3));
		script.getIO().setLevel(12);
		assertEquals("at level 12, need to roll 9 to hit AC 5",
		        9, script.getTHAC(5));
		assertEquals("at level 12, need to roll 20 to hit AC -6",
		        20, script.getTHAC(-6));
		script.getIO().setLevel(13);
		assertEquals("at level 13, need to roll 13 to hit AC 0",
		        13, script.getTHAC(0));
		assertEquals("at level 13, need to roll 10 to hit AC 3",
		        10, script.getTHAC(3));
		script.getIO().setLevel(14);
		assertEquals("at level 14, need to roll 8 to hit AC 5",
		        8, script.getTHAC(5));
		assertEquals("at level 14, need to roll 19 to hit AC -6",
		        19, script.getTHAC(-6));
		script.getIO().setLevel(15);
		assertEquals("at level 15, need to roll 12 to hit AC 0",
		        12, script.getTHAC(0));
		assertEquals("at level 15, need to roll 9 to hit AC 3",
		        9, script.getTHAC(3));
		script.getIO().setLevel(16);
		assertEquals("at level 16, need to roll 7 to hit AC 5",
		        7, script.getTHAC(5));
		assertEquals("at level 16, need to roll 18 to hit AC -6",
		        18, script.getTHAC(-6));
		script.getIO().setLevel(17);
		assertEquals("at level 17, need to roll 11 to hit AC 0",
		        11, script.getTHAC(0));
		assertEquals("at level 17, need to roll 8 to hit AC 3",
		        8, script.getTHAC(3));
		script.getIO().setLevel(18);
		assertEquals("at level 18, need to roll 6 to hit AC 5",
		        6, script.getTHAC(5));
		assertEquals("at level 18, need to roll 17 to hit AC -6",
		        17, script.getTHAC(-6));
		script.getIO().setLevel(19);
		assertEquals("at level 19, need to roll 10 to hit AC 0",
		        10, script.getTHAC(0));
		assertEquals("at level 19, need to roll 7 to hit AC 3",
		        7, script.getTHAC(3));
		script.getIO().setLevel(20);
		assertEquals("at level 20, need to roll 5 to hit AC 5",
		        5, script.getTHAC(5));
		assertEquals("at level 20, need to roll 16 to hit AC -6",
		        16, script.getTHAC(-6));
		script.getIO().setLevel(21);
		assertEquals("at level 21, need to roll 9 to hit AC 0",
		        9, script.getTHAC(0));
		assertEquals("at level 21, need to roll 6 to hit AC 3",
		        6, script.getTHAC(3));
		script.getIO().setLevel(21);
		assertEquals("at level 21, need to roll 4 to hit AC 5",
		        4, script.getTHAC(5));
		assertEquals("at level 21, need to roll 15 to hit AC -6",
		        15, script.getTHAC(-6));
		assertEquals("at level 21, need to roll 2 to hit AC 10",
		        2, script.getTHAC(10));
		script.getIO().setLevel(22);
		assertEquals("at level 22, need to roll 4 to hit AC 5",
		        4, script.getTHAC(5));
		assertEquals("at level 22, need to roll 15 to hit AC -6",
		        15, script.getTHAC(-6));
		assertEquals("at level 22, need to roll 2 to hit AC 10",
		        2, script.getTHAC(10));
	}
	@Test
	public void willNotEquipBastardSword() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new BastardSword());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipBattleAxe() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new BattleAxe());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipDagger() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Dagger());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipDart() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Dart());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipHandAxe() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new HandAxe());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipHeavyCrossbow() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new HeavyCrossbow());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipHeavyPick() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new HeavyPick());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipJavelin() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Javelin());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipLance() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Lance());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipLightCrossbow() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new LightCrossbow());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipLongbow() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Longbow());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipLongsword() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Longsword());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipPolearm() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Polearm());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipScimitar() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Scimitar());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipShortbow() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Shortbow());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipShortSword() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Shortsword());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipSilverDagger() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new SilverDagger());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipSpear() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Spear());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipTrident() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new Trident());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
	@Test
	public void willNotEquipTwoHandedSword() throws RPGException {
		BDDIO wio = ((BDDInteractive) Interactive.getInstance())
		        .getNewItem(new TwoHandedSword());
		Script.getInstance().sendIOScriptEvent(wio,
		        ScriptConsts.SM_004_INVENTORYUSE,
		        new Object[] {
		                ScriptVariables.TARGET_IO.toString(), // target
		                script.getIO().getRefId() // cleric ref id
		        },
		        null);
		assertNotEquals(wio.getRefId(),
		        script.getIO().getPCData().getEquippedItem(
		                EquipmentGlobals.EQUIP_SLOT_WEAPON));
	}
}
