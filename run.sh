#!/bin/bash

start_time=$(date +%s)

mvn exec:java -Dexec.mainClass="org.example.HazelcastCluster" -Dexec.args="Node1" &
pid1=$!
mvn exec:java -Dexec.mainClass="org.example.HazelcastCluster" -Dexec.args="Node2" &
pid2=$!
mvn exec:java -Dexec.mainClass="org.example.HazelcastCluster" -Dexec.args="Node3" &
pid3=$!

wait $pid1 $pid2 $pid3

end_time=$(date +%s)
elapsed=$((end_time - start_time))
echo "Total execution time: ${elapsed} seconds"
