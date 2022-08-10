package cn.hsa.filter;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;


/**
 *  使用谷歌hash算法来实现布隆过滤器，参考链接：
 *  <a href="https://www.appblog.cn/2021/04/27/SpringBoot%E5%9C%A8Redis%E4%B8%AD%E4%BD%BF%E7%94%A8BloomFilter%E5%B8%83%E9%9A%86%E8%BF%87%E6%BB%A4%E5%99%A8%E6%9C%BA%E5%88%B6/#%E9%A1%B9%E7%9B%AE%E4%BB%A3%E7%A0%81">...</a>
 * @author luonianxin
 * @version 1.0
 * @date 2022/8/8 16:16
 */
public class BloomFilterHelper<T> {

    private int numHashFunctions;

    private int bitSize;

    private Funnel<T> funnel;

    /**
     *  初始化布隆过滤器相关参数
     * @param funnel
     * @param expectedInsertions 预期最大数据数量
     * @param fpp 期望误判率
     */
    public BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
        Preconditions.checkArgument(funnel != null, "funnel不能为空");
        this.funnel = funnel;
        // 计算bit数组长度
        bitSize = optimalNumOfBits(expectedInsertions, fpp);
        // 计算hash方法执行次数
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
    }

    public int[] murmurHashOffset(T value) {
        int[] offset = new int[numHashFunctions];

        long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
        int hash1 = (int) hash64;
        int hash2 = (int) (hash64 >>> 32);
        for (int i = 1; i <= numHashFunctions; i++) {
            int nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }

        return offset;
    }

    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            // 设定最小期望长度
            p = Double.MIN_VALUE;
        }
        int sizeOfBitArray = (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
        return sizeOfBitArray;
    }

    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        int countOfHash = Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
        return countOfHash;
    }
}