package com.dalonedrow.module.ff.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.ff.constants.FFIo;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFRoomData;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.PermalockAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.ScriptActionType;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.SetRoomTextAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.ShowTextAction;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.EquipmentGlobals;
import com.dalonedrow.rpg.base.flyweights.EquipmentItemModifier;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.Script;

@SuppressWarnings("unchecked")
public class WebServiceClientTest {
	@Before
	public void before() throws RPGException {
		FFWoFMController.getInstance();
	}
	@Test
	public void canGetTextByName() throws PooledException {
		String l = FFWebServiceClient.getInstance().getTextByName("START");
		assertTrue(l.startsWith("Only a foolhardy adventurer would embark"));
		assertTrue(l.endsWith("see you alive again...\r\n"));
	}
	@Test
	public void willNotFindMissingText() throws PooledException {
		String l = FFWebServiceClient.getInstance().getTextByName("NOTTHERE");
		assertNull(l);
	}
	@Test
	public void canGetScriptBundleByName() throws PooledException {
		ScriptAction[] scripts =
				((FFWebServiceClient) FFWebServiceClient.getInstance())
				.getScriptBundleByName("DR278_OnBashDoorFailure");
		assertEquals(3, scripts.length);
		assertEquals(PermalockAction.class, scripts[0].getClass());
		assertEquals(SetRoomTextAction.class, scripts[1].getClass());
		assertEquals(ShowTextAction.class, scripts[2].getClass());
		assertTrue(scripts[1] instanceof SetRoomTextAction);
	}
	@Test
	public void canGetRoomByCode() throws PooledException {
		FFRoomData room = ((FFWebServiceClient)
				FFWebServiceClient.getInstance()).getRoomByCode(1);
		assertEquals(1, room.getCode());
		assertEquals("1", room.getTextid());
	}
	@Test
	public void canGetEquipmentItemModifierByCode() throws PooledException {
		EquipmentItemModifier modifier = ((FFWebServiceClient)
				FFWebServiceClient.getInstance())
				.getEquipmentItemModifierByCode("two_int");
		assertFalse(modifier.isPercentage());
		assertEquals(0, modifier.getSpecial());
		assertEquals(2, modifier.getValue(), 0.0001f);
	}
	@Test
	public void canGetItemByName() throws PooledException {
		try {
			FFInteractiveObject itemIO = ((FFWebServiceClient)
					FFWebServiceClient.getInstance())
					.getItemByName("Iron sword");
			assertEquals(1, itemIO.getItemData().getWeight());
			assertEquals(1, itemIO.getItemData().getStackSize());
			assertEquals("none", itemIO.getScript().getLocalStringVariableValue(
					"reagent"));
			assertTrue(itemIO.hasTypeFlag(EquipmentGlobals.OBJECT_TYPE_1H));
			assertTrue(itemIO.hasTypeFlag(EquipmentGlobals.OBJECT_TYPE_WEAPON));
			assertTrue(Script.getInstance().isIOInGroup(itemIO, "BLADE"));
			assertTrue(Script.getInstance().isIOInGroup(itemIO, "ARMORY"));
			assertEquals(2, (int)
					itemIO.getItemData().getEquipitem().getElement(
							FFIo.getElementIndex("IO_EQUIPITEM_ELEMENT_DAMAGES")
							).getValue());
			assertFalse(itemIO.getItemData().getEquipitem().getElement(
							FFIo.getElementIndex("IO_EQUIPITEM_ELEMENT_DAMAGES")
							).isPercentage());
			itemIO = ((FFWebServiceClient)
					FFWebServiceClient.getInstance())
					.getItemByName("Orcish cleaver");
			assertEquals(2, (int)
					itemIO.getItemData().getEquipitem().getElement(
							FFIo.getElementIndex("IO_EQUIPITEM_ELEMENT_DAMAGES")
							).getValue());
			assertEquals("A crude cleaver-style falchion.",
					new String(itemIO.getItemData().getDescription()));
		} catch (RPGException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void canGetNPCByName() throws PooledException {
		FFInteractiveObject npc =
				((FFWebServiceClient) FFWebServiceClient.getInstance())
				.getNPCByName("ORC1");
		assertEquals("ORC1", new String(npc.getNPCData().getName()));
	}
	@Test
	public void canGetAllRooms() throws PooledException {
		try {
			assertNotNull(FFWoFMController.getInstance().getRoomIOByCode(278));
		} catch (RPGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
