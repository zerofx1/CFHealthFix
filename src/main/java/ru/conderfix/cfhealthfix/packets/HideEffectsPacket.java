package ru.conderfix.cfhealthfix.packets;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;

public class HideEffectsPacket implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_EFFECT) return;

        final WrapperPlayServerEntityEffect wrapper = new WrapperPlayServerEntityEffect(event);
        if (event.getUser().getEntityId() == wrapper.getEntityId()) return;

        event.setCancelled(true);
    }
}
