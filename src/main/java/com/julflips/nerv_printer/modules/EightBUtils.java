package com.julflips.nerv_printer.modules;

import com.julflips.nerv_printer.Addon;
import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.EquipmentSlot;

public class EightBUtils extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> ensureGear = sgGeneral.add(new BoolSetting.Builder()
        .name("ensure-gear")
        .defaultValue(false)
        .build()
    );

    private final Setting<String> gearHome = sgGeneral.add(new StringSetting.Builder()
        .name("gear-home")
        .defaultValue("regear")
        .visible(ensureGear::get)
        .build()
    );

    private final Setting<Boolean> noLobby = sgGeneral.add(new BoolSetting.Builder()
        .name("no-lobby")
        .defaultValue(false)
        .build()
    );

    private final Setting<Boolean> forceRefresh = sgGeneral.add(new BoolSetting.Builder()
        .name("force-refresh")
        .defaultValue(false)
        .build()
    );

    private final Setting<String> master = sgGeneral.add(new StringSetting.Builder()
        .name("master")
        .defaultValue("i_am_tobias")
        .visible(forceRefresh::get)
        .build()
    );

    private final Setting<String> masterSuffix = sgGeneral.add(new StringSetting.Builder()
        .name("master-suffix")
        .defaultValue(" Whispers: refresh")
        .visible(forceRefresh::get)
        .build()
    );

    private final Setting<Boolean> autoLogin = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-login")
        .defaultValue(false)
        .build()
    );

    private final Setting<String> password = sgGeneral.add(new StringSetting.Builder()
        .name("password")
        .defaultValue("password123")
        .visible(autoLogin::get)
        .build()
    );

    private final Setting<String> autoLoginTrigger = sgGeneral.add(new StringSetting.Builder()
        .name("auto-login-trigger")
        .defaultValue("[8b8t] >> Use the command /login <password>.")
        .visible(autoLogin::get)
        .build()
    );

    private final Setting<String> loginCommand = sgGeneral.add(new StringSetting.Builder()
        .name("master-suffix")
        .defaultValue("/l")
        .visible(autoLogin::get)
        .build()
    );

    private final Setting<Boolean> fallFixer = sgGeneral.add(new BoolSetting.Builder()
        .name("fall-fixer")
        .defaultValue(false)
        .build()
    );

    private final Setting<String> fallFixerHome = sgGeneral.add(new StringSetting.Builder()
        .name("fall-fixer-home")
        .defaultValue("start")
        .visible(fallFixer::get)
        .build()
    );

    private final Setting<Integer> fallFixerThreshold = sgGeneral.add(new IntSetting.Builder()
        .name("fall-fixer--height-threshold")
        .defaultValue(64)
        .visible(fallFixer::get)
        .build()
    );

    // Internal timers
    private int ensureGearTimer = 20;
    private int fallFixerTimer = 20;
    private int noLobbyTimer = 0;

    // Force refresh sequence
    private int refreshIndex = -1;
    private int refreshTimer = 0;

    public EightBUtils() {
        super(Addon.CATEGORY, "8b-utils", "Utilities for 8b8t while nerv-printing.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;

        // Ensure Gear
        if (ensureGear.get()) {
            if (ensureGearTimer > 0) ensureGearTimer--;
            else {
                boolean hasArmor =
                    !mc.player.getEquippedStack(EquipmentSlot.HEAD).isEmpty() ||
                    !mc.player.getEquippedStack(EquipmentSlot.CHEST).isEmpty() ||
                    !mc.player.getEquippedStack(EquipmentSlot.LEGS).isEmpty() ||
                    !mc.player.getEquippedStack(EquipmentSlot.FEET).isEmpty();
                boolean inEnd = mc.world.getRegistryKey().getValue().toString().contains("the_end");

                if (!hasArmor && !inEnd) {
                    ChatUtils.sendPlayerMsg("/home " + gearHome.get());
                    switch (refreshIndex) {
                    case 0 -> ChatUtils.sendPlayerMsg(".toggle carpet-printer");
                    case 1 -> ChatUtils.sendPlayerMsg("/home " + gearHome.get());
                    case 2 -> ChatUtils.sendPlayerMsg(".toggle carpet-printer");
                    default -> {
                        refreshIndex = -1;
                        return;
                    }
                }
                refreshIndex++;
                refreshTimer = 10;
                }
            }
        }

        // No Lobby (End dimension, X/Z < 122)
        if (noLobby.get()) {
            if (noLobbyTimer > 0) noLobbyTimer--;
            else {
                boolean inEnd = mc.world.getRegistryKey().getValue().toString().contains("the_end");
                int x = mc.player.getBlockX();
                int z = mc.player.getBlockZ();

                if (inEnd && Math.abs(x) < 10 && Math.abs(z) < 122) {
                    ChatUtils.sendPlayerMsg("/8b8t");
                    noLobbyTimer = 20;
                }
            }
        }

        // Fall Fixer
        if (fallFixer.get()) {
            if (fallFixerTimer > 0) fallFixerTimer--;
            else {
                if (mc.player.getBlockY() < fallFixerThreshold.get()) {
                    ChatUtils.sendPlayerMsg("/home " + fallFixerHome.get());
                    fallFixerTimer = 20;
                }
            }
        }

        // Force Refresh sequence
        if (refreshIndex >= 0) {
            if (refreshTimer > 0) refreshTimer--;
            else {
                switch (refreshIndex) {
                    case 0 -> ChatUtils.sendPlayerMsg(".toggle carpet-printer");
                    case 1 -> ChatUtils.sendPlayerMsg(".settings carpet-printer activation-reset true");
                    case 2 -> ChatUtils.sendPlayerMsg(".toggle carpet-printer");
                    case 3 -> ChatUtils.sendPlayerMsg(".settings carpet-printer activation-reset false");
                    default -> {
                        refreshIndex = -1;
                        return;
                    }
                }
                refreshIndex++;
                refreshTimer = 10;
            }
        }
    }

    @EventHandler
    private void onChat(ReceiveMessageEvent event) {
        String msg = event.getMessage().getString();

        // Force Refresh
        if (forceRefresh.get() && msg.equals(master.get() + masterSuffix.get())) {
            refreshIndex = 0;
            refreshTimer = 10;
        }

        // AutoLogin
        if (autoLogin.get() && msg.equals(autoLoginTrigger.get())) {
            ChatUtils.sendPlayerMsg(loginCommand.get() + " " + password.get());
        }
    }
}
