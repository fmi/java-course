package bg.sofia.uni.fmi.mjt.pipeline.step;

/**
 * Represents a single processing step in a pipeline.
 * <p>
 * Each step transforms input of type {@code I} into output of type {@code O}.
 * Implementations should provide the transformation logic in the {@link #process(Object)}
 * method.
 *
 * @param <I> the type of input the step accepts
 * @param <O> the type of output the step produces
 */
public interface Step<I, O> {

    /**
     * Processes the given input and produces an output.
     *
     * @param input the input data to process
     * @return the output data produced by this step
     */
    O process(I input);

}
