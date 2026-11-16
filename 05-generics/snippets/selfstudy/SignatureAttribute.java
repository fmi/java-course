/*
 * ============================================================================
 *  GENERICS UNDER THE HOOD · THE SIGNATURE ATTRIBUTE
 * ============================================================================
 *
 *  Myth: "erasure throws away all the generic information."
 *  Truth: erasure removes the type argument from the RUNTIME representation
 *         (the descriptor), but the compiler KEEPS the generic info as a
 *         separate `Signature` metadata attribute in the .class file.
 *
 *  That is how reflection (getGenericType, getGenericReturnType) and separate
 *  compilation still "see" <T> even though the runtime descriptor is erased.
 *
 *  Compile with -g and read the verbose dump:
 *      javac -g SignatureAttribute.java
 *      javap -v SignatureAttribute.class
 *
 *  Class-level Signature (generic info retained as metadata):
 *      Signature: #NN   // <T:Ljava/lang/Number;>Ljava/lang/Object;
 *
 *  Method-level Signature on pick:
 *      Signature: #NN   // (TT;)TT;
 *
 *  Compare with `javap -p -s SignatureAttribute.class`, where the DESCRIPTOR
 *  (the runtime view) is the erased (Ljava/lang/Number;)Ljava/lang/Number;.
 *  Two views of the same method: erased descriptor vs retained Signature.
 * ============================================================================
 */
public class SignatureAttribute<T extends Number> {
    T pick(T x) { return x; }
}
