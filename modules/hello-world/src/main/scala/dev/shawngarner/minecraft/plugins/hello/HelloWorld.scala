package dev.shawngarner.minecraft.plugins.hello

import java.util.logging.Logger

import org.bukkit.command.{Command, CommandSender}
import org.bukkit.plugin.java.JavaPlugin

final class HelloWorld extends JavaPlugin {
  val log = Logger.getLogger("Minecraft")

  override def onLoad(): Unit = log.info("[HelloWorld] Loaded.")

  override def onEnable(): Unit = log.info("[HelloWorld] Starting up.")

  override def onCommand(sender: CommandSender, command: Command, commandLabel: String, args: Array[String]): Boolean = {
    if("hello".equalsIgnoreCase(commandLabel)) {
      val msg = "[Server] That'sss a very niccce EVERYTHING you have there..."
      getServer.broadcastMessage(msg)
      true
    } else {
      false
    }
  }
}

