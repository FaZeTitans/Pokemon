public class Obstacle {
    private float x, y;
    private final float width, height;
    private final ObstacleTypes type;
    private final boolean crossable;

    public Obstacle(float x, float y, float width, float height, ObstacleTypes type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.crossable = false;
    }

    public Obstacle(float x, float y, float width, float height, ObstacleTypes type, boolean crossable) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.crossable = crossable;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public ObstacleTypes getType() {
        return type;
    }

    public boolean isCrossable() {
        return crossable;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
