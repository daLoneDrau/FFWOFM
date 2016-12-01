/**
 * 
 */
package com.dalonedrow.module.basic_dnd.rpg.base.flyweights;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.basic_dnd.rpg.flyweights.BDDPC;
import com.dalonedrow.module.basic_dnd.rpg.systems.BDDController;
import com.dalonedrow.module.basic_dnd.rpg.systems.BDDInteractive;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author 588648
 *
 */
public class BDDPCTest {
	private BDDPC test;
	@Before
	public void before() throws RPGException {
		new BDDController();
		test = new BDDPC();
		test.setIo(((BDDInteractive) Interactive.getInstance()).getNewPlayer());
	}
	@Test
	public void canTestAllStrengthModifiers() throws RPGException {
		String attr = "STR";
		test.setBaseAttributeScore(attr, 3f);
		test.computeFullStats();
		assertEquals(-3f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 4f);
		test.computeFullStats();
		assertEquals(-2f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 5f);
		test.computeFullStats();
		assertEquals(-2f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 6f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 7f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 8f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 9f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 10f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 11f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 12f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 13f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 14f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 15f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 16f);
		test.computeFullStats();
		assertEquals(2f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(2f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(2f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 17f);
		test.computeFullStats();
		assertEquals(2f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(2f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(2f, test.getFullAttributeScore("FDB"), .01f);
		test.setBaseAttributeScore(attr, 18f);
		test.computeFullStats();
		assertEquals(3f, test.getFullAttributeScore("LAB"), .01f);
		assertEquals(3f, test.getFullAttributeScore("DB"), .01f);
		assertEquals(3f, test.getFullAttributeScore("FDB"), .01f);
	}
	@Test
	public void canTestAllDexterityModifiers() throws RPGException {
		String attr = "DEX";
		test.setBaseAttributeScore(attr, 3f);
		test.computeFullStats();
		assertEquals(3f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 4f);
		test.computeFullStats();
		assertEquals(2f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 5f);
		test.computeFullStats();
		assertEquals(2f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 6f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 7f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 8f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 9f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(0f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 10f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(0f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 11f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(0f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 12f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(0f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 13f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(1f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 14f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(1f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 15f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(1f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 16f);
		test.computeFullStats();
		assertEquals(-2f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(2f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 17f);
		test.computeFullStats();
		assertEquals(-2f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(2f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("IM"), .01f);
		test.setBaseAttributeScore(attr, 18f);
		test.computeFullStats();
		assertEquals(-3f, test.getFullAttributeScore("AC"), .01f);
		assertEquals(3f, test.getFullAttributeScore("MAB"), .01f);
		assertEquals(2f, test.getFullAttributeScore("IM"), .01f);
	}
	@Test
	public void canTestAllConstitutionModifiers() throws RPGException {
		test.setLevel(1);
		String attr = "CON";
		test.setBaseAttributeScore(attr, 3f);
		test.setBaseAttributeScore("MHP", 100);
		test.computeFullStats();
		assertEquals(-3f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 4f);
		test.computeFullStats();
		assertEquals(-2f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 5f);
		test.computeFullStats();
		assertEquals(-2f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 6f);
		test.computeFullStats();
		assertEquals(-1f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 7f);
		test.computeFullStats();
		assertEquals(-1f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 8f);
		test.computeFullStats();
		assertEquals(-1f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 9f);
		test.computeFullStats();
		assertEquals(0f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 10f);
		test.computeFullStats();
		assertEquals(0f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 11f);
		test.computeFullStats();
		assertEquals(0f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 12f);
		test.computeFullStats();
		assertEquals(0f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 13f);
		test.computeFullStats();
		assertEquals(1f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 14f);
		test.computeFullStats();
		assertEquals(1f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 15f);
		test.computeFullStats();
		assertEquals(1f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 16f);
		test.computeFullStats();
		assertEquals(2f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 17f);
		test.computeFullStats();
		assertEquals(2f, test.getAttributeModifier("MHP"), .01f);
		test.setBaseAttributeScore(attr, 18f);
		test.computeFullStats();
		assertEquals(3f, test.getAttributeModifier("MHP"), .01f);
	}
	@Test
	public void canTestAllWisdomModifiers() throws RPGException {
		String attr = "WIS";
		test.setBaseAttributeScore(attr, 3f);
		test.computeFullStats();
		assertEquals(-3f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(-3f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 4f);
		test.computeFullStats();
		assertEquals(-2f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 5f);
		test.computeFullStats();
		assertEquals(-2f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(-2f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 6f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 7f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 8f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(-1f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 9f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 10f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 11f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 12f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(0f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 13f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 14f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 15f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(1f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 16f);
		test.computeFullStats();
		assertEquals(2f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 17f);
		test.computeFullStats();
		assertEquals(2f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(2f, test.getFullAttributeScore("STS"), .01f);
		test.setBaseAttributeScore(attr, 18f);
		test.computeFullStats();
		assertEquals(3f, test.getFullAttributeScore("STB"), .01f);
		assertEquals(3f, test.getFullAttributeScore("STD"), .01f);
		assertEquals(3f, test.getFullAttributeScore("STE"), .01f);
		assertEquals(3f, test.getFullAttributeScore("STM"), .01f);
		assertEquals(3f, test.getFullAttributeScore("STO"), .01f);
		assertEquals(3f, test.getFullAttributeScore("STP"), .01f);
		assertEquals(3f, test.getFullAttributeScore("STW"), .01f);
		assertEquals(3f, test.getFullAttributeScore("STS"), .01f);
	}
	@Test
	public void canTestAllCharismaModifiers() throws RPGException {
		String attr = "CHA";
		test.setBaseAttributeScore(attr, 3f);
		test.computeFullStats();
		assertEquals(2f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 4f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 5f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 6f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 7f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 8f);
		test.computeFullStats();
		assertEquals(1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 9f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 10f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 11f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 12f);
		test.computeFullStats();
		assertEquals(0f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 13f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 14f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 15f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 16f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 17f);
		test.computeFullStats();
		assertEquals(-1f, test.getFullAttributeScore("RA"), .01f);
		test.setBaseAttributeScore(attr, 18f);
		test.computeFullStats();
		assertEquals(-2f, test.getFullAttributeScore("RA"), .01f);
	}
	@Test
	public void canCreateCleric() throws RPGException {
		for (int i = 10000; i >= 0; i--) {
			test.rollNewCleric();
			assertTrue(test.getFullAttributeScore("STR") >= 3);
			assertTrue(test.getFullAttributeScore("STR") <= 18);
			assertTrue(test.getFullAttributeScore("DEX") >= 3);
			assertTrue(test.getFullAttributeScore("DEX") <= 18);
			assertTrue(test.getFullAttributeScore("CON") >= 3);
			assertTrue(test.getFullAttributeScore("CON") <= 18);
			assertTrue(test.getFullAttributeScore("WIS") >= 3);
			assertTrue(test.getFullAttributeScore("WIS") <= 18);
			assertTrue(test.getFullAttributeScore("INT") >= 3);
			assertTrue(test.getFullAttributeScore("INT") <= 18);
			assertTrue(test.getFullAttributeScore("CHA") >= 3);
			assertTrue(test.getFullAttributeScore("CHA") <= 18);
			assertTrue(test.getBaseAttributeScore("MHP") >= 1);
			assertTrue(test.getBaseAttributeScore("MHP") <= 6);
			assertEquals(test.getFullAttributeScore("HP"),
					test.getFullAttributeScore("MHP"), .01f);
			assertTrue(test.getIo().isInGroup("CLERIC"));
			assertTrue(test.getIo().isInGroup("HUMAN"));
		}
		// check saving throws per level
		assertEquals(16, (int) test.getBaseAttributeScore("STB"));
		assertEquals(11, (int) test.getBaseAttributeScore("STD"));
		assertEquals(14, (int) test.getBaseAttributeScore("STE"));
		assertEquals(15, (int) test.getBaseAttributeScore("STM"));
		assertEquals(11, (int) test.getBaseAttributeScore("STO"));
		assertEquals(14, (int) test.getBaseAttributeScore("STP"));
		assertEquals(12, (int) test.getBaseAttributeScore("STW"));
		assertEquals(15, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(2);
		test.computeFullStats();
		assertEquals(16, (int) test.getBaseAttributeScore("STB"));
		assertEquals(11, (int) test.getBaseAttributeScore("STD"));
		assertEquals(14, (int) test.getBaseAttributeScore("STE"));
		assertEquals(15, (int) test.getBaseAttributeScore("STM"));
		assertEquals(11, (int) test.getBaseAttributeScore("STO"));
		assertEquals(14, (int) test.getBaseAttributeScore("STP"));
		assertEquals(12, (int) test.getBaseAttributeScore("STW"));
		assertEquals(15, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(3);
		test.computeFullStats();
		assertEquals(16, (int) test.getBaseAttributeScore("STB"));
		assertEquals(11, (int) test.getBaseAttributeScore("STD"));
		assertEquals(14, (int) test.getBaseAttributeScore("STE"));
		assertEquals(15, (int) test.getBaseAttributeScore("STM"));
		assertEquals(11, (int) test.getBaseAttributeScore("STO"));
		assertEquals(14, (int) test.getBaseAttributeScore("STP"));
		assertEquals(12, (int) test.getBaseAttributeScore("STW"));
		assertEquals(15, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(4);
		test.computeFullStats();
		assertEquals(16, (int) test.getBaseAttributeScore("STB"));
		assertEquals(11, (int) test.getBaseAttributeScore("STD"));
		assertEquals(14, (int) test.getBaseAttributeScore("STE"));
		assertEquals(15, (int) test.getBaseAttributeScore("STM"));
		assertEquals(11, (int) test.getBaseAttributeScore("STO"));
		assertEquals(14, (int) test.getBaseAttributeScore("STP"));
		assertEquals(12, (int) test.getBaseAttributeScore("STW"));
		assertEquals(15, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(5);
		test.computeFullStats();
		assertEquals(14, (int) test.getBaseAttributeScore("STB"));
		assertEquals(9, (int) test.getBaseAttributeScore("STD"));
		assertEquals(12, (int) test.getBaseAttributeScore("STE"));
		assertEquals(12, (int) test.getBaseAttributeScore("STM"));
		assertEquals(9, (int) test.getBaseAttributeScore("STO"));
		assertEquals(12, (int) test.getBaseAttributeScore("STP"));
		assertEquals(10, (int) test.getBaseAttributeScore("STW"));
		assertEquals(12, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(6);
		test.computeFullStats();
		assertEquals(14, (int) test.getBaseAttributeScore("STB"));
		assertEquals(9, (int) test.getBaseAttributeScore("STD"));
		assertEquals(12, (int) test.getBaseAttributeScore("STE"));
		assertEquals(12, (int) test.getBaseAttributeScore("STM"));
		assertEquals(9, (int) test.getBaseAttributeScore("STO"));
		assertEquals(12, (int) test.getBaseAttributeScore("STP"));
		assertEquals(10, (int) test.getBaseAttributeScore("STW"));
		assertEquals(12, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(7);
		test.computeFullStats();
		assertEquals(14, (int) test.getBaseAttributeScore("STB"));
		assertEquals(9, (int) test.getBaseAttributeScore("STD"));
		assertEquals(12, (int) test.getBaseAttributeScore("STE"));
		assertEquals(12, (int) test.getBaseAttributeScore("STM"));
		assertEquals(9, (int) test.getBaseAttributeScore("STO"));
		assertEquals(12, (int) test.getBaseAttributeScore("STP"));
		assertEquals(10, (int) test.getBaseAttributeScore("STW"));
		assertEquals(12, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(8);
		test.computeFullStats();
		assertEquals(14, (int) test.getBaseAttributeScore("STB"));
		assertEquals(9, (int) test.getBaseAttributeScore("STD"));
		assertEquals(12, (int) test.getBaseAttributeScore("STE"));
		assertEquals(12, (int) test.getBaseAttributeScore("STM"));
		assertEquals(9, (int) test.getBaseAttributeScore("STO"));
		assertEquals(12, (int) test.getBaseAttributeScore("STP"));
		assertEquals(10, (int) test.getBaseAttributeScore("STW"));
		assertEquals(12, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(9);
		test.computeFullStats();
		assertEquals(12, (int) test.getBaseAttributeScore("STB"));
		assertEquals(7, (int) test.getBaseAttributeScore("STD"));
		assertEquals(10, (int) test.getBaseAttributeScore("STE"));
		assertEquals(9, (int) test.getBaseAttributeScore("STM"));
		assertEquals(7, (int) test.getBaseAttributeScore("STO"));
		assertEquals(10, (int) test.getBaseAttributeScore("STP"));
		assertEquals(8, (int) test.getBaseAttributeScore("STW"));
		assertEquals(9, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(10);
		test.computeFullStats();
		assertEquals(12, (int) test.getBaseAttributeScore("STB"));
		assertEquals(7, (int) test.getBaseAttributeScore("STD"));
		assertEquals(10, (int) test.getBaseAttributeScore("STE"));
		assertEquals(9, (int) test.getBaseAttributeScore("STM"));
		assertEquals(7, (int) test.getBaseAttributeScore("STO"));
		assertEquals(10, (int) test.getBaseAttributeScore("STP"));
		assertEquals(8, (int) test.getBaseAttributeScore("STW"));
		assertEquals(9, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(11);
		test.computeFullStats();
		assertEquals(12, (int) test.getBaseAttributeScore("STB"));
		assertEquals(7, (int) test.getBaseAttributeScore("STD"));
		assertEquals(10, (int) test.getBaseAttributeScore("STE"));
		assertEquals(9, (int) test.getBaseAttributeScore("STM"));
		assertEquals(7, (int) test.getBaseAttributeScore("STO"));
		assertEquals(10, (int) test.getBaseAttributeScore("STP"));
		assertEquals(8, (int) test.getBaseAttributeScore("STW"));
		assertEquals(9, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(12);
		test.computeFullStats();
		assertEquals(12, (int) test.getBaseAttributeScore("STB"));
		assertEquals(7, (int) test.getBaseAttributeScore("STD"));
		assertEquals(10, (int) test.getBaseAttributeScore("STE"));
		assertEquals(9, (int) test.getBaseAttributeScore("STM"));
		assertEquals(7, (int) test.getBaseAttributeScore("STO"));
		assertEquals(10, (int) test.getBaseAttributeScore("STP"));
		assertEquals(8, (int) test.getBaseAttributeScore("STW"));
		assertEquals(9, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(13);
		test.computeFullStats();
		assertEquals(8, (int) test.getBaseAttributeScore("STB"));
		assertEquals(3, (int) test.getBaseAttributeScore("STD"));
		assertEquals(8, (int) test.getBaseAttributeScore("STE"));
		assertEquals(6, (int) test.getBaseAttributeScore("STM"));
		assertEquals(3, (int) test.getBaseAttributeScore("STO"));
		assertEquals(8, (int) test.getBaseAttributeScore("STP"));
		assertEquals(4, (int) test.getBaseAttributeScore("STW"));
		assertEquals(6, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(14);
		test.computeFullStats();
		assertEquals(8, (int) test.getBaseAttributeScore("STB"));
		assertEquals(3, (int) test.getBaseAttributeScore("STD"));
		assertEquals(8, (int) test.getBaseAttributeScore("STE"));
		assertEquals(6, (int) test.getBaseAttributeScore("STM"));
		assertEquals(3, (int) test.getBaseAttributeScore("STO"));
		assertEquals(8, (int) test.getBaseAttributeScore("STP"));
		assertEquals(4, (int) test.getBaseAttributeScore("STW"));
		assertEquals(6, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(15);
		test.computeFullStats();
		assertEquals(8, (int) test.getBaseAttributeScore("STB"));
		assertEquals(3, (int) test.getBaseAttributeScore("STD"));
		assertEquals(8, (int) test.getBaseAttributeScore("STE"));
		assertEquals(6, (int) test.getBaseAttributeScore("STM"));
		assertEquals(3, (int) test.getBaseAttributeScore("STO"));
		assertEquals(8, (int) test.getBaseAttributeScore("STP"));
		assertEquals(4, (int) test.getBaseAttributeScore("STW"));
		assertEquals(6, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(16);
		test.computeFullStats();
		assertEquals(8, (int) test.getBaseAttributeScore("STB"));
		assertEquals(3, (int) test.getBaseAttributeScore("STD"));
		assertEquals(8, (int) test.getBaseAttributeScore("STE"));
		assertEquals(6, (int) test.getBaseAttributeScore("STM"));
		assertEquals(3, (int) test.getBaseAttributeScore("STO"));
		assertEquals(8, (int) test.getBaseAttributeScore("STP"));
		assertEquals(4, (int) test.getBaseAttributeScore("STW"));
		assertEquals(6, (int) test.getBaseAttributeScore("STS"));
		test.getIo().setLevel(17);
		test.computeFullStats();
		assertEquals(6, (int) test.getBaseAttributeScore("STB"));
		assertEquals(2, (int) test.getBaseAttributeScore("STD"));
		assertEquals(6, (int) test.getBaseAttributeScore("STE"));
		assertEquals(5, (int) test.getBaseAttributeScore("STM"));
		assertEquals(2, (int) test.getBaseAttributeScore("STO"));
		assertEquals(6, (int) test.getBaseAttributeScore("STP"));
		assertEquals(4, (int) test.getBaseAttributeScore("STW"));
		assertEquals(5, (int) test.getBaseAttributeScore("STS"));
		// test THAC0
		test.getIo().setLevel(1);
	}
}