package codes.laivy.auth.impl;

import codes.laivy.address.Address;
import codes.laivy.auth.core.Account;
import codes.laivy.auth.core.Activity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

final class AccountImpl implements Account {

    // Static initializers

    private final @NotNull LaivyAuthApiImpl api;

    private final @NotNull String name;
    private final @NotNull UUID uuid;
    private @Nullable Type type;

    private char @Nullable [] password;
    private boolean authenticated;
    private @Nullable Integer version;

    private @Nullable Instant registration;

    // Playing time
    private @Nullable Instant lastPlayingTimeCheck = null;
    private @NotNull Duration playingTime;

    // Constructor

    AccountImpl(@NotNull LaivyAuthApiImpl api, @NotNull String name, @NotNull UUID uuid, @Nullable Type type, char @Nullable [] password, boolean authenticated, @Nullable Integer version, @Nullable Instant registration, @Nullable Instant lastPlayingTimeCheck, @NotNull Duration playingTime) {
        this.api = api;
        this.name = name;
        this.uuid = uuid;
        this.type = type;
        this.password = password;
        this.authenticated = authenticated;
        this.version = version;
        this.registration = registration;
        this.lastPlayingTimeCheck = lastPlayingTimeCheck;
        this.playingTime = playingTime;
    }

    // Getters

    @Override
    public @NotNull String getName() {
        return name;
    }
    @Override
    public @NotNull UUID getUniqueId() {
        return uuid;
    }

    @Override
    public char @Nullable [] getPassword() {
        if (password != null) {
            return Arrays.copyOf(password, password.length);
        } else {
            return null;
        }
    }
    @Override
    public void setPassword(char @Nullable [] password) {
        if (this.password == null) {
            return;
        }

        this.registration = password != null ? Instant.now() : null;
        this.password = password;
    }

    @Override
    public @Nullable Type getType() throws UnsupportedOperationException {
        if (!api.getConfiguration().isAutomaticAuthentication()) {
            throw new UnsupportedOperationException("the automatic authentication is disabled!");
        } else {
            return type;
        }
    }
    @Override
    public void setType(@Nullable Type type) {
        if (!api.getConfiguration().isAutomaticAuthentication()) {
            throw new UnsupportedOperationException("the automatic authentication is disabled!");
        } else {
            this.type = type;
        }
    }

    @Override
    public @Nullable Integer getVersion() {
        return version;
    }
    @Override
    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public @NotNull Duration getPlayingTime() {
        ping();
        return playingTime;
    }

    @Override
    public @Nullable Instant getRegistration() {
        return registration;
    }

    @Override
    public @NotNull Address @NotNull [] getAddresses() {
        return new Address[0];
    }
    @Override
    public @NotNull Activity @NotNull [] getActivities() {
        return new Activity[0];
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public boolean isRegistered() {
        return password != null;
    }

    // Modules

    void ping() {
        if (lastPlayingTimeCheck != null) {
            playingTime = playingTime.plus(Duration.between(Instant.now(), lastPlayingTimeCheck));
        }
    }

    // Implementations

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) return true;
        if (!(object instanceof AccountImpl)) return false;
        @NotNull AccountImpl account = (AccountImpl) object;
        return Objects.equals(api, account.api) && Objects.equals(uuid, account.uuid);
    }
    @Override
    public int hashCode() {
        return Objects.hash(api, uuid);
    }

    @Override
    public @NotNull String toString() {
        return "AccountImpl{" +
                "uuid=" + uuid +
                '}';
    }

}