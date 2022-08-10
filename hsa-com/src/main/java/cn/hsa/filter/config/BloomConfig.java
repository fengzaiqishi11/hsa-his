package cn.hsa.filter.config;

import cn.hsa.filter.BloomFilterHelper;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 *  布隆过滤器配置类
 * @author luonianxin
 * @version 1.0
 * @date 2022/8/8 16:29
 */
@Configuration
public class BloomConfig {

   @Bean
   public BloomFilterHelper<String> initBloomFilterHelper() {
      return new BloomFilterHelper<>((Funnel<String>) (from, into) -> into.putString(from, Charsets.UTF_8).putString(from, StandardCharsets.UTF_8),1000000,0.01);
   }
}
