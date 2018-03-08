package me.oahuaidan.staff.twofactor.thread;

import lombok.Getter;
import org.sql2o.Connection;

public class CreateTwoFactorTableThread extends Thread {

    @Getter private Connection connection;

    public CreateTwoFactorTableThread(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        connection.createQuery("CREATE TABLE IF NOT EXISTS twoFactor(uuid VARCHAR(64) NOT NULL PRIMARY KEY, secretKey VARCHAR(100) NULL, lastIP VARCHAR(64) NULL, mustAuth TINYINT NULL)"
        ).executeUpdate();
    }
}
