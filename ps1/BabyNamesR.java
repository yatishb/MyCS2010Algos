import java.util.*;

// write your matric number here:
// write your name here:
// write list of collaborators here (reading someone's post in IVLE discussion forum and using the idea is counted as collaborating):

class BabyNamesR {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  public BabyNamesR() {
    // Write necessary codes during construction;
    //
    // write your answer here



  }

  void AddSuggestion(String babyName) {
    // You have to insert the information: babyName
    // into your chosen data structure
    //
    // write your answer here



  }

  int Query(String SUBSTR) {
    int count = 0;

    // You have to answer how many baby names contain substring SUBSTR
    //
    // write your answer here



    return count;
  }

  void run() {
    // do not alter this method
    Scanner sc = new Scanner(System.in);
    int N = sc.nextInt();
    while (N-- > 0)
      AddSuggestion(sc.next());

    int Q = sc.nextInt();
    while (Q-- > 0)
      System.out.println(Query(sc.next())); // SUBSTR
  }

  public static void main(String[] args) {
    // do not alter this method
    BabyNamesR ps1R = new BabyNamesR();
    ps1R.run();
  }
}
