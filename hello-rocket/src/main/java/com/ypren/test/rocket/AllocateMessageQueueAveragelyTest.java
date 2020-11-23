package com.ypren.test.rocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.rocketmq.common.message.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllocateMessageQueueAveragelyTest {
    private static final Logger logger = LoggerFactory.getLogger(AllocateMessageQueueAveragelyTest.class);

    public static void main(String[] args) {
        testAllocate();
    }

    private static void testAllocate() {
        String topic = "topic_1";
        String broker = "broker_1";
        String currentCID = "consumer2";
        List<MessageQueue> mqAll = Arrays.asList(new MessageQueue(topic, broker, 1),
                new MessageQueue(topic, broker, 2),
                new MessageQueue(topic, broker, 3));
        List<String> cidAll = Arrays.asList("consumer1", "consumer2");
        List<MessageQueue> messageQueues = allocate("group1", currentCID, mqAll, cidAll);
        logger.info("{}", messageQueues);
    }

    public static List<MessageQueue> allocate(String consumerGroup, String currentCID,
                                        List<MessageQueue> mqAll,
                                       List<String> cidAll) {
        if (currentCID == null || currentCID.length() < 1) {
            throw new IllegalArgumentException("currentCID is empty");
        }
        if (mqAll == null || mqAll.isEmpty()) {
            throw new IllegalArgumentException("mqAll is null or mqAll empty");
        }
        if (cidAll == null || cidAll.isEmpty()) {
            throw new IllegalArgumentException("cidAll is null or cidAll empty");
        }

        List<MessageQueue> result = new ArrayList<MessageQueue>();
        if (!cidAll.contains(currentCID)) {
            logger.info("[BUG] ConsumerGroup: {} The consumerId: {} not in cidAll: {}",
                    consumerGroup,
                    currentCID,
                    cidAll);
            return result;
        }

        int index = cidAll.indexOf(currentCID);
        int mod = mqAll.size() % cidAll.size();
        int averageSize =
                mqAll.size() <= cidAll.size() ? 1 : (mod > 0 && index < mod ? mqAll.size() / cidAll.size()
                        + 1 : mqAll.size() / cidAll.size());
        int startIndex = (mod > 0 && index < mod) ? index * averageSize : index * averageSize + mod;
        int range = Math.min(averageSize, mqAll.size() - startIndex);
        for (int i = 0; i < range; i++) {
            int tmp = (startIndex + i) % mqAll.size();
            result.add(mqAll.get(tmp));
        }
        return result;
    }
}
