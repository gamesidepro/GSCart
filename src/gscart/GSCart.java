/*
 * Shopping cart for GameSide Servers
 */
package gscart;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import gscart.events.MainEventClass;
import gscart.executors.CartExecutor;
import gscart.executors.ItemUpdateExecutor;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

/**
 * @website gameside.pro
 * @author Twelvee
 */
@Plugin(id = "sidecart", name = "[GS] Cart", version = "1.2")
public class GSCart {
	public static GSCart instance;
        private DataBase db;
	public HashMap <Player, Long>blockBroken = new HashMap<Player, Long>();
        public HashMap <Player, Long>blockPlaced = new HashMap<Player, Long>();
        public HashMap <Player, HashMap>items_crafted = new HashMap<Player, HashMap>();
        public long lastsecond = System.currentTimeMillis()/1000;
	@Inject
	Game game;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private File configFile;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;

	@Inject
	private Logger logger;


	public File getConfigFile() {
		return configFile;
	}

	public ConfigurationLoader<CommentedConfigurationNode> getConfigManager() {
		return configManager;
	}

	public Logger getLogger() {
		return logger;
	}
        
        public DataBase getDb(){
            return db;
        }

	@Listener
	public void onPreInit(GamePreInitializationEvent event) {
	    instance = this;
	    logger.info("Loading GameSide Stats plugin...");
	}
	@Listener
	public void onInit(GameInitializationEvent event) {
		ConfigurationManager.getInstance().setup(configFile, configManager);
                
		CommandSpec cartExecutor = CommandSpec.builder().description(Text.of("Загрузить в инветарь"))
				.executor(new CartExecutor())
				.build();

		CommandSpec ItemUpdateExecutor = CommandSpec.builder().description(Text.of("Обновить ID"))
                                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("itemid"))))
				.executor(new ItemUpdateExecutor())
				.build();
                
                Sponge.getCommandManager().register(this, cartExecutor, Lists.newArrayList("cart", "toinv"));
                Sponge.getCommandManager().register(this, ItemUpdateExecutor, Lists.newArrayList("updshop", "обновить"));
                logger.info("[GS] CART LOADED SUCCESSFULLY.");
                game.getEventManager().registerListeners(this, new MainEventClass());
                db = new DataBase();
                db.connect();

        }
        
	
	@Listener
	public void onStart(GameStartedServerEvent event) {
		logger.info("[GS] CART LOADED SUCCESSFULLY 2.");
	}
}
