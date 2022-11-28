public class Player {
    private float x, y;
    private final float hitbox;
    private int speed;
    private boolean invulnerable;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox = 20;
        this.speed = 3;
        this.invulnerable = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHitbox() {
        return hitbox;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public void switchInvulnerability() {
        this.invulnerable = !this.invulnerable;
    }

    public void teleport(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Move player
     *
     * @param direction 0: top
     *                  1: right
     *                  2: bottom
     *                  3: left
     */
    public void move(int direction, float maxSize) {
        switch (direction) {
            case 0 -> this.y = this.y > 0 ? this.y - this.speed : 0;
            case 1 -> this.x = this.x + this.hitbox < maxSize ? this.x + this.speed : maxSize - this.hitbox;
            case 2 -> this.y = this.y + this.hitbox < maxSize ? this.y + this.speed : maxSize - this.hitbox;
            case 3 -> this.x = this.x > 0 ? this.x - this.speed : 0;
            default -> {
            }
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}