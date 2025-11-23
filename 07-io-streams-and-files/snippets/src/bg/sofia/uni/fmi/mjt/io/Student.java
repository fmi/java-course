package bg.sofia.uni.fmi.mjt.io;

import java.io.Serializable;

public record Student(String name, int age) implements Serializable {

    @Serial
    private static final long serialVersionUID = -4620197539457134073L;
    
}
