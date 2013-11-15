name := "sbt-pom-reader"

organization := "com.typesafe.sbt"

sbtPlugin := true

publishMavenStyle := false

libraryDependencies ++= Dependencies.pluginDependencies

git.baseVersion := "1.0"

versionWithGit

initialCommands :=
  """| import com.typesafe.sbt.pom._
     | import sbt._
     | val localRepo = file(sys.props("user.home")) / ".m2" / "repository"
     | val pom = loadEffectivePom(localRepo, file("src/sbt-test/simple-pom/can-extract-basics/pom.xml"))
     |""".stripMargin


scriptedSettings

scriptedLaunchOpts <+= version apply { v => "-Dproject.version="+v }

scriptedLaunchOpts ++= sys.process.javaVmArguments.filter(
  a => Seq("-Xmx", "-Xms", "-XX").exists(a.startsWith)
)

publishTo <<= (version) { v =>
  def scalasbt(repo: String) = ("scalasbt " + repo, "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-" + repo)
  val (name, repo) = if (v.endsWith("-SNAPSHOT")) scalasbt("snapshots") else scalasbt("releases")
  Some(Resolver.url(name, url(repo))(Resolver.ivyStylePatterns))
}
