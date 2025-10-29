package ru.conderfix.cfhealthfix;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.event.EventManager;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.conderfix.cfhealthfix.util.ServerVersionUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public final class CFHealthFix extends JavaPlugin {

    @Getter
    private static float fakeHealth;

    @Getter
    private static int fakeItemStackAmount, indexHealth, indexItem;

    private static final float MIN_FAKE_HEALTH = 1f;
    private static final float MAX_FAKE_HEALTH = 20f;

    private static final int MIN_ITEM_AMOUNT = 2;
    private static final int MAX_ITEM_AMOUNT = 64;

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

        final Logger logger = super.getServer().getLogger();
        logger.info("Server version: " + serverVersion.name());
        logger.info("Using health index: " + indexHealth);
        logger.info("Using item index: " + indexItem);
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }

    private void registerListeners() {
        final EventManager eventManager = PacketEvents.getAPI().getEventManager();
        final PacketListenerPriority listenerPriority = PacketListenerPriority.NORMAL;

        PacketListenerFactory.createListeners()
                .forEach(listener -> eventManager.registerListener(listener, listenerPriority));
    }

    private void initFakeValues() {
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        fakeHealth = threadLocalRandom.nextFloat(MIN_FAKE_HEALTH, MAX_FAKE_HEALTH);
        fakeItemStackAmount = threadLocalRandom.nextInt(MIN_ITEM_AMOUNT, MAX_ITEM_AMOUNT);
    }

    private void initPacketEvents() {
        final PacketEventsAPI<?> packetEventsAPI = PacketEvents.getAPI();

        packetEventsAPI.init();
        packetEventsAPI.getSettings()
                .checkForUpdates(false)
                .debug(false);
    }


}