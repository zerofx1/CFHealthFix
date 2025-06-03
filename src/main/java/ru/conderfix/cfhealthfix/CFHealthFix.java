package ru.conderfix.cfhealthfix;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.EventManager;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import ru.conderfix.cfhealthfix.packets.FakeHealthPacket;
import ru.conderfix.cfhealthfix.packets.FakeItemStackAmountPacket;
import ru.conderfix.cfhealthfix.packets.HideEffectsPacket;
import ru.conderfix.cfhealthfix.util.ServerVersionUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public final class CFHealthFix extends JavaPlugin {

    @Getter
    private static float fakeHealth;

    @Getter
    private static int fakeItemStackAmount, indexHealth, indexItem;

    private final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        this.initFakeValues();
        this.initPacketEvents();
        this.registerListeners();

        final ServerVersion serverVersion = ServerVersionUtil.getServerVersion();
        indexHealth = ServerVersionUtil.getIndexHealth(serverVersion);
        indexItem = ServerVersionUtil.getIndexItem(serverVersion);

        final Logger logger = Bukkit.getLogger();
        logger.info("Server version: " + serverVersion.name());
        logger.info("Using health index: " + indexHealth);
        logger.info("Using item index: " + indexItem);
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    private void initFakeValues() {
        fakeHealth = threadLocalRandom.nextFloat(1, 20);
        fakeItemStackAmount = threadLocalRandom.nextInt(2, 64);
    }

    private void registerListeners() {
        final EventManager eventManager = PacketEvents.getAPI().getEventManager();
        eventManager.registerListener(new HideEffectsPacket(), PacketListenerPriority.NORMAL);
        eventManager.registerListener(new FakeHealthPacket(), PacketListenerPriority.NORMAL);
        eventManager.registerListener(new FakeItemStackAmountPacket(), PacketListenerPriority.NORMAL);
    }

    private void initPacketEvents() {
        PacketEvents.getAPI().init();

        final PacketEventsSettings settings = PacketEvents.getAPI().getSettings();
        settings.checkForUpdates(false);
        settings.debug(false);
    }


}
