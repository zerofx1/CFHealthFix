package ru.conderfix.cfhealthfix.packets.impl;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;
import org.jetbrains.annotations.NotNull;
import ru.conderfix.cfhealthfix.packets.AbstractPacketListener;

public class HideEffectsPacket extends AbstractPacketListener {

    @Override
    public void onPacketSend(@NotNull PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_EFFECT) return;

        final WrapperPlayServerEntityEffect wrapper = new WrapperPlayServerEntityEffect(event);
        if (event.getUser().getEntityId() == wrapper.getEntityId()) return;

        event.setCancelled(true);
    }
}
