package com.dalonedrow.module.ff.constants;

import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * 
 * @author drau
 *
 */
public final class FFIo {
    /** flag indicating the interactive object is immortal. */
    public static final int      IO_16_IMMORTAL     =             32768;
    /** flag indicating the interactive object is a room. */
    public static final int      IO_17_ROOM     =             65536;
    /** flag indicating the interactive object is a door. */
    public static final int      IO_18_DOOR     =             131072;
	/** the number of inventory slots. */
    public static final int      INV_SLOTS   = 1;
    /** the number of equipment element modifiers. */
    public static final int      IO_EQUIPITEM_ELEMENT_NUMBER   = 6;
    /** the list of equipment element modifiers. */
	public static final String[]	IO_EQUIPITEM_ELEMENTS         = new String[] {
                                           "IO_EQUIPITEM_ELEMENT_STAMINA",
                                           "IO_EQUIPITEM_ELEMENT_SKILL",
                                           "IO_EQUIPITEM_ELEMENT_LUCK",
                                           "IO_EQUIPITEM_ELEMENT_DAMAGES",
                                           "IO_EQUIPITEM_ELEMENT_AIMTIME",
                                           "IO_EQUIPITEM_ELEMENT_ARMORCLASS"
                                           };
	/** hireling behavior. */
	public static final int BEHAVIOR_HIRELING = 1;
	/** escapee behavior. */
	public static final int BEHAVIOR_ESCAPEE = 2;
	/** fugitive behavior. */
	public static final int BEHAVIOR_FUGITIVE = 4;
	/** Hidden constructor. */
    private FFIo() {
		super();
	}
	public static int getElementIndex(String element) throws RPGException {
		int index = -1;
		for (int i = IO_EQUIPITEM_ELEMENTS.length - 1; i >= 0; i--) {
			if (IO_EQUIPITEM_ELEMENTS[i].equalsIgnoreCase(element)) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			throw new RPGException(ErrorMessage.INVALID_PARAM, "Invalid element - " + element);
		}
		return index;
	}
}
