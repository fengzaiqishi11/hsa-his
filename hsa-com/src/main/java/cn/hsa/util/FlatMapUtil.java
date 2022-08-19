package cn.hsa.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author luonianxin
 * @version 1.0
 * @date 2022/8/16 14:06
 */
public class FlatMapUtil {
    private FlatMapUtil() {
        throw new AssertionError("No instances for you!");
    }

    public static Map<String, Object> flatten(Map<String, Object> map) {
        return map.entrySet().stream()
                .flatMap(FlatMapUtil::flatten)
                .collect(LinkedHashMap::new, (m, e) -> m.put("/" + e.getKey(), e.getValue()), LinkedHashMap::putAll);
    }

    private static Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {

        if (entry == null) {
            return Stream.empty();
        }

        if (entry.getValue() instanceof Map<?, ?>) {
            return ((Map<?, ?>) entry.getValue()).entrySet().stream()
                    .flatMap(e -> flatten(new AbstractMap.SimpleEntry<>(entry.getKey() + "/" + e.getKey(), e.getValue())));
        }

        if (entry.getValue() instanceof List<?>) {
            List<?> list = (List<?>) entry.getValue();
            return IntStream.range(0, list.size())
                    .mapToObj(i -> new AbstractMap.SimpleEntry<String, Object>(entry.getKey() + "/" + i, list.get(i)))
                    .flatMap(FlatMapUtil::flatten);
        }

        return Stream.of(entry);
    }


    public static void main(String[] args) throws Exception{
        ObjectMapper mapper = new ObjectMapper();

        TypeReference<HashMap<String, Object>> type =
                new TypeReference<HashMap<String, Object>>() {};


        String  listN1 = "{\n" +
                "  \"total\": 1,\n" +
                "  \"count\": null,\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"id\": \"1457963289150578688\",\n" +
                "      \"hospCode\": \"1000001\",\n" +
                "      \"outCode\": \"10\",\n" +
                "      \"orderNo\": \"CK202111090001\",\n" +
                "      \"outStockId\": \"1353946199978094613\",\n" +
                "      \"inStockId\": \"1409432119265861632\",\n" +
                "      \"buyPriceAll\": 6.48,\n" +
                "      \"sellPriceAll\": 10,\n" +
                "      \"remark\": \"\",\n" +
                "      \"auditCode\": \"1\",\n" +
                "      \"auditId\": \"1290816158941450240\",\n" +
                "      \"auditName\": \"管理员\",\n" +
                "      \"auditTime\": 1640243832000,\n" +
                "      \"okAuditCode\": \"0\",\n" +
                "      \"okAuditId\": null,\n" +
                "      \"okAuditName\": null,\n" +
                "      \"okAuditTime\": null,\n" +
                "      \"crteId\": \"1290816158941450240\",\n" +
                "      \"crteName\": \"管理员\",\n" +
                "      \"crteTime\": 1636440511000,\n" +
                "      \"keyword\": null,\n" +
                "      \"startDate\": null,\n" +
                "      \"endDate\": null,\n" +
                "      \"ids\": null,\n" +
                "      \"locationNo\": null,\n" +
                "      \"name\": null,\n" +
                "      \"orderNos\": null,\n" +
                "      \"flag\": null,\n" +
                "      \"outDeptName\": \"西药房2\",\n" +
                "      \"djlxName\": \"同级调拨(库房)\",\n" +
                "      \"outId\": null,\n" +
                "      \"type\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sumData\": null\n" +
                "}";
        HashMap<String, Object> j1 = mapper.readValue(listN1,type);
        String  listN2 = "{\n" +
                "  \"total\": 1,\n" +
                "  \"count\": null,\n" +
                "  \"result\": [\n" +
                "    {\n" +
                "      \"id\": \"1457963289150578688\",\n" +
                "      \"hospCode\": \"1000001\",\n" +
                "      \"outCode\": \"10\",\n" +
                "      \"orderNo\": \"CK202111090001\",\n" +
                "      \"outStockId\": \"1353946199978094613\",\n" +
                "      \"inStockId\": \"1409432119265861632\",\n" +
                "      \"buyPriceAll\": 8.48,\n" +
                "      \"sellPriceAll\": 90,\n" +
                "      \"remark\": \"\",\n" +
                "      \"auditCode\": \"1\",\n" +
                "      \"auditId\": \"1290816158941450240\",\n" +
                "      \"auditName\": \"管理员\",\n" +
                "      \"auditTime\": 1640243832000,\n" +
                "      \"okAuditCode\": \"0\",\n" +
                "      \"okAuditId\": null,\n" +
                "      \"okAuditName\": null,\n" +
                "      \"okAuditTime\": null,\n" +
                "      \"crteId\": \"1290816158941450240\",\n" +
                "      \"crteName\": \"管理员\",\n" +
                "      \"crteTime\": 1636440511000,\n" +
                "      \"keyword\": null,\n" +
                "      \"startDate\": null,\n" +
                "      \"endDate\": null,\n" +
                "      \"ids\": null,\n" +
                "      \"locationNo\": null,\n" +
                "      \"name\": null,\n" +
                "      \"orderNos\": null,\n" +
                "      \"flag\": null,\n" +
                "      \"outDeptName\": \"西药房2\",\n" +
                "      \"djlxName\": \"同级调拨(库房)\",\n" +
                "      \"outId\": null,\n" +
                "      \"type\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sumData\": 100\n" +
                "}";
        HashMap<String, Object> j2 = mapper.readValue(listN2,type);

        Map<String, Object> flatten1 = FlatMapUtil.flatten(j1);
        Map<String, Object> flatten2 = FlatMapUtil.flatten(j2);
        MapDifference<String,Object> difference = Maps.difference(flatten1,flatten2);
        System.out.println(difference);
    }
}