wget https://raw.github.com/paulp/sbt-extras/1c1f38e34fcdf/sbt &&
chmod +x sbt &&
./sbt -XX:MaxPermSize=1G -Xmx2G -sbt-version $TRAVIS_SCALA_VERSION scripted

