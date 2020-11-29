package usantatecla;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntervalTest {
  
  private Point left = new Point(-2.2);
  private Point right = new Point(4.4);

  @BeforeEach
  public void before(){
    this.left = new Point(-2.2);
    this.right = new Point(4.4);
  }

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

  @Test
  public void givenIntervalWhenIntersectWithNonEnclosedIntervalThenFalse() {
    Interval interval = builder().indifferent(left.getEquals()).indifferent(right.getLess()).build();
    Interval nonEnclosed = builder().indifferent(right.getGreater()).indifferent(right.getGreater()).build();

    assertFalse(interval.isIntersected(nonEnclosed));
  }

  @Test
  public void givenOpenIntervalWhenIntersectWithOpenClosedIntervalThenTrue() {
    Interval openInterval = builder().open(left.getEquals()).open(right.getEquals()).build();
    Interval openClosed = builder().open(left.getEquals()).closed(right.getEquals()).build();

    assertTrue(openInterval.isIntersected(openClosed));
  }

  @Test
  public void givenLeftIndifferentOpenIntervalWhenIntersectWithOpenClosedIntervalThenTrue() {
    Interval indifferentOpen = builder().indifferent(left.getEquals()).open(right.getEquals()).build();
    Interval openClosed = builder().indifferent(left.getLess()).open(right.getEquals()).build();

    assertTrue(indifferentOpen.isIntersected(openClosed));
  }

  private static IntervalBuilder builder() {
    return new IntervalBuilder();
  }

}