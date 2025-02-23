package org.example;

import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.QueueConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.Objects;

public class HazelcastCluster {
    public static void main(String[] args) throws InterruptedException {
        String nodeName = args[0];
        Config config = new Config();
        config.setClusterName("q");

        QueueConfig queueConfig = config.getQueueConfig("boundedQueue");
        queueConfig.setMaxSize(10);

        JoinConfig joinConfig = config.getNetworkConfig().getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();
        tcpIpConfig.setEnabled(true)
                .addMember("10.10.229.153:5713")
                .addMember("10.10.229.153:5714")
                .addMember("10.10.229.153:5715");

        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);

        // map creation

//        IMap<Integer, Integer> map = hz.getMap("distributed-map");
//        config.getMapConfig("distributed-map").setBackupCount(3);
//        if (Objects.equals(nodeName, "node1")) {
//            for (int k = 0; k < 1000; ++k) {
//                map.put(k, k);
//            }
//        }


        // distributed map without locks
//
//        IMap<String, Integer> map = hz.getMap("test_30_000");
//
//        map.putIfAbsent("key", 0);
//        for (int k = 0; k < 10_000; k++) {
//            var value = map.get("key");
//            value++;
//            map.put("key", value);
//        }

        // pessimistic lock
//
//        IMap<String, Integer> map = hz.getMap("test_30_000");
//
//        map.putIfAbsent("key", 0);
//        for (int k = 0; k < 10_000; k++) {
//            map.lock("key");
//            try {
//                Integer value = map.get("key");
//                value++;
//                map.put("key", value);
//            } finally {
//                map.unlock("key");
//            }
//        }

        // optimistic lock

//        IMap<String, Integer> map = hz.getMap("test_30_000");
//        map.putIfAbsent("key", 0);
//        for (int k = 0; k < 10_000; k++) {
//            while (true) {
//                Integer oldValue = map.get("key");
//                Integer newValue = oldValue + 1;
//                Thread.sleep(10);
//                if (map.replace("key", oldValue, newValue)) {
//                    break;
//                }
//            }
//        }

        // queue task

        IQueue<Integer> queue = hz.getQueue("boundedQueue");

        if (Objects.equals(nodeName, "Node1")) {
            for (int i = 1; i <= 100; i++) {
                queue.put(i);
                System.out.println("Put: " + i + " in the queue");
            }
        } else {
            while (true) {
                Integer value = queue.take();
                System.out.println("Consumed: " + value + " from node = " + nodeName);
                if (value == 100) {
                    break;
                }
            }
        }
    }
}
