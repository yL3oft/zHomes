package me.yleoft.zHomes.hooks;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import me.yleoft.zHomes.Main;

public class WorldGuardHook {

    public static void setupFlags() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        // use-homes flag
        try {
            StateFlag useHomesFlag = new StateFlag("use-homes", true);
            registry.register(useHomesFlag);
            Main.useHomesFlag = useHomesFlag;
        } catch (FlagConflictException | IllegalStateException e) {
            Flag<?> existinguseHomesFlag = registry.get("use-homes");
            if (existinguseHomesFlag instanceof StateFlag) {
                Main.useHomesFlag = (StateFlag) existinguseHomesFlag;
            }
        }
        // set-homes flag
        try {
            StateFlag setHomesFlag = new StateFlag("set-homes", true);
            registry.register(setHomesFlag);
            Main.setHomesFlag = setHomesFlag;
        } catch (FlagConflictException | IllegalStateException e) {
            Flag<?> existingsetHomesFlag = registry.get("set-homes");
            if (existingsetHomesFlag instanceof StateFlag) {
                Main.setHomesFlag = (StateFlag) existingsetHomesFlag;
            }
        }
    }

}
