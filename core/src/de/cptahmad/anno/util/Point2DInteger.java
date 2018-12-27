package de.cptahmad.anno.util;

import java.util.Arrays;
import java.util.Objects;

public final class Point2DInteger
{
    public final int x, y;

    public Point2DInteger(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2DInteger that = (Point2DInteger) o;
        return x == that.x &&
               y == that.y;
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(new int[]{ x, y });
    }
}
