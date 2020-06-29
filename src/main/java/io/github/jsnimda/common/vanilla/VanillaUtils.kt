package io.github.jsnimda.common.vanilla

import io.github.jsnimda.common.util.*
import io.github.jsnimda.common.vanilla.alias.Identifier
import io.github.jsnimda.common.vanilla.alias.Screen
import net.minecraft.util.Util
import java.io.File
import java.nio.file.Path

val Path.loggingPath
  get() = VanillaUtils.loggingString(this)

object VanillaUtils {

  fun closeScreen() = Vanilla.mc().openScreen(null)
  fun openScreen(screen: Screen) = Vanilla.mc().openScreen(screen)
  fun openScreenNullable(screen: Screen?) = Vanilla.mc().openScreen(screen)
  fun openDistinctScreen(screen: Screen) { // do nothing if screen is same type as current
    if (Vanilla.screen()?.javaClass != screen.javaClass) openScreen(screen)
  }

  fun openDistinctScreenQuiet(screen: Screen) { // don't trigger Screen.remove()
    Vanilla.mc().currentScreen = null
    openDistinctScreen(screen)
  }

  private fun runDirectory(): Path = Vanilla.runDirectoryFile().toPath().normalize()
  private fun configDirectory(): Path = runDirectory() / "config"
  fun configDirectory(modName: String): Path = (configDirectory() / modName).apply { createDirectories() }

  fun getResourceAsString(identifier: String): String? = tryCatch {
    Vanilla.resourceManager().getResource(Identifier(identifier)).inputStream?.readToString()
  }

  fun languageCode(): String = Vanilla.languageManager().language.code

  fun loggingString(path: Path): String = // return ".minecraft/config/file.txt" etc
    (if (path.isAbsolute) path pathFrom (runDirectory() / "..") else path).toString()

  fun open(file: File) {
    // ResourcePackOptionsScreen.init()
    Util.getOperatingSystem().open(file)
  }
}