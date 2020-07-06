package dev.shawngarner.minecraft.plugins.buildahouse

import java.util.logging.Logger

import org.bukkit.block.Block
import org.bukkit.block.data.Bisected.Half
import org.bukkit.{Location, Material}
import org.bukkit.command.{Command, CommandSender}
import org.bukkit.craftbukkit.v1_16_R1.block.impl.CraftDoor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

object BuildAHouse {
  var origin: Option[Location] = None
  var firstHouse = true

  def buildMyHouse(inputWidth: Int, inputHeight: Int): Unit = {
    // Floor the dimensions at no less than 5 X 5
    val width = if (inputWidth < 5) 5 else inputWidth
    val height = if (inputHeight < 5) 5 else inputHeight

    origin.foreach { origin_ =>
      if (firstHouse) {
        // Center the first house on the player
        origin_.setY(origin_.getY() - 1);
        origin_.setZ(origin_.getZ() - (width/2));
        origin_.setX(origin_.getX() - (width/2));
        firstHouse = false;
      } else {
        // Move over to make next house if called in a loop.
        origin_.setX(origin_.getX() + width);
      }

      // Set the whole area to wood
      makeCube(0,0,0,width, height, Material.OAK_WOOD);
      // Set the inside of the cube to air
      makeCube(1,1,1,width-2, height-2, Material.AIR);

      // Pop a door in one wall
      val door = new Location(origin_.getWorld(),
        origin_.getX()+(width/2), origin_.getY(), origin_.getZ());

      // The door is two high, with a torch over the door
      door.setY(door.getY() +1);
      val bottom: Block = origin_.getWorld().getBlockAt(door);
      door.setY(door.getY() +1);
      val top: Block = origin_.getWorld().getBlockAt(door);
      door.setY(door.getY() +1);
      door.setZ(door.getZ() +1);
      val over: Block = origin_.getWorld().getBlockAt(door);


      val topData = new CraftDoor()
      topData.setHalf(Half.TOP)
      top.setBlockData(topData)

      val bottomData = new CraftDoor()
      bottomData.setHalf(Half.BOTTOM)
      bottom.setBlockData(bottomData)

      // And normal material constants
      top.setType(Material.OAK_DOOR);
      bottom.setType(Material.OAK_DOOR);
      over.setType(Material.TORCH);

    }

  }

  // Create a 3D cube, offset from the saved "origin"
  def makeCube(offsetX: Int, offsetY: Int, offsetZ: Int,
    width: Int, height: Int, what: Material): Unit = {

    // Base is i X j, k goes up for height
    (0 until width).foreach { i: Int =>
      (0 until width).foreach { j: Int =>
        (0 until height).foreach { k: Int =>
          origin.foreach { origin_ =>
            val world = origin_.getWorld
            val xLoc: Int = (origin_.getX + offsetX + i).toInt
            val yLoc: Int = (origin_.getY + offsetY + k).toInt
            val zLoc: Int = (origin_.getZ + offsetZ + j).toInt
            val block = world.getBlockAt(
              xLoc,
              yLoc,
              zLoc
            )
            block.setType(what)
          }
        }
      }
    }
  }

  object MyHouse {
    val width: Int = 10
    val height: Int = 10

    def build_me(): Unit = {
      buildMyHouse(width, height)
    }
  }
}

final class BuildAHouse extends JavaPlugin {
  val log = Logger.getLogger("Minecraft")

  override def onEnable(): Unit = log.info("[BuildAHouse] Start up.")
  override def onLoad(): Unit = log.info("[BuildAHouse] Server loaded.")
  override def onDisable(): Unit = log.info("[BuildAHouse] Server stopping.")

  override def onCommand(sender: CommandSender, command: Command, commandLabel: String, args: Array[String]): Boolean = {
    if("buildahouse".equalsIgnoreCase(commandLabel)) {
      sender match {
        case me: Player =>
          BuildAHouse.origin = Some(me.getLocation)
          BuildAHouse.firstHouse = true
          BuildAHouse.MyHouse.build_me()
          true
        case _ => false
      }
    } else {
      false
    }

  }
}

