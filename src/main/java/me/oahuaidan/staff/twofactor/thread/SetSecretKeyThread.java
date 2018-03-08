package me.oahuaidan.staff.twofactor.thread;

import lombok.Getter;
import org.sql2o.Connection;

import java.util.UUID;

public class SetSecretKeyThread extends Thread {

    @Getter private Connection connection;
    @Getter private UUID uuid;
    @Getter private String secretKey;
    @Getter private String ip;

    public SetSecretKeyThread(Connection connection, UUID uuid, String secretKey, String ip) {
        this.connection = connection;
        this.uuid = uuid;
        this.secretKey = secretKey;
        this.ip = ip;
    }

    @Override
    public void run() {
        if (getConnection().createQuery(String.format("SELECT secretKey FROM twoFactor WHERE uuid='%s'", getUuid().toString())).executeAndFetch(String.class).isEmpty()) {
            getConnection().createQuery("INSERT INTO twoFactor VALUES(:uuid, :secretKey, :lastIP, :mustAuth)")
                    .addParameter("uuid", uuid.toString())
                    .addParameter("secretKey", getSecretKey())
                    .addParameter("lastIP", getIp())
                    .addParameter("mustAuth", true)
                    .executeUpdate();
        } else {
            getConnection().createQuery(String.format("UPDATE twoFactor SET secretKey='%s' WHERE uuid='%s'", getSecretKey(), getUuid().toString())).executeUpdate();
        }
    }
}
