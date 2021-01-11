package me.daqem.xlifehealthmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.daqem.xlifehealthmod.capabilities.CapabilityEntityHealth;
import me.daqem.xlifehealthmod.utils.SendMessage;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TextFormatting;

import java.util.Collection;

import static me.daqem.xlifehealthmod.modifiers.XLifeModifiers.applyMaxHealthModifier;

public class XLifeCommands {

    private XLifeCommands() {

    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(Commands.literal("sethealth")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.argument("target", EntityArgument.players())
                        .then(Commands.argument("hearts", IntegerArgumentType.integer())
                                .executes(context -> setHealthtoPlayer(
                                        context.getSource(),
                                        EntityArgument.getPlayers(context, "target"),
                                        IntegerArgumentType.getInteger(context, "hearts")))
                        )
                )
        );
        dispatcher.register(Commands.literal("gethealth")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.argument("targets", EntityArgument.players())
                        .executes(context -> checkHealth(context.getSource(), EntityArgument.getPlayers(context, "targets")))
                )
        );
        dispatcher.register(Commands.literal("about")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.argument("xlifehealth", StringArgumentType.string())
                        .executes(context -> about(context.getSource()))
                )
        );
        dispatcher.register(Commands.literal("getmodifier")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .then(Commands.argument("targets", EntityArgument.players())
                        .executes(context -> checkModifier(context.getSource(), EntityArgument.getPlayers(context, "targets")))
                )
        );
    }

    private static int about(CommandSource source) {
        SendMessage.sendFeedback(source, TextFormatting.YELLOW + "=====================================");
        SendMessage.sendFeedback(source, TextFormatting.YELLOW + " ");
        SendMessage.sendFeedback(source, TextFormatting.GOLD + "About X-Life!");
        SendMessage.sendFeedback(source, TextFormatting.GOLD + " ");
        SendMessage.sendFeedback(source, TextFormatting.WHITE + "Author: DAQEM");
        SendMessage.sendFeedback(source, TextFormatting.WHITE + "Minecraft version: 1.14.4");
        SendMessage.sendFeedback(source, TextFormatting.WHITE + "Created on Forge 28.2.0");
        SendMessage.sendFeedback(source, TextFormatting.WHITE + "Report bugs to: admin@daqem.com");
        SendMessage.sendFeedback(source, TextFormatting.RED + "VERSION WITH CAPABILITIES");
        SendMessage.sendFeedback(source, TextFormatting.YELLOW + " ");
        SendMessage.sendFeedback(source, TextFormatting.YELLOW + "=====================================");

        return 1;
    }

    private static int checkHealth(CommandSource source, Collection<ServerPlayerEntity> targets) {
        for (ServerPlayerEntity player : targets) {
            SendMessage.sendFeedback(source, TextFormatting.YELLOW + "Health: " + TextFormatting.GOLD + player.getHealth());
            SendMessage.sendFeedback(source, TextFormatting.YELLOW + "Max Health: " + TextFormatting.GOLD + player.getMaxHealth());
            player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
                SendMessage.sendFeedback(source, TextFormatting.YELLOW + "Given by Mod: " + TextFormatting.GOLD + player.getMaxHealth());
            });
        }
        return 1;
    }

    private static int setHealthtoPlayer(CommandSource source, Collection<ServerPlayerEntity> targets, int hearts) {
        int health = (hearts * 2) - 20;
        if (!(hearts >= 11) && !(hearts <= 0)) {
            for (ServerPlayerEntity player : targets) {
                player.getCapability(CapabilityEntityHealth.ENTITY_HEALTH_CAPABILITY).ifPresent(h -> {
                    h.setHealth(hearts * 2);
                    applyMaxHealthModifier(player, health);
                    if (source.getName().equals(player.getGameProfile().getName())) {
                        SendMessage.sendFeedback(source, TextFormatting.YELLOW + "You have set your hearts to " + TextFormatting.GOLD + hearts + TextFormatting.YELLOW + ".");
                    } else {
                        SendMessage.sendFeedback(source, TextFormatting.YELLOW + "You have set " + TextFormatting.GOLD +
                                player.getGameProfile().getName() + "s " + TextFormatting.YELLOW + "hearts to " + TextFormatting.GOLD + hearts + TextFormatting.YELLOW + ".");
                    }
                });
                if (player.getHealth() >= player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            }
        } else {
            SendMessage.sendFeedback(source, TextFormatting.RED + " Error: [number of hearts] must be between 1 and 10.");
        }
        return 1;
    }

    private static int checkModifier(CommandSource source, Collection<ServerPlayerEntity> targets) {
        for (ServerPlayerEntity player : targets) {
            IAttributeInstance attribute = player.getAttribute(SharedMonsterAttributes.MAX_HEALTH);
            SendMessage.sendFeedback(source, TextFormatting.YELLOW + "Max Health: " + TextFormatting.RED + attribute.getModifiers().toString());
        }
        return 1;
    }
}
