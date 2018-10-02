package de.cptahmad.anno.util;

public class RectangleInt
{
    public int x, y;

    private int m_width, m_height;

    public RectangleInt(int x, int y)
    {
        this(x, y, 0, 0);
    }

    public RectangleInt(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        setSize(width, height);
    }

    public boolean contains(int px, int py)
    {
        return (px >= x && px <= x + m_width && py >= y && py <= y + m_height);
    }

    public boolean intersects(RectangleInt other)
    {
        return (contains(other.x, other.y) || contains(other.x, other.top()) ||
                contains(other.right(), other.y) || contains(other.right(), other.top()) ||
                other.contains(x, y) || other.contains(x, top()) ||
                other.contains(right(), y) || other.contains(right(), top()));
    }

    public int right()
    {
        return x + m_width;
    }

    public int top()
    {
        return y + m_height;
    }

    public void setWidth(int width)
    {
        if (width < 0) throw new IllegalArgumentException("the width cannot be less than zero");
        m_width = width;
    }

    public void setHeight(int height)
    {
        if (height < 0) throw new IllegalArgumentException("the height cannot be less than zero");
        m_height = height;
    }

    public void setSize(int width, int height)
    {
        setWidth(width);
        setHeight(height);
    }
}
