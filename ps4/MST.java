import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;

public class MST {

	private static Vector<Vector<IntegerPair>> AdjList; // Original Adjacency list
	private static Vector<Vector<IntegerPair>> newAdjList; // Adjacency list for just the MST
	private static Vector<Boolean> taken; // Boolean Vector to see if a vertex has been visited
	private static Vector<Integer> predecessor; //Vector storing predecessor of a vertex in an MST traversal
	private static Vector<Integer> maxWeight; //Vector storing the heaviest edge encountered by a vertex during traversal
	private static Vector<Vector<Integer>> paths;//Vector storing maxWeights for different MST sources
	private static PriorityQueue<IntegerTriple> pq;//Priority queue for Prim's algorithm
	//Integer Triple has been used as it makes the creation of the new Adjacency list easier
	private static int V;//Number of vertices

	public MST(Vector<Vector<IntegerPair>> AdjList, int V) {

		MST.AdjList = AdjList;
		newAdjList = new Vector<Vector<IntegerPair>>();
		for (int i = 0; i < V; i++)
			newAdjList.add(new Vector<IntegerPair>());
		taken = new Vector<Boolean>();
		predecessor = new Vector<Integer>();
		pq = new PriorityQueue<IntegerTriple>();
		paths = new Vector<Vector<Integer>>();
		MST.V = V;
	}

	private static void markVisited(Integer v) {
		taken.set(v, true);
		for (int j = 0; j < AdjList.get(v).size(); j++) {
			IntegerPair temp = AdjList.get(v).get(j);
			if (!taken.get(temp.first())) {
				pq.offer(new IntegerTriple(temp.second(), temp.first(), v));
				//PQ stores as (Edge weight, end vertex , start vertex) - Edge List
			}
		}
	}

	private static void travel() {
		while (!pq.isEmpty()) {
			IntegerTriple front = pq.poll();
			if (!taken.get(front.end())) {				
				//Add the new edges and vertices to the new Adjacency List
				newAdjList.get(front.start()).add(new IntegerPair(front.end(), front.weight()));
				newAdjList.get(front.end()).add(new IntegerPair(front.start(), front.weight()));
				markVisited(front.end());
			}
		}
	}

	private static void makeMST() {
		initialise();
		markVisited(0);//MST source is vertex 0
		travel();
	}

	private static void initialise() {
		taken.clear();
		taken.addAll(Collections.nCopies(V, false));
		predecessor.clear();
		predecessor.addAll(Collections.nCopies(V, -1));
		maxWeight = new Vector<Integer>();
		maxWeight.clear();
		maxWeight.addAll(Collections.nCopies(V, 0));
		pq.clear();
	}

	private static void maxWeightSearch(Integer v) {
		taken.set(v, true);
		for (int j = 0; j < newAdjList.get(v).size(); j++) {
			IntegerPair temp = newAdjList.get(v).get(j);
			if (!taken.get(temp.first())) {
				predecessor.set(temp.first(), v);
				if (!(predecessor.get(temp.first()) == -1)) {
					int w = maxWeight.get(predecessor.get(temp.first()));
					if (w < temp.second()) 
						maxWeight.set(temp.first(), temp.second());
					else
						maxWeight.set(temp.first(), w);
				}
				maxWeightSearch(temp.first());
			}
		}
	}

	public void traverseAllSources() {
		makeMST();
		int n = 10;
		if (V < 10)
			n = V;
		// n is the number of sources
		for (int i = 0; i < n; i++) {
			initialise();
			maxWeightSearch(i);
			paths.add(maxWeight);
		}
	}
	
	//Answer to query
	public int getMaxWeight(int source, int destination) {
		return paths.get(source).get(destination);
	}

}
