cd target/
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar csbot-0.1.0-snapshot-shaded.jar
cd ..