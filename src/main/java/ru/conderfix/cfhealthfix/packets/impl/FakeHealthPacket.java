package ru.conderfix.cfhealthfix.packets.impl;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import ru.conderfix.cfhealthfix.CFHealthFix;
import ru.conderfix.cfhealthfix.packets.AbstractPacketListener;

public class FakeHealthPacket extends AbstractPacketListener {

    private final int INDEX_HEALTH = CFHealthFix.getIndexHealth();
    private final float FAKE_HEALTH = CFHealthFix.getFakeHealth();

    @Override
    public void onPacketSend(@NotNull PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_METADATA) return;

        final WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata(event);
        if (event.getUser().getEntityId() == wrapper.getEntityId()) return;

        for (EntityData<?> entityData : wrapper.getEntityMetadata()) {
            if (entityData.getIndex() == INDEX_HEALTH &&
                    entityData.getValue() instanceof Float &&
                    ((Float) entityData.getValue()).intValue() >= 0.1f) super.cast(entityData).setValue(FAKE_HEALTH);
        }
    }

}
