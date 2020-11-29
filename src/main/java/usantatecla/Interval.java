package usantatecla;

public class Interval {

    private final Min min;
    private final Max max;

    public Interval(Min min, Max max) {
        assert min.value <= max.value;
        this.min = min;
        this.max = max;
    }

    public boolean isIntersected(Interval interval) {
        assert interval != null;
        if(interval.include(this.max.value) || this.include(interval.max.value)) return true;
        if(interval.include(this.min.value)) return true;
        return interval.include(this.min.value) && interval.include(this.max.value);
    }

    public boolean include(double value) {
        return this.min.isWithin(value) && this.max.isWithin(value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((max == null) ? 0 : max.hashCode());
        result = prime * result + ((min == null) ? 0 : min.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Interval other = (Interval) obj;
        if (max == null) {
            if (other.max != null)
                return false;
        } else if (!max.equals(other.max))
            return false;
        if (min == null) {
            return other.min == null;
        } else return min.equals(other.min);
    }

    @Override
    public String toString() {
        return this.min.toString() + ", " + max.toString();
    }

}