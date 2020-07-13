package test;

import java.util.*;
import java.util.Map.Entry;

public class TestLinkedHashMap {
    public static void main(String[] args) {
        TestLinkedHashMap test = new TestLinkedHashMap();
        test.testHashMap();
        test.test();
        System.out.println("--------------------------------");
        test.testAccessOrder();

        int a = 1;
        int b = 2;
        int c = 3;
        a = b = c;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }

    private void testHashMap() {
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("name1", "josan1");
        hashMap.put("name2", "josan2");
        hashMap.put("name3", "josan3");
        Set<Entry<String, String>> set = hashMap.entrySet();
        Iterator<Entry<String, String>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.out.println("key:" + key + ",value:" + value);
        }
    }

    private void test() {
        Map<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("name1", "josan1");
        linkedHashMap.put("name2", "josan2");
        linkedHashMap.put("name3", "josan3");
        Set<Entry<String, String>> set = linkedHashMap.entrySet();
        Iterator<Entry<String, String>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.out.println("key:" + key + ",value:" + value);
        }
    }

    private void testAccessOrder() {
        // 第三个参数用于指定accessOrder值
        Map<String, String> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
        linkedHashMap.put("name1", "josan1");
        linkedHashMap.put("name2", "josan2");
        linkedHashMap.put("name3", "josan3");
        System.out.println("开始时顺序：");
        Set<Entry<String, String>> set = linkedHashMap.entrySet();
        Iterator<Entry<String, String>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.out.println("key:" + key + ",value:" + value);
        }
        System.out.println("通过get方法，导致key为name1对应的Entry到表尾");
        linkedHashMap.get("name1");
        Set<Entry<String, String>> set2 = linkedHashMap.entrySet();
        Iterator<Entry<String, String>> iterator2 = set2.iterator();
        while (iterator2.hasNext()) {
            Entry entry = iterator2.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            System.out.println("key:" + key + ",value:" + value);
        }
    }
}
