package me.yleoft.zHomes.hooks;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import me.yleoft.zHomes.Main;

public class WorldGuardHook {

    public static void setupFlags() throws Exception {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            // use-homes flag
            StateFlag useHomesFlag = new StateFlag("use-homes", true);
            registry.register(useHomesFlag);
            Main.useHomesFlag = useHomesFlag;
            // set-homes flag
            StateFlag setHomesFlag = new StateFlag("set-homes", true);
            registry.register(setHomesFlag);
            Main.setHomesFlag = setHomesFlag;
            // bypass-home-warmup flag
            StateFlag bypassHomeWarmupFlag = new StateFlag("bypass-home-warmup", false);
            registry.register(bypassHomeWarmupFlag);
            Main.bypassHomeWarmupFlag = bypassHomeWarmupFlag;
            // bypass-home-cost flag
            StateFlag bypassHomeCostFlag = new StateFlag("bypass-home-cost", false);
            registry.register(bypassHomeCostFlag);
            Main.bypassHomeCostFlag = bypassHomeCostFlag;
        } catch (FlagConflictException | IllegalStateException e) {
            Flag<?> existinguseHomesFlag = registry.get("use-homes");
            if (existinguseHomesFlag instanceof StateFlag) {
                Main.useHomesFlag = (StateFlag) existinguseHomesFlag;
            }
            Flag<?> existingsetHomesFlag = registry.get("set-homes");
            if (existingsetHomesFlag instanceof StateFlag) {
                Main.setHomesFlag = (StateFlag) existingsetHomesFlag;
            }Flag<?> existingbypassHomeWarmupFlag = registry.get("bypass-home-warmup");
            if (existingbypassHomeWarmupFlag instanceof StateFlag) {
                Main.bypassHomeWarmupFlag = (StateFlag) existingbypassHomeWarmupFlag;
            }
            Flag<?> existingbypassHomeCostFlag = registry.get("bypass-home-cost");
            if (existingbypassHomeCostFlag instanceof StateFlag) {
                Main.bypassHomeCostFlag = (StateFlag) existingbypassHomeCostFlag;
            }

        }
    }

}
