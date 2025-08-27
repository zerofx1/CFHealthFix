package ru.conderfix.cfhealthfix.packets;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerUpdateLight;
import org.bukkit.Bukkit;
import ru.conderfix.cfhealthfix.CFHealthFix;

import java.util.List;

public class FakeHealthPacket implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_METADATA) return;

        final WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata(event);
        if (event.getUser().getEntityId() == wrapper.getEntityId()) return;

        for (EntityData<?> entityData : wrapper.getEntityMetadata()) {
            if (entityData.getIndex() == CFHealthFix.getIndexHealth() &&
                    entityData.getValue() instanceof Float &&
                    ((Float) entityData.getValue()).intValue() >= 0.1f) cast(entityData).setValue(CFHealthFix.getFakeHealth());
        }

    }

    @SuppressWarnings("unchecked")
    private static <T> EntityData<T> cast(EntityData<?> data) {
        return (EntityData<T>) data;
    }
}
