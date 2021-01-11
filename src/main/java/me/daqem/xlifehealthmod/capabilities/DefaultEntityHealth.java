package me.daqem.xlifehealthmod.capabilities;

public class DefaultEntityHealth implements IEntityHealth {

    private int health;

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void copyForRespawn(DefaultEntityHealth deadPlayer) {
        health = deadPlayer.health;
    }
}
