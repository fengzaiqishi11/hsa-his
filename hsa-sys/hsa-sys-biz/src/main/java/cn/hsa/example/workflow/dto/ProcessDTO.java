//package cn.hsa.example.workflow.dto;
//
//import java.io.Serializable;
//import java.util.Date;
//
//public class ProcessDTO implements Serializable {
//    /*流程实例id*/
//    private long id;
//    /*流程开始时间*/
//    private Date startDate;
//    /*流程结束时间*/
//    private Date endDate;
//    /*当前节点名称*/
//    private String nodeName;
//    /*流程发起人*/
//    private String starterId;
//    /*流程状态 0 运行中  4已完成*/
//    private int state;
//    /*流程完成状态 true false 判断是不是历史状态*/
//    private boolean finished;
//
//
//    /**
//     * 获取 流程实例id
//     *
//     * @return id 流程实例id
//     */
//    public long getId() {
//        return this.id;
//    }
//
//    /**
//     * 设置 流程实例id
//     *
//     * @param id 流程实例id
//     */
//    public void setId(long id) {
//        this.id = id;
//    }
//
//
//    /**
//     * 获取 当前节点名称
//     *
//     * @return nodeName 当前节点名称
//     */
//    public String getNodeName() {
//        return this.nodeName;
//    }
//
//    /**
//     * 设置 当前节点名称
//     *
//     * @param nodeName 当前节点名称
//     */
//    public void setNodeName(String nodeName) {
//        this.nodeName = nodeName;
//    }
//
//    /**
//     * 获取 流程发起人
//     *
//     * @return starterId 流程发起人
//     */
//    public String getStarterId() {
//        return this.starterId;
//    }
//
//    /**
//     * 设置 流程发起人
//     *
//     * @param starterId 流程发起人
//     */
//    public void setStarterId(String starterId) {
//        this.starterId = starterId;
//    }
//
//
//
//    /**
//     * 获取 流程完成状态 true false 判断是不是历史状态
//     *
//     * @return finished 流程完成状态 true false 判断是不是历史状态
//     */
//    public boolean isFinished() {
//        return this.finished;
//    }
//
//    /**
//     * 设置 流程完成状态 true false 判断是不是历史状态
//     *
//     * @param finished 流程完成状态 true false 判断是不是历史状态
//     */
//    public void setFinished(boolean finished) {
//        this.finished = finished;
//    }
//
//    /**
//     * 获取 流程开始时间
//     *
//     * @return startDate 流程开始时间
//     */
//    public Date getStartDate() {
//        return this.startDate;
//    }
//
//    /**
//     * 设置 流程开始时间
//     *
//     * @param startDate 流程开始时间
//     */
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }
//
//    /**
//     * 获取 流程结束时间
//     *
//     * @return endDate 流程结束时间
//     */
//    public Date getEndDate() {
//        return this.endDate;
//    }
//
//    /**
//     * 设置 流程结束时间
//     *
//     * @param endDate 流程结束时间
//     */
//    public void setEndDate(Date endDate) {
//        this.endDate = endDate;
//    }
//
//    /**
//     * 获取 流程状态 0 运行中  4已完成
//     *
//     * @return state 流程状态 0 运行中  4已完成
//     */
//    public int getState() {
//        return this.state;
//    }
//
//    /**
//     * 设置 流程状态 0 运行中  4已完成
//     *
//     * @param state 流程状态 0 运行中  4已完成
//     */
//    public void setState(int state) {
//        this.state = state;
//    }
//}
//
//
