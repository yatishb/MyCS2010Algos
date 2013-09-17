import java.util.*;

// A0091545A
// Bathini Yatish
// Nirav, Karthik, Anirup, Toh Yong Yao, Kwan Yong Kang Nicholas, Yuxuan

class OutForAWalk {
	private int V; // number of vertices in the graph (number of rooms in the building)
	private Vector<Vector<IntegerPair>> AdjList; // the weighted graph (the building), effort rating of each corridor is stored here too
	private MST tree; //Class storing MST and list of possible query answers
	
	
	public OutForAWalk() {		
	}

	void PreProcess() {
		tree = new MST(AdjList, V);
		tree.traverseAllSources();
	}

	int Query(int source, int destination) {
		int answer = 0;
		answer = tree.getMaxWeight(source, destination);
		return answer;
	}

	void run() {
		// do not alter this method
		Scanner sc = new Scanner(System.in);

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// clear the graph and read in a new graph as Adjacency List
			AdjList = new Vector<Vector<IntegerPair>>();
			for (int i = 0; i < V; i++) {
				AdjList.add(new Vector<IntegerPair>());
				int k = sc.nextInt();
				while (k-- > 0) {
					int j = sc.nextInt(), w = sc.nextInt();
					AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
				}
			}

			PreProcess(); // you may want to use this function or leave it empty
							// if you do not need it

			int Q = sc.nextInt();
			while (Q-- > 0)
				System.out.println(Query(sc.nextInt(), sc.nextInt()));
			System.out.println(); // separate the answer between two different
									// graphs
		}
	}

	public static void main(String[] args) {
		// do not alter this method		
		OutForAWalk ps4 = new OutForAWalk();
		ps4.run();
	}
}
