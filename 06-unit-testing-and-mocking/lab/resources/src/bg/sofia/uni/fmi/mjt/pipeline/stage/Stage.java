package bg.sofia.uni.fmi.mjt.pipeline.stage;

import java.util.ArrayList;
import java.util.List;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

/**
 * Represents a stage in a generic pipeline, consisting of a sequence of {@link Step steps}.
 * <p>
 * Each stage has a defined input type {@code I} and output type {@code O}. Steps within the
 * stage are executed sequentially, where the output of one step is passed as input to the
 * next step.
 *
 * @param <I> the type of input accepted by the stage
 * @param <O> the type of output produced by the stage
 */
public final class Stage<I, O> {

    private final ArrayList<Step<?, ?>> steps;

    /**
     * Creates a new stage starting with the given initial step.
     *
     * @param initialStep the first step of this stage
     * @param <I> the input type of the stage
     * @param <O> the output type of the initial step
     * @return a new stage instance containing the initial step
     *
     * @throws IllegalArgumentException if initialStep is null
     */
    public static <I, O> Stage<I, O> start(Step<I, O> initialStep) {
        return new Stage<>(List.of(initialStep));
    }

    /**
     * Private constructor used to initialize a stage with a list of steps.
     *
     * @param steps the list of steps to include in the stage
     */
    private Stage(List<Step<?, ?>> steps)
    {
        this.steps = new ArrayList<>(steps);
    }

    /**
     * Adds a new step to the stage.
     * <p>
     * The input type of the new step must be compatible with the current output type {@code O}.
     * The returned stage updates the output type to {@code NEW_O}.
     *
     * @param step the step to add
     * @param <NEW_O> the output type of the new step
     * @return this stage instance cast to have output type {@code NEW_O}
     *
     * @throws IllegalArgumentException if step is null
     */
    public <NEW_O> Stage<I, NEW_O> addStep(Step<? super O, NEW_O> step) {
        steps.add(step);
        return (Stage<I, NEW_O>) this;
    }

    /**
     * Executes all steps in this stage sequentially.
     * <p>
     * The input is passed to the first step, and the output of each step is passed to the
     * next. The result of the last step is returned as the output of the stage.
     *
     * @param input the input data to process
     * @return the output produced by the last step of this stage
     */
    public O execute(I input) {
        Object current = input;

        for (Step<?, ?> step : steps) {
            Step<Object, Object> typed = (Step<Object, Object>) step;
            current = typed.process(current);
        }

        return (O) current;
    }
}

