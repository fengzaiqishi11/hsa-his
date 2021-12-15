package tools;

import cn.hutool.core.util.IdcardUtil;
import org.junit.Test;

public class HutoolTest {

    @Test
    public void testIdCardUtil(){
        System.out.println("期待年份1997:是否相等" + (1997 == IdcardUtil.getYearByIdCard("320521194910303410")));
        System.out.println("期待年龄1岁:是否相等" + (1 == IdcardUtil.getAgeByIdCard("410581202011143410")));
        System.out.println("年龄岁：" +IdcardUtil.getAgeByIdCard("335581202012153410"));
        System.out.println("期待性别男:是否相等" + (1 == IdcardUtil.getGenderByIdCard("510113011124212")));
        System.out.println("期待性别女:是否相等" + (0 == IdcardUtil.getGenderByIdCard("500113011124282")));
    }
}
