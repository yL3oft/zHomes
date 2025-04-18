package me.yleoft.zHomes.hooks;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import me.yleoft.zHomes.Main;

public class WorldGuardHook {

    public static void setupFlag() throws Exception {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag("use-homes", true);
            registry.register(flag);
            Main.useHomesFlag = flag;
        } catch (FlagConflictException | IllegalStateException e) {
            Flag<?> existing = registry.get("use-homes");
            if (existing instanceof StateFlag) {
                Main.useHomesFlag = (StateFlag) existing;
            }
        }
    }

}
