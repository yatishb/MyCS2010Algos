import java.util.*;

// A0091545A
// Bathini Yatish
// 

class CutTheList {
	int[][] memo = new int[400][400];
	int N, K;
	static int[] L = new int[400];
	static int[][] length_dp = new int[610][610];
	int[][] weight = new int[400][400];

	public CutTheList() {
		// Write necessary codes during construction;
		//
		// write your answer here

	}

	int Query(int[] l, int n, int k) {
		int answer = 0;
		N = n;
		K = k;
		for (int i = 0; i < l.length; i++)
			L[i] = l[i];
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < K; j++) {
				memo[i][j] = -1;
			}
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if(i>j)
					weight[i][j] = 1000000;
				else if(i ==j)
					weight[i][j]= 0;
				else {
					int max = 0,min = 1000000;
					for(int m=i; m<=j; m++){
						max = Math.max(max, L[m]);
						min = Math.min(min, L[m]);
					}
					weight[i][j] = max-min; 
				}
			}
		

		answer = f(0, k);

		return answer;
	}

	/*
	 * int f(int u, int k) { if (u == N) // reach end of trail return memo[u][k]
	 * = 0; // no more walk to do else if (k == 0) // cannot camp anymore...
	 * return memo[u][k] = f(u, N - 1); // must go to finish...
	 * 
	 * if (memo[u][k] != -1) return memo[u][k];
	 * 
	 * int min_f =2000000000; for (int camp_here_tonight = u + 1;
	 * camp_here_tonight <= N; camp_here_tonight++) { min_f = Math.min(min_f,
	 * Math.max(f(u, camp_here_tonight), f(camp_here_tonight, k - 1))); }
	 * 
	 * return memo[u][k] = min_f;
	 */
	// f(u,k) = min of sum from f(u+1 to end N-1, k-1)

	int f(int u, int k) {
		if(k==1)
			//go to end directly
			return memo[u][k] = weight[u][N-1];
		if(u == N-1)
			return memo[u][k] = 0;
		
		int min = 1000000;
		/*for (int i = u + 1; i < N; i++) {
			if (memo[i][k - 1] != 1000000) {
				int weight = 0;
				int maxSub = 0, minSub = 100000;
				for (int j = u; j < i; j++) {
					maxSub = Math.max(maxSub, L[j]);
					minSub = Math.min(minSub, L[j]);
				}

				weight = maxSub - minSub;
				memo[i][k - 1] = Math.min(memo[i][k - 1], memo[i][k - 1]
						+ weight);
			} else {
				int weight = 0;
				int maxSub = 0, minSub = 100000;
				for (int j = u; j < i; j++) {
					maxSub = Math.max(maxSub, L[j]);
					minSub = Math.min(minSub, L[j]);
				}

				weight = maxSub - minSub;
				memo[u][k] = Math.min(memo[u][k], f(i, k - 1) + weight);
			}
			min = Math.min(min, memo[i][k - 1]);
		}

		memo[u][k] = min;*/
		for(int i=u+1; i<N; i++)
			min = Math.min(min, f(i, k - 1));
		return memo[u][k] = memo[u][k]+min;
	}

	/*
	 * private static int walk(int pos, int night_left) { if (pos == N) // reach
	 * end of trail return memo[pos][night_left] = 0; // no more walk to do else
	 * if (night_left == 0) // cannot camp anymore... return
	 * memo[pos][night_left] = walk_length(pos, N + 1); // must go to finish...
	 * 
	 * if (memo[pos][night_left] != -1) return memo[pos][night_left];
	 * 
	 * // general case: try all possible camping position int min_walk_length =
	 * 2000000000; for (int camp_here_tonight = pos + 1; camp_here_tonight <= N;
	 * camp_here_tonight++) { min_walk_length = Math.min(min_walk_length,
	 * Math.max(walk_length(pos, camp_here_tonight), walk(camp_here_tonight,
	 * night_left - 1))); }
	 * 
	 * return memo[pos][night_left] = min_walk_length; }
	 */

	void run() {
		// do not alter this method
		Scanner sc = new Scanner(System.in);

		int TC = sc.nextInt(); // there will be several test cases
		while (TC-- > 0) {
			// read the length N and K first
			int N = sc.nextInt(); // the length of list L
			int K = sc.nextInt(); // Esther wants to cut L into K sub-lists

			int[] L = new int[N]; // the original list L
			for (int i = 0; i < N; i++)
				L[i] = sc.nextInt();

			System.out.println(Query(L, N, K));
		}
	}

	public static void main(String[] args) {
		// do not alter this method
		CutTheList ps8 = new CutTheList();
		ps8.run();
	}
}
