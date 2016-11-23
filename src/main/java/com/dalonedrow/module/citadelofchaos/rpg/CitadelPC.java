package com.dalonedrow.module.citadelofchaos.rpg;

import java.util.HashMap;
import java.util.Map;

import com.dalonedrow.module.tft.rpg.TFTCharacterArchetype;
import com.dalonedrow.module.tft.rpg.TFTHumanoidRaces;
import com.dalonedrow.module.tft.rpg.TFTSpell;
import com.dalonedrow.module.tft.rpg.TFTTalent;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.constants.Gender;
import com.dalonedrow.rpg.base.flyweights.Attribute;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.IoPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * @author drau
 */
public final class CitadelPC extends IoPcData {
	/** flag indicating pretty printing has been turned on. */
	private boolean		prettyPrinting;
	/** the list of spells the character has. */
	private char[][]	spells;
	public CitadelPC() throws RPGException {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.IOPCData#defineAttributes()
	 */
	@Override
	protected void defineAttributes() throws RPGException {
		Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("SK", new Attribute("SK", "Skill"));
		map.put("ST", new Attribute("ST", "Stamina"));
		map.put("LK", new Attribute("LK", "Luck"));
		map.put("MSK", new Attribute("MSK", "Max Skill"));
		map.put("MST", new Attribute("MST", "Max Stamina"));
		map.put("MLK", new Attribute("MLK", "Max Luck"));
		super.setAttributes(map);
		map = null;
	}
	@Override
	protected int getNumberOfEquipmentSlots() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Determines if the character knows a specific spell.
	 * @param spell the spell short name
	 * @return <tt>true</tt> if the character knows the spell; <tt>false</tt>
	 *         otherwise
	 */
	public boolean knowsSpell(final char[] spell) {
		if (spells == null) {
			spells = new char[0][];
		}
		boolean found = false;
		for (int i = spells.length - 1; i >= 0; i--) {
			if (new String(spells[i]).equalsIgnoreCase(new String(spell))) {
				found = true;
				break;
			}
		}
		return found;
	}
	/**
	 * Learns a spell.
	 * @param spell the spell's short name
	 * @throws RPGException if the spell name is invalid
	 */
	public void learnSpell(final char[] spell) throws RPGException {
		if (spells == null) {
			spells = new char[0][];
		}
		if (TFTSpell.valueOf(spell) == null) {
			throw new RPGException(ErrorMessage.INVALID_PARAM,
					"No such spell, " + new String(spell) + ", exists");
		}
		if (!knowsSpell(spell)) {
			spells = ArrayUtilities.getInstance().extendArray(spell, spells);
		}
	}
	/**
	 * Sets the value of the flag indicating pretty printing has been turned on.
	 * @param flag the new value to set
	 */
	public void setPrettyPrinting(final boolean flag) {
		prettyPrinting = flag;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = null;
		try {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			if (prettyPrinting) {
				sb.append(new String(super.getName()));
				sb.append('\n');
				sb.append(new String(TFTHumanoidRaces.valueOf(
						super.getRace()).getFullName()));
				sb.append(' ');
				sb.append(Gender.GENDERS[super.getGender()]);
				sb.append('\n');
				TFTCharacterArchetype archetype =
						TFTCharacterArchetype.valueOf(getProfession());
				archetype.setPrettyPrinting(true);
				sb.append(archetype);
				sb.append("ST: ");
				sb.append(super.getFullAttributeScore("ST"));
				sb.append('\n');
				sb.append("DX: ");
				sb.append(super.getFullAttributeScore("DX"));
				sb.append('\n');
				sb.append("IQ: ");
				sb.append(super.getFullAttributeScore("IQ"));
				sb.append('\n');
				for (int i = 0, len = talents.length; i < len; i++) {
					TFTTalent talent = TFTTalent.valueOf(talents[i]);
					talent.setPrettyPrinting(true);
					sb.append(talent);
				}
				prettyPrinting = false;
			} else {
			}
			s = sb.toString();
			sb.returnToPool();
			sb = null;
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	/**
	 * Unlearns a spell the character knows.
	 * @param spell the spell's short name
	 */
	public void unlearnSpell(final char[] spell) {
		if (spells == null) {
			spells = new char[0][];
		}
		int index = -1;
		for (int i = spells.length - 1; i >= 0; i--) {
			if (new String(spells[i]).equalsIgnoreCase(new String(spell))) {
				index = i;
				break;
			}
		}
		if (index >= 0) {
			spells = ArrayUtilities.getInstance().removeIndex(index, spells);
		}
	}
}
