package com.dalonedrow.module.barbarianprince.graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.Test;

import com.dalonedrow.engine.sprite.base.SimplePoint;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.graph.HexCoordinateSystem;
import com.dalonedrow.rpg.graph.TwoDimensional;
import com.dalonedrow.rpg.graph.WeightedGraphEdge;
import com.dalonedrow.utils.ArrayUtilities;

public class HexMapTest {
	@Test
	public void canCreate() throws RPGException {
		assertNotNull(HexMap.getInstance());
	}
	@Test
	public void canGetPaths() throws RPGException {
		Hex[] paths = HexMap.getInstance().getLandTravelOptions(
				new SimplePoint(1, 1), 1);
		// 1,1 has two neighbors reachable
		assertEquals(paths.length, 2);
		assertFalse(HexMap.getInstance().hasPathTo(
				new SimplePoint(1, 1), new SimplePoint(20, 1), 2));
		assertTrue(HexMap.getInstance().hasPathTo(
				new SimplePoint(3, 11), new SimplePoint(4, 9), 2));
	}
	@Test
	public void canTellIfHexHasRiver() throws RPGException {
		SimplePoint pt = new SimplePoint(15, 1);
		assertTrue(HexMap.getInstance().hasRiverNode(pt));
		assertTrue(HexMap.getInstance().hasRiverNode(
				HexMap.getInstance().getHex(pt).getIndex()));
		pt = new SimplePoint(18, 9);
		assertFalse(HexMap.getInstance().hasRiverNode(pt));
		assertFalse(HexMap.getInstance().hasRiverNode(
				HexMap.getInstance().getHex(pt).getIndex()));
	}
	@Test
	public void canGetRiverPaths() throws RPGException {
		final SimplePoint pt = new SimplePoint(15, 1);
		RiverGraphNode[] nodes = HexMap.getInstance().getRiverNodesForHex(pt);
		assertEquals(nodes.length, 2);
		Hex[] paths = HexMap.getInstance().getRiverTravelOptions(pt);
		assertEquals(paths.length, 7);
		assertTrue(Arrays.asList(paths).contains(
				HexMap.getInstance().getHex(new SimplePoint(16, 2))));
		assertTrue(Arrays.asList(paths).contains(
				HexMap.getInstance().getHex(new SimplePoint(14, 2))));
		assertFalse(Arrays.asList(paths).contains(
				HexMap.getInstance().getHex(new SimplePoint(12, 2))));
	}
	@Test
	public void canGetRoads() throws RPGException {
		assertFalse(HexMap.getInstance().hasRoadTo(
				new SimplePoint(1, 1), new SimplePoint(1, 2), 1));
		// distance from 11,9 to 10, 10 by road is 2
		assertFalse(HexMap.getInstance().hasRoadTo(
				new SimplePoint(11, 9), new SimplePoint(10, 10), 1));
		assertTrue(HexMap.getInstance().hasRoadTo(
				new SimplePoint(11, 9), new SimplePoint(10, 9), 1));
		assertTrue(HexMap.getInstance().hasRoadTo(
				new SimplePoint(11, 9), new SimplePoint(10, 10), 2));
	}
	@Test
	public void canGetRiverCrossings() throws RPGException {
		assertFalse(HexMap.getInstance().hasRiverCrossingTo(
				new SimplePoint(1, 1), new SimplePoint(2, 1)));
		assertTrue(HexMap.getInstance().hasRiverCrossingTo(
				new SimplePoint(1, 1), new SimplePoint(1, 2)));
		assertTrue(HexMap.getInstance().hasRiverCrossingTo(
				new SimplePoint(1, 2), new SimplePoint(1, 1)));
		assertEquals(HexMap.getInstance().getRiverCrossingName(
				new SimplePoint(1, 1), new SimplePoint(1, 2)), "Tragoth River");
	}
	@Test
	public void canCrossTragoth() throws RPGException {
		assertFalse(HexMap.getInstance().hasRiverCrossingTo(
				new SimplePoint(1, 1), new SimplePoint(2, 1)));
		assertTrue(HexMap.getInstance().hasRiverCrossingTo(
				new SimplePoint(1, 1), new SimplePoint(1, 2)));
		assertTrue(HexMap.getInstance().hasRiverCrossingTo(
				new SimplePoint(1, 2), new SimplePoint(1, 1)));
		assertEquals(HexMap.getInstance().getRiverCrossingName(
				new SimplePoint(1, 1), new SimplePoint(1, 2)), "Tragoth River");
	}
	@Test
	public void canGetHexes() throws RPGException {
		Hex hex = HexMap.getInstance().getHex(new SimplePoint());
		assertNull(hex);
		hex = HexMap.getInstance().getHex(new SimplePoint(1, 1));
		assertNotNull(hex);
		assertEquals(hex.getLocationName(), "The Free City of Ogon");
		hex = HexMap.getInstance().getHex(1, 1);
		assertNotNull(hex);
		assertEquals(hex.getLocationName(), "The Free City of Ogon");
		hex = HexMap.getInstance().getHex(new SimplePoint(12, 12));
		assertNotNull(hex);
		assertEquals(hex.getLocationName(), "Huldra Castle");
		hex = HexMap.getInstance().getHex(12, 12);
		assertNotNull(hex);
		assertEquals(hex.getLocationName(), "Huldra Castle");
	}
	@Test
	public void canGetNeighbors() throws RPGException {
		Hex hex = HexMap.getInstance().getHex(new SimplePoint(1, 1));
		int neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_N);
		HexCoordinateSystem coords = new HexCoordinateSystem(
				HexCoordinateSystem.EVEN_Q);
		
		assertEquals(neighbor, -1);
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_NNE);
		assertEquals(neighbor, -1);
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_SSE);
		assertTrue(
				HexMap.getInstance().getHexById(neighbor).getLocation().equals(
						new SimplePoint(2, 1)));
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_S);
		assertTrue(
				HexMap.getInstance().getHexById(neighbor).getLocation().equals(
						new SimplePoint(1, 2)));
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_SSW);
		assertEquals(neighbor, -1);
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_NNW);
		assertEquals(neighbor, -1);
		
		hex = HexMap.getInstance().getHex(new SimplePoint(10, 10));
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_N);
		assertTrue(
				HexMap.getInstance().getHexById(neighbor).getLocation().equals(
						new SimplePoint(10, 9)));
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_NNE);
		assertTrue(
				HexMap.getInstance().getHexById(neighbor).getLocation().equals(
						new SimplePoint(11, 10)));
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_SSE);
		assertTrue(
				HexMap.getInstance().getHexById(neighbor).getLocation().equals(
						new SimplePoint(11, 11)));
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_S);
		assertTrue(
				HexMap.getInstance().getHexById(neighbor).getLocation().equals(
						new SimplePoint(10, 11)));
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_SSW);
		assertTrue(
				HexMap.getInstance().getHexById(neighbor).getLocation().equals(
						new SimplePoint(9, 11)));
		neighbor = HexMap.getInstance().getNeighbor(hex.getIndex(),
				HexCoordinateSystem.DIRECTION_NNW);
		assertTrue(
				HexMap.getInstance().getHexById(neighbor).getLocation().equals(
						new SimplePoint(9, 10)));
	}
}
