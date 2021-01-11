package me.daqem.xlifehealthmod.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityHealthProvider implements ICapabilitySerializable<CompoundNBT>{

    private final DefaultEntityHealth health = new DefaultEntityHealth();
    private final LazyOptional<IEntityHealth> healthOptional = LazyOptional.of(() -> health);

    public void invalidate() {
        healthOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return healthOptional.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY == null) {
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY.writeNBT(health, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY != null) {
            CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY.readNBT(health, null, nbt);
        }
    }
}
