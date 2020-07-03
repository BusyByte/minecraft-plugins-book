
lazy val pluginsDir = file(scala.sys.env.getOrElse("BUILD_TOOLS", throw new RuntimeException("BUILD_TOOLS Not Found"))) / "plugins"

lazy val root = (project in file("."))
  .settings(
    name := "minecraft-plugins-book",
    publish / skip := true,
    assembleArtifact in packageBin := false,
    assembleArtifact in assemblyPackageScala := false,
    assembleArtifact in assemblyPackageDependency := false,
  )
  .settings(commonSettings)
  .aggregate(`hello-world`)

lazy val `hello-world` = (project in file("modules/hello-world"))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq (
      "org.bukkit" % "craftbukkit" % "1.16.1-R0.1-SNAPSHOT" % Provided
    ),
    assemblyOutputPath in assembly := pluginsDir / "hello-world-plugin.jar"
  )


lazy val commonSettings = Seq(
  scalaVersion := "2.13.3",
  organization     := "dev.shawngarner",
  resolvers += Resolver.mavenLocal
)

