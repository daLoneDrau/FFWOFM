package com.dalonedrow.module.basic_dnd.rpg.flyweights;

import com.dalonedrow.module.shin_sam.rpg.constants.ShinSamEquipmentGlobals;
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
		{"STR", "Strength", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_STRENGTH },
		{"DEX", "Dexterity", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_DEXTERITY },
		{"CON", "Constitution", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_CONSTITUTION },
		{"INT", "Intelligence", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_INTELLIGENCE },
		{"WIS", "Wisdom", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_WISDOM },
		{"CHA", "Charisma", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_CHARISMA },
		{"AC", "Armor Class", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_ARMOR_CLASS },
		{"HP", "Hit Points", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINTS },
		{"MHP", "Max Hit Points", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINTS },
		{"THB", "To-Hit Bonus", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_TO_HIT_BONUS },
		{"DB", "Damage Bonus", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_DAMAGE_BONUS },
		{"FDB", "Force Doors Bonus", BDDEquipmentGlobals.EQUIPITEM_ELEMENT_FORCE_DOORS_BONUS },
	};
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
		if (super.getBaseAttributeScore(
		        "KI") > super.getFullAttributeScore("MKI")) {
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
	public void ARX_EQUIPMENT_RecreatePlayerMesh() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canIdentifyEquipment(IOEquipItem equipitem) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void applyRulesModifiers() {
		// attack bonus from might
		super.adjustAttributeModifier("AB", getAttributeModifier("MIT"));
		// armour class bonus from speed
		super.adjustAttributeModifier("AC", getAttributeModifier("SPD"));
		// max ki bonus from spirituality/piety
		if (Script.getInstance().isIOInGroup(super.getIo(), "SHUGENJA")) {
			super.adjustAttributeModifier(
			        "MKI", getAttributeModifier("SPR"));
		} else if (Script.getInstance().isIOInGroup(
		        super.getIo(), "SOHEI")) {
			super.adjustAttributeModifier(
			        "MKI", getAttributeModifier("PIE"));
		}
	}
	@Override
	protected void applyRulesPercentModifiers() {
	}
	public int getAttributeBonus(final String attr) {
		final int attribute = (int) (super.getBaseAttributeScore(attr)
		        + super.getAttributeModifier(attr));
		int bonus = 0;
		switch (attribute) {
		case 1:
		case 2:
			bonus = -2;
			break;
		case 3:
		case 4:
			bonus = -1;
			break;
		case 7:
		case 8:
			bonus = 1;
			break;
		case 9:
		case 10:
			bonus = 2;
			break;
		default:
			break;
		}
		return bonus;
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
	public boolean isInCombat() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getLifeAttribute() {
		return "HP";
	}

	@Override
	protected Object[][] getAttributeMap() {
		return BDDPC.attributeMap;
	}
}
