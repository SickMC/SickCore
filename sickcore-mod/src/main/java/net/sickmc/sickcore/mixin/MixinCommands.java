package net.sickmc.sickcore.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.sickmc.sickcore.games.Game;
import net.sickmc.sickcore.games.survival.CommonEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Commands.class)
public class MixinCommands {

    @Shadow @Final private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(method = "performCommand", at = @At("RETURN"))
    private void performCommand(CommandSourceStack commandSource, String command, CallbackInfoReturnable<Integer> cir){
        if (Game.current.getName().equals("Survival")) CommonEvents.INSTANCE.command(commandSource, command, cir, dispatcher);
    }

}
