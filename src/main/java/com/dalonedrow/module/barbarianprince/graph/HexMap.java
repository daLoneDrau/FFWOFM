package com.dalonedrow.module.barbarianprince.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.dalonedrow.engine.sprite.base.SimplePoint;
import com.dalonedrow.engine.sprite.base.SimpleVector3;
import com.dalonedrow.module.barbarianprince.constants.UiSettings;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.graph.DijkstraDirectedSearch;
import com.dalonedrow.rpg.graph.DijkstraUndirectedSearch;
import com.dalonedrow.rpg.graph.EdgeWeightedDirectedGraph;
import com.dalonedrow.rpg.graph.EdgeWeightedUndirectedGraph;
import com.dalonedrow.rpg.graph.HexCoordinateSystem;
import com.dalonedrow.rpg.graph.Hexagon;
import com.dalonedrow.rpg.graph.WeightedGraphEdge;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * 
 * @author drau
 *
 */
public class HexMap {
	/** the distance cost for travelling downriver. */
	private static final float DOWN_RIVER = .33f;
	/** the distance cost for travelling upriver. */
	private static final float UP_RIVER = .5f;
	/** the singleton instance. */
	private static HexMap instance;
	/**
	 * Gets the one and only instance of {@link HexMap}.
	 * @return {@link HexMap}
	 * @throws RPGException 
	 */
	public static HexMap getInstance() throws RPGException {
		if (HexMap.instance == null) {
			HexMap.instance = new HexMap();
		}
		return HexMap.instance;
	}
	/** Hidden constructor. 
	 * @throws RPGException */
	private HexMap() throws RPGException {
		load();
	}
	/** the hex coordinate system; useful for determining neighbors. */
	private HexCoordinateSystem coordinateSystem;
	/** the list of map hexes. */
	private Hex[] hexes;
	/** the map graph. */
	private EdgeWeightedUndirectedGraph hexGraph;
	/** the graph of all river crossing edges. */
	private EdgeWeightedUndirectedGraph riverCrossingsGraph;
	/** the graph of all river edges. */
	private EdgeWeightedDirectedGraph riverGraph;
	/** the list of river nodes. */
	private RiverGraphNode[] riverNodes;
	/** the graph of all road edges. */
	private EdgeWeightedUndirectedGraph roadGraph;
	/**
	 * Gets the path from the source hex to a destination.
	 * @param source the source location
	 * @param v the destination
	 * @return {@link WeightedGraphEdge}[]
	 * @throws RPGException if an error occurs
	 */
	public WeightedGraphEdge[] getAirPath(final int source, final int v) 
			throws RPGException {
		DijkstraUndirectedSearch dijkstra = 
				new DijkstraUndirectedSearch(hexGraph, source);
		WeightedGraphEdge[] path = dijkstra.pathTo(v);
		dijkstra = null;
		return path;
	}
	private int getNextRiverNodeId() {
		int i = 0;
		while (true) {
			boolean found = false;
			for (int j = 0, len = riverNodes.length; j < len; j++) {
				if (i == riverNodes[j].getIndex()) {
					found = true;
					break;
				}
			}
			if (!found) {
				break;
			}
			i++;
		}
		return i;
	}
	/**
	 * Gets a {@link Hex} by its coordinates.
	 * @param pt the coordinates
	 * @return {@link Hex}
	 */
	public Hex getHex(final SimplePoint pt) {
		Hex hex = null;
		for (int i = hexes.length - 1; i >= 0; i--) {
			if (hexes[i].getLocation().equals(pt)) {
				hex = hexes[i];
				break;
			}
		}
		return hex;
	}
	/**
	 * Gets a {@link Hex} by its coordinates.
	 * @param pt the coordinates
	 * @return {@link Hex}
	 */
	public Hex getHex(final SimpleVector3 v) {
		Hex hex = null;
		for (int i = hexes.length - 1; i >= 0; i--) {
			if (hexes[i].getHexagon().getVector().equals(v)) {
				hex = hexes[i];
				break;
			}
		}
		return hex;
	}
	/**
	 * Gets a {@link Hex} by its coordinates.
	 * @param x the x-coordinates
	 * @param y the y-coordinates
	 * @return {@link Hex}
	 */
	public Hex getHex(final int x, final int y) {
		Hex hex = null;
		for (int i = hexes.length - 1; i >= 0; i--) {
			if (hexes[i].getLocation().equals(x, y)) {
				hex = hexes[i];
				break;
			}
		}
		return hex;
	}
	/**
	 * Gets a {@link Hex} by its id.
	 * @param id the id
	 * @return {@link Hex}
	 */
	public Hex getHexById(final int id) {
		Hex hex = null;
		for (int i = hexes.length - 1; i >= 0; i--) {
			if (hexes[i].getIndex() == id) {
				hex = hexes[i];
				break;
			}
		}
		return hex;
	}
	/**
	 * Gets the path from the source hex to a destination.
	 * @param source the source location
	 * @param v the destination
	 * @return {@link WeightedGraphEdge}[]
	 * @throws RPGException if an error occurs
	 */
	public WeightedGraphEdge[] getLandPath(final int source, final int v) 
			throws RPGException {
		// is destination 
		DijkstraUndirectedSearch dijkstra = 
				new DijkstraUndirectedSearch(roadGraph, source);
		WeightedGraphEdge[] path = dijkstra.pathTo(v);
		if (path == null) {
			dijkstra = new DijkstraUndirectedSearch(hexGraph, source);
			path = dijkstra.pathTo(v);
		}
		dijkstra = null;
		return path;
	}
	/**
	 * Gets a list of path options that are reachable within a certain distance
	 * of the location.
	 * @param pt the location
	 * @param distance the distance
	 * @return {@link Hex}[]
	 * @throws RPGException if an error occurs
	 */
	public Hex[] getLandTravelOptions(final SimplePoint pt,
			final float distance) throws RPGException {
		DijkstraUndirectedSearch dijkstra = 
				new DijkstraUndirectedSearch(hexGraph, getHex(pt).getIndex());
		Hex[] list = new Hex[0];
		for (int i = hexes.length - 1; i >= 0; i--) {
			if (hexes[i].getIndex() == getHex(pt).getIndex()) {
				continue;
			}
			if (dijkstra.hasPathTo(hexes[i].getIndex())
					&& dijkstra.distanceTo(hexes[i].getIndex()) <= distance) {
				list = ArrayUtilities.getInstance().extendArray(hexes[i], list);
			}
		}
		return list;
	}
	/**
	 * Gets the reference id of a hex's neighbor.
	 * @param hexId the hex's id
	 * @param direction the direction
	 * @return {@link int}
	 * @throws RPGException if an error occurs
	 */
	public int getNeighbor(final int hexId, final int direction)
			throws RPGException {
		int neighbor = -1;
		Hex hex = getHexById(hexId);
		if (hex != null) {
			hex.getHexagon().getVector();
			hex = getHex(coordinateSystem.getNeighborCoordinates(
					hex.getHexagon(), direction));
			if (hex != null
					&& hex.getLocation().getY() > 0) {
				neighbor = hex.getIndex();
			}
		}
		return neighbor;
	}
	public RiverGraphNode[] getRiverNodesForHex(final SimplePoint pt) {
		return getRiverNodesForHex(getHex(pt).getIndex());
	}
	public RiverGraphNode[] getRiverNodesForHex(final int hexId) {
		RiverGraphNode[] nodes = new RiverGraphNode[0];
		for (int i = riverNodes.length - 1; i >= 0; i--) {
			if (riverNodes[i].getType() == RiverGraphNode.RIVER_RAFT
					&& (riverNodes[i].getBank0() == hexId
					|| riverNodes[i].getBank1() == hexId)) {
				nodes = ArrayUtilities.getInstance().extendArray(
						riverNodes[i], nodes);
			}
		}
		return nodes;
	}
	private Hex[] getRiverNodeAdjacencies(final int nodeId) {
		Hex[] list = new Hex[0];
		for (int i = riverNodes.length - 1; i >= 0; i--) {
			RiverGraphNode node = riverNodes[i];
			if (riverNodes[i].getType() == RiverGraphNode.RIVER_RAFT
					&& riverNodes[i].getIndex() == nodeId) {
				Hex hex = getHexById(node.getBank0());
				if (hex.getLocation().getY() > 0) {
					list = ArrayUtilities.getInstance().extendArray(hex, list);
				}
				hex = getHexById(node.getBank1());
				if (hex.getLocation().getY() > 0) {
					list = ArrayUtilities.getInstance().extendArray(hex, list);
				}
				hex = null;
			}
		}
		return list;
	}
	/**
	 * Gets a {@link RiverGraphNode} by its id.
	 * @param id the id
	 * @return {@link RiverGraphNode}
	 */
	public RiverGraphNode getRiverNodeById(final int id) {
		RiverGraphNode node = null;
		for (int i = riverNodes.length - 1; i >= 0; i--) {
			if (riverNodes[i].getIndex() == id) {
				node = riverNodes[i];
				break;
			}
		}
		return node;
	}
	/**
	 * Gets the list of path options available when traveling by river from
	 * a specific location.
	 * @param locId the location id
	 * @return {@link Hex}[]
	 * @throws RPGException if an error occurs
	 */
	public Hex[] getRiverTravelOptions(final int locId) throws RPGException {
		return getRiverTravelOptions(getHexById(locId));
	}
	/**
	 * Gets the list of path options available when traveling by river from
	 * a specific location.
	 * @param hex the location
	 * @return {@link Hex}[]
	 * @throws RPGException if an error occurs
	 */
	public Hex[] getRiverTravelOptions(final Hex hex)
			throws RPGException {
		return getRiverTravelOptions(hex.getLocation());
	}
	/**
	 * Gets the list of path options available when travelling by river from
	 * a specific location.
	 * @param pt the location
	 * @return {@link Hex}[]
	 * @throws RPGException if an error occurs
	 */
	public Hex[] getRiverTravelOptions(final SimplePoint pt)
			throws RPGException {
		RiverGraphNode[] nodes = HexMap.getInstance().getRiverNodesForHex(pt);
		Hex[] paths = new Hex[0];
		HashMap<SimplePoint, Integer> map = new HashMap<SimplePoint, Integer>();
		for (int i = nodes.length - 1; i >= 0; i--) {
			Hex[] list = HexMap.getInstance().getRiverPathOptions(nodes[i]);
			for (int j = list.length - 1; j >= 0; j--) {
				if (list[j].getLocation().equals(pt)) {
					// skip starting location
					continue;
				}
				if (map.containsKey(list[j].getLocation())) {
					// do not add locations twice
					continue;
				}
				map.put(list[j].getLocation(), 0);
				paths = ArrayUtilities.getInstance().extendArray(
						list[j], paths);
			}
			list = null;
		}
		nodes = null;
		map = null;
		return paths;
	}
	/**
	 * Gets the name of the river being crossed.
	 * @param from the source hex
	 * @param to the destination hex
	 * @return {@link String}
	 */
	public String getRiverCrossingName(final Hex from, final Hex to) {
		return WebServiceClient.getInstance().getRiverCrossingName(
				from.getIndex(), to.getIndex());
	}
	/**
	 * Gets the name of the river being crossed.
	 * @param from the source hex
	 * @param to the destination hex
	 * @return {@link String}
	 */
	public String getRiverCrossingName(final int from, final int to) {
		return  WebServiceClient.getInstance().getRiverCrossingName(from, to);
	}
	/**
	 * Gets the name of the river being crossed.
	 * @param from the source hex
	 * @param to the destination hex
	 * @return {@link String}
	 */
	public String getRiverCrossingName(final SimplePoint from,
			final SimplePoint to) {
		return WebServiceClient.getInstance().getRiverCrossingName(
				getHex(from).getIndex(), getHex(to).getIndex());
	}
	/**
	 * Gets a list of path options that are reachable within a certain distance
	 * of the location.
	 * @param pt the location
	 * @param distance the distance
	 * @return {@link Hex}[]
	 * @throws RPGException if an error occurs
	 */
	public double getRiverDistanceTo(final RiverGraphNode node, final int v)
			throws RPGException {
		DijkstraDirectedSearch dijkstra = 
				new DijkstraDirectedSearch(riverGraph, node.getIndex());
		return dijkstra.distanceTo(v);
	}
	/**
	 * Gets a specific river node by the hexes it separates.
	 * @param hex0 the id for the first hex
	 * @param hex1 the id for the second hex
	 * @return {@link RiverGraphNode}
	 */
	private RiverGraphNode getRiverNodeByCrossing(final int hex0, 
			final int hex1) {
		RiverGraphNode node = null;
		for (int i = riverNodes.length - 1; i >= 0; i--) {
			if ((riverNodes[i].getBank0() == hex0
					&& riverNodes[i].getBank1() == hex1)
					|| (riverNodes[i].getBank1() == hex0
							&& riverNodes[i].getBank0() == hex1)) {
				node = riverNodes[i];
				break;
			}
		}
		return node;
	}
	/**
	 * Gets the path from the source hex to a destination.
	 * @param source the source location
	 * @param v the destination
	 * @return {@link WeightedGraphEdge}[]
	 * @throws RPGException if an error occurs
	 */
	private WeightedGraphEdge[] getRiverPath(final int source, final int v) 
			throws RPGException {
		DijkstraDirectedSearch dijkstra = 
				new DijkstraDirectedSearch(riverGraph, source);
		WeightedGraphEdge[] path = dijkstra.pathTo(v);
		dijkstra = null;
		return path;
	}
	public WeightedGraphEdge[] getRiverPathList(final Hex hex0, final Hex hex1)
	throws RPGException {
		WeightedGraphEdge[] e = null;
		RiverGraphNode[] nodes0 = HexMap.getInstance().getRiverNodesForHex(
				hex0.getLocation());
		RiverGraphNode[] nodes1 = HexMap.getInstance().getRiverNodesForHex(
				hex1.getLocation());
		for (int i = nodes0.length - 1; i >= 0; i--) {
			for (int j = nodes1.length - 1; j >= 0; j--) {
				if (e == null) {
					e = getRiverPath(
							nodes0[i].getIndex(), nodes1[j].getIndex());
				} else {
					WeightedGraphEdge[] t = getRiverPath(
							nodes0[i].getIndex(), nodes1[j].getIndex());
					if (e.length > t.length) {
						e = t;
					}
					t = null;
				}
			}			
		}
		return e;
	}
	/**
	 * Gets a list of path options that are reachable within a certain distance
	 * of the location.
	 * @param node the river node
	 * @return {@link Hex}[]
	 * @throws RPGException if an error occurs
	 */
	private Hex[] getRiverPathOptions(final RiverGraphNode node)
			throws RPGException {
		DijkstraDirectedSearch dijkstra = 
				new DijkstraDirectedSearch(riverGraph, node.getIndex());
		Hex[] list = new Hex[0];
		for (int i = riverNodes.length - 1; i >= 0; i--) {
			if (dijkstra.hasPathTo(riverNodes[i].getIndex())
					&& dijkstra.distanceTo(riverNodes[i].getIndex()) <= 1f) {
				Hex[] adj = getRiverNodeAdjacencies(riverNodes[i].getIndex());
				for (int j = adj.length - 1; j >= 0; j--) {
					list = ArrayUtilities.getInstance().extendArray(
							adj[j], list);
				}
			}
		}
		return list;
	}
	/**
	 * Determines if a hex has a valid path to another under a specific 
	 * distance.
	 * @param from the source hex
	 * @param to the destination hex
	 * @param distance the max distance
	 * @return <tt>true</tt> if there is a valid path; <tt>false</tt> otherwise
	 * @throws RPGException of an error occurs
	 */
	public boolean hasPathTo(final SimplePoint from, final SimplePoint to,
			final float distance) throws RPGException {
		boolean hasPath = false;
		DijkstraUndirectedSearch dijkstra = 
				new DijkstraUndirectedSearch(hexGraph, getHex(from).getIndex());
		if (dijkstra.hasPathTo(getHex(to).getIndex())
				&& dijkstra.distanceTo(getHex(to).getIndex()) <= distance) {
			hasPath = true;
		}
		return hasPath;
	}
	/**
	 * Determines if a hex has a valid path to another under a specific 
	 * distance.
	 * @param from the source hex
	 * @param to the destination hex
	 * @return <tt>true</tt> if there is a valid path; <tt>false</tt> otherwise
	 * @throws RPGException of an error occurs
	 */
	public boolean hasRiverCrossingTo(final SimplePoint from,
			final SimplePoint to) throws RPGException {
		boolean hasPath = false;
		DijkstraUndirectedSearch dijkstra = new DijkstraUndirectedSearch(
				riverCrossingsGraph, getHex(from).getIndex());
		if (dijkstra.hasPathTo(getHex(to).getIndex())
				&& dijkstra.distanceTo(getHex(to).getIndex()) <= 1) {
			hasPath = true;
		}
		return hasPath;
	}
	/**
	 * Determines if a hex has a valid path to another under a specific 
	 * distance.
	 * @param from the source hex
	 * @param to the destination hex
	 * @return <tt>true</tt> if there is a valid path; <tt>false</tt> otherwise
	 * @throws RPGException of an error occurs
	 */
	public boolean hasRiverCrossingTo(final Hex from, final Hex to)
			throws RPGException {
		boolean hasPath = false;
		DijkstraUndirectedSearch dijkstra = new DijkstraUndirectedSearch(
				riverCrossingsGraph, from.getIndex());
		if (dijkstra.hasPathTo(to.getIndex())
				&& dijkstra.distanceTo(to.getIndex()) <= 1) {
			hasPath = true;
		}
		return hasPath;
	}
	/**
	 * Determines if a hex location has river nodes on one or more of its sides.
	 * @param hexId the hex id
	 * @return <tt>true</tt> if the hex has a river on one or more of its side;
	 * <tt>false</tt> otherwise
	 */
	public boolean hasRiverNode(final int hexId) {
		boolean has = false;
		for (int i = riverNodes.length - 1; i >= 0; i--) {
			if (riverNodes[i].getType() == RiverGraphNode.RIVER_RAFT
					&& (riverNodes[i].getBank0() == hexId
					|| riverNodes[i].getBank1() == hexId)) {
				has = true;
				break;
			}
		}
		return has;
	}
	/**
	 * Determines if a hex location has river nodes on one or more of its sides.
	 * @param pt the hex's location
	 * @return <tt>true</tt> if the hex has a river on one or more of its side;
	 * <tt>false</tt> otherwise
	 */
	public boolean hasRiverNode(final SimplePoint pt) {
		return hasRiverNode(getHex(pt).getIndex());
	}
	/**
	 * Determines if a hex has a valid path to another under a specific 
	 * distance.
	 * @param from the source hex
	 * @param to the destination hex
	 * @param distance the max distance
	 * @return <tt>true</tt> if there is a valid path; <tt>false</tt> otherwise
	 * @throws RPGException of an error occurs
	 */
	public boolean hasRoadTo(final SimplePoint from, final SimplePoint to,
			final float distance) throws RPGException {
		boolean hasPath = false;
		DijkstraUndirectedSearch dijkstra = new DijkstraUndirectedSearch(
				roadGraph, getHex(from).getIndex());
		if (dijkstra.hasPathTo(getHex(to).getIndex())
				&& dijkstra.distanceTo(getHex(to).getIndex()) <= distance) {
			hasPath = true;
		}
		return hasPath;
	}
	/**
	 * Determines if a hex has a valid path to another under a specific 
	 * distance.
	 * @param from the source hex
	 * @return <tt>true</tt> if there is a valid path; <tt>false</tt> otherwise
	 * @throws RPGException of an error occurs
	 */
	public boolean isOnRoad(final Hex from) throws RPGException {
		return isOnRoad(from.getIndex());
	}
	/**
	 * Determines if a vertex is on the road graph.
	 * @param v the source vertex
	 * @return <tt>true</tt> if there is a valid path; <tt>false</tt> otherwise
	 * @throws RPGException of an error occurs
	 */
	public boolean isOnRoad(final int v) throws RPGException {
		boolean is = false;
		for (int i = roadGraph.getNumberOfEdges() - 1; i >= 0; i--) {
			if (roadGraph.getEdge(i).getFrom() == v
					|| roadGraph.getEdge(i).getTo() == v) {
				is = true;
				break;
			}
		}
		return is;
	}
	/**
	 * Determines if a hex has a valid path to another under a specific 
	 * distance.
	 * @param from the source hex
	 * @param to the destination hex
	 * @param distance the max distance
	 * @return <tt>true</tt> if there is a valid path; <tt>false</tt> otherwise
	 * @throws RPGException of an error occurs
	 */
	public boolean hasRoadTo(final Hex from, final Hex to,
			final float distance) throws RPGException {
		boolean hasPath = false;
		DijkstraUndirectedSearch dijkstra = new DijkstraUndirectedSearch(
				roadGraph, from.getIndex());
		if (dijkstra.hasPathTo(to.getIndex())
				&& dijkstra.distanceTo(to.getIndex()) <= distance) {
			hasPath = true;
		}
		return hasPath;
	}
	/** 
	 * Loads the hex map. 
	 * @throws RPGException 
	 */
	private void load() throws RPGException {
		hexes = WebServiceClient.getInstance().loadHexes();
		hexGraph = new EdgeWeightedUndirectedGraph(0);
		for (int i = hexes.length - 1; i >= 0; i--) {
			hexGraph.addVertex(hexes[i]);
		}
		coordinateSystem = new HexCoordinateSystem(HexCoordinateSystem.EVEN_Q);
		for (int i = hexes.length - 1; i >= 0; i--) {
			Hexagon hexagon = new Hexagon(true, hexes[i].getIndex());
			hexagon.setCoordinates(
					coordinateSystem.getCubeCoordinates(
							(int) hexes[i].getLocation().getX(), 
							(int) hexes[i].getLocation().getY()));
			hexes[i].setHexagon(hexagon);
			coordinateSystem.addHexagon(hexes[i].getHexagon());
		}
		// copy hex to road map
		roadGraph = new EdgeWeightedUndirectedGraph(hexGraph);
		// copy hex to river crossings map
		riverCrossingsGraph = new EdgeWeightedUndirectedGraph(hexGraph);
		// copy hex to river map
		//riverGraph = new EdgeWeightedDirectedGraph(hexGraph);
		// create edges between all hexes
		for (int i = hexes.length - 1; i >= 0; i--) {
			if (hexes[i].getLocation().getY() == 0) {
				continue;
			}
			int dir = HexCoordinateSystem.DIRECTION_NNW;
			for (; dir >= HexCoordinateSystem.DIRECTION_N; dir--) {
				Hexagon h = coordinateSystem.getHexagon(
						coordinateSystem.getNeighborCoordinates(
								hexes[i].getHexagon(), dir));
				if (h != null
						&& getHexById(h.getId()).getLocation().getY() != 0) {
					hexGraph.addEdge(hexes[i].getIndex(), h.getId());
				}
			}
		}
		// create edges for roads
		int[][] edges = WebServiceClient.getInstance().loadRoads();
		for (int i = edges.length - 1; i >= 0; i--) {
			roadGraph.addEdge(edges[i][0], edges[i][1]);
		}
		// create edges for river crossings
		RiverCrossing[] riverCrossings = 
				WebServiceClient.getInstance().loadRiverCrossings();
		for (int i = riverCrossings.length - 1; i >= 0; i--) {
			riverCrossingsGraph.addEdge(riverCrossings[i].getFrom(),
					riverCrossings[i].getTo());
		}
		// add all land nodes to the river graph
		riverGraph = new EdgeWeightedDirectedGraph(0);
		riverNodes = new RiverGraphNode[0];
		for (int i = 0, len = hexes.length; i < len; i++) {
			if (getRiverNodeById(hexes[i].getIndex()) == null) {
				RiverGraphNode node = new RiverGraphNode();
				node.setType(RiverGraphNode.RIVER_BANK);
				node.setIndex(hexes[i].getIndex());
				node.setHexId(hexes[i].getIndex());
				node.setName("");
				riverNodes = ArrayUtilities.getInstance().extendArray(
						node, riverNodes);
				riverGraph.addVertex(node);
				node = null;
			}
		}
		// add all water nodes to the river graph
		for (int i = riverCrossings.length - 1; i >= 0; i--) {
			RiverGraphNode node = new RiverGraphNode();
			node.setType(RiverGraphNode.RIVER_RAFT);
			node.setIndex(getNextRiverNodeId());
			node.setBank0(riverCrossings[i].getFrom());
			node.setBank1(riverCrossings[i].getTo());
			node.setName(riverCrossings[i].getRiverName());
			riverNodes = ArrayUtilities.getInstance().extendArray(
					node, riverNodes);
			riverGraph.addVertex(node);
			node = null;
		}
		// get all nodes for Tragoth
		loadTragothRiver();
		loadRiver("Nesser River", false);
		loadRiver("Greater Nesser River", true);
		loadRiver("Lower Nesser River", true);
		loadRiver("Dienstal Branch", false);
		loadRiver("Largos River", true);
		// connect all rivers
		// TRAGOTH->NESSER
		RiverGraphNode up = getRiverNodeByCrossing(
				getHex(new SimplePoint(14, 1)).getIndex(),
				getHex(new SimplePoint(15, 1)).getIndex());
		RiverGraphNode down = getRiverNodeByCrossing(
				getHex(new SimplePoint(14, 1)).getIndex(),
				getHex(new SimplePoint(15, 2)).getIndex());
		riverGraph.addEdge(up.getIndex(), down.getIndex(), DOWN_RIVER);
		riverGraph.addEdge(down.getIndex(), up.getIndex(), UP_RIVER);
		up = getRiverNodeByCrossing(
				getHex(new SimplePoint(15, 1)).getIndex(),
				getHex(new SimplePoint(15, 2)).getIndex());
		riverGraph.addEdge(up.getIndex(), down.getIndex(), DOWN_RIVER);
		riverGraph.addEdge(down.getIndex(), up.getIndex(), UP_RIVER);
		// NESSER->GREATER NESSER
		up = getRiverNodeByCrossing(
				getHex(new SimplePoint(6, 9)).getIndex(),
				getHex(new SimplePoint(7, 9)).getIndex());
		down = getRiverNodeByCrossing(
				getHex(new SimplePoint(7, 9)).getIndex(),
				getHex(new SimplePoint(7, 10)).getIndex());
		riverGraph.addEdge(up.getIndex(), down.getIndex(), DOWN_RIVER);
		riverGraph.addEdge(down.getIndex(), up.getIndex(), UP_RIVER);
		up = getRiverNodeByCrossing(
				getHex(new SimplePoint(6, 9)).getIndex(),
				getHex(new SimplePoint(7, 10)).getIndex());
		riverGraph.addEdge(up.getIndex(), down.getIndex(), DOWN_RIVER);
		riverGraph.addEdge(down.getIndex(), up.getIndex(), UP_RIVER);
		// DIENSTAL->GREATER NESSER
		up = getRiverNodeByCrossing(
				getHex(new SimplePoint(12, 18)).getIndex(),
				getHex(new SimplePoint(12, 19)).getIndex());
		down = getRiverNodeByCrossing(
				getHex(new SimplePoint(12, 18)).getIndex(),
				getHex(new SimplePoint(11, 19)).getIndex());
		riverGraph.addEdge(up.getIndex(), down.getIndex(), UP_RIVER);
		riverGraph.addEdge(down.getIndex(), up.getIndex(), UP_RIVER);
		down = getRiverNodeByCrossing(
				getHex(new SimplePoint(11, 19)).getIndex(),
				getHex(new SimplePoint(12, 19)).getIndex());
		riverGraph.addEdge(up.getIndex(), down.getIndex(), DOWN_RIVER);
		riverGraph.addEdge(down.getIndex(), up.getIndex(), UP_RIVER);
		// LOWER NESSER->GREATER NESSER
		up = getRiverNodeByCrossing(
				getHex(new SimplePoint(14, 22)).getIndex(),
				getHex(new SimplePoint(15, 23)).getIndex());
		down = getRiverNodeByCrossing(
				getHex(new SimplePoint(15, 22)).getIndex(),
				getHex(new SimplePoint(15, 23)).getIndex());
		riverGraph.addEdge(up.getIndex(), down.getIndex(), DOWN_RIVER);
		riverGraph.addEdge(down.getIndex(), up.getIndex(), DOWN_RIVER);
		up = getRiverNodeByCrossing(
				getHex(new SimplePoint(14, 22)).getIndex(),
				getHex(new SimplePoint(15, 22)).getIndex());
		down = getRiverNodeByCrossing(
				getHex(new SimplePoint(14, 22)).getIndex(),
				getHex(new SimplePoint(15, 23)).getIndex());
		riverGraph.addEdge(up.getIndex(), down.getIndex(), DOWN_RIVER);
		riverGraph.addEdge(down.getIndex(), up.getIndex(), UP_RIVER);
	}
	/**
	 * Loads all nodes for a specific river.
	 * @param riverName the river's name
	 * @param upDown flag indicating the river's flow
	 * @throws RPGException if an error occurs
	 */
	private void loadRiver(final String riverName, final boolean upDown)
			throws RPGException {
		RiverGraphNode[] river = new RiverGraphNode[0];
		for (int i = 0, len = riverNodes.length; i < len; i++) {
			RiverGraphNode node = riverNodes[i];
			if (node.getType() == RiverGraphNode.RIVER_BANK) {
				node = null;
				continue;
			}
			if (riverName.equalsIgnoreCase(new String(node.getName()))) {
				river = ArrayUtilities.getInstance().extendArray(
						node, river);
			}
			node = null;
		}
		if (upDown) {
			for (int i = 0, len = river.length; i < len; i++) {
				if (i + 1 < len) {
					riverGraph.addEdge(river[i].getIndex(),
							river[i + 1].getIndex(), UP_RIVER);
				}
			}
			for (int i = river.length - 1; i >= 0; i--) {
				if (i - 1 >= 0) {
					riverGraph.addEdge(river[i].getIndex(),
							river[i - 1].getIndex(), DOWN_RIVER);
				}
			}
		} else {
			for (int i = 0, len = river.length; i < len; i++) {
				if (i + 1 < len) {
					riverGraph.addEdge(river[i].getIndex(),
							river[i + 1].getIndex(), DOWN_RIVER);
				}
			}
			for (int i = river.length - 1; i >= 0; i--) {
				if (i - 1 >= 0) {
					riverGraph.addEdge(river[i].getIndex(),
							river[i - 1].getIndex(), UP_RIVER);
				}
			}
		}
		river = null;
	}
	/**
	 * Loads all nodes for the Tragoth River.
	 * @throws RPGException if an error occurs
	 */
	private void loadTragothRiver() throws RPGException {
		RiverGraphNode[] river = new RiverGraphNode[0];
		for (int i = 0, len = riverNodes.length; i < len; i++) {
			RiverGraphNode node = riverNodes[i];
			if (node.getType() == RiverGraphNode.RIVER_BANK) {
				node = null;
				continue;
			}
			if ("Tragoth River".equalsIgnoreCase(new String(node.getName()))) {
				river = ArrayUtilities.getInstance().extendArray(
						node, river);
			}
			node = null;
		}
		Arrays.sort(river, new TragothRiverComparator(this));
		for (int i = 0, len = river.length; i < len; i++) {
			if (i + 1 < len) {
				riverGraph.addEdge(
						river[i].getIndex(), river[i + 1].getIndex(), UP_RIVER);
			}
		}
		for (int i = river.length - 1; i >= 0; i--) {
			if (i - 1 >= 0) {
				riverGraph.addEdge(river[i].getIndex(),
						river[i - 1].getIndex(), DOWN_RIVER);
			}
		}
		river = null;
	}
	/**
	 * Converts a {@link Hex} to a string to be displayed in the UI Location
	 * table.
	 * @param id the {@link Hex}'s reference id
	 * @return {@link String}
	 * @throws RPGException should never happen
	 */
	public String toUITableString(final int id) throws RPGException {
		Hex hex = getHexById(id);
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(hex.getLocationName());
			sb.append(" (");
			sb.append((int) hex.getLocation().getX());
			sb.append(", ");
			sb.append((int) hex.getLocation().getY());
			sb.append(")");
			sb.append('\n');
			if (!hex.getLocationName().equalsIgnoreCase(
					hex.getType().getTitle())) {
				sb.append(hex.getType().getTitle());
			} else {
				sb.append(' ');
			}
		} catch (PooledException e) {
			throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
		}
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		hex = null;
		return s;
	}
}
/**
 * Comparator for Tragoth River.
 * @author drau
 *
 */
class TragothRiverComparator implements Comparator<RiverGraphNode> {
	/** the hex map instance used for getting hex locations. */
	private HexMap m;
	/**
	 * Creates a new instance of {@link TragothRiverComparator}.
	 * @param hm the hex map instance 
	 */
	TragothRiverComparator(final HexMap hm) {
		m = hm;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(final RiverGraphNode arg0, final RiverGraphNode arg1) {
		int compare = 0;
		if (arg0.getType() == RiverGraphNode.RIVER_RAFT
				&& arg1.getType() == RiverGraphNode.RIVER_RAFT) {
			Hex bank00 = m.getHexById(arg0.getBank0());
			Hex bank01 = m.getHexById(arg0.getBank1());
			Hex bank10 = m.getHexById(arg1.getBank0());
			Hex bank11 = m.getHexById(arg1.getBank1());
			if (bank00.getLocation().getX() 
					< bank10.getLocation().getX()) {
				compare = -1;
			} else if (bank00.getLocation().getX() 
					== bank10.getLocation().getX()) {
				if (bank00.getLocation().getY() 
						< bank10.getLocation().getY()) {
					compare = -1;
				} else if (bank00.getLocation().getY() 
						== bank10.getLocation().getY()) {
					if (bank01.getLocation().getX() 
							< bank11.getLocation().getX()) {
						compare = -1;
					} else if (bank01.getLocation().getX() 
							== bank11.getLocation().getX()) {
						if (bank01.getLocation().getY() 
								< bank11.getLocation().getY()) {
							compare = -1;
						} else if (bank01.getLocation().getY() 
								== bank11.getLocation().getY()) {
							compare = 0;
						}
					} else {
						compare = 1;
					}
				} else {
					compare = 1;
				}
			} else {
				compare = 1;
			}
		}
		return compare;
	}	
}
