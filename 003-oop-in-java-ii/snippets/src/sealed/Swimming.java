package sealed;

// the compilation failure here is intended - 'Swimming' is not allowed in the sealed hierarchy of Exercise
sealed class Swimming implements Exercise {

    private static final int SWIMMING_CALORIES = 650;

    @Override
    public int getCaloriesBurnt() {
        return SWIMMING_CALORIES;
    }

}

//  Note that any subclasses of a sealed class defined in the same source file need not be mentioned in a permits clause of their parent
final class OceanSwimming extends Swimming {

    private static final int OCEAN_SWIMMING_CALORIES = 700;

    @Override
    public int getCaloriesBurnt() {
        return OCEAN_SWIMMING_CALORIES;
    }

}
