package exceptions;

public class HelpfulNPE {

    class A {
        public B b;
    }

    class B {
        public C c;
    }

    class C {
        public int number;
    }

    public void helpfulNPEdemo() {
        A a = new A();
        a.b.c.number = 100;
    }

    public static void main(String[] args) {
        new HelpfulNPE().helpfulNPEdemo();
    }

}
