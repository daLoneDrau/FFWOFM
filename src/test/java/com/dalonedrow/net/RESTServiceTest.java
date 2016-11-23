package com.dalonedrow.net;

import org.junit.Test;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.module.tft.rpg.TFTSpell;
import com.dalonedrow.module.tft.rpg.TFTTalent;
import com.dalonedrow.module.tft.rpg.TFTTalentPrerequisite;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class RESTServiceTest {
	private final String	spellUrl	=
												"http://localhost:8080/PracticeSpring/the_fantasy_trip/spells/";
	private final String	talentUrl	=
												"http://localhost:8080/PracticeSpring/the_fantasy_trip/talents/";
	@Test
	public void canGetAll() throws RPGException {
		RestService.getInstance().callService(talentUrl, (String[]) null);
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			json.fromJson(TFTTalent.class, entry.toString());
		}
		RestService.getInstance().callService(spellUrl, (String[]) null);
		root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		json = new Json();
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			json.fromJson(TFTSpell.class, entry.toString());
		}
	}
	@Test
	public void canGetSpells() throws RPGException {
		RestService.getInstance().callService(spellUrl, (String[]) null);
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			json.fromJson(TFTSpell.class, entry.toString());
		}
	}
	@Test
	public void canGetTalents() throws RPGException {
		RestService.getInstance().callService(talentUrl, (String[]) null);
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			json.fromJson(TFTTalent.class, entry.toString());
		}
	}
}
