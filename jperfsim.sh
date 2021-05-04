cd /
/Library/Java/JavaVirtualMachines/jdk1.8.0_241.jdk/bin/java
-XX:+UseGIGC
-Xms128m
-Xmx512m
-XX:MaxMetaspaceSize=256Xms
-Xss256k
-XX:MaxDirectMemorySize=Xms128m
-Xlog:gc*
-acking=summary
-Xlog:gc:/data/gc.Xlog
-jar galaxy-upload-0.0.1-SNAPSHOT > test.log & sg-4.2$

-XX:+UnlockDiagnostVmOptions
-XX:+PrintNMTStatistics


