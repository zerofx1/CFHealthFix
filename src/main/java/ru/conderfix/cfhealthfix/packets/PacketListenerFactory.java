package ru.conderfix.cfhealthfix.packets;

import ru.conderfix.cfhealthfix.packets.impl.FakeHealthPacket;
import ru.conderfix.cfhealthfix.packets.impl.FakeItemStackAmountPacket;
import ru.conderfix.cfhealthfix.packets.impl.HideEffectsPacket;

import java.util.List;

public class PacketListenerFactory {
    public static List<AbstractPacketListener> createListeners() {
        return List.of(
                new FakeHealthPacket(),
                new FakeItemStackAmountPacket(),
                new HideEffectsPacket()
        );
    }
}