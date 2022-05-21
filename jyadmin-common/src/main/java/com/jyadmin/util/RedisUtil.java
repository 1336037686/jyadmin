package com.jyadmin.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * 引自：https://juejin.cn/post/6844904030557356045#heading-5
 * @author LGX_TvT <br>
 * @version 1.0 <br>
 * Create by 2022-05-04 03:03 <br>
 * @description: RedisUtil <br>
 */
@Slf4j
@Component
public class RedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * key 是否存在
     *
     * @param key
     * @return
     */
    public boolean exists(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Set {@code value} for {@code key}, only if {@code key} does not exist.
     *
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     * @return
     */
    public boolean setNx(String key, String value, long expire, TimeUnit timeUnit) {
        try {
            return (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(key.getBytes(), value.getBytes(), Expiration.from(expire, timeUnit), RedisStringCommands.SetOption.ifAbsent()));
        } catch (Exception e) {
            log.error("设置缓存异常, key = {}", key, e);
        }
        return false;
    }

    /**
     * Set {@code value} for {@code key}, only if {@code key} does not exist.
     *
     * @param key
     * @param value
     * @param expire 有效期.（秒）
     * @return
     */
    public boolean setNx(String key, String value, long expire) {
        try {
            return setNx(key, value, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("设置缓存异常, key = {}", key, e);
        }
        return false;
    }

    /**
     * 递增 +1
     *
     * @param key
     * @return key递增后的值
     */
    public Long incrBy(String key) {
        try {
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return null;
    }

    /**
     * 递增
     *
     * @param key
     * @param delta 步长
     * @return key递增后的值
     */
    public Long incrBy(String key, long delta) {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return null;
    }

    /**
     * 将哈希值{@code hashKey}的{@code value}增加给定的{@code delta}。
     *
     * @param key
     * @param hashKey hashKey
     * @param delta   步长
     * @return key递增后的值
     */
    public Long hashIncrBy(String key, String hashKey, long delta) {
        try {
            return redisTemplate.opsForHash().increment(key, hashKey, delta);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return null;
    }

    /**
     * 为 key 设置 value
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setValue(String key, Serializable value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    /**
     * 设置 key 的 value 和过期时间
     *
     * @param key
     * @param value
     * @param second 到期时间(秒)
     * @return
     */
    public boolean setValue(final String key, final Serializable value, final long second) {
        return setValue(key, value, second, TimeUnit.SECONDS);
    }

    /**
     * 设置 key 的 value 和过期时间
     *
     * @param key
     * @param value
     * @param expire 到期时间
     * @param unit   到期时间计量单位
     * @return
     */
    public boolean setValue(String key, Serializable value, long expire, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, expire, unit);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }


    /**
     * 获取 key 只
     *
     * @param key
     * @return
     */
    public Object getValue(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return null;
    }

    /**
     * 设置 key 的 value 和过期时间
     *
     * @param key
     * @param expire 到期时间
     * @param unit   到期时间计量单位
     * @return
     */
    public boolean expire(String key, long expire, TimeUnit unit) {
        try {
            redisTemplate.expire(key, expire, unit);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    /**
     * 设置 key 的 value 和过期时间
     *
     * @param key
     * @param expire 到期时间(秒)
     * @return
     */
    public boolean expire(final String key, final long expire) {
        return expire(key, expire, TimeUnit.SECONDS);
    }

    public Boolean setList(String key, Collection<Serializable> values) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    public <T> Boolean setList(String key, Collection<T> values, long expire) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    public <T> boolean listAppend(final String key, T value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    public Boolean setHash(String key, Map<Object, Object> value) {
        try {
            redisTemplate.opsForHash().putAll(key, value);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    public Boolean setHash(String key, Map<Object, Object> value, long expire) {
        try {
            redisTemplate.opsForHash().putAll(key, value);
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    /**
     * 获取存储在{@code key}的整个哈希。
     *
     * @param key
     * @return
     */
    public Map<Object, Object> getMap(String key) {
        Map<Object, Object> v = null;
        try {
            v = redisTemplate.opsForHash().entries(key);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    /**
     * 从{@code key}处的哈希中获取给定{@code hashKey}的值。
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object getMap(String key, Object hashKey) {
        try {
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return null;
    }

    public List<Serializable> getList(String key) {
        List<Serializable> v = null;
        try {
            v = redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }

    public <T> List<T> getObjectList(String key) {
        List<T> v = null;
        try {
            v = redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return v;
    }


    public Boolean delete(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, key = {}, ex = {}", key, ex);
        }
        return false;
    }

    /**
     * 前缀匹配删除多个key
     *
     * @param keyPrefix 前缀
     * @return
     */
    public Boolean deleteLike(String keyPrefix) {
        try {
            if (StringUtils.isNotEmpty(keyPrefix)) {
                redisTemplate.delete(redisTemplate.keys(keyPrefix + "*"));
            }
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, keyPrefix = {}, ex = {}", keyPrefix, ex);
        }
        return false;
    }

    /**
     * 删除Key的集合
     *
     * @param keys
     */
    public Boolean delete(Set<String> keys) {
        try {
            redisTemplate.delete(keys);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, keys = {}, ex = {}", JSONObject.toJSONString(keys), ex);
        }
        return false;
    }

    /**
     * 删除Key的集合
     *
     * @param keys
     */
    public Boolean delete(String... keys) {
        try {
            redisTemplate.delete(keys);
            return true;
        } catch (Exception ex) {
            log.error("取缓存异常, keys = {}, ex = {}", JSONObject.toJSONString(keys), ex);
        }
        return false;
    }

    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey
     * @param newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey
     * @param newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }


    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }
}
