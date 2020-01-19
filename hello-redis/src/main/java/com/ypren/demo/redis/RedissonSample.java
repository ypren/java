package com.ypren.demo.redis;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedissonSample {

    private static final Logger logger = LoggerFactory.getLogger(RedissonSample.class);

    private static RedissonClient client;

    public static void main(String[] args) throws InterruptedException {
        // 1. Create config object
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        // 2. Create Redisson instance
        client = Redisson.create(config);

//       testTtl();

//        testIfGetMapIsNull();

//        testReadAllMap();

//        testUpload();

        testRHScan();

        client.shutdown();
    }

    public static void testUpload() {
        Map<Object, Object> map = client.getMap("METADATA");
        Map<Object, Object> mapAddressVO = client.getMap("ADDRESS_VO");
        System.out.println(mapAddressVO.size());
        System.out.println(map.size());
    }

    private static void testTtl() throws InterruptedException {
        // 3. Get Redis based Map
        RMap<String, String> map = client.getMap("test");
        logger.info("init map: {}", map);
//        map.put("1","2");

        map.expire(5, TimeUnit.SECONDS);

        while (null != map.get("1")) {
            String v = map.get("1");
            System.out.println(v);
            Thread.sleep(2 * 1000);
        }
    }

    private static void testIfGetMapIsNull() {
        RMap<String, String> map = client.getMap("test-get-element-from-map");
        logger.info("map: {}", map);
        logger.info("get object from map: {}", map.get("a"));
    }

    private static void testReadAllMap() {
        RMap<String, String> writeMap = client.getMap("test-read-all-map");
        writeMap.put("k-1", "v-1");
        writeMap.put("k-2", "v-2");

        Map<Object, Object> readMap = client.getMap("test-read-all-map").readAllMap();
        System.out.println(readMap);
    }

    private static void testRHScan() {
        Set<Map.Entry<Object, Object>> entries = client.getMap("test-read-all-map").entrySet();
//        Iterator<Map.Entry<Object, Object>> iterator = entries.iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Object, Object> entry =  iterator.next();
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }

        entries.forEach(objectObjectEntry -> {
            System.out.println(objectObjectEntry.getKey() + ":" + objectObjectEntry.getValue());
        });
    }
}
