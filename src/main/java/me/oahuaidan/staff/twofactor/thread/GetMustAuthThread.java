package me.oahuaidan.staff.twofactor.thread;

import lombok.Getter;
import org.sql2o.Connection;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class GetMustAuthThread extends Thread {

    @Getter private Connection connection;
    @Getter private UUID uuid;

    private AtomicBoolean mustAuth = new AtomicBoolean(false);
    public boolean getMustAuth() { return mustAuth.get(); }

    public GetMustAuthThread(Connection connection, UUID uuid) {
        this.connection = connection;
        this.uuid = uuid;
    }

    @Override
    public void run() {

        List<Integer> result = getConnection().createQuery(String.format("SELECT mustAuth FROM twoFactor WHERE uuid='%s'", getUuid().toString())).executeAndFetch(Integer.class);

        if (!result.isEmpty()) {
            if (result.get(0) == 0) {
                mustAuth.set(true);
            }
        }
    }
}
