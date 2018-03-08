package me.oahuaidan.staff.twofactor.thread;

import lombok.Getter;
import org.sql2o.Connection;

import java.util.UUID;

public class SetMustAuthThread extends Thread {

    @Getter private Connection connection;
    @Getter private UUID uuid;
    @Getter private boolean mustAuth;

    public SetMustAuthThread(Connection connection, UUID uuid, boolean mustAuth) {
        this.connection = connection;
        this.uuid = uuid;
        this.mustAuth = mustAuth;
    }

    @Override
    public void run() {
        getConnection().createQuery(String.format("UPDATE twoFactor SET mustAuth='%d' WHERE uuid='%s'", (isMustAuth() ? 0 : 1), getUuid().toString())).executeUpdate();
    }
}
