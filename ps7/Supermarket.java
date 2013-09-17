import java.util.*;

// A0091545A
// Bathini Yatish
// Hieu, Shubhendra, Nirav, Anirup, Karthik

/*
 * Used modified Floyd Warshall followed by DP. The modification made in Floyd Warshall
 * is that the inner 2 loops run only from 0 to K instead of 0 to N since we are interested
 * only in the distances between the vertices in the shopping list. Hence complexity obtained
 * = O(N*K^2 + K^2*2^K) 
 */

class Supermarket {
	private static int N; // number of items in the supermarket. V = N+1
	private static int K; // the number of items that Steven has to buy
	private static int[] shoppingList; // indices of items that Steven has to
										// buy
	private static int[][] T; // the complete weighted graph that measures the
								// direct walking time to go from one point to
								// another point in seconds
	private static int[][] Items;
	private static int[][] memo;
	private static int[] vertices; // shopping list + vertex 0
	private static PriorityQueue<IntegerPair> PQ = new PriorityQueue<IntegerPair>();

	public Supermarket() {}

	int Query() {
		Items = new int[N + 1][N + 1];

		vertices = new int[shoppingList.length + 1];
		vertices[0] = 0;
		for (int i = 1; i < vertices.length; i++) {
			vertices[i] = shoppingList[i - 1];
		}
		
		
		floydWarshall();
		
		Items = new int[vertices.length][vertices.length];
		for (int i = 0; i < vertices.length; i++)
		{
			for (int j = 0; j < vertices.length; j++) {
				Items[i][j] = T[vertices[i]][vertices[j]];
			}
		}
		
		
		memo = new int[K + 1][(int) Math.pow(2, K + 1)];
		for (int i = 0; i <= K; i++)
		{
			for (int j = 0; j < (int) Math.pow(2, K + 1); j++) {
				memo[i][j] = -1;
			}
		}
		return DP(0, 1);

	}

	int DP(int u, int visited) {
		if (visited == ((1 << (K + 1)) - 1)) {
			return Items[u][0];
		}
		if (memo[u][visited] != -1) {
			return memo[u][visited];
		}
		memo[u][visited] = Integer.MAX_VALUE;
		for (int i = 0; i < vertices.length; i++) {
			if ((visited & 1 << i) == 0) {
				memo[u][visited] = Math.min(memo[u][visited], Items[u][i]
						+ DP(i, (visited | (1 << i))));
			}
		}
		return memo[u][visited];
	}

	private static void floydWarshall() {
		for (int k = 0; k < N + 1; k++)
			for (int i = 0; i < K + 1; i++)
				for (int j = 0; j < K + 1; j++)
					T[vertices[i]][vertices[j]] = Math.min(
							T[vertices[i]][vertices[j]], T[vertices[i]][k]
									+ T[k][vertices[j]]);
	}

	private static void djikstra(int s, int i) {
		PQ.add(new IntegerPair(Items[i][s], s));
		while (!PQ.isEmpty()) {
			IntegerPair edge = PQ.remove();
			if (edge.first() == Items[i][edge.second()])
				for (int v = 0; v < N + 1; v++)
					if (Items[i][v] > Items[i][edge.second()]
							+ T[edge.second()][v]) {
						Items[i][v] = Items[i][edge.second()]
								+ T[edge.second()][v];
						PQ.add(new IntegerPair(Items[i][v], v));
					}
		}
	}

	void run() {
		// do not alter this method
		Scanner sc = new Scanner(System.in);

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			// read the information of the complete graph with N+1 vertices
			N = sc.nextInt();
			K = sc.nextInt(); // K is the number of items to be bought

			shoppingList = new int[K];
			for (int i = 0; i < K; i++)
				shoppingList[i] = sc.nextInt();

			T = new int[N + 1][N + 1];
			for (int i = 0; i <= N; i++)
				for (int j = 0; j <= N; j++)
					T[i][j] = sc.nextInt();

			System.out.println(Query());
		}
	}

	public static void main(String[] args) {
		// do not alter this method
		Supermarket ps7 = new Supermarket();
		ps7.run();
	}
}
