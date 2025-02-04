package ru.conderfix.cfhealthfix;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.conderfix.cfhealthfix.packets.FakeHealthPacket;
import ru.conderfix.cfhealthfix.packets.FakeItemStackAmountPacket;

import java.util.concurrent.ThreadLocalRandom;

public final class CFHealthFix extends JavaPlugin {

    @Getter
    private static float fakeHealth;

    @Getter
    private static int fakeItemStackAmount;

    private final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        fakeHealth = threadLocalRandom.nextFloat(1, 20);
        fakeItemStackAmount = threadLocalRandom.nextInt(2, 64);

        PacketEvents.getAPI().init();

        final PacketEventsSettings settings = PacketEvents.getAPI().getSettings();
        settings.checkForUpdates(false);
        settings.debug(false);

        this.registerPackets(new FakeHealthPacket(),
                             new FakeItemStackAmountPacket());
    }

    private void registerPackets(PacketListener... packetListener) {
        for (PacketListener listener : packetListener) {
            PacketEvents.getAPI().getEventManager().registerListener(listener, PacketListenerPriority.NORMAL);
        }
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}
