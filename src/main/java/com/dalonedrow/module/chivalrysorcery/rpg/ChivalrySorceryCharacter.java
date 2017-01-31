/**
 *
 */
package com.dalonedrow.module.chivalrysorcery.rpg;

import java.util.HashMap;
import java.util.Map;

import com.dalonedrow.rpg.base.flyweights.Attribute;
import com.dalonedrow.rpg.base.flyweights.IOPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class ChivalrySorceryCharacter extends IOPcData {
	/** the character's birth aspect. */
	private int		birthAspect;
	/** the character's body frame. */
	private int		bodyFrame;
	/** the character's carrying capacity. */
	private int		carryingCapacity;
	/**
	 * the total number of character prime requisite points the player has to
	 * spend.
	 */
	private int		cprPoints;
	/** the number of character prime requisite points the player has spent. */
	private int		cprPtsSpent;
	/** the character's family status. */
	private int		familyStatus;
	/** the character's father's social class. */
	private int		fatherSocialClass;
	/** the character's father's (or grandfather's) vocation. */
	private int		fatherVocation		= -999;
	/** the character's father's (or grandfather's) vocation rank. */
	private int		fatherVocationRank	= -999;
	private int		height;
	/** the experience point bonus provided by the character's horoscope. */
	private float	horoscopeXPBonus;
	/** flag indicating the character is their father's heir. */
	private boolean	isHeir;
	/** the number of siblings in the character's family. */
	private int		numSiblings;
	/**
	 * the character's real (biological) father's social class. only applies to
	 * bastards.
	 */
	private int		realFatherSocialClass;
	/**
	 * the character's real (biological) father's vocation. only applies to
	 * bastards.
	 */
	private int		realFatherVocation;
	/** the character's sibling rank. */
	private int		siblingRank;
	/** the character's social status score. */
	private int		socialStatus;
	private int		weight;
	/** the character's zodiac sign. */
	private int		zodiac;
	/** Creates a new instance of {@link ChivalrySorceryCharacter}. */
	public ChivalrySorceryCharacter() {
		super.initEquippedItems(1);
	}
	/**
	 * Adjusts the number of CPR points the character has spent.
	 * @param val the adjustment value, can be positive or negative
	 */
	public void adjustCprSpent(final int val) {
		cprPtsSpent += val;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.IOPCData#defineAttributes()
	 */
	@Override
	protected void defineAttributes() throws RPGException {
		Map<char[], Attribute> map = new HashMap<char[], Attribute>();
		map.put("AP".toCharArray(), new Attribute("AP", "Appearance"));
		map.put("BV".toCharArray(), new Attribute("BV", "Bardic Voice"));
		map.put("BL".toCharArray(), new Attribute("BL", "Body Level"));
		map.put("CH".toCharArray(), new Attribute("CH", "Charisma"));
		map.put("CN".toCharArray(), new Attribute("CN", "Constitution"));
		map.put("DX".toCharArray(), new Attribute("DX", "Dexterity"));
		map.put("FT".toCharArray(), new Attribute("FT", "Fatigue Level"));
		map.put("FC".toCharArray(), new Attribute("FC", "Ferocity"));
		map.put("IN".toCharArray(), new Attribute("IN", "Intelligence"));
		map.put("IN".toCharArray(), new Attribute("MB", "Max Body Level"));
		map.put("MF".toCharArray(), new Attribute("MF", "Max Fatigue Level"));
		map.put("PCF".toCharArray(),
				new Attribute("PCF", "Personal Combat Factor"));
		map.put("PT".toCharArray(), new Attribute("PT", "Piety"));
		map.put("ST".toCharArray(), new Attribute("ST", "Strength"));
		map.put("WI".toCharArray(), new Attribute("WI", "Wisdom"));
		super.setAttributes(map);
		map = null;
	}
	/**
	 * Gets the character's birth aspect.
	 * @return {@link int}
	 */
	public int getBirthAspect() {
		return birthAspect;
	}
	/**
	 * Gets the bodyFrame.
	 * @return <code>int</code>
	 */
	public int getBodyFrame() {
		return bodyFrame;
	}
	/**
	 * Gets the character's carrying capacity.
	 * @return <code>int</code>
	 */
	public int getCarryingCapacity() {
		return carryingCapacity;
	}
	/**
	 * Gets the number of unspent CPR points the character has remaining.
	 * @return <code>int</code>
	 */
	public int getCprPointsAvailable() {
		return cprPoints - cprPtsSpent;
	}
	/**
	 * Gets the character's family status.
	 * @return int
	 */
	public int getFamilyStatus() {
		return familyStatus;
	}
	/**
	 * Gets the character's father's social class.
	 * @return <code>int</code>
	 */
	public int getFatherSocialClass() {
		return fatherSocialClass;
	}
	/**
	 * Gets the character's father's (or grandfather's) vocation.
	 * @return {@link int}
	 */
	public int getFatherVocation() {
		return fatherVocation;
	}
	/**
	 * Gets the character's father's (or grandfather's) vocation rank.
	 * @return {@link int}
	 */
	public int getFatherVocationRank() {
		return fatherVocationRank;
	}
	/**
	 * Gets the height.
	 * @return <code>int</code>
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Gets the experience point bonus provided by the character's horoscope.
	 * @return <code>float</code>
	 */
	public float getHoroscopeXPBonus() {
		return horoscopeXPBonus;
	}
	/**
	 * Gets the number of siblings in the character's family.
	 * @return <code>int</code>
	 */
	public int getNumSiblings() {
		return numSiblings;
	}
	/**
	 * Gets the character's biological father's social class. Only applies to
	 * bastards.
	 * @return <code>int</code>
	 */
	public int getRealFatherSocialClass() {
		return realFatherSocialClass;
	}
	/**
	 * Gets the character's biological father's vocation. Only applies to
	 * bastards.
	 * @return <code>int</code>
	 */
	public int getRealFatherVocation() {
		return realFatherVocation;
	}
	/**
	 * Gets the character's sibling rank.
	 * @return int
	 */
	public int getSiblingRank() {
		return siblingRank;
	}
	/**
	 * Gets the character's social status score.
	 * @return <code>int</code>
	 */
	public int getSocialStatus() {
		return socialStatus;
	}
	/**
	 * Gets the weight.
	 * @return <code>int</code>
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * Gets the character's zodiac sign.
	 * @return {@link int}
	 */
	public int getZodiac() {
		return zodiac;
	}
	/**
	 * Determines if the character is their father's heir.
	 * @return true if the character is their father's heir, otherwise false
	 */
	public boolean isHeir() {
		return isHeir;
	}
	/**
	 * Sets the character's birth aspect.
	 * @param val the character's birth aspect to set
	 */
	public void setBirthAspect(final int val) {
		birthAspect = val;
	}
	/**
	 * Sets the value for the bodyFrame.
	 * @param val the value to set
	 */
	public void setBodyFrame(final int val) {
		bodyFrame = val;
	}
	/**
	 * Sets the value for the character's carrying capacity.
	 * @param val the value to set
	 */
	public void setCarryingCapacity(final int val) {
		carryingCapacity = val;
	}
	/**
	 * Sets the total number of character prime requisite points the player has
	 * to spend.
	 * @param val the value to set
	 */
	public void setCprPoints(final int val) {
		cprPoints = val;
	}
	/**
	 * Sets the character's family status.
	 * @param val the new family status
	 */
	public void setFamilyStatus(final int val) {
		familyStatus = val;
	}
	/**
	 * Sets the character's father's social class.
	 * @param val the new fatherSocialClass
	 */
	public void setFatherSocialClass(final int val) {
		fatherSocialClass = val;
	}
	/**
	 * Sets the character's father's (or grandfather's) vocation.
	 * @param val the character's father's (or grandfather's) vocation to set
	 */
	public void setFatherVocation(final int val) {
		fatherVocation = val;
	}
	/**
	 * Sets the character's father's (or grandfather's) vocation rank.
	 * @param val the character's father's (or grandfather's) vocation rank to
	 *            set
	 */
	public void setFatherVocationRank(final int val) {
		fatherVocationRank = val;
	}
	/**
	 * Sets the value for the height.
	 * @param val the value to set
	 */
	public void setHeight(final int val) {
		height = val;
	}
	/**
	 * Sets the flag indicating whether the character is their father's heir.
	 * @param flag the flag to set
	 */
	public void setHeir(final boolean flag) {
		isHeir = flag;
	}
	/**
	 * Sets the experience point bonus provided by the character's horoscope.
	 * @param val the value to set
	 */
	public void setHoroscopeXPBonus(final float val) {
		horoscopeXPBonus = val;
	}
	/**
	 * Sets the number of siblings in the character's family.
	 * @param val the number of siblings in the character's family to set
	 */
	public void setNumSiblings(final int val) {
		numSiblings = val;
	}
	/**
	 * Sets the character's biological father's social class.
	 * @param val the new fatherSocialClass
	 */
	public void setRealFatherSocialClass(final int val) {
		realFatherSocialClass = val;
	}
	/**
	 * Sets the character's biological father's vocation.
	 * @param val the new vocation
	 */
	public void setRealFatherVocation(final int val) {
		realFatherVocation = val;
	}
	/**
	 * Sets the character's sibling rank.
	 * @param val the new siblingClass
	 */
	public void setSiblingRank(final int val) {
		siblingRank = val;
	}
	/**
	 * Sets the character's social status score.
	 * @param val the character's social status score to set
	 */
	public void setSocialStatus(final int val) {
		socialStatus = val;
	}
	/**
	 * Sets the value for the weight.
	 * @param val the value to set
	 */
	public void setWeight(final int val) {
		weight = val;
	}
	/**
	 * Sets the character's zodiac sign.
	 * @param val the character's zodiac sign to set
	 */
	public void setZodiac(final int val) {
		zodiac = val;
	}
}
