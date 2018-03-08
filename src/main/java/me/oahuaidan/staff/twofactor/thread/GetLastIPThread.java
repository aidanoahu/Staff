package me.oahuaidan.staff.twofactor.thread;

import lombok.Getter;
import org.sql2o.Connection;

import java.util.List;
import java.util.UUID;

public class GetLastIPThread extends Thread {

    @Getter private Connection connection;
    @Getter private UUID uuid;

    @Getter private String ip;

    public GetLastIPThread(Connection connection, UUID uuid) {
        this.connection = connection;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        List<String> result = getConnection().createQuery(String.format("SELECT lastIP FROM twoFactor WHERE uuid='%s'", getUuid().toString())).executeAndFetch(String.class);

        if (result.isEmpty())
            this.ip = null;

        this.ip = result.get(0);
    }
}
