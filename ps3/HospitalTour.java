import java.util.*;

/*
 * This problem is solved using the Depth First Search mechanism implemented on the entered 
 * graph. The graph entered is read in the form of an Adjacency matrix and then we remove 
 * each vertex one by one from the graph and try to calculate the number of connected components
 * thus formed in the graph. The removed vertex is said to be an important room if it leads to
 * more then 2 connected components(that room and the other rooms as one). Querying for each 
 * removed vertex is V+E and thus V^2+V*E for doing the same with V vertices
 */

class HospitalTour {
	private int V; // number of vertices in the graph (number of rooms in the
					// hospital)
	private static Vector<Vector<Integer>> AdjList;
	private Vector<Integer> AdjListVertex;
	private Vector<Integer> RatingScore; // the weight of each vertex (rating
											// score of each room)

	public HospitalTour() {
		// Write necessary codes during construction
		//
		// write your answer here
	}

	int Query() {
		int answer = -1;
		int indexOfImpVertex[] = new int[100000], size = 0;

		if (AdjList.size() == 1)
			return answer;

		for (int i = 0; i < AdjList.size(); i++) {
			// removing one vertex completely from the adjacency list
			AdjListVertex = AdjList.get(i);
			AdjList.set(i, new Vector<Integer>());

			int visited[] = new int[100000];
			for (int j = 0; j < AdjList.size(); j++) {
				visited[j] = 0;
			}

			if (i == 0 & AdjList.size() > 1)
				DFS(visited, 1, i);
			else {
				DFS(visited, 0, i);
			}

			int connectedComp = 2;
			for (int j = 0; j < AdjList.size(); j++)
				if (visited[j] == 0 & j!=i) {
					connectedComp++;
				}
			AdjList.set(i, AdjListVertex);

			if (connectedComp > 2) {
				indexOfImpVertex[size] = i;
				size++;
			}
		}

		if (size > 0) {
			answer = RatingScore.get(indexOfImpVertex[0]);
		}
		for (int i = 1; i < size; i++) {
			if (RatingScore.get(indexOfImpVertex[i]) < answer)
				answer = RatingScore.get(indexOfImpVertex[i]);
		}

		return answer;
	}

	public static void DFS(int[] visited, int vertexAt, int ignoreVertex) {
		visited[vertexAt] = 1;
		int neighbours = 0;

		while (AdjList.get(vertexAt).size() > neighbours) {
			// check if vertex not visited and exists
			int vertexVisiting = AdjList.get(vertexAt).get(neighbours);
			if (visited[vertexVisiting] == 0
					& AdjList.get(vertexVisiting).size() > 0
					& vertexVisiting != ignoreVertex) {
				DFS(visited, vertexVisiting, ignoreVertex);
			}
			neighbours++;
		}
	}

	void run() {
		// you can alter this method
		Scanner sc = new Scanner(System.in);

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			V = sc.nextInt();

			// read rating scores, A (index 0), B (index 1), C (index 2), ...,
			// until the V-th index
			RatingScore = new Vector<Integer>();
			for (int i = 0; i < V; i++)
				RatingScore.add(sc.nextInt());

			// clear the graph and read in a new graph as Adjacency Matrix
			AdjList = new Vector<Vector<Integer>>();
			for (int i = 0; i < V; i++) {
				AdjListVertex = new Vector<Integer>();
				int k = sc.nextInt();
				while (k-- > 0) {

					int j = sc.nextInt();
					AdjListVertex.add(j); // edge weight is always 1 (the weight
											// is on vertices now)
				}
				AdjList.add(AdjListVertex);
			}

			System.out.println(Query());
		}
		sc.close();
	}

	public static void main(String[] args) {
		// do not alter this method
		HospitalTour ps3 = new HospitalTour();
		ps3.run();
	}
}
