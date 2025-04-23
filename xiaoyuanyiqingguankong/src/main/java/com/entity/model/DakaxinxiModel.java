package com.entity.model;

import com.entity.DakaxinxiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 打卡信息
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class DakaxinxiModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 打卡学生
     */
    private Integer xueshengId;


    /**
     * 打卡时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 体温
     */
    private Double dakaxinxiTiwen;


    /**
     * 打卡所在地
     */
    private String dakaxinxiDidian;


    /**
     * 一月内是否接触过确诊病例
     */
    private Integer quezhenTypes;


    /**
     * 一月内是否接触过疑似病例
     */
    private Integer yishiTypes;


    /**
     * 一月内是否去过中高风险地区
     */
    private Integer gaofengxianTypes;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：打卡学生
	 */
    public Integer getXueshengId() {
        return xueshengId;
    }


    /**
	 * 设置：打卡学生
	 */
    public void setXueshengId(Integer xueshengId) {
        this.xueshengId = xueshengId;
    }
    /**
	 * 获取：打卡时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：打卡时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：体温
	 */
    public Double getDakaxinxiTiwen() {
        return dakaxinxiTiwen;
    }


    /**
	 * 设置：体温
	 */
    public void setDakaxinxiTiwen(Double dakaxinxiTiwen) {
        this.dakaxinxiTiwen = dakaxinxiTiwen;
    }
    /**
	 * 获取：打卡所在地
	 */
    public String getDakaxinxiDidian() {
        return dakaxinxiDidian;
    }


    /**
	 * 设置：打卡所在地
	 */
    public void setDakaxinxiDidian(String dakaxinxiDidian) {
        this.dakaxinxiDidian = dakaxinxiDidian;
    }
    /**
	 * 获取：一月内是否接触过确诊病例
	 */
    public Integer getQuezhenTypes() {
        return quezhenTypes;
    }


    /**
	 * 设置：一月内是否接触过确诊病例
	 */
    public void setQuezhenTypes(Integer quezhenTypes) {
        this.quezhenTypes = quezhenTypes;
    }
    /**
	 * 获取：一月内是否接触过疑似病例
	 */
    public Integer getYishiTypes() {
        return yishiTypes;
    }


    /**
	 * 设置：一月内是否接触过疑似病例
	 */
    public void setYishiTypes(Integer yishiTypes) {
        this.yishiTypes = yishiTypes;
    }
    /**
	 * 获取：一月内是否去过中高风险地区
	 */
    public Integer getGaofengxianTypes() {
        return gaofengxianTypes;
    }


    /**
	 * 设置：一月内是否去过中高风险地区
	 */
    public void setGaofengxianTypes(Integer gaofengxianTypes) {
        this.gaofengxianTypes = gaofengxianTypes;
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
