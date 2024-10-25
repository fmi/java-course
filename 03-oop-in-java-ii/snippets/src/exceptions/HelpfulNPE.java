package exceptions;

public class HelpfulNPE {

    public static void main(String[] args) {
        new HelpfulNPE().helpfulNPEdemo();
    }

    public void helpfulNPEdemo() {
        A a = new A();
        a.b.c.number = 100;
    }

    class A {
        public B b;
    }

    class B {
        public C c;
    }

    class C {
        public int number;
    }

}
