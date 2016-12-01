package com.dalonedrow.module.basic_dnd.rpg.flyweights;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.module.basic_dnd.rpg.constants.BDDEquipmentGlobals;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.IOEquipItem;
import com.dalonedrow.rpg.base.flyweights.IoPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author Donald
 */
public class BDDPC extends IoPcData<BDDIO> {
	private static final int ATTR_MAX = 18;
	private static final int ATTR_MIN = 3;
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
	/** D6. */
	/**
	 * Creates a new instance of {@link BDDPC}.
	 * @throws RPGException
	 */
	public BDDPC() throws RPGException {
		super();
	}
	@Override
	protected void adjustMana(final float dmg) {
		super.setBaseAttributeScore("KI",
		        super.getBaseAttributeScore("KI") + dmg);
		if (super.getBaseAttributeScore("KI") > super.getFullAttributeScore(
		        "MKI")) {
			// if Hit Points now > max
			super.setBaseAttributeScore("KI",
			        super.getFullAttributeScore("MKI"));
		}
		if (super.getBaseAttributeScore("KI") < 0f) {
			// if Stamina now < 0
			super.setBaseAttributeScore("KI", 0f);
		}
	}
	@Override
	protected void applyRulesModifiers() throws RPGException {
		// *********************************************************************
		// STRENGTH BONUSES
		int mod = getAttributeBonus("STR");
		// melee attack bonus from might
		super.adjustAttributeModifier("LAB", mod);
		// damage bonus from might
		super.adjustAttributeModifier("DB", mod);
		// force doors bonus from might
		super.adjustAttributeModifier("FDB", mod);
		// *********************************************************************
		// DEXTERITY BONUSES
		mod = getAttributeBonus("DEX");
		// armor class bonus from speed
		super.adjustAttributeModifier("AC", -mod);
		// missile attack bonus from speed
		super.adjustAttributeModifier("MAB", mod);
		// initiative modifer from speed
		if ((int) super.getFullAttributeScore("DEX") <= 5) {
			super.adjustAttributeModifier("IM", mod + 1);
		} else if ((int) super.getFullAttributeScore("DEX") >= 16) {
			super.adjustAttributeModifier("IM", mod - 1);
		} else {
			super.adjustAttributeModifier("IM", mod);
		}
		// *********************************************************************
		// CONSITUTION BONUSES
		// hit point bonus from endurance
		mod = getAttributeBonus("CON");
		mod *= super.getLevel();
		if (mod >= super.getFullAttributeScore("MHP")) {
			mod = (int) (super.getFullAttributeScore("MHP")) - 1;
		}
		super.adjustAttributeModifier("MHP", mod);
		// *********************************************************************
		// WISDOM BONUSES
		mod = getAttributeBonus("WIS");
		// all saving throw bonuses from wisdom
		super.adjustAttributeModifier("STB", mod);
		super.adjustAttributeModifier("STD", mod);
		super.adjustAttributeModifier("STE", mod);
		super.adjustAttributeModifier("STM", mod);
		super.adjustAttributeModifier("STO", mod);
		super.adjustAttributeModifier("STP", mod);
		super.adjustAttributeModifier("STW", mod);
		super.adjustAttributeModifier("STS", mod);
		// *********************************************************************
		// CHARISMA BONUSES
		mod = getAttributeBonus("CHA");
		// reaction bonus from charisma
		if ((int) super.getFullAttributeScore("CHA") <= 5) {
			super.adjustAttributeModifier("RA", -mod - 1);
		} else if ((int) super.getFullAttributeScore("CHA") >= 16) {
			super.adjustAttributeModifier("RA", -mod + 1);
		} else {
			super.adjustAttributeModifier("RA", -mod);
		}
		// *********************************************************************
		// SAVING THROWS
		if (super.getIo().isInGroup("CLERIC")) {
			setClericSavingThrows();
		}
	}
	@Override
	protected void applyRulesPercentModifiers() {
		// *********************************************************************
		// PRIME REQUISITE BONUS
		if (super.getIo().isInGroup("CLERIC")) {
			int wis = (int) (super.getBaseAttributeScore("WIS"));
			if (wis <= 5) {
				super.setBaseAttributeScore("PRX", -0.1f);
			} else if (wis <= 8) {
				super.setBaseAttributeScore("PRX", -0.05f);
			} else if (wis >= 16) {
				super.setBaseAttributeScore("PRX", 0.1f);
			} else if (wis >= 13) {
				super.setBaseAttributeScore("PRX", 0.05f);
			} else {
				super.setBaseAttributeScore("PRX", 0f);
			}
		}
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
		final int attribute = (int) (super.getFullAttributeScore(attr));
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
	private int getMaxOfThreeRolls() {
		int roll = rollNewAttribute();
		for (int i = 1; i >= 0; i--) {
			roll = Math.max(roll, rollNewAttribute());
		}
		return roll;
	}
	@Override
	public boolean isInCombat() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 *
	 */
	public void print() {
		PooledStringBuilder sb =
		        StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append("STR  ");
			sb.append(super.getFullAttributeScore("STR"));
			sb.append('\n');
			sb.append("DEX  ");
			sb.append(super.getFullAttributeScore("DEX"));
			sb.append('\n');
			sb.append("CON  ");
			sb.append(super.getFullAttributeScore("CON"));
			sb.append('\n');
			sb.append("INT  ");
			sb.append(super.getFullAttributeScore("INT"));
			sb.append('\n');
			sb.append("WIS  ");
			sb.append(super.getFullAttributeScore("WIS"));
			sb.append('\n');
			sb.append("CHA  ");
			sb.append(super.getFullAttributeScore("CHA"));
			sb.append('\n');
			sb.append(super.getFullAttributeScore("HP"));
			sb.append('/');
			sb.append(super.getFullAttributeScore("MHP"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sb.toString());
		sb.returnToPool();
		sb = null;
	}
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
		int roll = Dice.THREE_D6.roll();
		while (roll < min
		        || roll > max) {
			roll = Dice.THREE_D6.roll();
		}
		return roll;
	}
	/**
	 * Rolls a new Cleric character.
	 * @throws RPGException if an error occurs
	 */
	public void rollNewCleric() throws RPGException {
		super.setLevel(1);
		super.clearModAbilityScores();
		// roll stats
		super.setBaseAttributeScore("STR", getMaxOfThreeRolls());
		super.setBaseAttributeScore("DEX", getMaxOfThreeRolls());
		super.setBaseAttributeScore("CON", getMaxOfThreeRolls());
		super.setBaseAttributeScore("WIS", getMaxOfThreeRolls());
		super.setBaseAttributeScore("INT", getMaxOfThreeRolls());
		super.setBaseAttributeScore("CHA", getMaxOfThreeRolls());
		super.setBaseAttributeScore("MHP", Dice.ONE_D6.roll());
		super.getIo().addGroup(Groups.CLERIC.toString());
		super.getIo().addGroup(Groups.HUMAN.toString());
		super.computeFullStats();
		// set full health
		super.setBaseAttributeScore("HP", super.getBaseAttributeScore("MHP"));
		super.adjustAttributeModifier("HP", super.getAttributeModifier("MHP"));
	}
	/** Sets all saving throws for the Cleric class. */
	private void setClericSavingThrows() {
		int breath = 16, deathPoison = 11, petrifyParalyze = 14,
		        spellsStaves = 15, wands = 12;
		int level = super.getIo().getLevel();
		if (level > 17) { // cleric saving throw tables stop at level 17
			level = 17;
		}
		switch (level) {
		case 17:
			breath -= 2;
			deathPoison--;
			petrifyParalyze -= 2;
			spellsStaves--;
		case 13:
		case 14:
		case 15:
		case 16:
			breath -= 4;
			deathPoison -= 4;
			petrifyParalyze -= 2;
			wands -= 4;
			spellsStaves -= 3;
		case 9:
		case 10:
		case 11:
		case 12:
			breath -= 2;
			deathPoison -= 2;
			petrifyParalyze -= 2;
			wands -= 2;
			spellsStaves -= 3;
		case 5:
		case 6:
		case 7:
		case 8:
			breath -= 2;
			deathPoison -= 2;
			petrifyParalyze -= 2;
			wands -= 2;
			spellsStaves -= 3;
			break;
		}
		super.setBaseAttributeScore("STB", breath);
		super.setBaseAttributeScore("STD", deathPoison);
		super.setBaseAttributeScore("STE", petrifyParalyze);
		super.setBaseAttributeScore("STM", spellsStaves);
		super.setBaseAttributeScore("STO", deathPoison);
		super.setBaseAttributeScore("STP", petrifyParalyze);
		super.setBaseAttributeScore("STW", wands);
		super.setBaseAttributeScore("STS", spellsStaves);
	}
}
