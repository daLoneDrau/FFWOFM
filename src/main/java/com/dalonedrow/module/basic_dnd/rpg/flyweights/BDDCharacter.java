package com.dalonedrow.module.basic_dnd.rpg.flyweights;

import java.util.HashMap;
import java.util.Map;

import com.dalonedrow.module.basic_dnd.rpg.constants.BDDEquipmentGlobals;
import com.dalonedrow.rpg.base.flyweights.Attribute;
import com.dalonedrow.rpg.base.flyweights.IOEquipItem;
import com.dalonedrow.rpg.base.flyweights.IoPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

public final class BDDCharacter extends IoPcData<BDDInteractiveObject> {

	public BDDCharacter() throws RPGException {
		super();
	}

	@Override
	public boolean isInCombat() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getLifeAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void adjustMana(float dmg) {
		// TODO Auto-generated method stub
		
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
		super.adjustAttributeModifier(AC, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_AC));
		super.adjustAttributeModifier(AB, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS));
		super.adjustAttributeModifier(ABD, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS_DRAGONS));
		super.adjustAttributeModifier(ABG, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS_GOBLINS));
		super.adjustAttributeModifier(ABK, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS_KOBOLDS));
		super.adjustAttributeModifier(ABL, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS_LYCANS));
		super.adjustAttributeModifier(ABO, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS_ORCS));
		super.adjustAttributeModifier(ABR, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS_REPTILES));
		super.adjustAttributeModifier(ABS, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS_SUMMONED));
		super.adjustAttributeModifier(ABT, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS_TROLLS_UNDEAD));
		super.adjustAttributeModifier(AC, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_AC));
		super.adjustAttributeModifier(CHA, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_CHA));
		super.adjustAttributeModifier(CON, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_CON));
		super.adjustAttributeModifier(DMG, super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_DAMAGES));
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS));
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS));
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS));
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS));
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS));
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS));
		super.adjustAttributeModifier("AB", super.ARX_EQUIPMENT_Apply(
		        BDDEquipmentGlobals.IO_EQUIP_ELEMENT_ATTACK_BONUS));
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
	private static final String AB = "AB";
	private static final String ABD = "ABD";
	private static final String ABG = "ABG";
	private static final String ABK = "ABK";
	private static final String ABL = "ABL";
	private static final String ABO = "ABO";
	private static final String ABR = "ABR";
	private static final String ABS = "ABS";
	private static final String ABT = "ABT";
	private static final String AC = "AC";
	private static final String CHA = "CHA";
	private static final String CON = "CON";
	private static final String DMG = "DMG";
	private static final String DEX = "DEX";
	private static final String HP = "HP";
	private static final String MHP = "MHP";
	private static final String ST_BREATH = "ST_BREATH";
	private static final String ST_GAZE = "ST_GAZE";
	private static final String ST_MAGIC_FIRE = "ST_MAGIC_FIRE";
	private static final String ST_POISON = "ST_POISON";
	private static final String ST_RAY = "ST_RAY";
	private static final String ST_SPELL = "ST_SPELL";
	private static final String ST_STAFF = "ST_STAFF";
	private static final String ST_WAND = "ST_WAND";
	private static final String STR = "STR";
	private static final String WIS = "WIS";
	@Override
	protected void defineAttributes() throws RPGException {
		Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put(AB, new Attribute(AB, "Attack Bonus"));
		map.put(ABD, new Attribute(ABD, "Attack Bonus vs. Dragons"));
		map.put(ABG, new Attribute(ABG, "Attack Bonus vs. Goblins"));
		map.put(ABK, new Attribute(ABK, "Attack Bonus vs. Kobolds"));
		map.put(ABL, new Attribute(ABL, "Attack Bonus vs. Lycanthropes"));
		map.put(ABO, new Attribute(ABO, "Attack Bonus vs. Orcs"));
		map.put(ABR, new Attribute(ABR, "Attack Bonus vs. Reptiles"));
		map.put(ABS, new Attribute(
				ABS, "Attack Bonus vs. Summoned Entities"));
		map.put(ABT, new Attribute(
				ABT, "Attack Bonus vs. Trolls or Undead"));
		map.put(AC, new Attribute(AC, "Armor Class"));
		map.put(CHA, new Attribute(CHA, "Charisma"));
		map.put(CON, new Attribute(CON, "Constitution"));
		map.put(DMG, new Attribute(DMG, "Damage"));
		map.put(DEX, new Attribute(DEX, "Dexterity"));
		map.put(HP, new Attribute(HP, "Hit Points"));
		map.put(MHP, new Attribute("MHP", "Maximum Hit Points"));
		map.put(ST_BREATH, new Attribute(
				ST_BREATH, "Saving Throw vs. Breath Weapons"));
		map.put(ST_GAZE, new Attribute(
				ST_GAZE, "Saving Throw vs. Gaze Attacks"));
		map.put(ST_MAGIC_FIRE, new Attribute(
				ST_MAGIC_FIRE, "Saving Throw vs. Magical Fire"));
		map.put(ST_POISON, new Attribute(
				ST_POISON, "Saving Throw vs. Poison"));
		map.put(ST_RAY, new Attribute(
				ST_RAY, "Saving Throw vs. Rays"));
		map.put(ST_SPELL, new Attribute(
				ST_SPELL, "Saving Throw vs. Spells"));
		map.put(ST_STAFF, new Attribute(
				ST_STAFF, "Saving Throw vs. Staves"));
		map.put(ST_WAND, new Attribute(
				ST_WAND, "Saving Throw vs. Wands"));
		map.put(STR, new Attribute(STR, "Strength"));
		map.put(WIS, new Attribute(WIS, "Wisdom"));
		super.setAttributes(map);
		map = null;
	}

	@Override
	protected float getBaseLife() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected float getBaseMana() {
		// TODO Auto-generated method stub
		return 0;
	}

}
