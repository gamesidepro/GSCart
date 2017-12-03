/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gscart.executors;

import gscart.ConfigurationManager;
import gscart.GSCart;
import gscart.utils.utils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 *
 * @author Twelvee
 */
public class ItemUpdateExecutor implements CommandExecutor{

 @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        String itemid = null;
        if(src instanceof Player) {
                if(args.hasAny(Text.of("itemid"))) {
                    itemid = args.<String>getOne("itemid").get();
                }
                /*
		Player player = (Player)src;
                String item_in_hand = player.getItemInHand(HandTypes.MAIN_HAND).toString();
                ItemStack stackInHand = player.getItemInHand(HandTypes.MAIN_HAND).orElseGet(()-> ItemStack.of(ItemTypes.NONE, 0));
                String secId = item_in_hand.split("\\@")[1];
                secId = secId.replaceAll("\\]", "");
                player.sendMessage(Text.builder("HAND_ID: "+stackInHand.getItem().getName()+" "+secId ).color(TextColors.YELLOW).build());
                String newval = stackInHand.getItem().getName()+" "+secId;
                //String table = ConfigurationManager.getInstance().getConfig().getNode("gs_shop").getString();
                GSCart.instance.getDb().getResultSet("UPDATE `gs_shop` SET `itemid`='"+newval+"' WHERE `id`='"+itemid+"'");
                utils.sendMessage(player, TextColors.GREEN, "[Игровая Сторона] ", TextColors.WHITE, "Вы успешно обновили ID в базе данных");
                */         
}
        return CommandResult.success();
    }
}
