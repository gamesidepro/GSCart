/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gscart.executors;

import gscart.ConfigurationManager;
import gscart.GSCart;
import gscart.utils.utils;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CartExecutor implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        
		if(src instanceof Player) {
			Player player = (Player)src;
                        //utils.sendMessage(player, player.getItemInHand(HandTypes.MAIN_HAND));
                        if(player.getItemInHand(HandTypes.MAIN_HAND).toString().equals("Optional.empty")){
                            utils.sendMessage(player, TextColors.RED, "[Игровая Сторона] ", TextColors.WHITE, " В вашей руке пусто!");    
                            return CommandResult.success();
                        }else{
                            String item_in_hand = player.getItemInHand(HandTypes.MAIN_HAND).toString();

                            ItemStack stackInHand = player.getItemInHand(HandTypes.MAIN_HAND).orElseGet(()-> ItemStack.of(ItemTypes.NONE, 0));
                            String secId = item_in_hand.split("\\@")[1];
                            secId = secId.replaceAll("\\]", "");
                            //player.sendMessage(Text.builder("HAND_ID: "+stackInHand.getItem().getName()+" "+secId ).color(TextColors.YELLOW).build());
                            String itemgameid = stackInHand.getItem().getName()+" "+secId;
                            String table = ConfigurationManager.getInstance().getConfig().getNode("shop_table").getString();
                            int amount = player.getItemInHand(HandTypes.MAIN_HAND).get().createSnapshot().getCount();
                            int getItemIdInShop = 0;
                            try {
                                getItemIdInShop = GSCart.instance.getDb().getItemID(table, itemgameid);
                            } catch (SQLException ex) {
                                Logger.getLogger(CartExecutor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if(getItemIdInShop==-1){
                                utils.sendMessage(player, TextColors.RED, "[Игровая Сторона] ", TextColors.WHITE, "Данный предмет запрещено вносить на сайт.");    
                            }else{
                                player.setItemInHand(HandTypes.MAIN_HAND, ItemStack.empty());

                                int userID = 0;
                                try {
                                    userID = GSCart.instance.getDb().getUserId("xf_user", player.getName());
                                } catch (SQLException ex) {
                                    Logger.getLogger(CartExecutor.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                if(userID!=-1){
                                    ArrayList isInUserINV = GSCart.instance.getDb().isInUserInv(getItemIdInShop+"", userID+"");
                                    if(isInUserINV.isEmpty()){
                                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                        String NOW = timestamp.toString();
                                        //utils.sendMessage(player, "["+userID+"]");
                                        GSCart.instance.getDb().getResultSet("INSERT INTO `gs_user_inventory` (`user_id`,`item_id`,`buy_time`,`amount`,`is_new`,`trans`) VALUES ('"+userID+"','"+getItemIdInShop+"','"+NOW+"','"+amount+"','1','1')");
                                    }else{
                                        GSCart.instance.getDb().getResultSet("UPDATE `gs_user_inventory` SET `amount`=`amount`+'"+amount+"' WHERE `user_id`='"+userID+"' AND `item_id`='"+getItemIdInShop+"'");
                                    }
                                    utils.sendMessage(player, TextColors.GREEN, "[Игровая Сторона] ", TextColors.WHITE, " Вещи успешно телепортировались в ваш инвентарь на сайте!");    
                                }


                            }   
                        }
                }
        return CommandResult.success();
    }
    
}
