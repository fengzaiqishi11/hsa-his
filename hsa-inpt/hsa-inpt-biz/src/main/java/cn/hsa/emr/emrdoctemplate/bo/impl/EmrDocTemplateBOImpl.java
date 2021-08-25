package cn.hsa.emr.emrdoctemplate.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.emr.emrclassify.dao.EmrClassifyDAO;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrdoctemplate.bo.EmrDocTemplateBO;
import cn.hsa.module.emr.emrdoctemplate.dao.EmrDocTemplateDAO;
import cn.hsa.module.emr.emrdoctemplate.dto.EmrDocTemplateDTO;
import cn.hsa.module.emr.emrpatient.dao.EmrPatientDAO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.TreeUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrdoctemplate.bo.impl
 * @Class_name: EmrDocTemplateBOImpl
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/12/9 8:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrDocTemplateBOImpl extends HsafBO implements EmrDocTemplateBO {

	@Resource
	private EmrDocTemplateDAO emrDocTemplateDAO;

	@Resource
	private EmrPatientDAO emrPatientDAO;

	@Resource
	private EmrClassifyDAO emrClassifyDAO;

	/**
	 * @Description: 保存医生自定义病历文档模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 9:23
	 * @Return
	 */
	@Override
	public boolean saveEmrDocTemplate(EmrDocTemplateDTO dto) {
		if (dto != null) {
			// 将完整的html格式的病历转换为字节数组存储
			byte[] buff = new byte[0];
			try {
				if (dto.getStringHtml() != null) {
					buff = dto.getStringHtml().getBytes("UTF-8"); // 需要将页面获取到的html格式的病历转为字节数组，并指定编码
				}
				} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			dto.setNrHtml(buff);
			// 将从页面抽取的map形式的元素转换为json格式存储
			if (dto.getNrMap() != null) {
				String json = JSONObject.toJSONString(dto.getNrMap());
				dto.setEmrJson(json);
			}
			if (dto.getId() == null || dto.getId().equals("")) {
				dto.setId(SnowflakeUtils.getId());
				return emrDocTemplateDAO.saveEmrDocTemplate(dto);
			} else {
				return emrDocTemplateDAO.updateEmrDocTemplate(dto) > 0;
			}
		}
		return false;
	}

	/**
	 * @Description: 根据共享范围与病历分类编码查找医生自定义病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:05
	 * @Return
	 */
	@Override
	public List<EmrDocTemplateDTO> getEmrDocTemplates(Map map) {
		List<EmrDocTemplateDTO> list = emrDocTemplateDAO.getEmrDocTemplates(map);
		if (list == null || list.size() == 0) {
			list = emrDocTemplateDAO.getEmrDocTemplatesForXJ(map);
		}
		return list;
	}

	/**
	 * @Description: 根据医生自定义模板的id查询自定义病历文档模板数据
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/12/9 11:48
	 * @Return
	 */
	@Override
	public EmrDocTemplateDTO selectEmrDocTemplate(Map map) {
		EmrDocTemplateDTO dto = emrDocTemplateDAO.selectEmrDocTemplate(map);
		Map nrMap = new HashMap();
		if (dto != null) {
			if (dto.getNrHtml() != null && !"".equals(dto.getNrHtml())) {
				String stringHtml = null;
				try {
					// 将数据库byte类型的数据转为string类型
					stringHtml = new String((byte[]) dto.getNrHtml(), "UTF-8");
					dto.setStringHtml(stringHtml);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			if (dto.getEmrJson() != null) {
				nrMap = JSONObject.parseObject((String) dto.getEmrJson());
				dto.setNrMap(nrMap);
			}
		}


		return dto;
	}


	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能
	/**
	 * @Description: 查询当前科室可用病历模板，仅根据科室id过滤，用于自定义病历模板维护
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 9:27
	 * @Return
	 */
	@Override
	public List<TreeMenuNode> selectClassifyTemplate(EmrPatientDTO emrPatientDTO) {
		// List<TreeMenuNode> codes = emrPatientDAO.getEmrClassifyTemplate(emrPatientDTO); // 2021年3月15日21:38:40 根据当前登录的科室查询病历模板数据
		List<TreeMenuNode> codes = emrPatientDAO.getEmrClassifyTemplateMZorZY(emrPatientDTO);
		return TreeUtils.buildByRecursive(queryTemplateTree(codes, emrPatientDTO.getHospCode()), "-1");
	}

	/**
	 * @Description: 自定义病历模板新增模板时根据病历模板id查询病历模板文件
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 11:49
	 * @Return
	 */
	@Override
	public EmrPatientDTO getEmrClassifyTemplate(EmrPatientDTO emrPatientDTO) {
		EmrPatientDTO dto = emrPatientDAO.getTemplateByCodeAndDept(emrPatientDTO);
		// 1、查询病历模板并将模板数据给emrPatientDTO TODO
		//EmrClassifyTemplateDTO emrClassifyTemplateDTO = emrPatientDAO.getEmrClassifyTemplateByTemplateId(emrPatientDTO);
		if (dto != null) {
			String stringTemplateHtml = null;
			try {
				// 将数据库byte类型的数据转为string类型
				stringTemplateHtml = new String(dto.getHtml(), "UTF-8");
				emrPatientDTO.setStringHtml(stringTemplateHtml);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return emrPatientDTO;
	}

	/**
	 * @Description: 根据共享范围查询自定义病历模板集合
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/5 15:56
	 * @Return
	 */
	@Override
	public List<TreeMenuNode> getCustomClassifyTemplates(Map map) {
		List<TreeMenuNode> codes = emrPatientDAO.getCustomClassifyTemplates(map);
		String hospCode = MapUtils.get(map, "hospCode");
		return TreeUtils.buildByRecursive(queryTemplateTree(codes, hospCode), "-1");
	}

	/**
	 * @Description: 根据自定义模板文件的id查询自定义模板文件详细信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 9:41
	 * @Return
	 */
	@Override
	public EmrDocTemplateDTO getCustomClassifyTemplate(Map map) {
		EmrDocTemplateDTO dto = emrDocTemplateDAO.selectEmrDocTemplate(map);
		if (dto != null) {
			if (dto.getNrHtml() != null && !"".equals(dto.getNrHtml())) {
				String stringHtml = null;
				try {
					// 将数据库byte类型的数据转为string类型
					stringHtml = new String((byte[]) dto.getNrHtml(), "UTF-8");
					dto.setStringHtml(stringHtml);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return dto;
	}

	/**
	 * @Description: 根据自定义病历模板id删除自定义病历模板
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/6 11:34
	 * @Return
	 */
	@Override
	public boolean deleteCustomClassifyTemplate(Map map) {
		return emrDocTemplateDAO.deleteEmrDocTemplate(map) > 0;
	}


	// =============================================2021年1月5日09:10:00  医生自定义病历模板功能


	public List<TreeMenuNode> queryTemplateTree(List<TreeMenuNode> tree , String hospCode){
		List<TreeMenuNode> resultMenuNodeList = new ArrayList();

		Map map = new HashMap();

		//完整的分类树
		EmrClassifyDTO emrClassifyDTO = new EmrClassifyDTO();
		emrClassifyDTO.setHospCode(hospCode);
		List<TreeMenuNode> emrClassifyTree = emrClassifyDAO.getEmrClassifyTree(emrClassifyDTO);

		if(tree.size() > 0){
			for (int i = 0; i < tree.size(); i++) {
				//循环递归找出
				resultMenuNodeList = getMenuTreeByName(emrClassifyTree, tree.get(i),resultMenuNodeList,map);
			}
			for (TreeMenuNode node : tree){
				resultMenuNodeList.add(node);
			}

		}
		if (resultMenuNodeList.size() > 0 ) {
			TreeMenuNode parent = new TreeMenuNode();
			parent.setLabel("病历分类");
			parent.setId("EMR");
			parent.setParentId("-1");
			resultMenuNodeList.add(parent);
		}
		return resultMenuNodeList;
	}

	public List<TreeMenuNode> getMenuTreeByName( List<TreeMenuNode> allMenuAndBtn,TreeMenuNode selectThemenu,List<TreeMenuNode> resultMenuNodeList,Map map) {
		for (int j = 0; j < allMenuAndBtn.size(); j++) {
			//查出上级菜单
			if(selectThemenu.getParentId().equals("EMR")){
				continue;
			}
			if(selectThemenu.getParentId().equals(allMenuAndBtn.get(j).getId())&&!selectThemenu.getParentId().equals("EMR")){
				if(!map.containsKey(allMenuAndBtn.get(j).getId())){
					map.put(allMenuAndBtn.get(j).getId(),allMenuAndBtn.get(j));
					resultMenuNodeList.add(allMenuAndBtn.get(j));
				}
				getMenuTreeByName(allMenuAndBtn,allMenuAndBtn.get(j),resultMenuNodeList,map);
			}
		}
		return resultMenuNodeList;
	}


}
