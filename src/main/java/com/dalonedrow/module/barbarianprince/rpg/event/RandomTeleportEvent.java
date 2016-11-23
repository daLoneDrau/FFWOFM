package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.engine.sprite.base.SimplePoint;
import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.module.barbarianprince.graph.Hex;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.rpg.action.MoveAction;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.graph.TwoDimensional;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * Event that instantly teleports the player's party to a random location.
 * @author drau
 *
 */
public final class RandomTeleportEvent extends Event {
	/** the list of random locations. */
	private int[] destinations;
	/** Creates a new instance of {@link RandomTeleportEvent}. */
	public RandomTeleportEvent() {
		destinations = new int[0];
	}
	/**
	 * Adds a random destination.
	 * @param d the destination
	 */
	public void addDestination(final int d) {
		destinations = 
				ArrayUtilities.getInstance().extendArray(d, destinations);
	}
	/**
	 * {@inheritDoc}
	 * @throws RPGException 
	 */
	@Override
	public void fire() throws RPGException {
		if (super.happens()) {
			int d = Diceroller.getInstance().getRandomIndex(destinations);
			SimplePoint destination =
					new TwoDimensional() { }.convertIntToPoint(d);
			Hex hex = HexMap.getInstance().getHex(destination);
			if (hex == null) {
				throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT,
						"Invalid destination " + destination);
			}
			// create a new move action
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(hex.getIndex());
			MoveAction.getInstance().execute();
			destination = null;
			hex = null;
		}
		super.setResolved();
	}	
}
