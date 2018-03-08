package me.oahuaidan.staff.twofactor.thread;

import lombok.Getter;
import org.sql2o.Connection;

import java.util.List;
import java.util.UUID;

public class GetSecretKeyThread extends Thread {

    @Getter private Connection connection;
    @Getter private UUID uuid;

    @Getter private String secretKey;

    public GetSecretKeyThread(Connection connection, UUID uuid) {
        this.connection = connection;
        this.uuid = uuid;
    }

    @Override
    public void run() {
        List<String> result = getConnection().createQuery(String.format("SELECT secretKey FROM twoFactor WHERE uuid='%s'", getUuid().toString())).executeAndFetch(String.class);

        if (result.isEmpty())
            this.secretKey = null;

        this.secretKey = result.get(0);
    }
}
