package ru.conderfix.cfhealthfix.packets;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import ru.conderfix.cfhealthfix.CFHealthFix;

public class FakeItemStackAmountPacket implements PacketListener {

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_METADATA) return;

        final WrapperPlayServerEntityMetadata wrapper = new WrapperPlayServerEntityMetadata(event);
        if (event.getUser().getEntityId() == wrapper.getEntityId()) return;

        wrapper.getEntityMetadata().forEach(entityData -> {
            if (entityData.getIndex() == 7) {
                if (entityData.getValue() instanceof ItemStack) {
                    final ItemStack itemStack = (ItemStack) entityData.getValue();
                    if (itemStack.getAmount() > 1) {
                        itemStack.setAmount(CFHealthFix.getFakeItemStackAmount());
                        entityData.setValue(itemStack);
                    }
                }
            }
        });
    }
}