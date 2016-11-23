package com.dalonedrow.module.barbarianprince.constants;

/**
 * 
 * @author drau
 *
 */
public final class BpIo {
    /** flag indicating the interactive object is a winged mount. */
    public static final int      IO_16_IMMORTAL     =             32768;
	/** the number of inventory slots. */
    public static final int      INV_SLOTS   = 1;
    /** the number of equipment element modifiers. */
    public static final int      IO_EQUIPITEM_ELEMENT_NUMBER   = 7;
    /** the list of equipment element modifiers. */
    public static final String[] IO_EQUIPITEM_ELEMENTS         = new String[] {
                                           "IO_EQUIPITEM_ELEMENT_ATTACK",
                                           "IO_EQUIPITEM_ELEMENT_BODY",
                                           "IO_EQUIPITEM_ELEMENT_DEFEND",
                                           "IO_EQUIPITEM_ELEMENT_MIND",
                                           "IO_EQUIPITEM_ELEMENT_MOVEMENT",
                                           "IO_EQUIPITEM_ELEMENT_MAX_BODY",
                                           "IO_EQUIPITEM_ELEMENT_MAX_MIND"

};
	/** hireling behavior. */
	public static final int BEHAVIOR_HIRELING = 1;
	/** escapee behavior. */
	public static final int BEHAVIOR_ESCAPEE = 2;
	/** fugitive behavior. */
	public static final int BEHAVIOR_FUGITIVE = 4;
	/** Hidden constructor. */
    private BpIo() {
		super();
	}
}
