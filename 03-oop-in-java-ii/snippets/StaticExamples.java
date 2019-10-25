// Importing static methods and variables with the keyword static.
import static java.lang.Math.PI;
import static java.util.Arrays.binarySearch;

class Project {
	// Constants, i.e static final variables are always written in capital case and underscores.
	private static final String PROJECT_PREFIX="proj-" ;
	private static int totalProjectInstances;
	
	private String name;
	
	public Project(String name) {
		// We can use static variable to count the number of Project instances created.
		// All instances of Project will share the same copy of the variable.
		totalProjectInstances++;
		
		this.name = name;
	}
	
	public static int getTotalProjectInstances() {
		return totalProjectInstances;
	}
	
// We cannot use instance variables/methods in static methods because static methods
// - are not bound to any instance, but rather to the class itself
	
//	public static void printName() {
//		System.out.println(this.name);
//	}

}