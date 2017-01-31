package com.dalonedrow.module.tft.rpg;

import java.util.HashMap;
import java.util.Map;

import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.constants.Gender;
import com.dalonedrow.rpg.base.flyweights.Attribute;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.IOPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * @author drau
 */
public final class TFTCharacter extends IOPcData {
	/** the penalty applied to hero's IQ costs when learning spells. */
	public static final int	HERO_SPELL_PENALTY	= 3;
	/** flag indicating pretty printing has been turned on. */
	private boolean			prettyPrinting;
	/** the list of spells the character has. */
	private char[][]		spells;
	/** the list of talents the character has. */
	private char[][]		talents;
	public TFTCharacter() throws RPGException {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * Adds a talent the character knows.
	 * @param talent the talent's short name
	 * @throws RPGException if the talent name is invalid
	 */
	public void addTalent(final char[] talent) throws RPGException {
		if (talents == null) {
			talents = new char[0][];
		}
		if (TFTTalent.valueOf(talent) == null) {
			throw new RPGException(ErrorMessage.INVALID_PARAM,
					"No such talent, " + new String(talent) + ", exists");
		}
		if (!hasTalent(talent)) {
			talents = ArrayUtilities.getInstance().extendArray(talent, talents);
		}
	}
	/** Clears all talents. */
	public void clearTalents() {
		talents = new char[0][];
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.IOPCData#defineAttributes()
	 */
	@Override
	protected void defineAttributes() throws RPGException {
		Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("ST", new Attribute("ST", "Strength"));
		map.put("DX", new Attribute("DX", "Dexterity"));
		map.put("IQ", new Attribute("IQ", "Intelligence"));
		super.setAttributes(map);
		map = null;
	}
	@Override
	protected int getNumberOfEquipmentSlots() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Gets the total IQ Cost of all spells the character has.
	 * @return {@link int}
	 */
	public int getTotalSpellIQCost() {
		int total = 0;

		if (spells == null) {
			spells = new char[0][];
		}
		for (int i = spells.length - 1; i >= 0; i--) {
			total++;
		}
		if (!isWizard()) {
			total *= HERO_SPELL_PENALTY;
		}
		return total;
	}
	/**
	 * Gets the total IQ Cost of all talents the character has.
	 * @return {@link int}
	 */
	public int getTotalTalentIQCost() {
		int total = 0;

		if (talents == null) {
			talents = new char[0][];
		}
		for (int i = talents.length - 1; i >= 0; i--) {
			TFTTalent talent = TFTTalent.valueOf(talents[i]);
			if (isWizard() && talent.hasWizardPenalty()) {
				total += talent.getIqCost() * 2;
			} else {
				total += talent.getIqCost();
			}
			talent = null;
		}
		return total;
	}
	/**
	 * Determines if the character has a specific talent.
	 * @param talent the talent's short name
	 * @return <tt>true</tt> if the character has the talent; <tt>false</tt>
	 *         otherwise
	 */
	public boolean hasTalent(final char[] talent) {
		if (talents == null) {
			talents = new char[0][];
		}
		boolean found = false;
		for (int i = talents.length - 1; i >= 0; i--) {
			if (new String(talents[i]).equalsIgnoreCase(new String(talent))) {
				found = true;
				break;
			}
		}
		return found;
	}
	/**
	 * Determines if the character has a specific talent.
	 * @param talent the talent's short name
	 * @return <tt>true</tt> if the character has the talent; <tt>false</tt>
	 *         otherwise
	 */
	public boolean hasTalent(final String talent) {
		return hasTalent(talent.toCharArray());
	}
	/**
	 * Determines if a character is a wizard.
	 * @return <tt>true</tt> if the character is a wizard; <tt>false</tt>
	 *         otherwise
	 */
	public boolean isWizard() {
		return getProfession() >= 0 && TFTCharacterArchetype.valueOf(
				getProfession()).getCharacterType() == 2;
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
	 * Determines if a character meets a talent's prerequirements.
	 * @param talent the {@link TFTTalent}
	 * @return <tt>true</tt> if the character meets the prerequirements;
	 *         <tt>false</tt> otherwise
	 */
	public boolean meetsTalentPrerequisites(final TFTTalent talent) {
		boolean meets = true;
		boolean hasARequired = false, meetsRequired = true;
		boolean hasOptional = false;
		TFTTalentPrerequisite[] prerequisites = talent.getPrerequisites();
		if (prerequisites.length > 0) {
			for (int i = 0, len = prerequisites.length; i < len; i++) {
				TFTTalentPrerequisite prereq = prerequisites[i];
				switch (prereq.getType()) {
				case TFTTalentPrerequisite.TALENT_PREREQUSITE:
					boolean hasTalent = hasTalent(
							prereq.getTalent().getShortName());
					if (prereq.isRequired()) {
						if (!hasARequired) {
							hasARequired = true;
						}
						if (!hasTalent) {
							meetsRequired = false;
						}
					} else {
						if (hasTalent) {
							hasOptional = true;
						}
					}
					break;
				case TFTTalentPrerequisite.ATTRIBUTE_PREREQUSITE:
					hasARequired = true;
					meetsRequired = getBaseAttributeScore(
							prereq.getAttributeName()) >= prereq
									.getAttributeMin();
					break;
				default: // SPELL_PREREQUISITE
					boolean knowsSpell = knowsSpell(
							prereq.getSpell().getShortName());
					if (prereq.isRequired()) {
						if (!hasARequired) {
							hasARequired = true;
						}
						if (!knowsSpell) {
							meetsRequired = false;
						}
					} else {
						if (knowsSpell) {
							hasOptional = true;
						}
					}
					break;
				}
				if (hasARequired && !meetsRequired) {
					// character is missing required talent
					break;
				}
			}
			if (hasARequired) {
				if (!meetsRequired) {
					meets = false;
				}
			} else {
				if (!hasOptional) {
					meets = false;
				}
			}
		}
		return meets;
	}
	/**
	 * Removes a talent the character knows.
	 * @param talent the talent's short name
	 * @throws RPGException if the talent is a prerequirement for another talent
	 */
	public void removeTalent(final char[] talent) throws RPGException {
		if (talents == null) {
			talents = new char[0][];
		}
		boolean[] meetsPre = new boolean[talents.length];
		for (int i = talents.length - 1; i >= 0; i--) {
			meetsPre[i] =
					meetsTalentPrerequisites(TFTTalent.valueOf(talents[i]));
		}
		int index = -1;
		for (int i = talents.length - 1; i >= 0; i--) {
			if (new String(talents[i]).equalsIgnoreCase(new String(talent))) {
				index = i;
				break;
			}
		}
		if (index >= 0) {
			talents = ArrayUtilities.getInstance().removeIndex(index, talents);
			meetsPre = ArrayUtilities.getInstance().removeIndex(
					index, meetsPre);
			// go through all talents to confirm player is still
			// has the required prereqs
			for (int i = talents.length - 1; i >= 0; i--) {
				if (!meetsTalentPrerequisites(TFTTalent.valueOf(talents[i]))
						&& meetsPre[i]) {
					addTalent(talent);
					// found a talent the character does not
					// qualify for after removing talent
					throw new RPGException(ErrorMessage.ILLEGAL_OPERATION,
							"The "
									+ new String(TFTTalent.valueOf(talent)
											.getFullName())
									+ " talent is required for the "
									+ new String(TFTTalent.valueOf(talents[i])
											.getFullName())
									+ " talent.");
				}
			}
		}
	}
	/**
	 * Removes a talent the character knows and any talents for which it is a
	 * prerequisite.
	 * @param talent the talent's short name
	 */
	public void removeTalentCascade(final char[] talent) {
		if (talents == null) {
			talents = new char[0][];
		}
		boolean[] meetsPre = new boolean[talents.length];
		for (int i = talents.length - 1; i >= 0; i--) {
			meetsPre[i] =
					meetsTalentPrerequisites(TFTTalent.valueOf(talents[i]));
		}
		int index = -1;
		for (int i = talents.length - 1; i >= 0; i--) {
			if (new String(talents[i]).equalsIgnoreCase(new String(talent))) {
				index = i;
				break;
			}
		}
		if (index >= 0) {
			talents = ArrayUtilities.getInstance().removeIndex(index, talents);
			meetsPre = ArrayUtilities.getInstance().removeIndex(
					index, meetsPre);
			boolean allClear = true;
			do {
				allClear = true;
				for (int i = talents.length - 1; i >= 0; i--) {
					if (!meetsTalentPrerequisites(TFTTalent.valueOf(talents[i]))
							&& meetsPre[i]) {
						talents =
								ArrayUtilities.getInstance().removeIndex(index,
										talents);
						meetsPre = ArrayUtilities.getInstance().removeIndex(
								index, meetsPre);
						allClear = false;
						break;
					}
				}
			} while (!allClear);
		}
	}
	/**
	 * Sets the value of the flag indicating pretty printing has been turned on.
	 * @param prettyPrinting the new value to set
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
	@Override
	public boolean isInCombat() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	protected void computeFullStats() throws RPGException {
		// TODO Auto-generated method stub
		
	}
}
