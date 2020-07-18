package dev.shawngarner.minecraft.plugins.simple

import java.util.logging.Logger

import org.bukkit.command.{Command, CommandSender}
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Sound

object Simple {
  val myName = "Shawn Garner"
  val myAge = 99
  val twiceMyAge = myAge * 2
  val volume = 0.1f
  val pitch = 1.0f
  val dayOnIo = 152853d

  def messagePlayer(p: Player): Unit = {
    val playerLocation = p.getLocation
    p.sendMessage("My name is " + myName)
    p.sendMessage("My age is " + myAge)
    p.sendMessage("Twice my age is " + twiceMyAge)
    p.sendMessage("Seconds in a day on Jupiter's Moon IO " + dayOnIo)
    p.playSound(playerLocation, Sound.ENTITY_GHAST_SCREAM, volume, pitch)
  }
}

import Simple._
final class Simple extends JavaPlugin {
  val log = Logger.getLogger("Minecraft")

  override def onLoad(): Unit = log.info("[Simple] Loaded.")

  override def onEnable(): Unit = log.info("[Simple] Enabled")

  override def onDisable(): Unit = log.info("[Simple] Disabled.")

  override def onCommand(sender: CommandSender, command: Command, commandLabel: String, args: Array[String]): Boolean = {
    if("simple".equalsIgnoreCase(commandLabel)) {
      sender match {
        case me: Player =>
          messagePlayer(me)
          true
        case _ => false
      }
    } else {
      false
    }
  }
}

