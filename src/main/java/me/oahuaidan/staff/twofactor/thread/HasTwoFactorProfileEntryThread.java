package me.oahuaidan.staff.twofactor.thread;

import lombok.Getter;
import org.sql2o.Connection;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class HasTwoFactorProfileEntryThread extends Thread {

    @Getter private Connection connection;
    @Getter private UUID uuid;

    private AtomicBoolean has = new AtomicBoolean(true);
    public boolean hasEntry() { return has.get(); }

    public HasTwoFactorProfileEntryThread(Connection connection, UUID uuid) {
        this.connection = connection;
        this.uuid = uuid;
    }

    @Override
    public void run() {

        List<String> result = getConnection().createQuery(String.format("SELECT secretKey FROM twoFactor WHERE uuid='%s'", getUuid().toString())).executeAndFetch(String.class);

        if (result.isEmpty()) {
            this.has.set(false);
            return;
        }

        if (result.get(0) == null || result.get(0).equalsIgnoreCase("null"))
            this.has.set(false);
    }
}
