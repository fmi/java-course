///**
// * ============================================================================
// *  LAB 02 · INTERACTIVE SNIPPET
// * ============================================================================
// *
// *  Java has no multiple inheritance of CLASSES but a class can implement
// *  many interfaces, and since Java 8 interfaces can define `default` method
// *  bodies. So the old C++ "diamond problem" sneaks back in through a side door.
// *
// *  1. Uncomment the class definition
// *  2. Why doesn't it compile? PREDICT:
// */
//public class DiamondDefault {
//
//    interface Retryable {
//        default String policy() { return "retry up to 3 times"; }
//    }
//
//    interface FailFast {
//        default String policy() { return "fail immediately"; }
//    }
//
//    static final class EmailChannel implements Retryable {
//    }
//
//    // Why doesn't it compile? How can we fix it?
//    // WHY: ____   FIX OPTIONS: ____ / ____ / ____
//    static final class SmsChannel implements Retryable, FailFast {
//
//    }
//
//    public static void main(String[] args) {
//        System.out.println(new EmailChannel().policy());
//
//        System.out.println(new SmsChannel().policy());
//    }
//}
