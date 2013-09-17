/*import java.util.*;

import BabyNames.BSTVertex;
import BabyNames.BabyName;

class SchedulingDeliveries {

	public static BSTVertex womenAtHospital = new BSTVertex();
	public static int heapSize;
	public static int arrivalEntryNumber;
	public static HashMap getIndexPosition = new HashMap();
	private static final int height_newvertex = 0;

	// class which stores the details of the pregnant women. Name, dilation and
	// arrival position
	class PregnantWomen {
		String name;
		int dilation, arrivalPosition;

		int returnParent(int i) {
			return (int) Math.floor(i / 2);
		}

		int returnLeft(int i) {
			return 2 * i;
		}

		int returnRight(int i) {
			return 2 * i + 1;
		}
	}

	// Class that defines a Binary Search Tree vertex(node)
	class BSTVertex {

		PregnantWomen Ob;
		BSTVertex left, right, parent;
		int height, weight, leftnodes, rightnodes;

		BSTVertex() {
			left = right = parent = null;
			height = -1;
			weight = leftnodes = rightnodes = 0;
		}
	}

	// class which stores the name of the women and the heap index position for
	// storage in hashmap
	class Women {
		String name;
		int indexInHeap;
	}

	public SchedulingDeliveries() {
		heapSize = 0;
		arrivalEntryNumber = 0;
	}

	// this method adds a particular woman to the list in the hospital
	// it adds the woman in the heap and adds the name with it's heap index in
	// the hashmap
	void ArriveAtHospital(String womanName, int dilation) {
		PregnantWomen Ob = new PregnantWomen();
		Ob.name = womanName;
		Ob.dilation = dilation;
		Ob.arrivalPosition = arrivalEntryNumber;

		arrivalEntryNumber++;
		heapSize++;
		// womenAtHospital.add(Ob);

		// Inserting the vertex in its position in the BST
		AddVertex(womenAtHospital, Ob, height_newvertex);
	}

	// Function that returns the position where the BabyName is to be inserted
	void AddVertex(BSTVertex T, PregnantWomen Ob, int height) {
		if (T.height == -1) {
			T.Ob = Ob;
			T.height = 0;
		} else {
			if (Ob.dilation < T.Ob.dilation
					| (Ob.dilation == T.Ob.dilation && Ob.arrivalPosition > T.Ob.arrivalPosition)) {
				if (T.left == null) {
					BSTVertex newVertex = new BSTVertex();
					newVertex.parent = T;
					T.left = newVertex;
					newVertex.left = null;
					newVertex.right = null;
					newVertex.Ob = Ob;
					newVertex.height = height_newvertex;
					T.leftnodes += 1;
				} else {
					T.leftnodes += 1;
					AddVertex(T.left, Ob, height + 1);
				}
			} else {
				if (T.right == null) {
					BSTVertex newVertex = new BSTVertex();
					newVertex.parent = T;
					T.right = newVertex;
					newVertex.left = null;
					newVertex.right = null;
					newVertex.Ob = Ob;
					newVertex.height = height_newvertex;
					T.rightnodes += 1;
				} else {
					T.rightnodes += 1;
					AddVertex(T.right, Ob, height + 1);
				}
			}
			calculateHeightVertex(T);
		}
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

		if (Parent.parent == null)// root is being rotated
			womenAtHospital = Temp;
		else if (Parent == Parent.parent.left)
			Parent.parent.left = Temp;
		else
			Parent.parent.right = Temp;

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

		if (Parent.parent == null)// root is being rotated
			womenAtHospital = Temp;
		else if (Parent == Parent.parent.left)
			Parent.parent.left = Temp;
		else
			Parent.parent.right = Temp;

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

	
	void updateBST(String womanName, int increaseDilation, BSTVertex T){
		if(T==null)
			return;
		else{
			if(T.Ob.name.equals(womanName) == true)
				T.Ob.dilation+=increaseDilation;
			else{
				updateBST(womanName,increaseDilation, T.left);
				updateBST(womanName,increaseDilation, T.right);
			}
			calculateHeightVertex(T);
		}
	}
	
	// this method is to update the dilation level of any particular woman in
	// the hospital
	/*
	 * it uses the woman name and tries to find her position in the heap by
	 * using the hashmap. this woman in the heap can now be accessed in O(1) and
	 * updated. then the shiftup is to put the woman back to the correct
	 * position in the heap and the index is also updated in the hashmap
	 */
/*	void UpdateDilation(String womanName, int increaseDilation) {
		getIndexPosition.get(womanName)	;
		}

	// this method gives birth to the woman in the hospital
	/*
	 * this method finds the index of the woman in the heap using the hashmap in
	 * O(1) and is then removed from the hashmap and the heap. its position in
	 * the heap is filled by the right-most node in the heap and then the
	 * shiftdown occurs and the indices of the nodes involved is altered in the
	 * hashmap
	 */
/*	void GiveBirth(String womanName) {
		PregnantWomen womanAtPosition;
		Women Ob = (Women) getIndexPosition.get(womanName);

		womanAtPosition = womenAtHospital.get(Ob.indexInHeap);
		if (womanAtPosition.name.equals(womanName) == true) {
			womenAtHospital.set(Ob.indexInHeap, womenAtHospital.get(heapSize));
			heapSize--;

			Women womanChanged = new Women();
			womanChanged.name = womenAtHospital.get(Ob.indexInHeap).name;
			womanChanged.indexInHeap = Ob.indexInHeap;
			getIndexPosition.remove(womanChanged.name);
			getIndexPosition.put(womanChanged.name, womanChanged);

			womenAtHospital.remove(heapSize + 1);
			getIndexPosition.remove(womanName);

			if (Ob.indexInHeap < heapSize)
				shiftDown(Ob.indexInHeap);
		}
	}

	// this method is to compare and accordingly alter the heap
	/*
	 * this method checks for the dilation value of the woman and its parent and
	 * alters the heap accordingly. In case of the same dilation values, it
	 * compares their arrival positions to decide their positions. The swap
	 * function is invoked to swap and the hashmap is accordingly altered
	 */
/*	void shiftUp(int i) {
		PregnantWomen A = womenAtHospital.get(i);

		if (i != 1) {
			PregnantWomen A_Parent = womenAtHospital.get(A.returnParent(i));

			while (i > 1) {
				if (A_Parent.dilation < A.dilation
						| (A_Parent.dilation == A.dilation & A.arrivalPosition < A_Parent.arrivalPosition)) {
					swap(i, A.returnParent(i));

					i = A.returnParent(i);
					if (i != 1) {
						A = womenAtHospital.get(i);
						A_Parent = womenAtHospital.get(A.returnParent(i));
					}
				} else
					break;
			}
		}
	}

	// this method is to compare and accordingly alter the heap
	/*
	 * this method checks for the dilation value of the woman and its children
	 * and alters the heap accordingly. In case of the same dilation values, it
	 * compares their arrival positions to decide their positions. The swap
	 * function is invoked to swap and the hashmap is accordingly altered
	 */
/*	void shiftDown(int i) {
		PregnantWomen maxWomen = womenAtHospital.get(i);
		int max = i;

		while (i < heapSize) {
			if (maxWomen.returnLeft(i) <= heapSize) {
				PregnantWomen child = womenAtHospital.get(maxWomen
						.returnLeft(i));
				if (maxWomen.dilation < child.dilation
						| (maxWomen.dilation == child.dilation & maxWomen.arrivalPosition > child.arrivalPosition)) {
					maxWomen = child;
					max = maxWomen.returnLeft(i);
				}
			}
			if (maxWomen.returnRight(i) <= heapSize) {
				PregnantWomen child = womenAtHospital.get(maxWomen
						.returnRight(i));
				if (maxWomen.dilation < child.dilation
						| (maxWomen.dilation == child.dilation & maxWomen.arrivalPosition > child.arrivalPosition)) {
					maxWomen = child;
					max = maxWomen.returnRight(i);
				}
			}
			if (max != i) {
				swap(i, max);
				i = max;
			} else
				break;
		}
	}

	// this method is to swap the positions of two women in a heap and also
	// accordingly alters the hashmap
	void swap(int A, int B) {
		PregnantWomen Temp = womenAtHospital.get(A);
		womenAtHospital.set(A, womenAtHospital.get(B));
		womenAtHospital.set(B, Temp);

		// update the treemap for women now at B
		Women Ob = (Women) getIndexPosition.get(Temp.name);
		Ob.indexInHeap = B;
		getIndexPosition.remove(Temp.name);
		getIndexPosition.put(Ob.name, Ob);

		// update the treemap for woman now at A
		Temp = womenAtHospital.get(A);
		Ob = (Women) getIndexPosition.get(Temp.name);
		Ob.indexInHeap = A;
		getIndexPosition.remove(Temp.name);
		getIndexPosition.put(Ob.name, Ob);
	}

	// this method is to determine the name of the woman who needs the most
	// attention, i.e., is on top of the priority queue
	String Query() {
		String answer;
		if (heapSize > 0) {
			PregnantWomen womanNeedAttention = womenAtHospital.get(1);
			answer = womanNeedAttention.name;
		} else
			answer = "The delivery suite is empty";
		return answer;
	}

	void run() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		while (N-- > 0) {
			int cmd = sc.nextInt();
			switch (cmd) {
			case 0:
				ArriveAtHospital(sc.next(), sc.nextInt());
				break;
			case 1:
				UpdateDilation(sc.next(), sc.nextInt());
				break;
			case 2:
				GiveBirth(sc.next());
				break;
			case 3:
				System.out.println(Query());
				break;
			}
		}
	}

	public static void main(String[] args) {
		SchedulingDeliveries ps2 = new SchedulingDeliveries();
		ps2.run();
	}
}
*/