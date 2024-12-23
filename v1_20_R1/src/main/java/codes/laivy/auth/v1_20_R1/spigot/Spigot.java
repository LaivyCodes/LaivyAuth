package codes.laivy.auth.v1_20_R1.spigot;

import codes.laivy.auth.v1_20_R1.reflections.ServerReflections;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.io.Flushable;
import java.io.IOException;
import java.util.Objects;

// todo: disable Connection-throttling at reconnect verification
final class Spigot implements Flushable {

    // Static initializers

    private static volatile @UnknownNullability Spigot instance;

    public static synchronized void initialize() {
        instance = new Spigot();
    }
    public static synchronized void interrupt() throws IOException {
        if (instance != null) try {
            instance.flush();
        } finally {
            instance = null;
        }
    }

    // Object

    private final @NotNull Injection injection;

    private Spigot() {
        // Set online-mode=true
        ServerReflections.setOnlineMode(true);

        // Retrieve channel and create injection
        @NotNull Channel channel = ServerReflections.getServerChannel();
        this.injection = new Injection(channel);
    }

    // Getters

    public @NotNull Injection getInjection() {
        return injection;
    }

    // Loaders

    @Override
    public void flush() throws IOException {
        getInjection().flush();
    }

    // Implementations

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) return true;
        if (!(object instanceof @NotNull Spigot spigot)) return false;
        return Objects.equals(getInjection(), spigot.getInjection());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(getInjection());
    }

}
