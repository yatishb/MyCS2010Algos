import java.util.*;


class Labor {
  private int V; // number of vertices in the graph (number of junctions in Singapore map)
  private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge
  private TreeSet<IntegerPair> priorityQueue = new TreeSet<IntegerPair>();
  private int[] weightToVertex = new int[250000];
  
  public Labor() {}

  int Query() {
    // You have to report the shortest path from Steven and Grace's home (vertex 0)
    // to reach their chosen hospital (vertex 1)
    
	//created array to store distance from vertex0 to each vertex. initalising this array with a large distance
	for(int i=0; i<V; i++)
		weightToVertex[i] = 10000000;
	  
    //creating the priority queue. Adding (0,source) and (infinity, vertex) for other vertices
    priorityQueue.add(new IntegerPair(0,0));
    
    while(priorityQueue.isEmpty() == false){
    	//dequeuing the priority queue
    	IntegerPair firstEdgePair = (IntegerPair) priorityQueue.first();
    	int minValue = firstEdgePair.first();
    	int vertexRelaxed = firstEdgePair.second();
    	priorityQueue.remove(firstEdgePair);
    	
    	//distance to a vertex changes if the relaxed edge reduces the distance to it
    	if(weightToVertex[vertexRelaxed] > minValue)
    		weightToVertex[vertexRelaxed] = minValue;
    	
    	//adding the edges originating from the relaxed vertex into the priority queue
    	for(int i=0; i<AdjList.get(vertexRelaxed).size(); i++){
    		int v = AdjList.get(vertexRelaxed).get(i).first();
    		int edgeWeight = AdjList.get(vertexRelaxed).get(i).second();
    		if(weightToVertex[v] > (weightToVertex[vertexRelaxed] + edgeWeight)){
    			weightToVertex[v] = weightToVertex[vertexRelaxed] + edgeWeight;
    			priorityQueue.add(new IntegerPair(weightToVertex[v], v));
    		}
    	}
    }

    //The hospital is always ar vertex1, so returning the distance to vertex1 directly
    return weightToVertex[1];
  }

  void run() {
    // do not alter this method
    Scanner sc = new Scanner(System.in);

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new Vector < Vector < IntegerPair > >();
      for (int i = 0; i < V; i++) {
        AdjList.add(new Vector<IntegerPair>());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (length of road) is stored here
        }
      }

      System.out.println(Query());
    }
  }

  public static void main(String[] args) {
    // do not alter this method
    Labor ps5 = new Labor();
    ps5.run();
  }
}
