package usantatecla;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class IntervalTest {

    private static Point left = pointAt(-2.2);
    private static Point right = pointAt(4.4);

    @Test
    public void givenIntervaOpenOpenlwhenIncludeWithIncludedValueThenTrue() {
        Interval interval = builder().open(left.getEquals()).open(right.getEquals()).build();
        assertFalse(interval.include(left.getLess()));
        assertFalse(interval.include(left.getEquals()));
        assertTrue(interval.include(left.getGreater()));
        assertTrue(interval.include(right.getLess()));
        assertFalse(interval.include(right.getEquals()));
        assertFalse(interval.include(right.getGreater()));
    }

    @Test
    public void givenIntervaOpenOpenlwhenInc3ludeWithIncludedValueThenTrue() {
        Interval interval = builder().closed(left.getEquals()).open(right.getEquals()).build();
        assertFalse(interval.include(left.getLess()));
        assertTrue(interval.include(left.getEquals()));
        assertTrue(interval.include(left.getGreater()));

        assertTrue(interval.include(right.getLess()));
        assertFalse(interval.include(right.getEquals()));
        assertFalse(interval.include(right.getGreater()));
    }

    @Test
    public void givenIntervaOpenOpenlwhenIncludeWit3hIncludedValueThenTrue() {
        Interval interval = builder().open(left.getEquals()).closed(right.getEquals()).build();
        assertFalse(interval.include(left.getLess()));
        assertFalse(interval.include(left.getEquals()));
        assertTrue(interval.include(left.getGreater()));

        assertTrue(interval.include(right.getLess()));
        assertTrue(interval.include(right.getEquals()));
        assertFalse(interval.include(right.getGreater()));
    }

    @Test
    public void givenIntervaOpenOpenlwhenIncludeWithInclude5dValueThenTrue() {
        Interval interval = builder().closed(left.getEquals()).closed(right.getEquals()).build();
        assertFalse(interval.include(left.getLess()));
        assertTrue(interval.include(left.getEquals()));
        assertTrue(interval.include(left.getGreater()));

        assertTrue(interval.include(right.getLess()));
        assertTrue(interval.include(right.getEquals()));
        assertFalse(interval.include(right.getGreater()));
    }

    @Test
    public void givenIntervalWhenIntersectWithNullIntervalThenError() {
        Interval interval = builder().closed(left.getEquals()).closed(right.getEquals()).build();
        assertThrows(AssertionError.class, () -> interval.isIntersected(null));
    }

    @ParameterizedTest(name = "#{index} - Test with String : {0} vs. {1}")
    @MethodSource("intersectionTestCasesProvider")
    void test_method_string(String pivot, String other, IntervalBuilder pivotBuilder, IntervalBuilder otherBuilder, boolean expectedResult) {
        Interval pivotInterval = pivotBuilder.build();
        Interval otherInterval = otherBuilder.build();

        assertThat(pivotInterval.isIntersected(otherInterval), CoreMatchers.is(expectedResult));
    }

    private static Stream<Arguments> intersectionTestCasesProvider() {
        return Stream.of(
                Arguments.of("--{--}-------", "--------{--}-", builder().indifferent(left.getEquals()).indifferent(right.getLess()), builder().indifferent(right.getGreater()).indifferent(right.getGreater()), false),
                Arguments.of("--(---)------", "--(---]------", builder().open(left.getEquals()).open(right.getEquals()), builder().open(left.getEquals()).closed(right.getEquals()), true),
                Arguments.of("---{--)------", "-{----)------", builder().indifferent(left.getEquals()).open(right.getEquals()), builder().indifferent(left.getLess()).open(right.getEquals()), true),
                Arguments.of("-----{---}---","---{-------}-", builder().indifferent(left.getGreater()).indifferent(right.getLess()), builder().indifferent(left.getEquals()).indifferent(right.getEquals()), true),
                Arguments.of("---(------}--","---(----}----", builder().open(left.getEquals()).indifferent(right.getEquals()), builder().open(left.getEquals()).indifferent(right.getLess()), true),
                Arguments.of("--(------]---","--(------]---", builder().open(left.getEquals()).closed(right.getEquals()), builder().open(left.getEquals()).closed(right.getEquals()), true),
                Arguments.of("----{----}---","--{---}------", builder().indifferent(left.getGreater()).indifferent(right.getGreater()), builder().indifferent(left.getEquals()).indifferent(right.getEquals()), true),
                Arguments.of("----{----]---","--{------]---", builder().indifferent(left.getGreater()).closed(right.getEquals()), builder().indifferent(left.getLess()).closed(right.getEquals()), true),
                Arguments.of("-{-----)----","---{---)----", builder().indifferent(left.getLess()).open(right.getEquals()), builder().indifferent(left.getEquals()).open(right.getEquals()), true),
                Arguments.of("-{-----}----","---{-----}--", builder().indifferent(left.getLess()).indifferent(right.getEquals()), builder().indifferent(left.getGreater()).indifferent(right.getGreater()), true),
                Arguments.of("-[-----)----","-[-----)----", builder().closed(left.getEquals()).open(right.getEquals()), builder().closed(left.getEquals()).open(right.getEquals()), true),
                Arguments.of("-[---}----","-[-----}----", builder().closed(left.getEquals()).indifferent(right.getLess()), builder().closed(left.getEquals()).indifferent(right.getEquals()), true),
                Arguments.of("-{-------}--","---{---}----", builder().indifferent(left.getEquals()).indifferent(right.getEquals()), builder().indifferent(left.getLess()).indifferent(right.getLess()), true),
                Arguments.of("-{-------]--","---{-----]--", builder().indifferent(left.getEquals()).closed(right.getEquals()), builder().indifferent(left.getGreater()).closed(right.getEquals()), true),
                Arguments.of("-[-------}--","-[-----}----", builder().closed(left.getEquals()).indifferent(right.getGreater()), builder().closed(left.getEquals()).indifferent(right.getEquals()), true)
        );
    }


    private static IntervalBuilder builder() {
        return new IntervalBuilder();
    }

    private static Point pointAt(double p) {
        return new Point(p);
    }

}