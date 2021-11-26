package bg.sofia.uni.fmi.mjt.io;

import java.io.Serializable;

public record Student(String name, int age) implements Serializable {}
