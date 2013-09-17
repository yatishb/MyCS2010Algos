import java.util.*;

// A0091545A
// Bathini Yatish
// CP2.5, Nirav Gandhi, Carl Simon, Karthik Raj

/*
 * Implementing Tarjan algorithm which runs in O(V+E)
 */

class HospitalTourR {
	
	private Vector<Vector<Integer>> AdjList; // Adjacency List of Rooms
	private Vector<Integer> RatingScore; // the weight of each vertex
	
	private int dfs_low[];
	private int dfs_num[];
	
	private int num;
	private int dfs_parent[];
	private int dfsRoot;
	private int rootChildren;
	
	private boolean[] importantRoom;
	private boolean visited[];
	private int leastImpRoomToBeDecorated;

	public HospitalTourR() {

	}

	// identifies the most Important Room and stores it in the boolean array.
	public void importantRoom(int vertexAt) {
		visited[vertexAt] = true;
		dfs_low[vertexAt] = dfs_num[vertexAt] = num++;

		for (int j = 0; j < AdjList.get(vertexAt).size(); j++) {
			int vertexVisiting = AdjList.get(vertexAt).get(j);

			if (visited[vertexVisiting] == false) {
				dfs_parent[vertexVisiting] = vertexAt;

				if (vertexAt == dfsRoot)
					rootChildren++;// the special case
				importantRoom(vertexVisiting);

				if (dfs_low[vertexVisiting] >= dfs_num[vertexAt])
					importantRoom[vertexAt] = true;

				dfs_low[vertexAt] = Math.min(dfs_low[vertexAt], dfs_low[vertexVisiting]);
			} else if (vertexVisiting != dfs_parent[vertexAt])
				dfs_low[vertexAt] = Math.min(dfs_low[vertexAt], dfs_num[vertexVisiting]);
		}
	}

	//This function will check the array of important rooms for the least valued imp room
	int Query() {
		for (int i = 0; i < AdjList.size(); i++) {
			if (importantRoom[i]) {
				if (RatingScore.get(i) < leastImpRoomToBeDecorated)
					leastImpRoomToBeDecorated = RatingScore.get(i);
			}
		}
		
		return leastImpRoomToBeDecorated;

	}

	void run() {
		// do not alter this method
		Scanner sc = new Scanner(System.in);

		num = 0;
		int TC = sc.nextInt();// there will be several test cases
		while (TC-- > 0) {
			int V;
			V = sc.nextInt();
			
			dfs_low = new int[V];
			dfs_num = new int[V];
			visited = new boolean[V];
			dfs_parent = new int[V];
			importantRoom = new boolean[V];
			leastImpRoomToBeDecorated = -1;

			RatingScore = new Vector<Integer>();
			for (int i = 0; i < V; i++)
				RatingScore.add(sc.nextInt());

			for (int i = 0; i < V; i++) {
				importantRoom[i] = false;
				dfs_parent[i] = -1;
				visited[i] = false;
			}

			// Accepting the graph as an Adjacency List
			AdjList = new Vector<Vector<Integer>>();
			for (int i = 0; i < V; i++) {
				int k = sc.nextInt();
				AdjList.add(new Vector<Integer>());
				while (k-- > 0) {
					int j = sc.nextInt();
					AdjList.get(i).add(j);
				}
			}

			for (int i = 0; i < V; i++) {
				if (visited[i] == false) {
					dfsRoot = i;
					rootChildren = 0;
					importantRoom(i);
					importantRoom[dfsRoot] = (rootChildren > 1);
				}
			}

			System.out.println(Query());
		}
		sc.close();
	}

	public static void main(String[] args) {
		HospitalTourR ps3 = new HospitalTourR();

		ps3.run();
	}
}