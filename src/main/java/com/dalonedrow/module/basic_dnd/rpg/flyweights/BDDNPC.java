package com.dalonedrow.module.basic_dnd.rpg.flyweights;

import com.dalonedrow.module.basic_dnd.rpg.constants.BDDEquipmentGlobals;
import com.dalonedrow.rpg.base.flyweights.IoNpcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class BDDNPC extends IoNpcData<BDDIO> {
	/** the list of attributes and their matching names and modifiers. */
	private static Object[][] attributeMap = new Object[][] {
	        { "ABD", "Attack Bonus vs. Dragons",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_DRAGONS },
	        { "ABG", "Attack Bonus vs. Goblinoids",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_GOBLINS },
	        { "ABK", "Attack Bonus vs. Kobolds",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_KOBOLDS },
	        { "ABL", "Attack Bonus vs. Lycanthropes",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_LYCANS },
	        { "ABM", "Attack Bonus vs. Mystical Monsters",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_MAGICAL_MONSTERS },
	        { "ABR", "Attack Bonus vs. Regenerating Monsters",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_REGENERATING },
	        { "ABU", "Attack Bonus vs. Undead",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_UNDEAD },
	        { "ABW", "Attack Bonus vs. Wizards",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_WIZARDS },
	        { "AC", "Armor Class",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ARMOR_CLASS },
	        { "CHA", "Charisma",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_CHARISMA },
	        { "CON", "Constitution",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_CONSTITUTION },
	        { "DB", "Damage Bonus",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_DAMAGE_BONUS },
	        { "DEX", "Dexterity",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_DEXTERITY },
	        { "FDB", "Force Doors Bonus",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_FORCE_DOORS_BONUS },
	        { "HP", "Hit Points",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINTS },
	        { "IM", "Initiative Modifer",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_INITIATIVE_BONUS },
	        { "INT", "Intelligence",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_INTELLIGENCE },
	        { "LAB", "Melee Bonus",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_MELEE_ATTACK_BONUS },
	        { "MAB", "Missile Attack Bonus",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_MISSILE_ATTACK_BONUS },
	        { "MHP", "Max Hit Points",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_MAX_HIT_POINTS },
	        { "PRX", "Prime Requisite Experience Adjustment",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_PRIME_REQUISITE_XP_BONUS },
	        { "RA", "Reaction",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_REACTION },
	        { "STB", "Saving Throw vs. Breath Weapons",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_BREATH },
	        { "STD", "Saving Throw vs. Death",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_DEATH },
	        { "STE", "Saving Throw vs. Petrification",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_PETRIFY },
	        { "STM", "Saving Throw vs. Magic",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_MAGIC },
	        { "STO", "Saving Throw vs. Poison",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_POISON },
	        { "STP", "Saving Throw vs. Paralyzation",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_PARALYZE },
	        { "STR", "Strength",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_STRENGTH },
	        { "STW", "Saving Throw vs. Wands",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_WAND },
	        { "STS", "Saving Throw vs. Spell Devices",
	                BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_SPELL_DEVICES },
	        { "WIS", "Wisdom", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_WISDOM } };

	public BDDNPC() throws RPGException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void adjustLife(float dmg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void adjustMana(float dmg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ARX_NPC_ManagePoison() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void defineAttributes() throws RPGException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getBaseLife() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getBaseMana() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean hasLifeRemaining() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void moveToInitialPosition() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void restoreLifeToMax() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stopActiveAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void stopIdleAnimation() {
		// TODO Auto-generated method stub
		
	}

}
