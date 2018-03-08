package me.oahuaidan.staff;

import lombok.Getter;
import me.oahuaidan.staff.command.*;
import me.oahuaidan.staff.listener.FreezeListener;
import me.oahuaidan.staff.listener.JoinLeaveListener;
import me.oahuaidan.staff.listener.ModModeListener;
import me.oahuaidan.staff.listener.runnable.FreezeRunnable;
import me.oahuaidan.staff.twofactor.TwoFactorJoinListener;
import me.oahuaidan.staff.twofactor.TwoFactorListeners;
import me.oahuaidan.staff.util.Basic;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Staff extends JavaPlugin {

    @Getter private static Staff instance;
    @Getter private Sql2o sql;

    @Getter private Connection connection;

    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        FileConfiguration c = getConfig();
        sql = new Sql2o(String.format("mysql://%s:%s/%s", c.getString("database.ip"), c.getString("database.port"), c.getString("database.name")),
                c.getString("database.username"), c.getString("database.password"));

        this.connection = sql.open();

        // Remove the comment below to debug a SQL error
        // this.connection.setRollbackOnException(false);

        new FreezeRunnable().runTaskTimer(this, 100, 100);

        Basic.registerCommands(this,
                new ModCommand(this),
                new FreezeCommand(this),
                new PanicCommand(this),
                new StaffChatCommand(this),
                new RequestCommand(this),
                new ReportCommand(this),
                new TwoFactorCommand(this),
                new TwoFactorSetupCommand(this));

        Basic.registerListeners(this,
                new ModModeListener(),
                new FreezeListener(),
                new JoinLeaveListener(),
                new TwoFactorJoinListener(),
                new TwoFactorListeners());
    }

    public void onDisable() {

        // Close SQL database connection
        this.connection.close();

    }
}
