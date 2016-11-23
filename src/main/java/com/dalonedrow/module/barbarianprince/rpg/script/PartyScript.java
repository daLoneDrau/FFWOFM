/**
 *
 */
package com.dalonedrow.module.barbarianprince.rpg.script;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.constants.BpScriptConsts;
import com.dalonedrow.module.barbarianprince.graph.Hex;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.log.HeroLog;
import com.dalonedrow.module.barbarianprince.log.LogEntry;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.rpg.BpScriptable;
import com.dalonedrow.module.barbarianprince.rpg.action.MoveAction;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.module.barbarianprince.turn.TimeTrack;
import com.dalonedrow.module.barbarianprince.ui.console.GUI;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptConstants;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.graph.WeightedGraphEdge;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class PartyScript extends BpScriptable {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onDesert() throws RPGException {
		BpIoGroup io = (BpIoGroup) super.getIO();
		int code = super.getLocalIntVariableValue("code");
		BpInteractiveObject deserter =
				(BpInteractiveObject) Interactive.getInstance().getIO(
						super.getLocalIntVariableValue("io_id"));
		// remove deserter
		BpController.getInstance().removeIOFromParty(deserter.getRefId());
		// send messages
		String s;
		switch (code) {
		case BpScriptConsts.CODE_LOST_GUIDE:
			s = WebServiceClient.getInstance().loadText("move_msg_lost_guide");
			s = TextProcessor.getInstance().processText(
					null,
					deserter,
					(String[]) null,
					s);
			break;
		default:
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid desertion code " + code);
		}
		GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
		HeroLog.getInstance().addEntry(new LogEntry(
				BpController.getInstance().getCurrentTurn().getCurrentPhase(),
				TimeTrack.getInstance().getDay(),
				TimeTrack.getInstance().getWeek(),
				HeroLog.LOG_CODE_DESERTER,
				new String(deserter.getNPCData().getName())));
		// notify each party member that someone deserted
		for (int i = 0, len = io.getNumberOfMembers(); i < len; i++) {
			BpInteractiveObject member;
			if (Interactive.getInstance().hasIO(io.getMember(i))) {
				member =
						(BpInteractiveObject) Interactive.getInstance()
								.getIO(io.getMember(i));
				Script.getInstance().sendIOScriptEvent(
						member,
						BpScriptConsts.SM_076_DESERT,
						new Object[] {
								"io_id", deserter.getRefId(),
								"code", code },
						"desertion");
				member = null;
			}
		}
		// release IO
		Interactive.getInstance().releaseIO(deserter.getRefId());
		deserter = null;
		return super.onLost();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onLost() throws RPGException {
		BpIoGroup io = (BpIoGroup) super.getIO();
		int code = super.getLocalIntVariableValue("code");
		String s;
		switch (code) {
		case BpScriptConsts.CODE_LOST_AIRBORNE:
			s = WebServiceClient.getInstance().loadText(
					"move_msg_lost_airborne");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					(String[]) null,
					s);
			break;
		case BpScriptConsts.CODE_LOST_AIRBORNE_DRIFT:
			int destination =
					super.getLocalIntVariableValue("final_destination");
			int newLoc = -1;
			while (newLoc == -1 || newLoc == destination) {
				int dir = Diceroller.getInstance().rolldXPlusY(
						Dice.D6, -1);
				newLoc = HexMap.getInstance().getNeighbor(
						io.getLocation(), dir);
			}
			io.setLocation(newLoc);
			Hex driftHex = HexMap.getInstance().getHexById(newLoc);
			s = WebServiceClient.getInstance().loadText(
					"move_msg_lost_airborne_drift");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] { driftHex.toMsgString() },
					s);
			driftHex = null;
			break;
		case BpScriptConsts.CODE_LOST_RIVER_CROSSING:
			Hex from = HexMap.getInstance().getHexById(
					super.getLocalIntVariableValue("move_departure"));
			Hex to = HexMap.getInstance().getHexById(
					super.getLocalIntVariableValue("move_target"));
			s = WebServiceClient.getInstance().loadText(
					"move_msg_lost_river");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] {
							HexMap.getInstance()
									.getRiverCrossingName(from, to) },
					s);
			from = null;
			to = null;
			break;
		case BpScriptConsts.CODE_LOST_FOOT:
			from = HexMap.getInstance().getHexById(
					super.getLocalIntVariableValue("move_departure"));
			s = WebServiceClient.getInstance().loadText("move_msg_lost");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] { from.toMsgString() },
					s);
			from = null;
			break;
		default:
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid lost code " + code);
		}
		GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
		HeroLog.getInstance().addEntry(new LogEntry(
				BpController.getInstance().getCurrentTurn().getCurrentPhase(),
				TimeTrack.getInstance().getDay(),
				TimeTrack.getInstance().getWeek(),
				HeroLog.LOG_CODE_LOST,
				super.getLocalIntVariableValue("move_departure")));
		io = null;
		s = null;
		return super.onLost();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onMovement() throws RPGException {
		MoveAction.getInstance().takeMove();
		BpIoGroup io = (BpIoGroup) super.getIO();
		Hex from = null;
		if (super.hasLocalVariable("move_departure")) {
			from = HexMap.getInstance().getHexById(
					super.getLocalIntVariableValue("move_departure"));
		}
		Hex to = HexMap.getInstance().getHexById(
				super.getLocalIntVariableValue("move_target"));
		int destination = super.getLocalIntVariableValue("final_destination");
		int code = super.getLocalIntVariableValue("code");
		io.setLocation(to.getIndex());
		// send GUI messages
		String s;
		switch (code) {
		case BpScriptConsts.CODE_MOVE_AIRBORNE:
			if (to.getIndex() == destination) {
				s = WebServiceClient.getInstance().loadText(
						"move_msg_airborne_landing");
			} else {
				s = WebServiceClient.getInstance().loadText(
						"move_msg_airborne");
			}
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] { to.toMsgString() },
					s);
			break;
		case BpScriptConsts.CODE_MOVE_FOOT:
			s = WebServiceClient.getInstance().loadText("move_msg_success");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] { to.toMsgString() },
					s);
			break;
		case BpScriptConsts.CODE_MOVE_INSTANT:
			s = WebServiceClient.getInstance().loadText("move_msg_teleport");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] { to.toMsgString() },
					s);
			break;
		case BpScriptConsts.CODE_MOVE_RAFT:
			s = WebServiceClient.getInstance().loadText("move_msg_raft");
			String name = null;
			WeightedGraphEdge[] e =
					HexMap.getInstance().getRiverPathList(from, to);
			for (int i = e.length - 1; i >= 0; i--) {
				int bank0 = HexMap.getInstance().getRiverNodeById(
						e[i].getTo()).getBank0();
				int bank1 = HexMap.getInstance().getRiverNodeById(
						e[i].getTo()).getBank1();
				if (bank0 == to.getIndex()
						|| bank1 == to.getIndex()) {
					name = new String(HexMap.getInstance().getRiverNodeById(
							e[i].getTo()).getName());
					break;
				}
			}
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] { name, to.toMsgString() },
					s);
			e = null;
			name = null;
			break;
		case BpScriptConsts.CODE_MOVE_RIVER_CROSSING:
			s = WebServiceClient.getInstance().loadText("move_msg_cross_river");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] {
							HexMap.getInstance().getRiverCrossingName(from, to),
							to.toMsgString() },
					s);
			break;
		case BpScriptConsts.CODE_MOVE_ROAD:
			s = WebServiceClient.getInstance().loadText("move_msg_road");
			s = TextProcessor.getInstance().processText(
					null,
					io,
					new String[] { to.toMsgString() },
					s);
			break;
		default:
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid movement code " + code);
		}
		GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
		HeroLog.getInstance().addEntry(new LogEntry(
				TimeTrack.getInstance().getTime(),
				TimeTrack.getInstance().getDay(),
				TimeTrack.getInstance().getWeek(),
				HeroLog.LOG_CODE_MOVE,
				super.getLocalIntVariableValue("move_target")));
		// notify each party member that they have moved
		for (int i = 0, len = io.getNumberOfMembers(); i < len; i++) {
			BpInteractiveObject member;
			if (Interactive.getInstance().hasIO(io.getMember(i))) {
				member =
						(BpInteractiveObject) Interactive.getInstance()
								.getIO(io.getMember(i));
				Script.getInstance().sendIOScriptEvent(
						member,
						ScriptConsts.SM_21_MOVE,
						new Object[] { "move_target", to.getIndex() },
						"move");
				member = null;
			}
		}
		io = null;
		from = null;
		to = null;
		s = null;
		return ScriptConstants.ACCEPT;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onRest() throws RPGException {
		BpIoGroup io = (BpIoGroup) super.getIO();
		// notify each party member that they have moved
		for (int i = 0, len = io.getNumberOfMembers(); i < len; i++) {
			BpInteractiveObject member;
			if (Interactive.getInstance().hasIO(io.getMember(i))) {
				member =
						(BpInteractiveObject) Interactive.getInstance()
								.getIO(io.getMember(i));
				Script.getInstance().sendIOScriptEvent(
						member,
						BpScriptConsts.SM_078_REST,
						null,
						"rest");
				member = null;
			}
		}
		String s = WebServiceClient.getInstance().loadText("rest_msg_success");
		s = TextProcessor.getInstance().processText(
				null,
				io,
				(String[]) null,
				s);
		GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
		HeroLog.getInstance().addEntry(new LogEntry(
				TimeTrack.getInstance().getTime(),
				TimeTrack.getInstance().getDay(),
				TimeTrack.getInstance().getWeek(),
				HeroLog.LOG_CODE_REST,
				s));
		io = null;
		s = null;
		return ScriptConstants.ACCEPT;
	}
}
