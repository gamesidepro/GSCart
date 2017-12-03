package gscart;

import java.io.File;
import java.io.IOException;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

/**
 *
 * @author Twelvee
 */
public class ConfigurationManager {
	private static ConfigurationManager instance = new ConfigurationManager();
	
	public static ConfigurationManager getInstance() {
		return instance;
	}
	
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;
	private CommentedConfigurationNode config;
	
	public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
		this.configLoader = configLoader;
		
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
				loadConfig();		
				
				config.getNode("host").setValue("localhost");
				config.getNode("login").setValue("root");
				config.getNode("password").setValue("");
				config.getNode("shop_table").setValue("gs_shop");
				//config.getNode("table_achives").setValue("gs_achives_ht");
                                //config.getNode("json_achives").setValue("http://gameside.pro/HTAchives.json");
				
				saveConfig();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			loadConfig();
		}
	}
	
	public CommentedConfigurationNode getConfig() {
		return config;
	}
	
	public ConfigurationLoader<CommentedConfigurationNode> getConfigLoader() {
		return configLoader;
	}
	
	public void saveConfig() {
		try {
			configLoader.save(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		try {
			config = configLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
