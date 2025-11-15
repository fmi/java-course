package bg.sofia.uni.fmi.mjt.pipeline;

import java.util.List;
import bg.sofia.uni.fmi.mjt.pipeline.stage.Stage;

/**
 * Represents a generic data processing pipeline consisting of multiple {@link Stage stages}.
 * Each stage transforms data from one type to another, allowing sequential transformations
 * from the pipeline input type {@code I} to the output type {@code O}.
 * <p>
 * The pipeline also caches execution results for previously seen inputs. When a cached
 * input is provided, the pipeline returns the cached output instead of re-executing
 * the stages.
 *
 * @param <I> the type of input data for the pipeline
 * @param <O> the type of output data produced by the pipeline
 */
public final class Pipeline<I, O> {

    private final Cache cache;
    private final List<Stage<?, ?>> stages;

    /**
     * Creates a new pipeline with an initial stage.
     *
     * @param initialStage the first stage of the pipeline
     * @param <I>          the type of input data for the pipeline
     * @param <O>          the type of output produced by the initial stage
     * @return a new pipeline starting with the given stage
     *
     * @throws IllegalArgumentException if initialStage is null
     */
    public static <I, O> Pipeline<I, O> start(Stage<I, O> initialStage) {
        return new Pipeline<>(List.of(initialStage));
    }

    /**
     * Private constructor used to initialize the pipeline with a list of stages.
     *
     * @param stages the initial list of stages
     */
    private Pipeline(List<Stage<?, ?>> stages) {
        this.stages = stages;
        this.cache = new Cache();
    }

    /**
     * Adds a new stage at the end of the pipeline.
     * <p>
     * The input type of the new stage must be compatible with the output type of
     * the current pipeline. The returned pipeline is updated to reflect the new
     * output type {@code NEW_O}.
     *
     * @param stage the stage to add
     * @param <NEW_O> the output type of the new stage
     * @return this pipeline instance cast to a pipeline producing {@code NEW_O}
     */
    public <NEW_O> Pipeline<I, NEW_O> addStage(Stage<? super O, NEW_O> stage) {
        stages.add(stage);
        return (Pipeline<I, NEW_O>) this;
    }

    /**
     * Executes the pipeline on the given input.
     * <p>
     * The input flows through all stages sequentially. If the same input has been
     * processed before, the cached output is returned instead of re-executing the
     * stages.
     *
     * @param input the input data to process
     * @return the output produced by the last stage of the pipeline
     */
    public O execute(I input) {
        Object current = input;
        if (cache.containsKey(input))
            return (O) cache.getCachedValue(input);

        for (Stage<?, ?> stage : stages) {
            Stage<Object, Object> typed = (Stage<Object, Object>) stage;
            current = typed.execute(current);
        }

        cache.cacheValue(input, current);
        return (O) current;
    }
}


