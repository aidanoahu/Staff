package me.oahuaidan.staff.command;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.twofactor.TwoFactorAPI;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.util.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TwoFactorSetupCommand extends CommandBase {

    public TwoFactorSetupCommand(Staff staff) {
        super(staff, "2fasetup", "/(command)");
    }

    protected void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (TwoFactorAPI.hasEntry(p.getUniqueId())) {
            p.sendMessage(Basic.translate("&cYou already have two factor authentication setup."));
            return;
        }

        GoogleAuthenticator g = new GoogleAuthenticator();
        String secret = g.createCredentials().getKey();

        p.sendMessage(Basic.translate("&6Enter this key on your phone using Google Authenticator or Authy, then type &f/2fa <code> &6to continue: &f" + secret));

        TwoFactorAPI.setSecret(p.getUniqueId(), secret, p.getAddress().getAddress().toString());
    }
}
