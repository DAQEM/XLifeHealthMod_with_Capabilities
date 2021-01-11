package me.daqem.xlifehealthmod.capabilities;

public interface IEntityHealth {

    void setHealth(int health);

    int getHealth();

    void copyForRespawn(DefaultEntityHealth oldStore);
}
