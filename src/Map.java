public class Map {
    private float x, y;
    private final float width, height;

    public Map(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    /**
     * Move map
     *
     * @param direction 0: player to top / map to bottom
     *                  1: player to right / map to left
     *                  2: player to bottom / map to top
     *                  3: player to left / map to right
     */
    public void move(int direction, float maxWidth, float maxHeight, float vitesse, Player player, Obstacle[] obstacles) {
        // Test collisions
        for (Obstacle o : obstacles) {
            if (o.isCrossable() || player.isInvulnerable()) continue;
            if (o.getType() == ObstacleTypes.RECTANGLE) {
                switch (direction) {
                    case 0:
                        if (player.getX() + player.getHitbox() > o.getX() &&
                                player.getX() < o.getX() + o.getWidth() &&
                                player.getY() - player.getSpeed() < o.getY() + o.getHeight() &&
                                player.getY() + player.getHitbox() > o.getY()
                        ) return;
                        break;
                    case 1:
                        if (player.getY() + player.getHitbox() > o.getY() &&
                                player.getY() < o.getY() + o.getHeight() &&
                                player.getX() < o.getX() + o.getWidth() &&
                                player.getX() + player.getHitbox() + player.getSpeed() > o.getX()
                        ) return;
                        break;
                    case 2:
                        if (player.getX() + player.getHitbox() > o.getX() &&
                                player.getX() < o.getX() + o.getWidth() &&
                                player.getY() < o.getY() + o.getHeight() &&
                                player.getY() + player.getHitbox() + player.getSpeed() > o.getY()
                        ) return;
                        break;
                    case 3:
                        if (player.getY() + player.getHitbox() > o.getY() &&
                                player.getY() < o.getY() + o.getHeight() &&
                                player.getX() - player.getSpeed() < o.getX() + o.getWidth() &&
                                player.getX() + player.getHitbox() > o.getX()
                        ) return;
                        break;
                    default:
                        break;
                }
            }
        }

        // Player move (with liberty)
        if (direction == 3 && player.getX() > maxWidth / 2) {
            player.move(3, maxWidth);
            return;
        }
        if (direction == 1 && player.getX() < maxWidth / 2) {
            player.move(1, maxWidth);
            return;
        }
        if (direction == 0 && player.getY() > maxHeight / 2) {
            player.move(0, maxHeight);
            return;
        }
        if (direction == 2 && player.getY() < maxHeight / 2) {
            player.move(2, maxHeight);
            return;
        }

        // Player move (map)
        switch (direction) {
            case 0:
                if (this.y < 0) {
                    this.y = this.y + vitesse;
                    for (Obstacle o : obstacles) {
                        o.setY(o.getY() + vitesse);
                    }
                } else {
                    this.y = 0;
                    player.move(direction, maxHeight);
                }
                break;
            case 1:
                if (this.x + this.width > maxWidth) {
                    this.x = this.x - vitesse;
                    for (Obstacle o : obstacles) {
                        o.setX(o.getX() - vitesse);
                    }
                } else {
                    this.x = maxWidth - this.width;
                    player.move(direction, maxWidth);
                }
                break;
            case 2:
                if (this.y + this.height > maxHeight) {
                    this.y = this.y - vitesse;
                    for (Obstacle o : obstacles) {
                        o.setY(o.getY() - vitesse);
                    }
                } else {
                    this.y = maxHeight - this.height;
                    player.move(direction, maxHeight);
                }
                break;
            case 3:
                if (this.x < 0) {
                    this.x = this.x + vitesse;
                    for (Obstacle o : obstacles) {
                        o.setX(o.getX() + vitesse);
                    }
                } else {
                    this.x = 0;
                    player.move(direction, maxWidth);
                }
                break;
            default:
                break;
        }
    }
}
