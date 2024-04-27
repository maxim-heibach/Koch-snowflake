public class Vector2 {
    public final double x;
    public final double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 normalized() {
        double len = length();
        return new Vector2(x / len, y / len);
    }

    public Vector2 scale(double scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }
    
    public Vector2 linearInterpolation(Vector2 other, double t) {
        return new Vector2((1 - t) * x + t * other.x, (1 - t) * y + t * other.y);
    }
}