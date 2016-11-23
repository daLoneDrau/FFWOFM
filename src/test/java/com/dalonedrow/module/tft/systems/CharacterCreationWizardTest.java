package com.dalonedrow.module.tft.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.module.tft.rpg.TFTCharacterArchetype;
import com.dalonedrow.module.tft.rpg.TFTHumanoidRaces;
import com.dalonedrow.module.tft.rpg.TFTSpell;
import com.dalonedrow.module.tft.rpg.TFTTalent;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class CharacterCreationWizardTest {
	private CharacterCreationWizard wizard;
	@Before
	public void before() {
		wizard = new CharacterCreationWizard();
	}
	@Test
	public void canCreateNewCharacter() throws RPGException, PooledException {
		wizard.createNewCharacter();
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 0f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 0f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 0f, .01f);
		assertEquals(wizard.getExtraPoints(), 0);
		wizard.setGender(0);
		wizard.setGender(0);
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 8f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 8f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 8f, .01f);
		assertEquals(wizard.getExtraPoints(), 8);
		wizard.createNewCharacter();
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 0f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 0f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 0f, .01f);
		assertEquals(wizard.getExtraPoints(), 0);
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 8f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 8f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 8f, .01f);
		assertEquals(wizard.getExtraPoints(), 8);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 6f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 10f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 8f, .01f);
		assertEquals(wizard.getExtraPoints(), 8);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 10f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 6f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 8f, .01f);
		assertEquals(wizard.getExtraPoints(), 8);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 6f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 8f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 10f,
				.01f);
		assertEquals(wizard.getExtraPoints(), 6);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 6f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 6f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 6f, .01f);
		assertEquals(wizard.getExtraPoints(), 8);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 4f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 12f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 8f, .01f);
		assertEquals(wizard.getExtraPoints(), 6);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 10f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 10f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 6f, .01f);
		assertEquals(wizard.getExtraPoints(), 0);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 14f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 10f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 8f, .01f);
		assertEquals(wizard.getExtraPoints(), 2);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 25f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 9f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 7f, .01f);
		assertEquals(wizard.getExtraPoints(), 0);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 16f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 11f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 8f, .01f);
		assertEquals(wizard.getExtraPoints(), 0);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 14f,
				.01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("DX"), 8f, .01f);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("IQ"), 8f, .01f);
		assertEquals(wizard.getExtraPoints(), 8);
		for (int i = 255; i >= 0; i--) {
			wizard.assignRandomRace();
		}
	}
	@Test
	public void canRemoveTalentCascade()
			throws PooledException, RPGException {
		wizard.setGender(0);
		wizard.setRace(0);
		wizard.assignTalent(TFTTalent.valueOf("thrown_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("spear_thrower"));
		assertTrue(wizard.getNewCharacter().hasTalent("thrown_weapons"));
		assertTrue(wizard.getNewCharacter().hasTalent("spear_thrower"));
		wizard.removeTalentCascade(TFTTalent.valueOf("thrown_weapons"));
		assertFalse(wizard.getNewCharacter().hasTalent("thrown_weapons"));
		assertFalse(wizard.getNewCharacter().hasTalent("spear_thrower"));
	}
	@Test
	public void canSetArchetypeCentaurBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeCentaurTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeDwarfWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeElfWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGargoyleTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGiantTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeGoblinWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHalflingWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHobgoblinAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHobgoblinBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHobgoblinGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHobgoblinLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHobgoblinMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHobgoblinPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHobgoblinScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHobgoblinWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		assertEquals(wizard.getExtraPoints(), 2);
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeHumanWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		assertEquals(wizard.getExtraPoints(), 2);
		wizard.getNewCharacter().setPrettyPrinting(true);
	}
	@Test
	public void canSetArchetypeOrcThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeOrcWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_01_ORC);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetArchetypeReptileMenWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_11_REPTILE_MEN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test
	public void canSetTalentIQEnough()
			throws PooledException, RPGException {
		final int six = 6;
		wizard.setGender(0);
		wizard.setRace(0);
		for (int i = six; i > 0; i--) {
			wizard.adjustAttributeScore(CharacterCreationWizard.IQ, true);
		}
		wizard.assignTalent(TFTTalent.valueOf("engineer"));
		assertTrue(wizard.getNewCharacter().hasTalent("engineer"));
	}
	@Test
	public void canSetTalentsWithNoPrerequisites()
			throws PooledException, RPGException {
		final int six = 6;
		wizard.setGender(0);
		wizard.setRace(0);
		for (int i = six; i > 0; i--) {
			wizard.adjustAttributeScore(CharacterCreationWizard.IQ, true);
		}
		TFTTalent[] talents = TFTTalent.values();
		for (int i = 0, len = talents.length; i < len; i++) {
			TFTTalent talent = talents[i];
			if (talent.getPrerequisites().length == 0) {
				
				wizard.assignTalent(talent);
				wizard.removeTalent(talent);
			}
		}
	}
	@Test
	public void canSetTalentsWithPrerequisites()
			throws PooledException, RPGException {
		final int six = 6;
		wizard.setGender(0);
		wizard.setRace(0);
		// spear_thrower
		wizard.assignTalent(TFTTalent.valueOf("thrown_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("spear_thrower"));
		wizard.removeTalent(TFTTalent.valueOf("spear_thrower"));
		wizard.removeTalent(TFTTalent.valueOf("thrown_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("pole_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("spear_thrower"));
		wizard.removeTalent(TFTTalent.valueOf("spear_thrower"));
		wizard.removeTalent(TFTTalent.valueOf("pole_weapons"));
		// shuriken
		wizard.assignTalent(TFTTalent.valueOf("thrown_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("shuriken"));
		wizard.removeTalent(TFTTalent.valueOf("shuriken"));
		wizard.removeTalent(TFTTalent.valueOf("thrown_weapons"));
		// net_trident
		wizard.assignTalent(TFTTalent.valueOf("pole_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("net_trident"));
		wizard.removeTalent(TFTTalent.valueOf("net_trident"));
		wizard.removeTalent(TFTTalent.valueOf("pole_weapons"));
		// bola
		wizard.assignTalent(TFTTalent.valueOf("thrown_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("bola"));
		wizard.removeTalent(TFTTalent.valueOf("bola"));
		wizard.removeTalent(TFTTalent.valueOf("thrown_weapons"));
		// boomerang
		wizard.assignTalent(TFTTalent.valueOf("thrown_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("boomerang"));
		wizard.removeTalent(TFTTalent.valueOf("boomerang"));
		wizard.removeTalent(TFTTalent.valueOf("thrown_weapons"));
		// naginata
		wizard.assignTalent(TFTTalent.valueOf("pole_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("naginata"));
		wizard.removeTalent(TFTTalent.valueOf("naginata"));
		wizard.removeTalent(TFTTalent.valueOf("pole_weapons"));
		// warrior
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.assignTalent(TFTTalent.valueOf("warrior"));
		wizard.removeTalent(TFTTalent.valueOf("warrior"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		// veteran
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, true);
		wizard.assignTalent(TFTTalent.valueOf("warrior"));
		wizard.assignTalent(TFTTalent.valueOf("veteran"));
		wizard.removeTalent(TFTTalent.valueOf("veteran"));
		wizard.removeTalent(TFTTalent.valueOf("warrior"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.ST, false);
		// missile_weapons
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("crossbow"));
		wizard.assignTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("crossbow"));
		wizard.assignTalent(TFTTalent.valueOf("bow"));
		wizard.assignTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("bow"));
		wizard.assignSpell(TFTSpell.valueOf("wizards_wrath"));
		wizard.assignTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeSpell(TFTSpell.valueOf("wizards_wrath"));
		wizard.assignSpell(TFTSpell.valueOf("lightning"));
		wizard.assignTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeSpell(TFTSpell.valueOf("lightning"));
		wizard.assignSpell(TFTSpell.valueOf("fireball"));
		wizard.assignTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeSpell(TFTSpell.valueOf("fireball"));
		wizard.assignSpell(TFTSpell.valueOf("magic_fist"));
		wizard.assignTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("missile_weapons"));
		wizard.removeSpell(TFTSpell.valueOf("magic_fist"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// diving
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("swimming"));
		wizard.assignTalent(TFTTalent.valueOf("diving"));
		wizard.removeTalent(TFTTalent.valueOf("diving"));
		wizard.removeTalent(TFTTalent.valueOf("swimming"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// unarmed_combat
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// acrobatics
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("acrobatics"));
		wizard.removeTalent(TFTTalent.valueOf("acrobatics"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// new_followers
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("charisma"));
		wizard.assignTalent(TFTTalent.valueOf("new_followers"));
		wizard.removeTalent(TFTTalent.valueOf("new_followers"));
		wizard.removeTalent(TFTTalent.valueOf("charisma"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// remove_traps
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("detect_traps"));
		wizard.assignTalent(TFTTalent.valueOf("remove_traps"));
		wizard.removeTalent(TFTTalent.valueOf("remove_traps"));
		wizard.removeTalent(TFTTalent.valueOf("detect_traps"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// fencing
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("sword"));
		wizard.assignTalent(TFTTalent.valueOf("fencing"));
		wizard.removeTalent(TFTTalent.valueOf("fencing"));
		wizard.removeTalent(TFTTalent.valueOf("sword"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// two_weapons
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("sword"));
		wizard.assignTalent(TFTTalent.valueOf("two_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("two_weapons"));
		wizard.removeTalent(TFTTalent.valueOf("sword"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// woodsman
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("naturalist"));
		wizard.assignTalent(TFTTalent.valueOf("woodsman"));
		wizard.removeTalent(TFTTalent.valueOf("woodsman"));
		wizard.removeTalent(TFTTalent.valueOf("naturalist"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// expert_riding
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("riding"));
		wizard.assignTalent(TFTTalent.valueOf("expert_riding"));
		wizard.removeTalent(TFTTalent.valueOf("expert_riding"));
		wizard.removeTalent(TFTTalent.valueOf("riding"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// goldsmith
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("appraisal"));
		wizard.assignTalent(TFTTalent.valueOf("goldsmith"));
		wizard.removeTalent(TFTTalent.valueOf("goldsmith"));
		wizard.removeTalent(TFTTalent.valueOf("appraisal"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// shipbuilder
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("seamanship"));
		wizard.assignTalent(TFTTalent.valueOf("shipbuilder"));
		wizard.removeTalent(TFTTalent.valueOf("shipbuilder"));
		wizard.removeTalent(TFTTalent.valueOf("seamanship"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// expert_naturalist
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("naturalist"));
		wizard.assignTalent(TFTTalent.valueOf("expert_naturalist"));
		wizard.removeTalent(TFTTalent.valueOf("expert_naturalist"));
		wizard.removeTalent(TFTTalent.valueOf("naturalist"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// spying
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("silent_movement"));
		wizard.assignTalent(TFTTalent.valueOf("spying"));
		wizard.removeTalent(TFTTalent.valueOf("spying"));
		wizard.removeTalent(TFTTalent.valueOf("silent_movement"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// expert_appraisal
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("appraisal"));
		wizard.assignTalent(TFTTalent.valueOf("expert_appraisal"));
		wizard.removeTalent(TFTTalent.valueOf("expert_appraisal"));
		wizard.removeTalent(TFTTalent.valueOf("appraisal"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// captain
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("seamanship"));
		wizard.assignTalent(TFTTalent.valueOf("captain"));
		wizard.removeTalent(TFTTalent.valueOf("captain"));
		wizard.removeTalent(TFTTalent.valueOf("seamanship"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// thief_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.assignTalent(TFTTalent.valueOf("thief"));
		wizard.assignTalent(TFTTalent.valueOf("thief_ii"));
		wizard.removeTalent(TFTTalent.valueOf("thief_ii"));
		wizard.removeTalent(TFTTalent.valueOf("thief"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		// armourer_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("armourer"));
		wizard.assignTalent(TFTTalent.valueOf("armourer_ii"));
		wizard.removeTalent(TFTTalent.valueOf("armourer_ii"));
		wizard.removeTalent(TFTTalent.valueOf("armourer"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// unarmed_combat_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_ii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_ii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		// monster_followers_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("monster_followers"));
		wizard.assignTalent(TFTTalent.valueOf("monster_followers_ii"));
		wizard.removeTalent(TFTTalent.valueOf("monster_followers_ii"));
		wizard.removeTalent(TFTTalent.valueOf("monster_followers"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// mechanician_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("mechanician"));
		wizard.assignTalent(TFTTalent.valueOf("mechanician_ii"));
		wizard.removeTalent(TFTTalent.valueOf("mechanician_ii"));
		wizard.removeTalent(TFTTalent.valueOf("mechanician"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// scholar
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("literacy"));
		wizard.assignTalent(TFTTalent.valueOf("scholar"));
		wizard.removeTalent(TFTTalent.valueOf("scholar"));
		wizard.removeTalent(TFTTalent.valueOf("literacy"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// tactics_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("tactics"));
		wizard.assignTalent(TFTTalent.valueOf("tactics_ii"));
		wizard.removeTalent(TFTTalent.valueOf("tactics_ii"));
		wizard.removeTalent(TFTTalent.valueOf("tactics"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// mathemetician
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("literacy"));
		wizard.assignTalent(TFTTalent.valueOf("mathemetician"));
		wizard.removeTalent(TFTTalent.valueOf("mathemetician"));
		wizard.removeTalent(TFTTalent.valueOf("literacy"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// unarmed_combat_iii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_ii"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_iii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_iii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_ii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		// unarmed_combat_iv
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_ii"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_iii"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_iv"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_iv"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_iii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_ii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		// unarmed_combat_v
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, true);
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_ii"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_iii"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_iv"));
		wizard.assignTalent(TFTTalent.valueOf("unarmed_combat_v"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_v"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_iv"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_iii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat_ii"));
		wizard.removeTalent(TFTTalent.valueOf("unarmed_combat"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.DX, false);
		// physicker_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("physicker"));
		wizard.assignTalent(TFTTalent.valueOf("physicker_ii"));
		wizard.removeTalent(TFTTalent.valueOf("physicker_ii"));
		wizard.removeTalent(TFTTalent.valueOf("physicker"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// priest_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("priest"));
		wizard.assignTalent(TFTTalent.valueOf("priest_ii"));
		wizard.removeTalent(TFTTalent.valueOf("priest_ii"));
		wizard.removeTalent(TFTTalent.valueOf("priest"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		// bard_ii
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, true);
		wizard.assignTalent(TFTTalent.valueOf("bard"));
		wizard.assignTalent(TFTTalent.valueOf("bard_ii"));
		wizard.removeTalent(TFTTalent.valueOf("bard_ii"));
		wizard.removeTalent(TFTTalent.valueOf("bard"));
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
		wizard.adjustAttributeScoreGodMode(CharacterCreationWizard.IQ, false);
	}
	@Test
	public void canUseAllPoints()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.adjustAttributeScore(1, true);
		wizard.adjustAttributeScore(1, false);
		wizard.adjustAttributeScore(0, true);
		wizard.adjustAttributeScore(0, true);
		assertEquals(
				wizard.getNewCharacter().getFullAttributeScore("ST"), 16f,
				.01f);
	}
	@Test
	public void printRaceProfessionMatrix()
			throws PooledException, RPGException {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		TFTHumanoidRaces[] races = TFTHumanoidRaces.values();
		int[] raceCols = new int[races.length];
		for (int i = 0, len = races.length; i < len; i++) {
			raceCols[i] = races[i].getShortName().length;
		}

		TFTCharacterArchetype[] archetypes = TFTCharacterArchetype.values();
		int maxLeftColLen = 0;
		for (int i = 0, len = archetypes.length; i < len; i++) {
			maxLeftColLen = Math.max(maxLeftColLen,
					archetypes[i].getShortName().length);
		}
		for (int i = maxLeftColLen; i >= 0; i--) {
			sb.append(' ');
		}
		for (int i = 0, len = races.length; i < len; i++) {
			sb.append('|');
			sb.append(races[i].getShortName());
			if (i + 1 >= len) {
				sb.append('|');
			}
		}
		sb.append('\n');

		for (int i = maxLeftColLen; i >= 0; i--) {
			sb.append('-');
		}
		for (int i = 0, len = races.length; i < len; i++) {
			sb.append('-');
			for (int j = races[i].getShortName().length - 1; j >= 0; j--) {
				sb.append('-');
			}
			if (i + 1 >= len) {
				sb.append('-');
			}
		}
		sb.append('\n');
		for (int i = 0, len = archetypes.length; i < len; i++) {
			sb.append(archetypes[i].getShortName());
			for (int j =
					maxLeftColLen - archetypes[i]
							.getShortName().length; j >= 0; j--) {
				sb.append(' ');
			}
			// test archetypes
			for (int j = 0, lenj = races.length; j < lenj; j++) {
				sb.append('|');
				wizard.createNewCharacter();
				wizard.setGender(1);
				wizard.setRace(races[j].getId());
				try {
					wizard.setArchetype(archetypes[i].getId());
					int half = races[j].getShortName().length / 2;
					for (int k = half - 1; k >= 0; k--) {
						sb.append(' ');
					}
					sb.append('x');
					for (int k =
							races[j].getShortName().length - 1
									- half; k > 0; k--) {
						sb.append(' ');
					}
				} catch (RPGException ex) {
					for (int k =
							races[j].getShortName().length - 1; k >= 0; k--) {
						sb.append(' ');
					}
				}
				if (j + 1 >= len) {
					sb.append('|');
				}
			}
			sb.append('\n');
			// line seperator
			for (int j = maxLeftColLen; j >= 0; j--) {
				sb.append('-');
			}
			for (int j = 0, lenj = races.length; j < lenj; j++) {
				sb.append('-');
				for (int k = races[j].getShortName().length - 1; k >= 0; k--) {
					sb.append('-');
				}
				if (j + 1 >= len) {
					sb.append('-');
				}
			}
			sb.append('\n');
		}
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		
	}
	@Test(expected = RPGException.class)
	public void willNotAdjustBeforeCreate()
			throws PooledException, RPGException {
		wizard.adjustAttributeScore(0, false);
	}
	@Test(expected = RPGException.class)
	public void willNotAdjustBeforeRace()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.adjustAttributeScore(0, false);
	}
	@Test(expected = RPGException.class)
	public void willNotDecreaseBelowMin()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.setRace(TFTHumanoidRaces.RACE_00_HUMAN);
		wizard.adjustAttributeScore(0, false);
	}
	@Test(expected = RPGException.class)
	public void willNotIncreaseMissingAttr()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.adjustAttributeScore(55, true);
	}
	@Test(expected = RPGException.class)
	public void willNotIncreaseProotwaddleIq()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.adjustAttributeScore(2, true);
	}
	@Test(expected = RPGException.class)
	public void willNotRemoveTalentPrereq()
			throws PooledException, RPGException {
		wizard.setGender(0);
		wizard.setRace(0);
		wizard.assignTalent(TFTTalent.valueOf("thrown_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("spear_thrower"));
		assertTrue(wizard.getNewCharacter().hasTalent("thrown_weapons"));
		assertTrue(wizard.getNewCharacter().hasTalent("spear_thrower"));
		wizard.removeTalent(TFTTalent.valueOf("thrown_weapons"));
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeBeforeCreation()
			throws PooledException, RPGException {
		wizard.setArchetype(1);
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeBeforeGender()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.setArchetype(1);
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeBeforeRace()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setArchetype(1);
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeCentaurWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_08_CENTAUR);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeDwarfAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeDwarfBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeDwarfCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeDwarfThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_03_DWARF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeElfBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_02_ELF);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoylePriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGargoyleWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_10_GARGOYLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantPriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGiantWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_09_GIANT);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGoblinBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGoblinSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeGoblinTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_04_GOBLIN);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHalflingBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHalflingSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHalflingTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_06_HALFLING);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHobgoblinAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHobgoblinBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHobgoblinBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHobgoblinCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHobgoblinSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHobgoblinTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeHobgoblinThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_05_HOBGOBLIN);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeInvalid()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(0);
		wizard.setArchetype(255);
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeMaleAmazon()
			throws PooledException, RPGException {
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(0);
		wizard.setArchetype(TFTCharacterArchetype.HERO_03_AMAZON);
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleAdept()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_13_ADEPT;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleAmazon()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_03_AMAZON;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleBarbarian()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_01_BARBARIAN;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleBard()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_11_BARD;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleBlademaster()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_05_BLADEMASTER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleCleptesMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleGadgeteer()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_07_GADGETEER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleLeader()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_02_LEADER;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleMercenary()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_04_MERCENARY;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddlePriest()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_08_PRIEST;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleScholar()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_09_SCHOLAR;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleSolidusMagus()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleTank()
			throws PooledException, RPGException {
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(
						TFTCharacterArchetype.HERO_00_TANK);
		wizard.createNewCharacter();
		wizard.setGender(0);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(TFTCharacterArchetype.HERO_00_TANK);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleThief()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_06_THIEF;
		TFTCharacterArchetype archetypeObj =
				TFTCharacterArchetype.valueOf(prof);
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("ST") >= archetypeObj.getMinimumSt());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("DX") >= archetypeObj.getMinimumDx());
		assertTrue(wizard.getNewCharacter()
				.getBaseAttributeScore("IQ") >= archetypeObj.getMinimumIq());
		wizard.getNewCharacter().setPrettyPrinting(true);
		
	}
	@Test(expected = RPGException.class)
	public void willNotSetArchetypeProotwaddleWoodsman()
			throws PooledException, RPGException {
		int prof = TFTCharacterArchetype.HERO_10_WOODSMAN;
		wizard.createNewCharacter();
		wizard.setGender(1);
		wizard.setRace(TFTHumanoidRaces.RACE_07_PROOTWADDLE);
		wizard.setArchetype(prof);
	}
	@Test(expected = RPGException.class)
	public void willNotSetInvalidGender() throws PooledException, RPGException {
		wizard.setGender(2);
	}
	@Test(expected = RPGException.class)
	public void willNotSetInvalidRace() throws PooledException, RPGException {
		wizard.setGender(0);
		wizard.setRace(255);
	}
	@Test(expected = RPGException.class)
	public void willNotSetInvalidRaceBeforeGender()
			throws PooledException, RPGException {
		wizard.setRace(255);
	}
	@Test(expected = RPGException.class)
	public void willNotSetTalentBeforePrerequisite()
			throws PooledException, RPGException {
		wizard.setGender(0);
		wizard.setRace(0);
		wizard.assignTalent(TFTTalent.valueOf("spear_thrower"));
	}
	@Test(expected = RPGException.class)
	public void willNotSetTalentIQCostTooHigh()
			throws PooledException, RPGException {
		wizard.setGender(0);
		wizard.setRace(8);
		wizard.assignTalent(TFTTalent.valueOf("knife"));
		wizard.assignTalent(TFTTalent.valueOf("sword"));
		wizard.assignTalent(TFTTalent.valueOf("ax_mace"));
		wizard.assignTalent(TFTTalent.valueOf("pole_weapons"));
		wizard.assignTalent(TFTTalent.valueOf("bow"));
		wizard.assignTalent(TFTTalent.valueOf("crossbow"));
	}
	@Test(expected = RPGException.class)
	public void willNotSetTalentIQTooLow()
			throws PooledException, RPGException {
		wizard.setGender(0);
		wizard.setRace(0);
		wizard.assignTalent(TFTTalent.valueOf("engineer"));
	}
}
