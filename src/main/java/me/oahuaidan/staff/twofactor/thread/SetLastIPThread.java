package me.oahuaidan.staff.twofactor.thread;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.sql2o.Connection;

import java.util.UUID;

public class SetLastIPThread extends Thread {

    @Getter private Connection connection;
    @Getter private UUID uuid;
    @Getter private String ip;

    public SetLastIPThread(Connection connection, UUID uuid, String ip) {
        this.connection = connection;
        this.uuid = uuid;
        this.ip = ip;
    }

    public SetLastIPThread(Connection connection, Player player) {
        this.connection = connection;
        this.uuid = player.getUniqueId();
        this.ip = player.getAddress().getAddress().toString();
    }

    @Override
    public void run() {
        getConnection().createQuery(String.format("UPDATE twoFactor SET lastIP='%s' WHERE uuid='%s'", getIp(), getUuid().toString())).executeUpdate();
    }
}
