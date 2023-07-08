package com.jyadmin.util;

import cn.hutool.core.map.MapUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据工具类
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2023-06-30 09:27 <br>
 * @description: DataUtil <br>
 */
public class DataUtil {

    /**
     * 根据Keys 删除 map里面的项
     */
    public static Map<String, Object> removeItemForMap(Map<String, Object> map, String...keys) {
        if (MapUtil.isEmpty(map)) return map;
        for (String key : keys) {
            if (map.containsKey(key)) map.remove(key);
        }
        return map;
    }

    /**
     * 根据 key 获取 map内的值，如果为空则返回默认值
     */
    public static <K, V> V getValueForMap(Map<K, V> map, K key, V defaultValue) {
        if (MapUtil.isEmpty(map)) return null;
        V v = map.get(key);
        if (Objects.isNull(v)) return defaultValue;
        return v;
    }

    /**
     * 根据 key 获取 map内的值并转换为String，如果为空则返回null
     */
    public static <K, V> String getValueForMap(Map<K, V> map, K key) {
        V value = getValueForMap(map, key, null);
        if (Objects.isNull(value)) return null;
        return String.valueOf(value);
    }

    /**
     * 将String类型的ID集合转换为Long
     */
    public static Set<Long> convertToLongForSet(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return new HashSet<>();
        return ids.stream().map(Long::parseLong).collect(Collectors.toSet());
    }

}
