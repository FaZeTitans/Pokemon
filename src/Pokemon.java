import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.w3c.dom.css.Rect;


public class Pokemon extends BasicGame {
    private boolean debugMode;
    private boolean consoleShown;
    private TextField console;
    private boolean startingScreenShown;
    private Map map;
    private Player player;
    private Obstacle[] obstacles;

    // Superposed Screens
    private boolean inventoryShown;
    private boolean pauseMenuShown;

    // Fonts
    /**
     * Fonts: Pokemon Solid | Pokemon GB | Pokemon Hollow
     */
    private TrueTypeFont screenTitleFont;
    private TrueTypeFont consoleFont;

    // Images
    private Image startingScreen;
    private Image mapTexture;
    private Image background;


    public Pokemon(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        debugMode = true;
        consoleShown = false;
        startingScreenShown = true;
        map = new Map(0, 0, 3840, 2160); // 1920:1080
        player = new Player((float) gc.getWidth() / 2, (float) gc.getHeight() / 2);
        obstacles = new Obstacle[2];
        loadObstacles();

        // Fonts
        java.awt.Font awtScreenTitleFont = new java.awt.Font("Montserrat", java.awt.Font.BOLD, 48);
        screenTitleFont = new TrueTypeFont(awtScreenTitleFont, false);
        java.awt.Font awtConsoleFont = new java.awt.Font("Montserrat", java.awt.Font.PLAIN, 14);
        consoleFont = new TrueTypeFont(awtConsoleFont, false);

        // Images
        startingScreen= new Image("./resources/startingScreen.png");
        mapTexture = new Image("./resources/grass.png");
        background = new Image("./resources/background.png");

        // Superposed Screens
        inventoryShown = false;
        pauseMenuShown = false;

        console = new TextField(gc, consoleFont, 50, 20, gc.getWidth() - 100, 20, abstractComponent -> {
            Input input = gc.getInput();
            if (input.isKeyPressed(Input.KEY_ENTER)) {
                String cmd = console.getText();
                if (cmd.startsWith(">quit")) gc.exit();
                if (cmd.startsWith(">god")) player.switchInvulnerability();
                if (cmd.startsWith(">inv")) inventoryShown = !inventoryShown;
                if (cmd.startsWith(">pause")) pauseMenuShown = !pauseMenuShown;
                if (cmd.startsWith(">tp")) player.teleport(Float.parseFloat(cmd.split(" ")[1]) ,
                        Float.parseFloat(cmd.split(" ")[2]));

                console.setText(">");
                console.setCursorPos(1);
                consoleShown = false;
            }
        });
    }

    @Override
    public void update(GameContainer gc, int delta) {
        Input input = gc.getInput();

        /*
        // Player independent move
        if(input.isKeyDown(Input.KEY_Z) || input.isKeyDown(Input.KEY_UP)) player.move(0,gc.getHeight());
        if(input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) player.move(1,gc.getWidth());
        if(input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN)) player.move(2,gc.getHeight());
        if(input.isKeyDown(Input.KEY_Q) || input.isKeyDown(Input.KEY_LEFT)) player.move(3,gc.getWidth());
        */

        if (!inventoryShown && !pauseMenuShown && !consoleShown && !startingScreenShown) {
            if (input.isKeyDown(Input.KEY_Z) || input.isKeyDown(Input.KEY_UP))
                map.move(0, gc.getWidth(), gc.getHeight(), player.getSpeed(), player, obstacles);
            if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT))
                map.move(1, gc.getWidth(), gc.getHeight(), player.getSpeed(), player, obstacles);
            if (input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN))
                map.move(2, gc.getWidth(), gc.getHeight(), player.getSpeed(), player, obstacles);
            if (input.isKeyDown(Input.KEY_Q) || input.isKeyDown(Input.KEY_LEFT))
                map.move(3, gc.getWidth(), gc.getHeight(), player.getSpeed(), player, obstacles);
        }

        if (input.isKeyPressed(Input.KEY_I)) {
            if (!pauseMenuShown && !consoleShown && !startingScreenShown) inventoryShown = !inventoryShown;
        }

        if (input.isKeyPressed(Input.KEY_P)) {
            if (startingScreenShown) return;
            if (inventoryShown) inventoryShown = false;
            if (consoleShown) return;
            pauseMenuShown = !pauseMenuShown;
        }

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            if (inventoryShown) inventoryShown = false;
            else if (consoleShown) consoleShown = false;
            else pauseMenuShown = !pauseMenuShown;
        }

        if (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_SPACE)){
            if (startingScreenShown) startingScreenShown = false;
        }

        if (input.isKeyPressed(Input.KEY_F3)) debugMode = !debugMode;
        if (input.isKeyPressed(Input.KEY_F8) || input.isKeyPressed(Input.KEY_DIVIDE)) {
            consoleShown = !consoleShown;
            console.setText(">");
            console.setFocus(true);
            console.setCursorPos(1);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        /*
        Map Color:
        g.setColor(Color.green);
        g.fillRect(map.getX(), map.getY(), map.getWidth(), map.getHeight());
        */
        g.drawImage(mapTexture, 0, 0);
        // Map Border:
        g.setColor(Color.darkGray);
        g.setLineWidth(15);
        g.drawRoundRect(map.getX(), map.getY(), map.getWidth(), map.getHeight(), 10);
        // Player
        g.setColor(Color.red);
        g.fillRect(player.getX(), player.getY(), player.getHitbox(), player.getHitbox());

        // Obstacles
        g.setColor(Color.blue);
        g.setLineWidth(1);
        for (Obstacle o : obstacles) {
            switch (o.getType()) {
                case RECTANGLE -> g.drawRect(o.getX(), o.getY(), o.getWidth(), o.getHeight());
                case OVAL -> g.drawOval(o.getX(), o.getY(), o.getWidth(), o.getHeight());
                case ARC ->
                        g.drawArc(o.getX(), o.getY(), o.getWidth(), o.getHeight(), o.getX() + o.getWidth(), o.getY() + o.getHeight());
                case LINE -> g.drawLine(o.getX(), o.getY(), o.getX() + o.getWidth(), o.getY() + o.getHeight());
            }
        }

        // Pause Menu
        if (pauseMenuShown) {
            g.drawImage(background, 0, 0);

            g.setColor(Color.white);
            g.setFont(screenTitleFont);
            g.setDrawMode(1);
            drawCenteredX("Pause Menu", gc, screenTitleFont, g, 50);
        }

        // Inventory
        if (inventoryShown) {
            g.drawImage(background, 0, 0);

            g.setColor(Color.white);
            g.setFont(screenTitleFont);
            drawCenteredX("Inventaire", gc, screenTitleFont, g, 50);
        }

        // Starting Screen
        if (startingScreenShown){
            g.drawImage(startingScreen, 0,0);
        }

        // Debug Mode
        if (debugMode) {
            g.resetFont();
            g.setColor(Color.white);
            g.drawString(
                    "Window: w=" + gc.getWidth() + " | h=" + gc.getHeight() + "\n" +
                            "Player:\n" +
                            "   x=" + player.getX() + " | y=" + player.getY() + "\n" +
                            "   speed=" + player.getSpeed() + "\n" +
                            "   hitboxSize=" + player.getHitbox() + "\n" +
                            "   invulnerable=" + player.isInvulnerable() +
                            "\nMap:\n" +
                            "   x=" + map.getX() + " | y=" + map.getY() + "\n" +
                            "   w=" + map.getWidth() + " | h=" + map.getHeight() +
                            "\nObstacles: " + obstacles.length + " loaded" +
                            "\nInventory: " + (inventoryShown ? "opened" : "closed") +
                            "\nStarting Screen: " + (startingScreenShown ? "opened" : "closed") +
                            "\nPause Menu: " + (pauseMenuShown ? "opened" : "closed")
                    , 3, 0);
        }
        if (consoleShown) {
            g.resetFont();
            g.drawImage(background, 0, 0);
            g.setColor(Color.white);
            console.render(gc, g);
        }
    }

    public void loadObstacles() {
        Obstacle o1 = new Obstacle(200, 200, 200, 100, ObstacleTypes.RECTANGLE);
        Obstacle o2 = new Obstacle(400, 200, 200, 300, ObstacleTypes.RECTANGLE);

        obstacles[0] = o1;
        obstacles[1] = o2;
    }

    public void drawCentered(String chars, Rect r, Font font, Graphics g) {
        // Draw Centered Rect
    }

    public void drawCentered(String chars, GameContainer gc, Font font, Graphics g) {
        // Draw Centered Screen
        float width = font.getWidth(chars);
        float height = font.getHeight(chars);

        g.drawString(chars, (gc.getWidth() - width) / 2, (gc.getHeight() - height) / 2);
    }

    public void drawCenteredX(String chars, GameContainer gc, Font font, Graphics g, float yPosition) {
        // Draw Centered Screen
        float width = font.getWidth(chars);

        g.drawString(chars, (gc.getWidth() - width) / 2, yPosition);
    }

    public void drawCenteredY(String chars, GameContainer gc, Font font, Graphics g, float xPosition) {
        // Draw Centered Screen
        float height = font.getHeight(chars);

        g.drawString(chars, xPosition, (gc.getHeight() - height) / 2);
    }

    public void clickInteraction(float x, float y) {

    }
}