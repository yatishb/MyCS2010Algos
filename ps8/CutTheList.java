import java.util.*;

class CutTheList {
	// if needed, declare a private data structure here that
	// is accessible to all methods in this class
	// --------------------------------------------
	int dp[][];
	int maxDp[];
	int minDp[];

	public CutTheList() {
		// Write necessary codes during construction;
		//
		// write your answer here

	}

	int Query(int[] L, int N, int K) {
		int answer = 0;
		dp = new int[N + 1][K + 1];
		maxDp = new int[N + 1];
		minDp = new int[N + 1];
		findMaxMin(L, N);
		for (int i = 0; i <= N; i++) {
			Arrays.fill(dp[i], -1);
		}
		answer = getAns(L, 0, K - 1, N);
		return answer;
	}

	int getAns(int[] L, int current, int left, int N) {
		if (N - current - 1 == left)
			return 0;
		if (left == 0)
			return maxDp[current] - minDp[current];
		if (dp[current][left] >= 0)
			return dp[current][left];
		int min = -1;
		int m, M;
		m = M = L[current];
		for (int i = current + 1; i < N - left + 1; i++) {
			int temp = M - m + getAns(L, i, left - 1, N);
			if (min < 0 || min > temp)
				min = temp;
			if (m > L[i])
				m = L[i];
			if (M < L[i])
				M = L[i];
		}
		dp[current][left] = min;
		return min;
	}

	void findMaxMin(int L[], int N) {
		int max, min;
		max = min = L[N - 1];
		maxDp[N - 1] = minDp[N - 1] = max;
		for (int i = N - 2; i >= 0; i--) {
			if (max < L[i])
				max = L[i];
			if (min > L[i])
				min = L[i];
			maxDp[i] = max;
			minDp[i] = min;
		}
	}

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
