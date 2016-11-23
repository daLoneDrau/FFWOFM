package com.dalonedrow.module.barbarianprince.rpg;

import java.util.HashMap;
import java.util.Map;

import com.dalonedrow.rpg.base.flyweights.Attribute;
import com.dalonedrow.rpg.base.flyweights.IoNpcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * 
 * @author drau
 *
 */
public final class BpNpc extends IoNpcData<BpInteractiveObject> {
	/** the list of hexes where this NPC can act as a local guide. */
	private int[] localGuide;
	public BpNpc() throws RPGException {
		super();
		localGuide = new int[0];
		// TODO Auto-generated constructor stub
	}
	/**
	 * Adds a hex where the NPC can act as a local guide.
	 * @param hexId the hex id
	 */
	public void addLocalGuideHex(final int hexId) {
		if (!isLocalGuide(hexId)) {
			localGuide = 
					ArrayUtilities.getInstance().extendArray(hexId, localGuide);
		}
	}
	/**
	 * Determines if the NPC can act as a local guide for a specific location.
	 * @param hexId the hex id
	 * @return <tt>true</tt> if the NPC can act as a guide; <tt>false</tt>
	 * otherwise
	 */
	public boolean isLocalGuide(final int hexId) {
		boolean is = false;
		for (int i = localGuide.length - 1; i >= 0; i--) {
			if (localGuide[i] == hexId) {
				is = true;
				break;
			}
		}
		return is;
	}
	@Override
	protected void defineAttributes() throws RPGException {
		Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("CS", new Attribute("CS", "Combat Skill"));
		map.put("EN", new Attribute("EN", "Endurance"));
		map.put("WE", new Attribute("WE", "Wealth"));
		map.put("WO", new Attribute("WO", "Wounds"));
		map.put("PW", new Attribute("PW", "Poison Wounds"));
		super.setAttributes(map);
		map = null;
	}

}
