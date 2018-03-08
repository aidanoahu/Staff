package me.oahuaidan.staff.command;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import me.oahuaidan.staff.Staff;
import me.oahuaidan.staff.twofactor.TwoFactorAPI;
import me.oahuaidan.staff.util.Basic;
import me.oahuaidan.staff.util.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TwoFactorCommand extends CommandBase {

    public TwoFactorCommand(Staff staff) {
        super(staff, "2fa", "/(command) <code>");
    }

    protected void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if (!TwoFactorAPI.getMustAuth(p.getUniqueId())) {
            p.sendMessage(Basic.translate("&cYou don't need to complete a two factor request."));
            return;
        }

        if (args.length == 0) {
            p.sendMessage(Basic.translate("&cUsage: " + getUsage()));
            return;
        }

        if (args.length > 1) {
            p.sendMessage(Basic.translate("&cThe code must be entered as a whole number."));
            return;
        }

        int code = 0;
        try {
            code = Integer.valueOf(args[0]);
        } catch (Exception e) {
            p.sendMessage(Basic.translate("&cThe code must be all numbers."));
            return;
        }

        GoogleAuthenticator g = new GoogleAuthenticator();
        boolean valid = g.authorize(TwoFactorAPI.getSecret(p.getUniqueId()), code);

        if (valid) {
            TwoFactorAPI.setMustAuth(p.getUniqueId(), false);
            TwoFactorAPI.setLastIP(p);
            p.sendMessage(Basic.translate("&aTwo Factor authentication completed."));
        } else {
            p.sendMessage(Basic.translate("&cInvalid code."));
        }
    }
}
