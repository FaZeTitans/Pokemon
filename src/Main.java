import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
    public static void main(String[] args) throws SlickException {
        Pokemon pokemon = new Pokemon("Pokemon 2D");
        AppGameContainer app = new AppGameContainer(pokemon);
        app.setTargetFrameRate(100);
        app.setShowFPS(false);
        app.setDisplayMode(1080, 720, false);
        app.start();
    }
}

// Some Textures: https://www.reddit.com/r/pokemon/comments/2vdw3p/looking_for_a_good_resource_for_pok%C3%A9mon_textures/
// Mobs Textures: https://assetstore.unity.com/?category=2d%2Fcharacters&orderBy=1