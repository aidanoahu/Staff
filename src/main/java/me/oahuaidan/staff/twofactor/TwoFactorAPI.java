package me.oahuaidan.staff.twofactor;

import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.twofactor.thread.*;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TwoFactorAPI {

    private static Staff pl = Staff.getInstance();

    public static String getSecret(UUID uuid) {
        GetSecretKeyThread thread = new GetSecretKeyThread(pl.getConnection(), uuid);
        thread.run();
        return thread.getSecretKey();
    }

    public static String getLastIP(UUID uuid) {
        GetLastIPThread thread = new GetLastIPThread(pl.getConnection(), uuid);
        thread.run();
        return thread.getIp();
    }

    public static boolean getMustAuth(UUID uuid) {
        GetMustAuthThread thread = new GetMustAuthThread(pl.getConnection(), uuid);
        thread.run();
        return thread.getMustAuth();
    }

    public static void setSecret(UUID uuid, String secret, String ip) {
        SetSecretKeyThread thread = new SetSecretKeyThread(pl.getConnection(), uuid, secret, ip);
        thread.run();
    }

    public static void setMustAuth(UUID uuid, boolean mustAuth) {
        SetMustAuthThread thread = new SetMustAuthThread(pl.getConnection(), uuid, mustAuth);
        thread.run();
    }

    public static void setLastIP(UUID uuid, String ip) {
        SetLastIPThread thread = new SetLastIPThread(pl.getConnection(), uuid, ip);
        thread.run();
    }

    public static void setLastIP(Player player) {
        SetLastIPThread thread = new SetLastIPThread(pl.getConnection(), player);
        thread.run();
    }

    public static boolean hasEntry(UUID uuid) {
        HasTwoFactorProfileEntryThread thread = new HasTwoFactorProfileEntryThread(pl.getConnection(), uuid);
        thread.run();
        return thread.hasEntry();
    }

    public static void createTableIfNotExists() {
        CreateTwoFactorTableThread thread = new CreateTwoFactorTableThread(pl.getConnection());
        thread.run();
    }

}
