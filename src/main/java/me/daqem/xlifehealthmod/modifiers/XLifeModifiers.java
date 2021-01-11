package me.daqem.xlifehealthmod.modifiers;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class XLifeModifiers {

    public static final UUID MAX_HEALTH_MODIFIER = UUID.fromString("CA3F55D3-645A-4FA8-A497-9C13A33DB5CD");

    public static void applyMaxHealthModifier(PlayerEntity player, float amount) {
        IAttributeInstance attribute = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
        attribute.removeModifier(MAX_HEALTH_MODIFIER);
        attribute.applyModifier(new AttributeModifier(MAX_HEALTH_MODIFIER, "MaxHealth", amount, AttributeModifier.Operation.ADDITION));
        if (player.getMaxHealth() > amount + 20) {
            player.setHealth(player.getMaxHealth());
        }
    }
}

