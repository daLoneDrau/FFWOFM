package com.dalonedrow.module.barbarianprince.rpg;

import com.dalonedrow.module.barbarianprince.constants.BpIo;
import com.dalonedrow.rpg.base.flyweights.EquipmentItemModifier;
import com.dalonedrow.rpg.base.flyweights.IOEquipItem;

/**
 * 
 * @author drau
 *
 */
public final class BPEquipment implements IOEquipItem {
    /** the list of equipment modifiers. */
    private final EquipmentItemModifier[] elements;
    /** Creates a new instance of {@link BPEquipment}. */
    public BPEquipment() {
        elements = new EquipmentItemModifier[BpIo
                                             .IO_EQUIPITEM_ELEMENT_NUMBER];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = new EquipmentItemModifier();
        }
    }
    /**
     * {@inheritDoc}
     */
	@Override
	public void free() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
	}
    /**
     * {@inheritDoc}
     */
	@Override
	public EquipmentItemModifier getElement(final int index) {
		return elements[index];
	}
    /**
     * {@inheritDoc}
     */
	@Override
	public void reset() {
		for (int i = 0; i < elements.length; i++) {
			elements[i].clearData();
		}
	}
}
