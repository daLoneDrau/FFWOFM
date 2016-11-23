package com.dalonedrow.module.barbarianprince.rpg;

import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptConstants;
import com.dalonedrow.rpg.base.flyweights.Scriptable;

/**
 * 
 * @author drau
 *
 */
public abstract class BpScriptable extends Scriptable<BpInteractiveObject> {
	/**
	 * A desertion event.
	 * @return {link int}
	 * @throws RPGException if an error occurs
	 */
	public int onDesert() throws RPGException {
		return ScriptConstants.ACCEPT;
	}
	/**
	 * A lost event.
	 * @return {link int}
	 * @throws RPGException if an error occurs
	 */
	public int onLost() throws RPGException {
		return ScriptConstants.ACCEPT;
	}
	/**
	 * Time change event.
	 * @return {link int}
	 * @throws RPGException if an error occurs
	 */
	public int onTimeChange() throws RPGException {
		return ScriptConstants.ACCEPT;
	}
	/**
	 * A rest event.
	 * @return {link int}
	 * @throws RPGException if an error occurs
	 */
	public int onRest() throws RPGException {
		return ScriptConstants.ACCEPT;
	}
}
