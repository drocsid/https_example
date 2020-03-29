sbt clean +assembly && native-image --initialize-at-build-time --enable-https --static --verbose -jar ./target/scala-2.13/weatherdemo-assembly-0.0.1-SNAPSHOT.jar weatherdemo

# --enable-https or --enable-url-protocols=https https://github.com/oracle/graal/issues/390
