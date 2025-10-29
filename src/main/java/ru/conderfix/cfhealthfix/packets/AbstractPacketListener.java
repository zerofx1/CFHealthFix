package ru.conderfix.cfhealthfix.packets;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPacketListener implements PacketListener {
    abstract public void onPacketSend(@NotNull PacketSendEvent event);

    @SuppressWarnings("unchecked")
    protected <T> EntityData<@NotNull T> cast(EntityData<?> data) {
        return (EntityData<@NotNull T>) data;
    }
}
