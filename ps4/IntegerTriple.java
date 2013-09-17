
public class IntegerTriple implements Comparable<Object> {
	Integer _weight, _end,_start;

	  public IntegerTriple(Integer w,Integer f, Integer s) {
	    _weight = w;
		_end = f;
	    _start = s;
	  }

	  public int compareTo(Object o) {
	    if (!this.weight().equals(((IntegerTriple)o).weight()))
	      return this.weight() - ((IntegerTriple)o).weight();
	    else
	      return this.end() - ((IntegerTriple)o).end();
	  }

	  Integer start() { return _start; }
	  Integer end() { return _end; }
	  Integer weight() {return _weight;}
}
