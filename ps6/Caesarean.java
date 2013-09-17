import java.util.*;

// A0091545A
// Bathini Yatish
// Yung Yi

class Caesarean {
  private int V; // number of vertices in the graph (steps of a Caesarean section surgery)
  private int E; // number of edges in the graph (dependency information between various steps of a Caesarean section surgery)
  private Vector < IntegerPair > EL; // the unweighted graph, an edge (u, v) in EL implies that step u must be performed before step v
  private Vector < Integer > estT; // the estimated time to complete each step
  private Vector < Vector < IntegerPair > > AdjList;
  private Vector <Integer> toposort;
  
  private int visited[] = new int[100000000];
  private int distance[] = new int[100000000];

  public Caesarean() {

  }
  
  
  public void convertToList(){
	  AdjList = new Vector<Vector<IntegerPair>>();
	  for (int i = 0; i < V; i++) {
		  AdjList.add(new Vector<IntegerPair>());		
	}
	 
	  for(int i=0; i<EL.size(); i++){
		  IntegerPair edge = EL.get(i);
		  AdjList.get(edge.first()).add(new IntegerPair(edge.second(), estT.get(edge.first())));
	  }
  }

  private void DFS(int v) {
	  visited[v] = 1;
	  for(int i = 0; i< AdjList.get(v).size(); i++) {
		  int neighbour  = AdjList.get(v).get(i).first();
		  if(visited[neighbour] == 0) {
			  DFS(neighbour);
		  }
	  }	
	  toposort.add(v); // Post order addition to list
  }
  
  // Toposort starting from vertex 0
  private void toposort() {	  
	  for(int i = 0 ; i < V ; i++)
		  if(visited[i]==0)
			  DFS(i);
	  Collections.reverse(toposort); // Reverse to get the correct order
  }


  
  int Query() {
    convertToList();
    
    for(int i=0; i<V; i++) {
    	visited[i] = 0;
    	distance[i] = -100000;
    }
    toposort = new Vector<Integer>();
    toposort();
    distance[0]=0;
    
    for(int i=0; i<toposort.size(); i++){
    	int vertex = toposort.get(i);
    	for(int j=0; j<AdjList.get(vertex).size(); j++){
    		IntegerPair edge = AdjList.get(vertex).get(j);
    		if(distance[edge.first()]<distance[vertex]+edge.second())
    			distance[edge.first()] = distance[vertex]+edge.second();
    	}
    }
    
    return distance[V-1];
  }

  void run() {
    // do not alter this method
    Scanner sc = new Scanner(System.in);

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt(); E = sc.nextInt(); // read V and then E

      estT = new Vector < Integer > ();
      for (int i = 0; i < V; i++)
        estT.add(sc.nextInt());

      // clear the graph and read in a new graph as an unweighted Edge List (only using IntegerPair, not IntegerTriple)
      EL = new Vector < IntegerPair > ();
      for (int i = 0; i < E; i++)
        EL.add(new IntegerPair(sc.nextInt(), sc.nextInt())); // just directed edge (u -> v)

      System.out.println(Query());
    }
  }

  public static void main(String[] args) {
    // do not alter this method
    Caesarean ps6 = new Caesarean();
    ps6.run();
  }
}
