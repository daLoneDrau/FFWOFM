/**
 *
 */
package com.dalonedrow.module.barbarianprince.rpg.script;

import java.util.Arrays;

import com.dalonedrow.engine.sprite.base.SimplePoint;
import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.constants.BpScriptConsts;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.log.HeroLog;
import com.dalonedrow.module.barbarianprince.log.LogEntry;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.rpg.BpScriptable;
import com.dalonedrow.module.barbarianprince.rpg.action.EncounterAction;
import com.dalonedrow.module.barbarianprince.rpg.action.MoveAction;
import com.dalonedrow.module.barbarianprince.rpg.action.PreDawnAction;
import com.dalonedrow.module.barbarianprince.rpg.action.RestAction;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.BpScript;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.module.barbarianprince.turn.TimePhase;
import com.dalonedrow.module.barbarianprince.turn.TimeTrack;
import com.dalonedrow.module.barbarianprince.ui.console.GUI;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author drau
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class MasterScript extends BpScriptable {
	/**
	 * Determines if the player's party is north of the Tragoth River.
	 * @return <tt>true</tt> if the party is north of the river; <tt>false</tt>
	 *         otherwise
	 * @throws RPGException if an error occurs
	 */
	private boolean isPartyNorthOfTragoth() throws RPGException {
		int[] arr = { HexMap.getInstance().getHex(1, 1).getIndex(),
				HexMap.getInstance().getHex(2, 1).getIndex(),
				HexMap.getInstance().getHex(3, 1).getIndex(),
				HexMap.getInstance().getHex(3, 2).getIndex(),
				HexMap.getInstance().getHex(4, 1).getIndex(),
				HexMap.getInstance().getHex(5, 1).getIndex(),
				HexMap.getInstance().getHex(5, 2).getIndex(),
				HexMap.getInstance().getHex(6, 1).getIndex(),
				HexMap.getInstance().getHex(7, 1).getIndex(),
				HexMap.getInstance().getHex(8, 1).getIndex(),
				HexMap.getInstance().getHex(9, 1).getIndex(),
				HexMap.getInstance().getHex(10, 1).getIndex(),
				HexMap.getInstance().getHex(11, 1).getIndex(),
				HexMap.getInstance().getHex(12, 1).getIndex(),
				HexMap.getInstance().getHex(13, 1).getIndex(),
				HexMap.getInstance().getHex(15, 1).getIndex(),
				HexMap.getInstance().getHex(16, 1).getIndex(),
				HexMap.getInstance().getHex(17, 1).getIndex(),
				HexMap.getInstance().getHex(18, 1).getIndex(),
				HexMap.getInstance().getHex(19, 1).getIndex(),
				HexMap.getInstance().getHex(20, 1).getIndex() };
		Arrays.sort(arr);
		return Arrays.binarySearch(
				arr, BpController.getInstance().getPartyLocation()) >= 0;
	}
	/**
	 * Event 001 - The Adventure Begins.
	 * @return <code>int</code>
	 * @throws RPGException if an error occurs
	 */
	public int onEvent001() throws RPGException {
		// display start message
		String s = WebServiceClient.getInstance().loadText(
				"event_001");
		s = TextProcessor.getInstance().processText(
				null,
				null,
				(String[]) null,
				s);
		GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
		// move to random location
		SimplePoint pt = (SimplePoint) Diceroller.getInstance().getRandomObject(
				new SimplePoint[] {
						new SimplePoint(1, 1), new SimplePoint(7, 1),
						new SimplePoint(8, 1), new SimplePoint(13, 1),
						new SimplePoint(15, 1), new SimplePoint(18, 1) });
		BpIoGroup group = (BpIoGroup) Interactive.getInstance().getIO(
				BpController.getInstance().getPlayerParty());
		int targ = HexMap.getInstance().getHex(pt).getIndex();
		Script.getInstance().sendIOScriptEvent(group,
				ScriptConsts.SM_21_MOVE,
				new Object[] { "final_destination", targ,
						"move_target", targ,
						"code", BpScriptConsts.CODE_MOVE_INSTANT },
				"move");
		// set PreDawnAction as the current action to resolve
		PreDawnAction.getInstance().reset();
		BpController.getInstance().setTurnAction(PreDawnAction.getInstance());
		s = null;
		pt = null;
		group = null;
		return ScriptConsts.ACCEPT;
	}
	/**
	 * Event 002 - Mercenary Royal Guardsmen.
	 * @return <code>int</code>
	 * @throws RPGException if an error occurs
	 */
	public int onEvent002() throws RPGException {
		System.out.println("onEvent002");
		if (TimeTrack.getInstance().getTime() == TimePhase.PHASE_03_PRE_EVENING
				&& isPartyNorthOfTragoth()) {
			System.out.println("PHASE_03_PRE_EVENING and NoT");
			int roll = Diceroller.getInstance().rolldX(Dice.D6);
			roll -= 3;
			int[] arr = { HexMap.getInstance().getHex(1, 1).getIndex(),
					HexMap.getInstance().getHex(15, 1).getIndex() };
			Arrays.sort(arr);
			if (Arrays.binarySearch(
					arr, BpController.getInstance().getPartyLocation()) >= 0) {
				roll += 1;
			}
			if (roll > 0) {
				System.out.println("high roll " + roll);
				String s = WebServiceClient.getInstance().loadText(
						"event_002_init");
				s = TextProcessor.getInstance().processText(
						null,
						null,
						(String[]) null,
						s);
				GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
				s = WebServiceClient.getInstance().loadText(
						"event_002_log");
				s = TextProcessor.getInstance().processText(
						null,
						(BaseInteractiveObject) Interactive.getInstance().getIO(
								BpController.getInstance().getPlayerParty()),
						(String[]) null,
						s);
				HeroLog.getInstance().addEntry(new LogEntry(
						TimeTrack.getInstance().getTime(),
						TimeTrack.getInstance().getDay(),
						TimeTrack.getInstance().getWeek(),
						HeroLog.LOG_CODE_ENCOUNTER,
						s));
				// TODO generate combat
				MoveAction.getInstance().endMoves();
			} else {
				System.out.println("low roll " + roll);
			}
		} else {
			System.out.println("wrong time/wrong loc");
		}
		return ScriptConsts.ACCEPT;
	}
	private void setEncounter() throws RPGException {
		if (super.hasLocalVariable("turn_action")) {
			// event came after a daily action
			switch (super.getLocalIntVariableValue("turn_action")) {
			case BpScriptConsts.ACTION_REST:
				BpController.getInstance().setTurnAction(
						EncounterAction.getInstance());
				break;
			}
		}
	}
	/**
	 * Event 003 - Swordsman.
	 * @return <code>int</code>
	 * @throws RPGException if an error occurs
	 */
	public int onEvent003() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent004() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent005() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent006() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent007() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent008() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent009() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent010() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent011() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent012() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent013() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent014() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent015() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent016() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent017() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent018() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent019() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent020() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent021() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent022() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent023() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent024() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent025() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent026() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent027() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent028() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent029() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent030() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent031() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent032() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent033() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent034() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent035() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent036() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent037() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent038() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent039() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent040() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent041() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent042() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent043() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent044() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent045() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent046() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent047() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent048() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent049() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent050() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent051() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent052() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent053() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent054() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent055() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent056() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent057() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent058() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent059() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent060() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent061() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent062() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent063() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent064() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent065() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent066() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent067() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent068() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent069() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent070() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent071() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent072() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent073() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent074() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent075() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent076() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent077() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent078() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent079() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent080() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent081() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent082() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent083() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent084() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent085() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent086() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent087() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent088() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent089() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent090() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent091() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent092() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent093() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent094() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent095() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent096() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent097() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent098() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent099() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent100() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent101() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent102() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent103() {
		return ScriptConsts.ACCEPT;
	}
	/**
	 * Event 104 - Good Tailwinds.
	 * @return <code>int</code>
	 * @throws RPGException if an error occurs
	 */
	public int onEvent104() throws RPGException {
		System.out.println("onEvent104");
		String s = WebServiceClient.getInstance().loadText(
				"event_104_init");
		s = TextProcessor.getInstance().processText(
				null,
				null,
				(String[]) null,
				s);
		GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
		s = WebServiceClient.getInstance().loadText("event_104_log");
		s = TextProcessor.getInstance().processText(
				null,
				null,
				(String[]) null,
				s);
		HeroLog.getInstance().addEntry(new LogEntry(
				TimeTrack.getInstance().getTime(),
				TimeTrack.getInstance().getDay(),
				TimeTrack.getInstance().getWeek(),
				HeroLog.LOG_CODE_MESSAGE,
				s));
		// add an extra move
		MoveAction.getInstance().extraMove();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent105() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent106() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent107() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent108() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent109() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent110() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent111() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent112() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent113() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent114() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent115() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent116() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent117() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent118() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent119() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent120() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent121() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent122() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent123() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent124() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent125() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent126() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent127() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent128() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent129() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent130() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent131() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent132() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent133() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent134() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent135() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent136() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent137() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent138() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent139() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent160() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent161() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent162() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent163() {
		return ScriptConsts.ACCEPT;
	}
	public int onEvent164() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent165() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	public int onEvent166() throws RPGException {
		 setEncounter();
		return ScriptConsts.ACCEPT;
	}
	/*
	 * (non-Javadoc)
	 * @see
	 * com.dalonedrow.module.barbarianprince.rpg.BpScriptable#onTimeChange()
	 */
	@Override
	public int onTimeChange() throws RPGException {
		if (TimeTrack.getInstance()
				.getTime() == TimePhase.PHASE_03_PRE_EVENING) {
			System.out.println("adding event 002 due to time change");
			Script.getInstance().stackSendIOScriptEvent(
					((BpScript) Script.getInstance()).getMasterScript(),
					BpScriptConsts.SM_302_E002,
					null,
					"mercenary_thugs");
		}
		return super.onTimeChange();
	}
}
