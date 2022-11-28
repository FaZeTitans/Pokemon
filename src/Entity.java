import org.newdawn.slick.Image;

public class Entity {
    private int maxHealth;

    private int level;
    private int exp;
    private int health;
    private int defense;
    private int speed;
    private PokemonTypes type;
    private Attack[] attacks;
    private Image texture;

    public Entity() {
        this.maxHealth = 20;
        this.level = 1;
        this.exp = 0;
        this.health = 20;
        this.defense = 20;
        this.speed = 3;
        this.attacks = new Attack[4];
        this.attacks[0] = new Attack();
    }

    public boolean attack(Attack attack, Entity e){
        if (attack.canUse() && !attack.hasMissed()){
            float damage = attack.getDamage() * (1-e.defense / 100) * (1-(this.speed - e.speed) / 100);
            if (this.type == e.type) damage *= 0.5;
            e.health = (int) Math.max(0, damage);
            return true;
        }
        return false;
    }

    public boolean isDied(){
        return this.health <= 0;
    }

    public boolean addExp(int exp){
        if (this.level >= 100) return false;
        this.exp = exp;
        if(this.exp > 5/* TODO calc level exponential */) {

            return true;
        }
        return false;
    }

    public void levelUp(){
        while(this.exp > 5/* TODO calc level exponetial */){
            if (this.level >= 100) return;
            this.exp -= 0; // TODO remove level adding
            this.level += 1;
            this.maxHealth += this.level % 2 == 0 ? 2 : 3;
            this.health += this.level % 2 == 0 ? 2 : 3;
            this.defense += Math.max(100, this.level % 2 == 0 ? 2 : 1);
            this.speed += Math.max(100, this.level % 2 == 0 ? 0 : 1);
        }
    }

    public void heal(){
        this.health = maxHealth;
    }
}
