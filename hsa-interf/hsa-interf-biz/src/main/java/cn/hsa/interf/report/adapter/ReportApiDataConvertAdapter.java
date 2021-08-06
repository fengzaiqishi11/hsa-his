package cn.hsa.interf.report.adapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.jmreport.desreport.render.handler.convert.ApiDataConvertAdapter;
import org.springframework.stereotype.Component;

/**
 *  报表接口数据格式统一转换器
 */
@Component("reportApiDataConvertAdapter")
public class ReportApiDataConvertAdapter implements ApiDataConvertAdapter {
    /**
     * 返回list数据集，转换成积木报表需要格式{}，没有嵌套
     * 注意：需要json格式，不用data包裹起来了
     * @param jsonObject 接口数据原始对象
     * @return
     */
    @Override
    public String getData(JSONObject jsonObject) {
        if(jsonObject.containsKey("pagelist")) {
            JSONArray pageList = jsonObject.getJSONArray("pagelist");
            JSONArray array = new JSONArray();
            for (int i = 0; i < pageList.size(); i++) {
                JSONObject object = new JSONObject();
                String name = pageList.getJSONObject(i).getString("name");
                String id = pageList.getJSONObject(i).getString("id");
                String zhicheng = pageList.getJSONObject(i).getString("zhicheng");
                JSONArray fuze = pageList.getJSONObject(i).getJSONArray("fuze");
                for (int j = 0; j < fuze.size(); j++) {
                    String banji = fuze.getJSONObject(j).getString("banji");
                    String xueke = fuze.getJSONObject(j).getString("xueke");
                    object.put("name", name);
                    object.put("id", id);
                    object.put("zhicheng", zhicheng);
                    object.put("banji", banji);
                    object.put("xueke", xueke);
                    array.add(object);
                }
            }
        }
        return null;
    }

    /**
     * 返回links
     * @param jsonObject 接口数据原始对象
     * @return
     */
    @Override
    public String getLinks(JSONObject jsonObject) {
        return null;
    }

    /**
     * 返回总页数
     * @param jsonObject 接口数据原始对象
     * @return
     */
    @Override
    public String getTotal(JSONObject jsonObject) {
        return null;
    }

    /**
     * 返回总条数
     * @param jsonObject 接口数据原始对象
     * @return
     */
    @Override
    public String getCount(JSONObject jsonObject) {
        return null;
    }
}
