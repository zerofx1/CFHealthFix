package ru.conderfix.cfhealthfix.packets.impl;

import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import org.jetbrains.annotations.NotNull;
import ru.conderfix.cfhealthfix.CFHealthFix;
import ru.conderfix.cfhealthfix.packets.AbstractPacketListener;

public class FakeItemStackAmountPacket extends AbstractPacketListener {

    private final int INDEX_ITEM = CFHealthFix.getIndexItem();
    private final int FAKE_ITEM_AMOUNT = CFHealthFix.getFakeItemStackAmount();

    @Override
    public void onPacketSend(@NotNull PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_METADATA) return;

        final WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata(event);
        if (event.getUser().getEntityId() == wrapper.getEntityId()) return;

        for (EntityData<?> entityData : wrapper.getEntityMetadata()) {
            if (entityData.getIndex() == INDEX_ITEM
                    && entityData.getValue() instanceof ItemStack itemStack
                    && itemStack.getAmount() > 1) {

                itemStack.setAmount(FAKE_ITEM_AMOUNT);
                super.cast(entityData).setValue(itemStack);
            }
        }
    }
}
