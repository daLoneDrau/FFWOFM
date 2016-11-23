package com.dalonedrow.module.shin_sam.rpg.base.flyweights;

import java.util.HashMap;
import java.util.Map;

import com.dalonedrow.module.shin_sam.rpg.constants.ShinSamEquipmentGlobals;
import com.dalonedrow.rpg.base.flyweights.Attribute;
import com.dalonedrow.rpg.base.flyweights.IOEquipItem;
import com.dalonedrow.rpg.base.flyweights.IoPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * 
 * @author Donald
 *
 */
public class ShinSamPC extends IoPcData<ShinSamIO> {

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
	protected void computeFullStats() throws RPGException {
		super.clearModAbilityScores();
		// get equipment modifiers
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS));
		super.adjustAttributeModifier("AC", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ARMOR_CLASS));
		super.adjustAttributeModifier("END", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ENDURANCE));
		super.adjustAttributeModifier("HP", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINTS));
		super.adjustAttributeModifier("HPR", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINT_RECOVERY));
		super.adjustAttributeModifier("KI", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_KI));
		super.adjustAttributeModifier("MHP", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MAX_HIT_POINTS));
		super.adjustAttributeModifier("MIT", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MIGHT));
		super.adjustAttributeModifier("MKI", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MAX_KI));
		super.adjustAttributeModifier("MR", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MOVEMENT_RATE));
		super.adjustAttributeModifier("PIE", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_PIETY));
		super.adjustAttributeModifier("SPD", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SPEED));
		super.adjustAttributeModifier("SPR", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SPIRITUALITY));
		super.adjustAttributeModifier("ST", super.ARX_EQUIPMENT_Apply(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SAVING_THROW));
		// apply percent modifiers
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ATTACK_BONUS,
		        super.getBaseAttributeScore("AB")
		                + super.getAttributeModifier("AB")));
		super.adjustAttributeModifier("AC", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ARMOR_CLASS,
		        super.getBaseAttributeScore("AC")
		                + super.getAttributeModifier("AC")));
		super.adjustAttributeModifier("END", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_ENDURANCE,
		        super.getBaseAttributeScore("END")
		                + super.getAttributeModifier("END")));
		super.adjustAttributeModifier("HP", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINTS,
		        super.getBaseAttributeScore("HP")
		                + super.getAttributeModifier("HP")));
		super.adjustAttributeModifier("HPR", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_HIT_POINT_RECOVERY,
		        super.getBaseAttributeScore("HPR")
		                + super.getAttributeModifier("HPR")));
		super.adjustAttributeModifier("KI", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_KI,
		        super.getBaseAttributeScore("KI")
		                + super.getAttributeModifier("KI")));
		super.adjustAttributeModifier("MHP", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MAX_HIT_POINTS,
		        super.getBaseAttributeScore("MHP")
		                + super.getAttributeModifier("MHP")));
		super.adjustAttributeModifier("MIT", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MIGHT,
		        super.getBaseAttributeScore("MIT")
		                + super.getAttributeModifier("MIT")));
		super.adjustAttributeModifier("MKI", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MAX_KI,
		        super.getBaseAttributeScore("MKI")
		                + super.getAttributeModifier("MKI")));
		super.adjustAttributeModifier("MR", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_MOVEMENT_RATE,
		        super.getBaseAttributeScore("MR")
		                + super.getAttributeModifier("MR")));
		super.adjustAttributeModifier("PIE", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_PIETY,
		        super.getBaseAttributeScore("PIE")
		                + super.getAttributeModifier("PIE")));
		super.adjustAttributeModifier("SPD", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SPEED,
		        super.getBaseAttributeScore("SPD")
		                + super.getAttributeModifier("SPD")));
		super.adjustAttributeModifier("SPR", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SPIRITUALITY,
		        super.getBaseAttributeScore("SPR")
		                + super.getAttributeModifier("SPR")));
		super.adjustAttributeModifier("ST", super.ARX_EQUIPMENT_ApplyPercent(
		        ShinSamEquipmentGlobals.EQUIPITEM_ELEMENT_SAVING_THROW,
		        super.getBaseAttributeScore("ST")
		                + super.getAttributeModifier("ST")));
		// apply attribute bonuses
		// attacke bonus from might
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
	protected void defineAttributes() throws RPGException {
		Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("AB", new Attribute("AB", "Attack Bonus"));
		map.put("AC", new Attribute("AC", "Armor Class"));
		map.put("END", new Attribute("END", "Endurance"));
		map.put("HP", new Attribute("HP", "Hit Points"));
		map.put("HPR", new Attribute("HPR", "Hit Point Recovery"));
		map.put("KI", new Attribute("KI", "Ki"));
		map.put("MHP", new Attribute("MHP", "Max Hit Points"));
		map.put("MIT", new Attribute("MIT", "Might"));
		map.put("MKI", new Attribute("MKI", "Max Ki"));
		map.put("MR", new Attribute("MR", "Movement Rate"));
		map.put("NLD", new Attribute("NLD", "Non-lethal Damage"));
		map.put("PIE", new Attribute("PIE", "Piety"));
		map.put("SPD", new Attribute("SPD", "Speed"));
		map.put("SPR", new Attribute("SPR", "Spirituality"));
		map.put("ST", new Attribute("ST", "Saving Throw"));
		super.setAttributes(map);
		map = null;
	}

	public int getAttributeBonus(final String attr) {
		final int attribute = (int) (super.getBaseAttributeScore(attr)
		        + super.getAttributeModifier(attr));
		int bonus = 0;
		switch (attribute) {
		case 1:
			bonus = -2;
			break;
		case 2:
			bonus = -1;
			break;
		case 9:
			bonus = 1;
			break;
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

}
