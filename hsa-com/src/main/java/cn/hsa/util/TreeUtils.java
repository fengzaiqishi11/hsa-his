package cn.hsa.util;

import cn.hsa.base.TreeMenuNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @描叙
 * @创建者 zengfeng
 * @修改者 zengfeng
 * @版本 V1.0
 * @日期 2020/7/18  11:21
 */
public class TreeUtils {
      /**
        * 两层循环实现建树
        *
        * @param TreeMenuNodes 传入的树节点列表
        * @return
        */
    public <T extends TreeMenuNode> List<T> bulid(List<T> TreeMenuNodes, String root) {
        List<T> trees = new ArrayList<>();
        for (T TreeMenuNode : TreeMenuNodes) {
            if (root.equals(TreeMenuNode.getParentId())) {
                trees.add(TreeMenuNode);
            }
            for (T it : TreeMenuNodes) {
                if (it.getParentId().equals(TreeMenuNode.getId())) {
                     if (TreeMenuNode.getChildren() == null) {
                        TreeMenuNode.setChildren(new ArrayList<>());
                     }
                     TreeMenuNode.add(it);
                 }
             }
             }
        return trees;
    }

    /**
    * 使用递归方法建树
    *
    * @param TreeMenuNodes
    * @return
    */
    public static <T extends TreeMenuNode> List<T> buildByRecursive(List<T> TreeMenuNodes, String root) {
        List<T> trees = new ArrayList<T>();
        for (T TreeMenuNode : TreeMenuNodes) {
            if (root.equals(TreeMenuNode.getParentId())) {
                trees.add(findChildren(TreeMenuNode, TreeMenuNodes));
            }
        }
        return trees;
    }

    /**
    * 递归查找子节点
    *
    * @param TreeMenuNodes
    * @return
    */
    public static <T extends TreeMenuNode> T findChildren(T TreeMenuNode, List<T> TreeMenuNodes) {
        for (T it : TreeMenuNodes) {
            if (TreeMenuNode.getId().equals(it.getParentId())) {
                if (TreeMenuNode.getChildren() == null) {
                    TreeMenuNode.setChildren(new ArrayList<>());
                }
                TreeMenuNode.add(findChildren(it, TreeMenuNodes));
             }
        }
        return TreeMenuNode;
    }

  /**
   * 使用递归方法建树
   *
   * @param TreeMenuNodes
   * @return
   */
  public static <T extends TreeMenuNode> List<T> buildByRecursive1(List<T> TreeMenuNodes, String root) {
    List<T> trees = new ArrayList<T>();
    for (T TreeMenuNode : TreeMenuNodes) {
      if (root.equals(TreeMenuNode.getParentCode())) {
        trees.add(findChildren1(TreeMenuNode, TreeMenuNodes));
      }
    }
    return trees;
  }

  /**
   * 递归查找子节点
   *
   * @param TreeMenuNodes
   * @return
   */
  public static <T extends TreeMenuNode> T findChildren1(T TreeMenuNode, List<T> TreeMenuNodes) {
    for (T it : TreeMenuNodes) {
      if (TreeMenuNode.getCode().equals(it.getParentCode())) {
        if (TreeMenuNode.getChildren() == null) {
          TreeMenuNode.setChildren(new ArrayList<>());
        }
        TreeMenuNode.add(findChildren1(it, TreeMenuNodes));
      }
    }
    return TreeMenuNode;
  }


    /**
     *  使用递归方法获取子节点ID串
     * @param TreeMenuNodes 树全部数据(list数据，不是树结构，直接从数据库查询数据)
     * @param root 查找开始根节点
     * @return 返回子节点ID串
     */
    public static String getChidldrenIds(List<TreeMenuNode> TreeMenuNodes, String root) {
        List<TreeMenuNode> trees = new ArrayList<TreeMenuNode>();
        for (TreeMenuNode treeMenuNode : TreeMenuNodes) {
            if (root.equals(treeMenuNode.getParentId())) {
                trees.add(findChildrenIds(treeMenuNode, TreeMenuNodes, trees));
            }
        }
        //获取所有数据ID
        for(TreeMenuNode treeMenuNode : trees){
            root = root + ","+ treeMenuNode.getId() ;
        }
        return root;
    }

    /**
     *  递归查找子节点子节点所有数据
     * @param TreeMenuNode 查询节点
     * @param TreeMenuNodes 所有数据
     * @param trees 节点数据
     * @return
     */
    public static <T extends TreeMenuNode> T findChildrenIds(T TreeMenuNode, List<T> TreeMenuNodes, List<TreeMenuNode> trees) {
        for (T it : TreeMenuNodes) {
            if (TreeMenuNode.getId().equals(it.getParentId())) {
                trees.add(findChildrenIds(it, TreeMenuNodes, trees));
            }
        }
        return TreeMenuNode;
    }

    public static void main(String[] args){

        List<TreeMenuNode> data = new ArrayList<>();

        TreeMenuNode node = new TreeMenuNode();
        node.setId("1");
        node.setParentId("0");
        node.setLabel("甘肃省");
        data.add(node);

        TreeMenuNode node1 = new TreeMenuNode();
        node1.setId("2");
        node1.setParentId("0");
        node1.setLabel("湖南");
        data.add(node1);

        TreeMenuNode node2 = new TreeMenuNode();
        node2.setId("3");
        node2.setParentId("2");
        node2.setLabel("长沙");
        data.add(node2);

        TreeMenuNode node3 = new TreeMenuNode();
        node3.setId("4");
        node3.setParentId("3");
        node3.setLabel("望城");
        data.add(node3);

        TreeUtils ut = new TreeUtils();
        System.out.println(ut.getChidldrenIds(data, "2"));
    }
}
