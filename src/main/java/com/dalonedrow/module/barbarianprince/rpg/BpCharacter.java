package com.dalonedrow.module.barbarianprince.rpg;

import java.util.HashMap;
import java.util.Map;

import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.Attribute;
import com.dalonedrow.rpg.base.flyweights.IoPcData;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class BpCharacter extends IoPcData {
	/** flag indicating pretty printing has been turned on. */
	private boolean			prettyPrinting;
	/**
	 * Creates a new instance of {@link BpCharacter}.
	 * @throws RPGException if an error occurs
	 */
	public BpCharacter() throws RPGException {
		super();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void computeFullStats() throws RPGException {
		// TODO Auto-generated method stub
		// set attribute modifiers
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void defineAttributes() throws RPGException {
		Map<String, Attribute> map = new HashMap<String, Attribute>();
		map.put("CS", new Attribute("CS", "Combat Skill"));
		map.put("EN", new Attribute("EN", "Endurance"));
		map.put("WE", new Attribute("WE", "Wealth"));
		map.put("WO", new Attribute("WO", "Wounds"));
		map.put("PW", new Attribute("PW", "Poison Wounds"));
		map.put("WI", new Attribute("WI", "Wit & Wiles"));
		super.setAttributes(map);
		map = null;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getNumberOfEquipmentSlots() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isInCombat() {
		// TODO Auto-generated method stub
		return false;
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
				sb.append("CS: ");
				sb.append(super.getFullAttributeScore("CS"));
				sb.append('\n');
				sb.append("EN: ");
				sb.append(super.getFullAttributeScore("EN"));
				sb.append('\n');
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
}
