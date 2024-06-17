package kr.blugon.sethome

import kr.blugon.sethome.commands.registerCommands
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

class Sethome : JavaPlugin(), Listener {

    companion object {
        lateinit var plugin: JavaPlugin

        val saveFile = File("plugins/Sethome/homes.yml")
        lateinit var homeYaml: YamlConfiguration

        private val homesMap = HashMap<UUID, HashMap<String, Location>>()
        val Player.homes: HashMap<String, Location>
            get() {
                if(homesMap[this.uniqueId] == null) homesMap[this.uniqueId] = HashMap()
                return homesMap[this.uniqueId]!!
            }
        val UUID.homes: HashMap<String, Location>
            get() {
                if(homesMap[this] == null) homesMap[this] = HashMap()
                return homesMap[this]!!
            }

        fun Player.saveHomes() {
            this.uniqueId.saveHomes()
        }
        fun UUID.saveHomes() {
            val uuid = this
            uuid.homes.forEach { (key, value) ->
                homeYaml.set("${uuid}.${key}", value)
            }
            homeYaml.save(saveFile)
        }
    }

    override fun onEnable() {
        plugin = this
        homeYaml = YamlConfiguration.loadConfiguration(saveFile)
        logger.info("Plugin Enable")
        Bukkit.getPluginManager().registerEvents(this, this)
        homeYaml.getKeys(false).forEach { key ->
            val uuid = UUID.fromString(key)
            for(value in homeYaml.getKeys(true)) {
                if(value == uuid.toString()) continue
                if(!value.startsWith(uuid.toString())) continue
                val home = value.split(".")[1]
                uuid.homes[home] = homeYaml.getLocation(value)?: continue
            }
        }

        registerCommands()
    }

    override fun onDisable() {
        homesMap.forEach { (uuid, homes) ->
            uuid.saveHomes()
        }
        logger.info("Plugin disabled")
    }
}