public class Attack {

    private int damage;
    private PokemonTypes type;
    private int maxUses;
    private int uses;
    private float missPercentage;


    public int getDamage() {
        return damage;
    }

    public boolean canUse(){
        return maxUses - uses > 0;
    }

    public boolean hasMissed(){
        return Math.random() * 100 < this.missPercentage;
    }
}
