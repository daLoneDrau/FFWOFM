package com.dalonedrow.module.barbarianprince.systems;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.Time;
import com.dalonedrow.module.barbarianprince.constants.BpIo;
import com.dalonedrow.module.barbarianprince.constants.BpScriptConsts;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.BpScriptTimer;
import com.dalonedrow.module.barbarianprince.rpg.BpScriptable;
import com.dalonedrow.module.barbarianprince.rpg.BpStackedEvent;
import com.dalonedrow.module.barbarianprince.rpg.script.MasterScript;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author drau
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class BpScript
		extends Script<BpInteractiveObject, BpScriptTimer, BpScriptable> {
	private BpStackedEvent[]			eventstack;
	private int							eventTotalCount	= 0;
	private final BpInteractiveObject	master;
	private BpStackedEvent[]			postScriptStack;
	/** the list of script timers. */
	private BpScriptTimer[]				scriptTimers;
	private int							stackFlow		= 8;
	/** Creates a new instance of {@link BpScript}. */
	BpScript() {
		master = (BpInteractiveObject) 
				((BpInteractive) Interactive.getInstance()).getMasterScript();
		eventStackInit();
		super.setInstance(this);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eventStackClear() {
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			if (eventstack[i].exists()) {
				eventstack[i].setParams(null);
				eventstack[i].setEventname(null);
				eventstack[i].setSender(null);
				eventstack[i].setExist(false);
				eventstack[i].setIo(null);
				eventstack[i].setMsg(0);
			}
			if (postScriptStack[i].exists()) {
				postScriptStack[i].setParams(null);
				postScriptStack[i].setEventname(null);
				postScriptStack[i].setSender(null);
				postScriptStack[i].setExist(false);
				postScriptStack[i].setIo(null);
				postScriptStack[i].setMsg(0);
			}
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eventStackClearForIo(final BpInteractiveObject io) {
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			if (eventstack[i].exists()
					&& io.equals(eventstack[i].getIo())) {
				eventstack[i].setParams(null);
				eventstack[i].setEventname(null);
				eventstack[i].setSender(null);
				eventstack[i].setExist(false);
				eventstack[i].setIo(null);
				eventstack[i].setMsg(0);
			}
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eventStackExecute() throws RPGException {
		int count = 0;
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			if (eventstack[i].exists()) {
				int ioid = eventstack[i].getIo().getRefId();
				if (Interactive.getInstance().hasIO(ioid)) {
					if (eventstack[i].getSender() != null) {
						int senderid = eventstack[i].getSender().getRefId();
						if (Interactive.getInstance().hasIO(senderid)) {
							super.setEventSender(eventstack[i].getSender());
						} else {
							super.setEventSender(null);
						}
					} else {
						super.setEventSender(null);
					}
					sendIOScriptEvent(eventstack[i].getIo(),
							eventstack[i].getMsg(), eventstack[i].getParams(),
							eventstack[i].getEventname());
				}
				eventstack[i].setParams(null);
				eventstack[i].setEventname(null);
				eventstack[i].setSender(null);
				eventstack[i].setExist(false);
				eventstack[i].setIo(null);
				eventstack[i].setMsg(0);
				count++;
				if (count >= stackFlow) {
					break;
				}
			}
		}
		transferPostScriptStack();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eventStackExecuteAll() throws RPGException {
		stackFlow = 9999999;
		eventStackExecute();
		stackFlow = 20;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void eventStackInit() {
		eventstack = new BpStackedEvent[ScriptConsts.MAX_EVENT_STACK];
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			eventstack[i] = new BpStackedEvent();
		}
		postScriptStack = new BpStackedEvent[ScriptConsts.MAX_EVENT_STACK];
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			postScriptStack[i] = new BpStackedEvent();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BpInteractiveObject getIOMaxEvents() throws RPGException {
		int max = -1;
		int ionum = -1;
		BpInteractiveObject io = null;
		for (int i = 0; i < Interactive.getInstance().getNumberIOs(); i++) {
			if (Interactive.getInstance().hasIO(i)) {
				BpInteractiveObject hio =
						(BpInteractiveObject) Interactive.getInstance()
								.getIO(i);
				if (hio.getStatCount() > max) {
					ionum = i;
					max = hio.getStatCount();
				}
			}
		}
		if (max > 0
				&& ionum > -1) {
			io = (BpInteractiveObject) Interactive.getInstance().getIO(ionum);
		}
		return io;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BpInteractiveObject getIOMaxEventsSent() throws RPGException {
		int max = -1;
		int ionum = -1;
		BpInteractiveObject io = null;
		for (int i = 0; i < Interactive.getInstance().getNumberIOs(); i++) {
			if (Interactive.getInstance().hasIO(i)) {
				BpInteractiveObject hio =
						(BpInteractiveObject) Interactive.getInstance()
								.getIO(i);
				if (hio.getStatSent() > max) {
					ionum = i;
					max = hio.getStatSent();
				}
			}
		}
		if (max > 0
				&& ionum > -1) {
			io = (BpInteractiveObject) Interactive.getInstance().getIO(ionum);
		}
		return io;
	}
	/**
	 * Gets the IO for the master script.
	 * @return {@link BaseInteractiveObject}
	 */
	public BpInteractiveObject getMasterScript() {
		return master;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public BpScriptTimer[] getScriptTimers() {
		return scriptTimers;
	}
	private void postScriptStackClear() {
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			if (postScriptStack[i].exists()) {
				postScriptStack[i].setParams(null);
				postScriptStack[i].setEventname(null);
				postScriptStack[i].setSender(null);
				postScriptStack[i].setExist(false);
				postScriptStack[i].setIo(null);
				postScriptStack[i].setMsg(0);
			}
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int sendScriptEvent(final BpScriptable event, final int msg,
			final Object[] params, final BpInteractiveObject io,
			final String evname, final int info) throws RPGException {
		if (super.isDebug()) {
			System.out.println(this.getClass().getCanonicalName()
					+ ".sendScriptEvent(event="
					+ event.getClass().getCanonicalName() + ", io=" + io
					+ ", msg=" + msg);
		}
		if (io != null) {
			if (io.hasGameFlag(IoGlobals.GFLAG_MEGAHIDE)
					&& msg != ScriptConsts.SM_43_RELOAD) {
				return ScriptConsts.ACCEPT;
			}

			if (io.getShow() == IoGlobals.SHOW_FLAG_DESTROYED) {
				// destroyed
				return ScriptConsts.ACCEPT;
			}
			eventTotalCount++;
			io.setStatCount(io.getStatCount() + 1);

			if (io.hasIOFlag(IoGlobals.IO_06_FREEZESCRIPT)) {
				if (msg == ScriptConsts.SM_41_LOAD) {
					return ScriptConsts.ACCEPT;
				}
				return ScriptConsts.REFUSE;
			}

			if (io.hasIOFlag(IoGlobals.IO_03_NPC)
					&& !io.hasIOFlag(IoGlobals.IO_09_DWELLING)) {
				try {
					// MagicRealmNPC.getInstance().computeNPCFullStats(io);
				} catch (Exception ex) {
					// JOGLErrorHandler.getInstance().fatalError(ex);
				}
				/*
				 * if (io.getNPCData().getHarmTaken() >= (int) io.getNPCData()
				 * .getVulnerability().getFull() && msg !=
				 * ScriptConsts.SM_12_DEAD && msg != ScriptConsts.SM_17_DIE &&
				 * msg != ScriptConsts.SM_255_EXECUTELINE && msg !=
				 * ScriptConsts.SM_43_RELOAD && msg !=
				 * ScriptConsts.SM_255_EXECUTELINE && msg !=
				 * ScriptConsts.SM_28_INVENTORY2_OPEN && msg !=
				 * ScriptConsts.SM_29_INVENTORY2_CLOSE) { return
				 * ScriptConsts.ACCEPT; }
				 */
			}

			// change weapon if one breaks
			/*
			 * if (((io->ioflags & IO_FIX) || (io->ioflags & IO_ITEM)) && (msg
			 * == ScriptConsts.SM_BREAK)) { ManageCasseDArme(io); }
			 */
		}
		BpScriptable script = null;
		if (event == null) {
			System.out.println(io.getClass().getSimpleName());
			System.out.println("pc::"+io.hasIOFlag(IoGlobals.IO_01_PC));
			System.out.println("npc::"+io.hasIOFlag(IoGlobals.IO_03_NPC));
			System.out.println("horse::"+io.hasIOFlag(IoGlobals.IO_04_HORSE));
			System.out.println("winged::"+io.hasIOFlag(IoGlobals.IO_15_WINGED_MOUNT));
			System.out.println("party::"+io.hasIOFlag(IoGlobals.IO_14_PARTY));
		}
		event.getMaster();
		if (script == null) {
			script = event;
		}
		switch (msg) {
		case ScriptConsts.SM_55_COLLIDE_NPC:
			if (script.hasAllowedEvent(
					ScriptConsts.DISABLE_COLLIDE_NPC)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_10_CHAT:
			if (script.hasAllowedEvent(ScriptConsts.DISABLE_CHAT)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_016_HIT:
			if (script.hasAllowedEvent(ScriptConsts.DISABLE_HIT)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_28_INVENTORY2_OPEN:
			if (script.hasAllowedEvent(
					ScriptConsts.DISABLE_INVENTORY2_OPEN)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_46_HEAR:
			if (script.hasAllowedEvent(ScriptConsts.DISABLE_HEAR)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_23_UNDETECTPLAYER:
		case ScriptConsts.SM_22_DETECTPLAYER:
			if (script.hasAllowedEvent(ScriptConsts.DISABLE_DETECT)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_57_AGGRESSION:
			if (script.hasAllowedEvent(
					ScriptConsts.DISABLE_AGGRESSION)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_008_MAIN:
			if (script.hasAllowedEvent(ScriptConsts.DISABLE_MAIN)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_73_CURSORMODE:
			if (script.hasAllowedEvent(
					ScriptConsts.DISABLE_CURSORMODE)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_74_EXPLORATIONMODE:
			if (script.hasAllowedEvent(
					ScriptConsts.DISABLE_EXPLORATIONMODE)) {
				return ScriptConsts.REFUSE;
			}
			break;
		case ScriptConsts.SM_61_KEY_PRESSED:
			/*
			 * float dwCurrTime = ARX_TIME_Get(); if ((dwCurrTime -
			 * g_TimeStartCinemascope) < 3000) { return REFUSE; }
			 */
			return ScriptConsts.REFUSE;
		}
		// set parameters on script that was sent
		if (params != null
				&& params.length > 0) {
			for (int i = 0; i < params.length; i += 2) {
				try {
					event.setLocalVariable((String) params[i], params[i + 1]);
				} catch (Exception ex) {
					// JOGLErrorHandler.getInstance().fatalError(ex);
				}
			}
		}
		if (evname != null) {
			event.setLocalVariable("EVENT_NAME", evname);
		}
		// makeSSEPARAMS(params);
		// switch message sent and react to it:
		try {
			switch (msg) {
			case ScriptConsts.SM_001_INIT:
				script.onInit();
				break;
			case ScriptConsts.SM_21_MOVE:
				script.onMovement();
				break;
			case BpScriptConsts.SM_076_DESERT:
				script.onDesert();
				break;
			case BpScriptConsts.SM_077_LOST:
				script.onLost();
				break;
			case BpScriptConsts.SM_078_REST:
				script.onRest();
				break;
			case BpScriptConsts.SM_300_TIME_CHANGE:
				script.onTimeChange();
				break;
			case BpScriptConsts.SM_301_E001:
				((MasterScript) script).onEvent001();
				break;
			case BpScriptConsts.SM_302_E002:
				((MasterScript) script).onEvent002();
				break;
			case BpScriptConsts.SM_303_E003:
				((MasterScript) script).onEvent003();
				break;
			case BpScriptConsts.SM_304_E004:
				((MasterScript) script).onEvent004();
				break;
			case BpScriptConsts.SM_305_E005:
				((MasterScript) script).onEvent005();
				break;
			case BpScriptConsts.SM_306_E006:
				((MasterScript) script).onEvent006();
				break;
			case BpScriptConsts.SM_307_E007:
				((MasterScript) script).onEvent007();
				break;
			case BpScriptConsts.SM_308_E008:
				((MasterScript) script).onEvent008();
				break;
			case BpScriptConsts.SM_309_E009:
				((MasterScript) script).onEvent009();
				break;
			case BpScriptConsts.SM_310_E010:
				((MasterScript) script).onEvent010();
				break;
			case BpScriptConsts.SM_311_E011:
				((MasterScript) script).onEvent011();
				break;
			case BpScriptConsts.SM_312_E012:
				((MasterScript) script).onEvent012();
				break;
			case BpScriptConsts.SM_313_E013:
				((MasterScript) script).onEvent013();
				break;
			case BpScriptConsts.SM_314_E014:
				((MasterScript) script).onEvent014();
				break;
			case BpScriptConsts.SM_315_E015:
				((MasterScript) script).onEvent015();
				break;
			case BpScriptConsts.SM_316_E016:
				((MasterScript) script).onEvent016();
				break;
			case BpScriptConsts.SM_317_E017:
				((MasterScript) script).onEvent017();
				break;
			case BpScriptConsts.SM_318_E018:
				((MasterScript) script).onEvent018();
				break;
			case BpScriptConsts.SM_319_E019:
				((MasterScript) script).onEvent019();
				break;
			case BpScriptConsts.SM_320_E020:
				((MasterScript) script).onEvent020();
				break;
			case BpScriptConsts.SM_321_E021:
				((MasterScript) script).onEvent021();
				break;
			case BpScriptConsts.SM_322_E022:
				((MasterScript) script).onEvent022();
				break;
			case BpScriptConsts.SM_323_E023:
				((MasterScript) script).onEvent023();
				break;
			case BpScriptConsts.SM_324_E024:
				((MasterScript) script).onEvent024();
				break;
			case BpScriptConsts.SM_325_E025:
				((MasterScript) script).onEvent025();
				break;
			case BpScriptConsts.SM_326_E026:
				((MasterScript) script).onEvent026();
				break;
			case BpScriptConsts.SM_327_E027:
				((MasterScript) script).onEvent027();
				break;
			case BpScriptConsts.SM_328_E028:
				((MasterScript) script).onEvent028();
				break;
			case BpScriptConsts.SM_329_E029:
				((MasterScript) script).onEvent029();
				break;
			case BpScriptConsts.SM_330_E030:
				((MasterScript) script).onEvent030();
				break;
			case BpScriptConsts.SM_331_E031:
				((MasterScript) script).onEvent031();
				break;
			case BpScriptConsts.SM_332_E032:
				((MasterScript) script).onEvent032();
				break;
			case BpScriptConsts.SM_333_E033:
				((MasterScript) script).onEvent033();
				break;
			case BpScriptConsts.SM_334_E034:
				((MasterScript) script).onEvent034();
				break;
			case BpScriptConsts.SM_335_E035:
				((MasterScript) script).onEvent035();
				break;
			case BpScriptConsts.SM_336_E036:
				((MasterScript) script).onEvent036();
				break;
			case BpScriptConsts.SM_337_E037:
				((MasterScript) script).onEvent037();
				break;
			case BpScriptConsts.SM_338_E038:
				((MasterScript) script).onEvent038();
				break;
			case BpScriptConsts.SM_339_E039:
				((MasterScript) script).onEvent039();
				break;
			case BpScriptConsts.SM_340_E040:
				((MasterScript) script).onEvent040();
				break;
			case BpScriptConsts.SM_341_E041:
				((MasterScript) script).onEvent041();
				break;
			case BpScriptConsts.SM_342_E042:
				((MasterScript) script).onEvent042();
				break;
			case BpScriptConsts.SM_343_E043:
				((MasterScript) script).onEvent043();
				break;
			case BpScriptConsts.SM_344_E044:
				((MasterScript) script).onEvent044();
				break;
			case BpScriptConsts.SM_345_E045:
				((MasterScript) script).onEvent045();
				break;
			case BpScriptConsts.SM_346_E046:
				((MasterScript) script).onEvent046();
				break;
			case BpScriptConsts.SM_347_E047:
				((MasterScript) script).onEvent047();
				break;
			case BpScriptConsts.SM_348_E048:
				((MasterScript) script).onEvent048();
				break;
			case BpScriptConsts.SM_349_E049:
				((MasterScript) script).onEvent049();
				break;
			case BpScriptConsts.SM_350_E050:
				((MasterScript) script).onEvent050();
				break;
			case BpScriptConsts.SM_351_E051:
				((MasterScript) script).onEvent052();
				break;
			case BpScriptConsts.SM_352_E052:
				((MasterScript) script).onEvent052();
				break;
			case BpScriptConsts.SM_353_E053:
				((MasterScript) script).onEvent053();
				break;
			case BpScriptConsts.SM_354_E054:
				((MasterScript) script).onEvent054();
				break;
			case BpScriptConsts.SM_355_E055:
				((MasterScript) script).onEvent055();
				break;
			case BpScriptConsts.SM_356_E056:
				((MasterScript) script).onEvent056();
				break;
			case BpScriptConsts.SM_357_E057:
				((MasterScript) script).onEvent057();
				break;
			case BpScriptConsts.SM_358_E058:
				((MasterScript) script).onEvent058();
				break;
			case BpScriptConsts.SM_359_E059:
				((MasterScript) script).onEvent059();
				break;
			case BpScriptConsts.SM_360_E060:
				((MasterScript) script).onEvent060();
				break;
			case BpScriptConsts.SM_361_E061:
				((MasterScript) script).onEvent061();
				break;
			case BpScriptConsts.SM_362_E062:
				((MasterScript) script).onEvent062();
				break;
			case BpScriptConsts.SM_363_E063:
				((MasterScript) script).onEvent063();
				break;
			case BpScriptConsts.SM_364_E064:
				((MasterScript) script).onEvent064();
				break;
			case BpScriptConsts.SM_365_E065:
				((MasterScript) script).onEvent065();
				break;
			case BpScriptConsts.SM_366_E066:
				((MasterScript) script).onEvent066();
				break;
			case BpScriptConsts.SM_367_E067:
				((MasterScript) script).onEvent067();
				break;
			case BpScriptConsts.SM_368_E068:
				((MasterScript) script).onEvent068();
				break;
			case BpScriptConsts.SM_369_E069:
				((MasterScript) script).onEvent069();
				break;
			case BpScriptConsts.SM_370_E070:
				((MasterScript) script).onEvent070();
				break;
			case BpScriptConsts.SM_371_E071:
				((MasterScript) script).onEvent071();
				break;
			case BpScriptConsts.SM_372_E072:
				((MasterScript) script).onEvent072();
				break;
			case BpScriptConsts.SM_373_E073:
				((MasterScript) script).onEvent073();
				break;
			case BpScriptConsts.SM_374_E074:
				((MasterScript) script).onEvent074();
				break;
			case BpScriptConsts.SM_375_E075:
				((MasterScript) script).onEvent075();
				break;
			case BpScriptConsts.SM_376_E076:
				((MasterScript) script).onEvent076();
				break;
			case BpScriptConsts.SM_377_E077:
				((MasterScript) script).onEvent077();
				break;
			case BpScriptConsts.SM_378_E078:
				((MasterScript) script).onEvent078();
				break;
			case BpScriptConsts.SM_379_E079:
				((MasterScript) script).onEvent079();
				break;
			case BpScriptConsts.SM_380_E080:
				((MasterScript) script).onEvent080();
				break;
			case BpScriptConsts.SM_381_E081:
				((MasterScript) script).onEvent081();
				break;
			case BpScriptConsts.SM_382_E082:
				((MasterScript) script).onEvent082();
				break;
			case BpScriptConsts.SM_383_E083:
				((MasterScript) script).onEvent083();
				break;
			case BpScriptConsts.SM_384_E084:
				((MasterScript) script).onEvent084();
				break;
			case BpScriptConsts.SM_385_E085:
				((MasterScript) script).onEvent085();
				break;
			case BpScriptConsts.SM_386_E086:
				((MasterScript) script).onEvent086();
				break;
			case BpScriptConsts.SM_387_E087:
				((MasterScript) script).onEvent087();
				break;
			case BpScriptConsts.SM_388_E088:
				((MasterScript) script).onEvent088();
				break;
			case BpScriptConsts.SM_389_E089:
				((MasterScript) script).onEvent099();
				break;
			case BpScriptConsts.SM_390_E090:
				((MasterScript) script).onEvent090();
				break;
			case BpScriptConsts.SM_391_E091:
				((MasterScript) script).onEvent091();
				break;
			case BpScriptConsts.SM_392_E092:
				((MasterScript) script).onEvent092();
				break;
			case BpScriptConsts.SM_393_E093:
				((MasterScript) script).onEvent093();
				break;
			case BpScriptConsts.SM_394_E094:
				((MasterScript) script).onEvent094();
				break;
			case BpScriptConsts.SM_395_E095:
				((MasterScript) script).onEvent095();
				break;
			case BpScriptConsts.SM_396_E096:
				((MasterScript) script).onEvent096();
				break;
			case BpScriptConsts.SM_397_E097:
				((MasterScript) script).onEvent097();
				break;
			case BpScriptConsts.SM_398_E098:
				((MasterScript) script).onEvent098();
				break;
			case BpScriptConsts.SM_399_E099:
				((MasterScript) script).onEvent099();
				break;
			case BpScriptConsts.SM_400_E100:
				((MasterScript) script).onEvent100();
				break;
			case BpScriptConsts.SM_401_E101:
				((MasterScript) script).onEvent101();
				break;
			case BpScriptConsts.SM_402_E102:
				((MasterScript) script).onEvent102();
				break;
			case BpScriptConsts.SM_403_E103:
				((MasterScript) script).onEvent103();
				break;
			case BpScriptConsts.SM_404_E104:
				((MasterScript) script).onEvent104();
				break;
			case BpScriptConsts.SM_405_E105:
				((MasterScript) script).onEvent105();
				break;
			case BpScriptConsts.SM_406_E106:
				((MasterScript) script).onEvent106();
				break;
			case BpScriptConsts.SM_407_E107:
				((MasterScript) script).onEvent107();
				break;
			case BpScriptConsts.SM_408_E108:
				((MasterScript) script).onEvent108();
				break;
			case BpScriptConsts.SM_409_E109:
				((MasterScript) script).onEvent109();
				break;
			case BpScriptConsts.SM_410_E110:
				((MasterScript) script).onEvent110();
				break;
			case BpScriptConsts.SM_411_E111:
				((MasterScript) script).onEvent111();
				break;
			case BpScriptConsts.SM_412_E112:
				((MasterScript) script).onEvent112();
				break;
			case BpScriptConsts.SM_413_E113:
				((MasterScript) script).onEvent113();
				break;
			case BpScriptConsts.SM_414_E114:
				((MasterScript) script).onEvent114();
				break;
			case BpScriptConsts.SM_415_E115:
				((MasterScript) script).onEvent115();
				break;
			case BpScriptConsts.SM_416_E116:
				((MasterScript) script).onEvent116();
				break;
			case BpScriptConsts.SM_417_E117:
				((MasterScript) script).onEvent117();
				break;
			case BpScriptConsts.SM_418_E118:
				((MasterScript) script).onEvent118();
				break;
			case BpScriptConsts.SM_419_E119:
				((MasterScript) script).onEvent119();
				break;
			case BpScriptConsts.SM_420_E120:
				((MasterScript) script).onEvent120();
				break;
			case BpScriptConsts.SM_421_E121:
				((MasterScript) script).onEvent121();
				break;
			case BpScriptConsts.SM_422_E122:
				((MasterScript) script).onEvent122();
				break;
			case BpScriptConsts.SM_423_E123:
				((MasterScript) script).onEvent123();
				break;
			case BpScriptConsts.SM_424_E124:
				((MasterScript) script).onEvent124();
				break;
			case BpScriptConsts.SM_425_E125:
				((MasterScript) script).onEvent125();
				break;
			case BpScriptConsts.SM_426_E126:
				((MasterScript) script).onEvent126();
				break;
			case BpScriptConsts.SM_427_E127:
				((MasterScript) script).onEvent127();
				break;
			case BpScriptConsts.SM_428_E128:
				((MasterScript) script).onEvent128();
				break;
			case BpScriptConsts.SM_429_E129:
				((MasterScript) script).onEvent129();
				break;
			case BpScriptConsts.SM_430_E130:
				((MasterScript) script).onEvent130();
				break;
			case BpScriptConsts.SM_431_E131:
				((MasterScript) script).onEvent131();
				break;
			case BpScriptConsts.SM_432_E132:
				((MasterScript) script).onEvent132();
				break;
			case BpScriptConsts.SM_433_E133:
				((MasterScript) script).onEvent133();
				break;
			case BpScriptConsts.SM_434_E134:
				((MasterScript) script).onEvent134();
				break;
			case BpScriptConsts.SM_435_E135:
				((MasterScript) script).onEvent135();
				break;
			case BpScriptConsts.SM_436_E136:
				((MasterScript) script).onEvent136();
				break;
			case BpScriptConsts.SM_437_E137:
				((MasterScript) script).onEvent137();
				break;
			case BpScriptConsts.SM_438_E138:
				((MasterScript) script).onEvent138();
				break;
			case BpScriptConsts.SM_439_E139:
				((MasterScript) script).onEvent139();
				break;
			case BpScriptConsts.SM_460_E160:
				((MasterScript) script).onEvent160();
				break;
			case BpScriptConsts.SM_461_E161:
				((MasterScript) script).onEvent161();
				break;
			case BpScriptConsts.SM_462_E162:
				((MasterScript) script).onEvent162();
				break;
			case BpScriptConsts.SM_463_E163:
				((MasterScript) script).onEvent163();
				break;
			case BpScriptConsts.SM_464_E164:
				((MasterScript) script).onEvent164();
				break;
			case BpScriptConsts.SM_465_E165:
				((MasterScript) script).onEvent165();
				break;
			case BpScriptConsts.SM_466_E166:
				((MasterScript) script).onEvent166();
				break;
			default:
				throw new Exception("No action defined for message " + msg);
			}
		} catch (Exception ex) {
			JOGLErrorHandler.getInstance().fatalError(ex);
		}

		int ret = ScriptConsts.ACCEPT;
		return ret;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stackSendIOScriptEvent(final BpInteractiveObject io,
			final int msg, final Object[] params, final String eventname) {
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			if (!eventstack[i].exists()) {
				if (params != null
						&& params.length > 0) {
					eventstack[i].setParams(params);
				} else {
					eventstack[i].setParams(null);
				}
				if (eventname != null
						&& eventname.length() > 0) {
					eventstack[i].setEventname(eventname);
				} else {
					eventstack[i].setEventname(null);
				}

				eventstack[i].setSender(super.getEventSender());
				eventstack[i].setIo(io);
				eventstack[i].setMsg(msg);
				eventstack[i].setExist(true);
				break;
			}
		}
	}
	public void stackSendPostScriptEvent(final BpInteractiveObject io,
			final int msg, final Object[] params, final String eventname) {
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			if (!postScriptStack[i].exists()) {
				if (params != null
						&& params.length > 0) {
					postScriptStack[i].setParams(params);
				} else {
					postScriptStack[i].setParams(null);
				}
				if (eventname != null
						&& eventname.length() > 0) {
					postScriptStack[i].setEventname(eventname);
				} else {
					postScriptStack[i].setEventname(null);
				}

				postScriptStack[i].setSender(super.getEventSender());
				postScriptStack[i].setIo(io);
				postScriptStack[i].setMsg(msg);
				postScriptStack[i].setExist(true);
				break;
			}
		}
	}
	/**
	 * {@inheritDoc}
	 * @throws RPGException 
	 */
	@Override
	public void timerCheck() throws RPGException {
		if (super.getActiveTimers() > 0) {
			for (int i = 0, len = super.getMaxTimerScript(); i < len; i++) {
				BpScriptTimer st = scriptTimers[i];
				if (st.exists()) {
					if (st.hasFlag(1)) {
						if (!st.getIo().hasGameFlag(IoGlobals.GFLAG_ISINTREATZONE)) {
							while (st.getLastTimeCheck() + st.getCycleLength() < Time
									.getInstance().getGameTime()) {
								st.setLastTimeCheck(st.getLastTimeCheck() + st.getCycleLength());
							}
							continue;
						}
					}
					if (st.getLastTimeCheck() + st.getCycleLength() < Time.getInstance()
							.getGameTime()) {
						BpScriptable es = (BpScriptable) st.getScript();
						BpInteractiveObject io = st.getIo();
						int pos = st.getPos();
						if (es != null) {
							if (st.getName().equalsIgnoreCase("_R_A_T_")) {
								// if (Manage_Specific_RAT_Timer(st))
								continue;
							}
						}
						if (st.getRepeatTimes() == 1) {
							super.timerClearByNum(i);
						} else {
							if (st.getRepeatTimes() != 0) {
								st.setRepeatTimes(st.getRepeatTimes() - 1);
							}
							st.setLastTimeCheck(st.getLastTimeCheck() + st.getCycleLength());
						}

						if (es != null
								&& Interactive.getInstance().hasIO(
										io.getRefId())) {
							sendScriptEvent(es,
									ScriptConsts.SM_255_EXECUTELINE,
									null,
									io, null, pos);
						}
					}
				}
			}
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void timerFirstInit(final int number) {
		if (number < 100) {
			super.setMaxTimerScript(100);
		} else {
			super.setMaxTimerScript(number);
		}
		if (scriptTimers != null) {
			for (int i = 0; i < scriptTimers.length; i++) {
				scriptTimers[i] = null;
			}
			scriptTimers = null;
		}
		scriptTimers = new BpScriptTimer[super.getMaxTimerScript()];
		for (int i = 0; i < scriptTimers.length; i++) {
			scriptTimers[i] = new BpScriptTimer();
		}
		super.setActiveTimers(0);
	}
	private void transferPostScriptStack() {
		for (int i = 0; i < ScriptConsts.MAX_EVENT_STACK; i++) {
			if (postScriptStack[i].exists()) {
				stackSendIOScriptEvent(
						postScriptStack[i].getIo(),
						postScriptStack[i].getMsg(),
						postScriptStack[i].getParams(),
						postScriptStack[i].getEventname());
			}
		}
	}
}
