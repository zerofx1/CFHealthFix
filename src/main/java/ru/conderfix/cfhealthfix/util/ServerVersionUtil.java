package ru.conderfix.cfhealthfix.util;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServerVersionUtil {

    private static final int INDEX_HEALTH_116 = 8;
    private static final int INDEX_HEALTH_117 = 9;

    private static final int INDEX_ITEM_116 = 7;
    private static final int INDEX_ITEM_117 = 8;

    public ServerVersion getServerVersion() {
        return PacketEvents.getAPI().getServerManager().getVersion();
    }

    public int getIndexHealth(ServerVersion serverVersion) {
        validateServerVersion(serverVersion);
        return (serverVersion.compareTo(ServerVersion.V_1_17) <= 0)
                ? INDEX_HEALTH_116
                : INDEX_HEALTH_117;
    }

    public int getIndexItem(ServerVersion serverVersion) {
        validateServerVersion(serverVersion);
        return (serverVersion.compareTo(ServerVersion.V_1_17) <= 0)
                ? INDEX_ITEM_116
                : INDEX_ITEM_117;
    }

    private void validateServerVersion(ServerVersion serverVersion) {
        if (serverVersion == ServerVersion.ERROR) throw new IllegalStateException("Server version is not supported!");
    }
}
