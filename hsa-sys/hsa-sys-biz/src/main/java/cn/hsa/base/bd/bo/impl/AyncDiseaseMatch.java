package cn.hsa.base.bd.bo.impl;

import cn.hsa.module.base.bd.dao.BaseDiseaseDAO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yuelong.chen
 * @version 2.0.0
 * @ClassName AyncDiseaseMatch.java
 * @Description TODO
 * @createTime 2022年05月13日 21:52:00
 */
@Component
@Slf4j
public class AyncDiseaseMatch {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 疾病管理数据库访问接口
     */
    @Resource
    private BaseDiseaseDAO baseDiseaseDAO;

    @Async
    public void executeAsyncTask( List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList,String hospCode) {
        try {
            logger.info("线程" + Thread.currentThread().getName() + " 执行异步任务：" + insureDiseaseMatchDTOList.size());
            baseDiseaseDAO.updateDiseaseMatch(insureDiseaseMatchDTOList,hospCode);
        }catch (Exception ee){
            throw ee;
        }

    }
}
