package bg.sofia.uni.fmi.jira;

public class IdGenerator {

	private static int id;

	public static int generate() {
		return ++id;
	}
}
