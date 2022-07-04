//package cn.hsa.example.id;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.Enumeration;
//import java.util.Random;
//
//public class Snowflake {
//    private final static long TWEPOCH = 1288834974657L;
//
//    // 机器标识位数
//    private final static long WORKER_ID_BITS = 5L;
//
//    // 数据中心标识位数
//    private final static long DATA_CENTER_ID_BITS = 5L;
//
//    // 机器ID最大值 31
//    private final static long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
//
//    // 数据中心ID最大值 31
//    private final static long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);
//
//    // 毫秒内自增位
//    private final static long SEQUENCE_BITS = 12L;
//
//    // 机器ID偏左移12位
//    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
//
//    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
//
//    // 时间毫秒左移22位
//    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
//
//    private final static long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);
//
//    private long lastTimestamp = -1L;
//
//    private long sequence = 0L;
//    private final long workerId;
//    private final long dataCenterId;
//    private static volatile Snowflake snowflake = null;
//    private static Object lock = new Object();
//
//    //单例禁止new实例化
//    private Snowflake(long workerId, long dataCenterId) {
//        if (workerId > MAX_WORKER_ID || workerId < 0) {
//            workerId = getRandom();
//        }
//
//        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
//
//            throw new IllegalArgumentException(String.format("%s 数据中心ID最大值 必须是 %d 到 %d 之间", dataCenterId, 0, MAX_DATA_CENTER_ID));
//        }
//        this.workerId = workerId;
//        this.dataCenterId = dataCenterId;
//    }
//
//    /**
//     * 获取单列
//     *
//     * @return
//     */
//    public static Snowflake getInstanceSnowflake() {
//        if (snowflake == null) {
//            synchronized (lock) {
//                long workerId ;
//                long dataCenterId = getRandom();
//                try {
//                    //第一次使用获取mac地址的
//                    workerId = getWorkerId();
//                } catch (Exception e) {
//                    workerId = getRandom();
//                }
//                snowflake = new Snowflake(workerId, dataCenterId);
//            }
//        }
//        return snowflake;
//    }
//
//    /**
//     * 生成1-31之间的随机数
//     *
//     * @return
//     */
//    private static long getRandom() {
//        int max = (int) (MAX_WORKER_ID);
//        int min = 1;
//        Random random = new Random();
//        long result = random.nextInt(max - min) + min;
//        return result;
//    }
//
//    public static String getSnowflakeId() throws Exception{
//        return Snowflake.getInstanceSnowflake().nextId()+"";
//    }
//
//    private synchronized long nextId() throws Exception {
//        long timestamp = time();
//        if (timestamp < lastTimestamp) {
//            throw new Exception("时钟向后移动，拒绝生成id  " + (lastTimestamp - timestamp) + " milliseconds");
//        }
//
//        if (lastTimestamp == timestamp) {
//            // 当前毫秒内，则+1
//            sequence = (sequence + 1) & SEQUENCE_MASK;
//            if (sequence == 0) {
//                // 当前毫秒内计数满了，则等待下一秒
//                timestamp = tilNextMillis(lastTimestamp);
//            }
//        } else {
//            sequence = 0;
//        }
//        lastTimestamp = timestamp;
//
//        // ID偏移组合生成最终的ID，并返回ID
//        long nextId = ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
//                | (dataCenterId << DATA_CENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
//
//        return nextId;
//    }
//
//    private long tilNextMillis(final long lastTimestamp) {
//        long timestamp = this.time();
//        while (timestamp <= lastTimestamp) {
//            timestamp = this.time();
//        }
//        return timestamp;
//    }
//
//    private long time() {
//        return System.currentTimeMillis();
//    }
//
//    @SuppressWarnings("Duplicates")
//    private static long getWorkerId() throws SocketException, UnknownHostException, NullPointerException {
//        @SuppressWarnings("unused")
//        InetAddress ip = InetAddress.getLocalHost();
//
//        NetworkInterface network = null;
//        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
//        while (en.hasMoreElements()) {
//            NetworkInterface nint = en.nextElement();
//            if (!nint.isLoopback() && nint.getHardwareAddress() != null) {
//                network = nint;
//                break;
//            }
//        }
//
//        @SuppressWarnings("ConstantConditions")
//        byte[] mac = network.getHardwareAddress();
//
//        Random rnd = new Random();
//        byte rndByte = (byte) (rnd.nextInt() & 0x000000FF);
//
//        // 取mac地址最后一位和随机数
//        return ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) rndByte) << 8))) >> 6;
//    }
//
//
//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        try {
//            for (int i = 0, len = 100000; i < len; i++) {
//                //getSnowflakeId();
//                System.out.println(getSnowflakeId());
//            }
//        } catch (Exception e) {
//
//        }
//        System.out.println("10万耗时: " + (System.currentTimeMillis()-start) + "ms");
//
//    }
//
//}
