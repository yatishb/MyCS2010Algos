import java.util.*;

class SchedulingDeliveries {

	public static Vector<PregnantWomen> womenAtHospital = new Vector();
	public static int heapSize;
	public static int arrivalEntryNumber;
	public static HashMap getIndexPosition = new HashMap();

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

	// class which stores the name of the women and the heap index position for
	// storage in hashmap
	class Women {
		String name;
		int indexInHeap;
	}

	public SchedulingDeliveries() {
		heapSize = 0;
		arrivalEntryNumber = 0;
		PregnantWomen Ob = new PregnantWomen();
		Ob.arrivalPosition = 0;
		womenAtHospital.add(Ob);
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
		womenAtHospital.add(Ob);

		Women womanEnteredToTree = new Women();
		womanEnteredToTree.name = womanName;
		womanEnteredToTree.indexInHeap = heapSize;
		getIndexPosition.put(womanEnteredToTree.name, womanEnteredToTree);

		if (heapSize > 1)
			shiftUp(heapSize);
	}

	// this method is to update the dilation level of any particular woman in
	// the hospital
	/*
	 * it uses the woman name and tries to find her position in the heap by
	 * using the hashmap. this woman in the heap can now be accessed in O(1) and
	 * updated. then the shiftup is to put the woman back to the correct
	 * position in the heap and the index is also updated in the hashmap
	 */
	void UpdateDilation(String womanName, int increaseDilation) {
		Women Ob = (Women) getIndexPosition.get(womanName);
		PregnantWomen womanAtPosition = womenAtHospital.get(Ob.indexInHeap);

		if (womanAtPosition.name.equals(womanName) == true) {
			womanAtPosition.dilation += increaseDilation;

			womenAtHospital.set(Ob.indexInHeap, womanAtPosition);
			shiftUp(Ob.indexInHeap);
		}
	}

	// this method gives birth to the woman in the hospital
	/*
	 * this method finds the index of the woman in the heap using the hashmap in
	 * O(1) and is then removed from the hashmap and the heap. its position in
	 * the heap is filled by the right-most node in the heap and then the
	 * shiftdown occurs and the indices of the nodes involved is altered in the
	 * hashmap
	 */
	void GiveBirth(String womanName) {
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
	void shiftUp(int i) {
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
	void shiftDown(int i) {
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
