package ru.conderfix.cfhealthfix.util;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServerVersionUtil {

    public ServerVersion getServerVersion() {
        return PacketEvents.getAPI().getServerManager().getVersion();
    }

    public int getIndexHealth(ServerVersion serverVersion) {
        isServerVersionNotSupported(serverVersion);

        if (serverVersion.compareTo(ServerVersion.V_1_17) <= 0) {
            return 8; // индекс здоровья до 1.16
        } else {
            return 9; // индекс здоровья после 1.16
        }
    }

    public int getIndexItem(ServerVersion serverVersion) {
        isServerVersionNotSupported(serverVersion);

        if (serverVersion.compareTo(ServerVersion.V_1_17) <= 0) {
            return 7; // индекс айтема до 1.16
        } else {
            return 8; // индекс айтема после 1.16
        }
    }

    private void isServerVersionNotSupported(ServerVersion serverVersion) {
        if (serverVersion == ServerVersion.ERROR) {
            throw new IllegalStateException("Server version is not supported");
        }
    }
}
