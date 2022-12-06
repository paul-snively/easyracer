enablePlugins(GraalVMNativeImagePlugin)

name := "easyracer-server"

scalaVersion := "3.2.1"

fork := true

val zioVersion = "2.0.5"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio"              % zioVersion,
  "dev.zio" %% "zio-concurrent"   % zioVersion,

  "dev.zio" %% "zio-http"         % "0.0.3",

  "dev.zio" %% "zio-test"         % zioVersion % Test,

  "dev.zio" %% "zio-test-sbt"     % zioVersion % Test,

  "dev.zio" %% "zio-http-testkit" % "0.0.3"    % Test,
)

testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

Compile / packageDoc / publishArtifact := false

Compile / doc / sources := Seq.empty

graalVMNativeImageOptions ++= Seq(
  "--static",
  "--no-fallback",
  "--install-exit-handlers",

  "--initialize-at-run-time=io.netty.channel.DefaultFileRegion",
  "--initialize-at-run-time=io.netty.channel.epoll.Native",
  "--initialize-at-run-time=io.netty.channel.epoll.Epoll",
  "--initialize-at-run-time=io.netty.channel.epoll.EpollEventLoop",
  "--initialize-at-run-time=io.netty.channel.epoll.EpollEventArray",
  "--initialize-at-run-time=io.netty.channel.kqueue.KQueue",
  "--initialize-at-run-time=io.netty.channel.kqueue.KQueueEventLoop",
  "--initialize-at-run-time=io.netty.channel.kqueue.KQueueEventArray",
  "--initialize-at-run-time=io.netty.channel.kqueue.Native",
  "--initialize-at-run-time=io.netty.channel.unix.Limits",
  "--initialize-at-run-time=io.netty.channel.unix.Errors",
  "--initialize-at-run-time=io.netty.channel.unix.IovArray",
  "--initialize-at-run-time=io.netty.handler.codec.compression.ZstdOptions",
  "--initialize-at-run-time=io.netty.handler.ssl.BouncyCastleAlpnSslUtils",

  //"--enable-http",
  //"--enable-https",
  //"--allow-incomplete-classpath", // for netty
  //"--verbose",
  "--libc=musl",
  //"--report-unsupported-elements-at-runtime",
  "-H:+ReportExceptionStackTraces",
)

//fork := true

//run / javaOptions += s"-agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image"
//javaOptions += s"-agentlib:native-image-agent=trace-output=${(target in GraalVMNativeImage).value}/trace-output.json"