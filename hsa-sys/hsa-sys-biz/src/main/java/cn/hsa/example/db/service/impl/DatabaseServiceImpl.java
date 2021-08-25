package cn.hsa.example.db.service.impl;

import cn.hsa.example.db.service.DatabaseService;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

//    @Autowired
//    private UserBO userBO;

    @Override
    public String selectTime() {
//        return userBO.selectTime();
        return "";
    }
}
