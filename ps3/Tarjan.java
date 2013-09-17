import java.util.*;

// A0091545A
// Bathini Yatish
// CP2.5, Nirav Gandhi, Carl Simon, Karthik Raj

/*
 * Implementing Tarjan algorithm which runs in O(V+E)
 */

class Tarjan {
	private int V; // number of vertices in the graph (number of rooms in the
					// hospital)
	private Vector<Vector<Integer>> AdjList; // the graph (the hospital)
	private Vector<Integer> RatingScore; // the weight of each vertex (rating
											// score of each room)
	private int dfs_low[];
	private int dfs_num[];
	private boolean visited[];
	private int num;
	private int dfs_parent[];
	private int dfsRoot;
	private boolean[] importantRoom;
	private int rootChildren;
	private int leastDecorated;

	public Tarjan() {

	}

	// identifies the most Important Room and stores it in the boolean array.
	public void importantRoom(int u) {
		visited[u] = true;
		dfs_low[u] = dfs_num[u] = num++;

		for (int j = 0; j < AdjList.get(u).size(); j++) {
			int v = AdjList.get(u).get(j);

			if (visited[v] == false) {
				dfs_parent[v] = u;

				if (u == dfsRoot)
					rootChildren++;// the special case
				importantRoom(v);

				if (dfs_low[v] >= dfs_num[u])
					importantRoom[u] = true;

				dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
			} else if (v != dfs_parent[u])
				dfs_low[u] = Math.min(dfs_low[u], dfs_num[v]);
		}
	}

	//This function will check the array of important rooms for the least valued imp room
	int Query() {
		for (int i = 0; i < V; i++) {
			if (importantRoom[i]) {
				if (RatingScore.get(i) < leastDecorated)
					leastDecorated = RatingScore.get(i);
			}
		}
		if (leastDecorated == Integer.MAX_VALUE)// Assigned the largest value
												// possible in run()
			return -1;// no important Room
		else
			return leastDecorated;

	}

	void run() {
		// do not alter this method
		Scanner sc = new Scanner(System.in);

		num = 0;
		int TC = sc.nextInt();// there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();
			dfs_low = new int[V];
			dfs_num = new int[V];
			visited = new boolean[V];
			dfs_parent = new int[V];
			importantRoom = new boolean[V];
			leastDecorated = Integer.MAX_VALUE;

			RatingScore = new Vector<Integer>();
			for (int i = 0; i < V; i++)
				RatingScore.add(sc.nextInt());

			for (int i = 0; i < V; i++) {
				importantRoom[i] = false;
				dfs_parent[i] = -1;
				visited[i] = false;
			}

			// Accepting the graph as an Adjaceny List
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
		Tarjan ps3 = new Tarjan();

		ps3.run();
	}
}
