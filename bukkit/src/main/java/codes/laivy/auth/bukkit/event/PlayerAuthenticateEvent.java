package codes.laivy.auth.bukkit.event;

import codes.laivy.auth.account.Account;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class PlayerAuthenticateEvent extends AuthEvent implements Cancellable {

    // Object

    private boolean cancelled = false;

    public PlayerAuthenticateEvent(@NotNull Account account) {
        super(account);
    }

    // Cancellable

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
