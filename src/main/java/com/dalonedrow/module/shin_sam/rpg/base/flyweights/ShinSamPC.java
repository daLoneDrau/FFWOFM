package com.dalonedrow.module.shin_sam.rpg.base.flyweights;

import com.dalonedrow.module.shin_sam.rpg.constants.ShinSamEquipmentGlobals;
import com.dalonedrow.rpg.base.flyweights.IOEquipItem;
import com.dalonedrow.rpg.base.flyweights.IOPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * 
 * @author Donald
 *
 */
public class ShinSamPC extends IOPcData<ShinSamIO> {
	/**
	 * Creates a new instance of {@link ShinSamPC}.
	 * @throws RPGException
	 */
	public ShinSamPC() throws RPGException {
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
	/** the list of attributes and their matching names and modifiers. */
	private static Object[][] attributeMap = new Object[][] {
		{"AB", "Attack Bonus", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS},
		{"AC", "Armor Class", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ARMOR_CLASS},
		{"END", "Endurance", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ENDURANCE},
		{"HP", "Points", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINTS},
		{"HPR", "Hit Point Recovery", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINT_RECOVERY},
		{"KI", "Ki", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_KI},
		{"MHP", "Max Hit Points", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MAX_HIT_POINTS},
		{"MIT", "Might", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MIGHT},
		{"MKI", "Max Ki", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MAX_KI},
		{"MR", "Movement Rate", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MOVEMENT_RATE},
		{"NLD", "Non-lethal Damage", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_NON_LETHAL_DAMAGE},
		{"PIE", "Piety", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_PIETY},
		{"SPD", "Speed", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SPEED},
		{"SPR", "Spirituality", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SPIRITUALITY},
		{"ST", "Saving Throw", ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SAVING_THROW}
	};

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
		return ShinSamPC.attributeMap;
	}
}
