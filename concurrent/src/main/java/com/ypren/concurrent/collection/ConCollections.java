package com.ypren.concurrent.collection;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConCollections {

   public void testConcurrentHashMap() {
      Map<String, String> map = new ConcurrentHashMap<String, String>();
      map.put("key1", "value1");
      log.info("{}", map);
   }

   public void testSyncList() {
      List<String> syncList = new CopyOnWriteArrayList<String>();
      syncList.add("string");
      log.info("{}", syncList);
   }
}
