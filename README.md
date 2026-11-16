# hazelcast

Experiments with a three-node Hazelcast cluster in Java:

- distributed map with concurrent increments — no locking vs. pessimistic vs. optimistic locking
- bounded distributed queue — one producer node, two consumer nodes

The active scenario is selected by (un)commenting blocks in `HazelcastCluster.java`.

## Run

```bash
mvn compile
./run.sh   # starts Node1, Node2, Node3 and prints total execution time
```

Member addresses are hardcoded in `HazelcastCluster.java` — change them to your machine's IP.
