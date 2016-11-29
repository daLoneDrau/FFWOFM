package com.dalonedrow.module.basic_dnd.rpg.flyweights;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.module.basic_dnd.rpg.constants.BDDEquipmentGlobals;
import com.dalonedrow.rpg.base.flyweights.IOEquipItem;
import com.dalonedrow.rpg.base.flyweights.IoPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 *
 * @author Donald
 *
 */
public class BDDPC extends IoPcData<BDDIO> {
	/** the list of attributes and their matching names and modifiers. */
	private static Object[][] attributeMap = new Object[][] {
			{ "ABD", "Attack Bonus vs. Dragons", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_DRAGONS },
			{ "ABG", "Attack Bonus vs. Goblinoids", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_GOBLINS },
			{ "ABK", "Attack Bonus vs. Kobolds", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_KOBOLDS },
			{ "ABL", "Attack Bonus vs. Lycanthropes", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_LYCANS },
			{ "ABM", "Attack Bonus vs. Mystical Monsters",
					BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_MAGICAL_MONSTERS },
			{ "ABR", "Attack Bonus vs. Regenerating Monsters",
					BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_REGENERATING },
			{ "ABU", "Attack Bonus vs. Undead", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_UNDEAD },
			{ "ABW", "Attack Bonus vs. Wizards", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS_WIZARDS },
			{ "AC", "Armor Class", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ARMOR_CLASS },
			{ "CHA", "Charisma", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_CHARISMA },
			{ "CON", "Constitution", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_CONSTITUTION },
			{ "DB", "Damage Bonus", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_DAMAGE_BONUS },
			{ "DEX", "Dexterity", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_DEXTERITY },
			{ "FDB", "Force Doors Bonus", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_FORCE_DOORS_BONUS },
			{ "HP", "Hit Points", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINTS },
			{ "IM", "Initiative Modifer", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_INITIATIVE_BONUS },
			{ "INT", "Intelligence", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_INTELLIGENCE },
			{ "LAB", "Melee Bonus", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_MELEE_ATTACK_BONUS },
			{ "MAB", "Missile Attack Bonus", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_MISSILE_ATTACK_BONUS },
			{ "MHP", "Max Hit Points", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_MAX_HIT_POINTS },
			{ "RA", "Reaction", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_REACTION },
			{ "STB", "Saving Throw vs. Breath Weapons", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_BREATH },
			{ "STD", "Saving Throw vs. Death", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_DEATH },
			{ "STE", "Saving Throw vs. Petrification", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_PETRIFY },
			{ "STM", "Saving Throw vs. Magic", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_MAGIC },
			{ "STO", "Saving Throw vs. Poison", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_POISON },
			{ "STP", "Saving Throw vs. Paralyzation", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_PARALYZE },
			{ "STR", "Strength", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_STRENGTH },
			{ "STW", "Saving Throw vs. Wands", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_WAND },
			{ "STS", "Saving Throw vs. Spell Devices", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ST_SPELL_DEVICES },
			{ "WIS", "Wisdom", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_WISDOM } };

	/**
	 * Creates a new instance of {@link BDDPC}.
	 * 
	 * @throws RPGException
	 */
	public BDDPC() throws RPGException {
		super();
	}

	@Override
	protected void adjustMana(final float dmg) {
		super.setBaseAttributeScore("KI", super.getBaseAttributeScore("KI") + dmg);
		if (super.getBaseAttributeScore("KI") > super.getFullAttributeScore("MKI")) {
			// if Hit Points now > max
			super.setBaseAttributeScore("KI", super.getFullAttributeScore("MKI"));
		}
		if (super.getBaseAttributeScore("KI") < 0f) {
			// if Stamina now < 0
			super.setBaseAttributeScore("KI", 0f);
		}
	}

	@Override
	protected void applyRulesModifiers() {
		// attack bonus from might
		super.adjustAttributeModifier("AB", getAttributeModifier("MIT"));
		// armour class bonus from speed
		super.adjustAttributeModifier("AC", getAttributeModifier("SPD"));
		// max ki bonus from spirituality/piety
		if (Script.getInstance().isIOInGroup(super.getIo(), "SHUGENJA")) {
			super.adjustAttributeModifier("MKI", getAttributeModifier("SPR"));
		} else if (Script.getInstance().isIOInGroup(super.getIo(), "SOHEI")) {
			super.adjustAttributeModifier("MKI", getAttributeModifier("PIE"));
		}
	}

	@Override
	protected void applyRulesPercentModifiers() {
	}

	@Override
	public void ARX_EQUIPMENT_RecreatePlayerMesh() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canIdentifyEquipment(IOEquipItem equipitem) {
		// TODO Auto-generated method stub
		return false;
	}

	public int getAttributeBonus(final String attr) {
		final int attribute = (int) (super.getBaseAttributeScore(attr) + super.getAttributeModifier(attr));
		int bonus = 0;
		switch (attribute) {
		case 1:
		case 2:
		case 3:
			bonus = -3;
			break;
		case 4:
		case 5:
			bonus = -2;
			break;
		case 6:
		case 7:
		case 8:
			bonus = -1;
			break;
		case 13:
		case 14:
		case 15:
			bonus = 1;
			break;
		case 16:
		case 17:
			bonus = 2;
			break;
		case 18:
		case 19:
		case 20:
			bonus = 3;
			break;
		default:
			break;
		}
		return bonus;
	}

	@Override
	protected Object[][] getAttributeMap() {
		return BDDPC.attributeMap;
	}

	@Override
	protected float getBaseLife() {
		return super.getFullAttributeScore("HP");
	}

	@Override
	protected float getBaseMana() {
		return super.getFullAttributeScore("KI");
	}

	@Override
	protected String getLifeAttribute() {
		return "HP";
	}

	@Override
	public boolean isInCombat() {
		// TODO Auto-generated method stub
		return false;
	}
	private static final int THREE = 3;
	private static final int D6 = 6;
	private static final int ATTR_MIN = 3;
	private static final int ATTR_MAX = 18;
	/**
	 * Rolls for a new attribute between 3-18.
	 * @return {@link int}
	 */
	private int rollNewAttribute() {
		return rollNewAttribute(ATTR_MIN, ATTR_MAX);
	}
	/**
	 * Rolls for a new attribute between the min and max allowed.
	 * @param min the minimum attribute score
	 * @param max the maximum attribute score
	 * @return {@link int}
	 */
	private int rollNewAttribute(final int min, final int max) {
		int roll = Diceroller.getInstance().rollXdY(THREE, D6);
		while (roll < min
				|| roll > max) {
			roll = Diceroller.getInstance().rollXdY(THREE, D6);
		}
		return roll;
	}
	private int getMaxOfThreeRolls() {
		int roll = rollNewAttribute();
		for (int i = 1; i >= 0; i--) {
			roll = Math.max(roll, rollNewAttribute());
		}
		return roll;
	}
	public void rollNewCleric() {
		super.setBaseAttributeScore("STR", getMaxOfThreeRolls());
		super.setBaseAttributeScore("DEX", getMaxOfThreeRolls());
		super.setBaseAttributeScore("CON", getMaxOfThreeRolls());
		super.setBaseAttributeScore("WIS", getMaxOfThreeRolls());
		super.setBaseAttributeScore("INT", getMaxOfThreeRolls());
		super.setBaseAttributeScore("CHA", getMaxOfThreeRolls());
		Script.getInstance().addToGroup(super.getIo(), "CLERIC");
	}
}
