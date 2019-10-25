

interface Addictable {
	void addict();
}

interface Lovable {
	void love();
}

interface JavaLanguage extends Addictable, Lovable {
	// JavaLanguage inherits both addict() and love() methods.
	// The class that will implement JavaLanguage is required to implement them or declare itself as an abstract.
}

class Java13 implements JavaLanguage {
	// If we do not implement the inherited methods we will get a compile-time error:
	// - "The type Java13 must implement the inherited abstract method Addictable.addict()"

	@Override
	public void addict() {
		System.out.println("I am addicted to Java 13!");
	}

	@Override
	public void love() {
		System.out.println("I love Java 13!");
	}
}

abstract class Java implements JavaLanguage {
	// We are not required to implement the methods in an abstract class.
}

interface Person {
	default void talk() {
		System.out.println("Hi, I am a programmer!");
	}
}

interface Bot {
	default void talk() {
		System.out.println("011010000110100100100001");
	}
}

interface Programmer extends Person, Bot {
	// We will get a compile-time error if we don't implement the talk() method here:
	// - "Duplicate default methods named talk with the parameters () and () are inherited from the types Bot and Person"
	
	default void talk() {
		Bot.super.talk();
		// Person.super.talk();
	}
}

class FMIProgrammer implements Programmer {
	
}

public class InterfaceExamples {
	public static void main(String[] args) {
		FMIProgrammer programmer = new FMIProgrammer();
		
		System.out.println(programmer instanceof Programmer); // true
		System.out.println(programmer instanceof Person); // true
		System.out.println(programmer instanceof Bot); // true
	}
}