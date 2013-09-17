import java.util.*;

class BabyNames {

	private static final int genderPreference_NotGiven = -1;
	private static final int genderPreference_Both = 0;
	private static final int genderPreference_Male = 1;
	private static final int genderPreference_Female = 2;
	private static final int height_newvertex = 0;

	int count;

	BSTVertex BSTrootMale = new BSTVertex();
	BSTVertex BSTrootFemale = new BSTVertex();

	// Class to store the name and the gender of the baby
	class BabyName {
		String name;
		int genderPreference;

		BabyName() {
			name = null;
			genderPreference = genderPreference_NotGiven;
		}
	}

	// Class that defines a Binary Search Tree vertex(node)
	class BSTVertex {

		BabyNames.BabyName Ob;
		BSTVertex left, right, parent;
		int height, weight, leftnodes, rightnodes;

		BSTVertex() {
			left = right = parent = null;
			height = -1;
			weight = leftnodes = rightnodes = 0;
		}
	}

	public BabyNames() {
		count = 0;
	}

	void AddSuggestion(String babyName, int genderSuitability) {
		BabyName newBabyName = new BabyName();
		newBabyName.genderPreference = genderSuitability;
		newBabyName.name = babyName;

		// Inserting the vertex in its position in the BST
		if (genderSuitability == genderPreference_Male)
			AddVertex(BSTrootMale, newBabyName, height_newvertex);
		else
			AddVertex(BSTrootFemale, newBabyName, height_newvertex);
	}

	int Query(String START, String END, int genderPreference) {
		count = 0;
		if (genderPreference == genderPreference_Male)
			count = TraversalAndSearch(START, END, genderPreference,
					BSTrootMale);
		else if (genderPreference == genderPreference_Female)
			count = TraversalAndSearch(START, END, genderPreference,
					BSTrootFemale);
		else {
			TraversalAndSearch(START, END, genderPreference, BSTrootMale);
			count = TraversalAndSearch(START, END, genderPreference,
					BSTrootFemale);
		}

		return count;
	}

	// Function to calculate the height of a vertex
	void calculateHeightVertex(BSTVertex T) {
		if (T == null)
			return;
		else if (T.left == null & T.right == null) {
			T.height = height_newvertex;
		} else {
			int leftTreeHeight, rightTreeHeight;
			if (T.left == null)
				leftTreeHeight = -1;
			else
				leftTreeHeight = T.left.height;

			if (T.right == null)
				rightTreeHeight = -1;
			else
				rightTreeHeight = T.right.height;

			if (leftTreeHeight > rightTreeHeight)
				T.height = leftTreeHeight + 1;
			else
				T.height = rightTreeHeight + 1;

			if (!(leftTreeHeight - rightTreeHeight <= 1 & leftTreeHeight
					- rightTreeHeight >= -1)) {
				balanceTree(T, leftTreeHeight, rightTreeHeight);
			}

		}
	}

	private void balanceTree(BSTVertex T, int leftTreeHeight,
			int rightTreeHeight) {
		if (leftTreeHeight - rightTreeHeight == 2)
			balanceForLeftMoreThanRight(T);
		else if (leftTreeHeight - rightTreeHeight == -2)
			balanceForRightMoreThanLeft(T);

	}

	private void balanceForRightMoreThanLeft(BSTVertex T) {
		int lhgt;
		int rhgt;
		if (T.right.left == null)
			lhgt = -1;
		else
			lhgt = T.right.left.height;
		if (T.right.right == null)
			rhgt = -1;
		else
			rhgt = T.right.right.height;

		if (lhgt - rhgt == -1)
			Rotateleft(T);
		else if (lhgt - rhgt == 1) {
			RotateRight(T.right);
			Rotateleft(T);
		}
	}

	private void balanceForLeftMoreThanRight(BSTVertex T) {
		int lhgt;
		int rhgt;
		if (T.left.left == null)
			lhgt = -1;
		else
			lhgt = T.left.left.height;
		if (T.left.right == null)
			rhgt = -1;
		else
			rhgt = T.left.right.height;

		if (lhgt - rhgt == 1)
			RotateRight(T);
		else if (lhgt - rhgt == -1) {
			Rotateleft(T.left);
			RotateRight(T);
		}
	}

	// Function to rotate right in BST
	void RotateRight(BSTVertex Parent) {
		if (Parent.left == null)
			return;
		BSTVertex Temp = Parent.left;

		if (Parent.Ob.genderPreference == genderPreference_Male) {
			if (Parent.parent == null)// root is being rotated
				BSTrootMale = Temp;
			else if (Parent == Parent.parent.left)
				Parent.parent.left = Temp;
			else
				Parent.parent.right = Temp;
		} else {
			if (Parent.parent == null)// root is being rotated
				BSTrootFemale = Temp;
			else if (Parent == Parent.parent.left)
				Parent.parent.left = Temp;
			else
				Parent.parent.right = Temp;
		}

		Temp.parent = Parent.parent;
		Parent.parent = Temp;
		Parent.left = Temp.right;
		if (Temp.right != null)
			Temp.right.parent = Parent;
		Temp.right = Parent;
		Parent.leftnodes = Temp.rightnodes;
		Temp.rightnodes = Parent.rightnodes + Parent.leftnodes + 1;

		calculateHeightVertex(Parent);
		calculateHeightVertex(Temp);
	}

	// Function to rotate left in BST
	void Rotateleft(BSTVertex Parent) {
		if (Parent.right == null)
			return;
		BSTVertex Temp = Parent.right;

		if (Parent.Ob.genderPreference == genderPreference_Male) {
			if (Parent.parent == null)// root is being rotated
				BSTrootMale = Temp;
			else if (Parent == Parent.parent.left)
				Parent.parent.left = Temp;
			else
				Parent.parent.right = Temp;
		} else {
			if (Parent.parent == null)// root is being rotated
				BSTrootFemale = Temp;
			else if (Parent == Parent.parent.left)
				Parent.parent.left = Temp;
			else
				Parent.parent.right = Temp;
		}

		Temp.parent = Parent.parent;
		Parent.parent = Temp;
		Parent.right = Temp.left;
		if (Temp.left != null)
			Temp.left.parent = Parent;
		Temp.left = Parent;
		Parent.rightnodes = Temp.leftnodes;
		Temp.leftnodes = Parent.rightnodes + Parent.leftnodes + 1;

		calculateHeightVertex(Parent);
		calculateHeightVertex(Temp);
	}

	// Function that returns the position where the BabyName is to be inserted
	void AddVertex(BSTVertex T, BabyName babyNameToBeAdded, int height) {
		if (T.height == -1) {
			T.Ob = babyNameToBeAdded;
			T.height = 0;
		} else {
			if (babyNameToBeAdded.name.compareTo(T.Ob.name) == 0) {

				if (T.right == null) {
					BSTVertex newVertex = new BSTVertex();
					newVertex.parent = T;
					T.right = newVertex;
					newVertex.left = null;
					newVertex.right = null;
					newVertex.Ob = babyNameToBeAdded;
					newVertex.height = height_newvertex;
					T.rightnodes += 1;
				} else {
					T.rightnodes += 1;
					AddVertex(T.right, babyNameToBeAdded, height + 1);
				}
			} else if (babyNameToBeAdded.name.compareTo(T.Ob.name) < 0) {
				if (T.left == null) {
					BSTVertex newVertex = new BSTVertex();
					newVertex.parent = T;
					T.left = newVertex;
					newVertex.left = null;
					newVertex.right = null;
					newVertex.Ob = babyNameToBeAdded;
					newVertex.height = height_newvertex;
					T.leftnodes += 1;
				} else {
					T.leftnodes += 1;
					AddVertex(T.left, babyNameToBeAdded, height + 1);
				}
			} else {
				if (T.right == null) {
					BSTVertex newVertex = new BSTVertex();
					newVertex.parent = T;
					T.right = newVertex;
					newVertex.left = null;
					newVertex.right = null;
					newVertex.Ob = babyNameToBeAdded;
					newVertex.height = height_newvertex;
					T.rightnodes += 1;
				} else {
					T.rightnodes += 1;
					AddVertex(T.right, babyNameToBeAdded, height + 1);
				}
			}
			calculateHeightVertex(T);
		}
	}

	// Function to search for the baby name within the specified range
	int TraversalAndSearch(String Start, String End, int genderPreference,
			BSTVertex T) {
		if (T == null) {
			return count;
		} else if (T.Ob.name.compareTo(Start) >= 0
				& T.Ob.name.compareTo(End) < 0) {
			
			if (genderPreference == genderPreference_Both) {
				if(T.parent==null){
					count += 1;
					TraversalAndSearch(Start, End, genderPreference, T.left);
					TraversalAndSearch(Start, End, genderPreference, T.right);
				}else if(T.parent.Ob.name.compareTo(End)<0 & T.parent.left==T){
					count+=T.rightnodes+1;
					TraversalAndSearch(Start, End, genderPreference, T.left);
				}else if(T.parent.Ob.name.compareTo(Start)>=0 & T.parent.right==T){
					count+=T.leftnodes+1;
					TraversalAndSearch(Start, End, genderPreference, T.right);
				}else{
					count+=1;
					TraversalAndSearch(Start, End, genderPreference, T.left);
					TraversalAndSearch(Start, End, genderPreference, T.right);
				}
			} 
			
			else if (T.Ob.genderPreference == genderPreference) {
				if(T.parent==null){
					count += 1;
					TraversalAndSearch(Start, End, genderPreference, T.left);
					TraversalAndSearch(Start, End, genderPreference, T.right);
				}else if(T.parent.Ob.name.compareTo(End)<0 & T.parent.left==T){
					count+=T.rightnodes+1;
					TraversalAndSearch(Start, End, genderPreference, T.left);
				}else if(T.parent.Ob.name.compareTo(Start)>=0 & T.parent.right==T){
					count+=T.leftnodes+1;
					TraversalAndSearch(Start, End, genderPreference, T.right);
				}else{
					count+=1;
					TraversalAndSearch(Start, End, genderPreference, T.left);
					TraversalAndSearch(Start, End, genderPreference, T.right);
				}
			}

		} else if (T.Ob.name.compareTo(Start) < 0) {
			TraversalAndSearch(Start, End, genderPreference, T.right);
		} else if (T.Ob.name.compareTo(End) >= 0) {
			TraversalAndSearch(Start, End, genderPreference, T.left);
		}

		return count;
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		while (N-- > 0)
			AddSuggestion(sc.next(), sc.nextInt());

		int Q = sc.nextInt();
		while (Q-- > 0)
			System.out.println(Query(sc.next(), // START
					sc.next(), // END
					sc.nextInt())); // GENDER

		sc.close();
	}

	public static void main(String[] args) {
		BabyNames ps1 = new BabyNames();
		long starttime = System.currentTimeMillis();
		ps1.run();
		long endtime = System.currentTimeMillis();
		System.out.print(endtime-starttime);
	}
}
