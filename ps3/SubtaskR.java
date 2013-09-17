import java.util.*;

// A0091523
// KARTHIK RAJASEKARAN
// COLLABORATORS:
// (IVLE FORUM) CARL SIMONS

class SubtaskR {
	
	class GraphPoint {
		int id;
		int lowPoint;
		int depth;
		boolean visited;
		Vector<Integer> neighbours;
		public GraphPoint(int newid) {
			// TODO Auto-generated constructor stub
			id = newid; 
			lowPoint = depth = -1;
			visited = false;
			neighbours = new Vector<Integer>();
		}
	}
	
  private int V; // number of vertices in the graph (number of rooms in the hospital)
  private Vector< GraphPoint > AdjList; // the graph (the hospital)
  private Vector < Integer > RatingScore; // the weight of each vertex (rating score of each room)
  private Vector <Integer> articulationPoints;
  
  
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  public SubtaskR() {
    // Write necessary codes during construction
    //
    // write your answer here

	 articulationPoints = new Vector<Integer>();
	 
  }
  
  public void print(){
	  for(int i=0 ; i<AdjList.size() ; i++){
		  for(int j=0 ; j<AdjList.get(i).neighbours.size() ; j++){
			  System.out.print((AdjList.get(i)).neighbours.elementAt(j) +" ");
		  }
		  System.out.println();
	  }
  }
  
  	void DFSrec(GraphPoint u,int depthAtCurrentLevel,int callingItem){
  		int children=0;
   		u.visited = true;
   		u.depth = depthAtCurrentLevel;
   		u.lowPoint = depthAtCurrentLevel;
   		//System.out.println(u.id+" "+u.depth+" "+u.lowPoint);
   		
   		for(int i=0 ; i<u.neighbours.size() ;i++){
   			if(AdjList.get(u.neighbours.get(i)).visited == false){
   				
   				children++;	
   				
   				DFSrec(AdjList.get(u.neighbours.get(i)),depthAtCurrentLevel+1,u.id);
   				
   				u.lowPoint = Math.min(u.lowPoint, AdjList.get(u.neighbours.get(i)).lowPoint);
   				
   				if(AdjList.get(u.neighbours.get(i)).lowPoint >= u.depth && u.depth!=1){
   					articulationPoints.add(u.id);
   				}
   			}
   			else if(callingItem != AdjList.get(u.neighbours.get(i)).id){
   				u.lowPoint = Math.min(u.lowPoint, AdjList.get(u.neighbours.get(i)).depth);
   			}
   			
   		}
   		
   		if(u.depth==1 && children>1 ){
   			articulationPoints.add(u.id);
   		}
   		//System.out.println(u.id+" "+u.depth+" "+u.lowPoint);
   	}
  
  int Query() {
    
    if(articulationPoints.size()==0){
    	return -1;
    }
  
    int min=RatingScore.elementAt(articulationPoints.elementAt(0));
   for(int i=0;i<articulationPoints.size();i++){
	  if( min> RatingScore.elementAt(articulationPoints.elementAt(i)) ){
		  min=RatingScore.elementAt(articulationPoints.elementAt(i));
	  }
   }
   return min;
  }

  // You can add extra function if needed
  // --------------------------------------------

  void printCutVertex(){
  	for(int i=0;i<articulationPoints.size();i++){
  		System.out.print(articulationPoints.elementAt(i)+" ");
  	}
  }

  
  
  // --------------------------------------------

  
  void run() {
    // do not alter this method
    Scanner sc = new Scanner(System.in);

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      RatingScore = new Vector < Integer > ();
      for (int i = 0; i < V; i++){
        RatingScore.add(sc.nextInt());
      }
      
      for (int i = 0; i < V; i++){
    	  if(TC==19 && RatingScore.get(i) == 41){
    		  System.out.println("ERROR :"+i);
    	  }
      }

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new Vector< GraphPoint >();
      
      for (int i = 0; i < V; i++) {
    	  GraphPoint tempStore = new GraphPoint(i);
    	  int k = sc.nextInt();
 
    	  while (k-- > 0) {
    		  int j = sc.nextInt();
    		  tempStore.neighbours.add(j);
    	  }
        
    	  AdjList.add(tempStore);
      }
      
      for(int i=0;i<AdjList.size();i++){
    	if(AdjList.elementAt(i).visited==false){
    		  DFSrec(AdjList.elementAt(0), 1,AdjList.elementAt(0).id);
    	  }
      }
      System.out.println(Query());
      //print();
      //printCutVertex();
     articulationPoints.clear();
     
    }
  }

  public static void main(String[] args) {
    // do not alter this method
    SubtaskR ps3 = new SubtaskR();
    ps3.run();
  }
}
