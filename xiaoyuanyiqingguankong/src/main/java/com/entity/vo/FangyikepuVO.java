package com.entity.vo;

import com.entity.FangyikepuEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 防疫科普
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("fangyikepu")
public class FangyikepuVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 标题
     */

    @TableField(value = "fangyikepu_name")
    private String fangyikepuName;


    /**
     * 图片
     */

    @TableField(value = "fangyikepu_photo")
    private String fangyikepuPhoto;


    /**
     * 相关文件
     */

    @TableField(value = "fangyikepu_file")
    private String fangyikepuFile;


    /**
     * 内容
     */

    @TableField(value = "fangyikepu_content")
    private String fangyikepuContent;


    /**
     * 发布时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：标题
	 */
    public String getFangyikepuName() {
        return fangyikepuName;
    }


    /**
	 * 获取：标题
	 */

    public void setFangyikepuName(String fangyikepuName) {
        this.fangyikepuName = fangyikepuName;
    }
    /**
	 * 设置：图片
	 */
    public String getFangyikepuPhoto() {
        return fangyikepuPhoto;
    }


    /**
	 * 获取：图片
	 */

    public void setFangyikepuPhoto(String fangyikepuPhoto) {
        this.fangyikepuPhoto = fangyikepuPhoto;
    }
    /**
	 * 设置：相关文件
	 */
    public String getFangyikepuFile() {
        return fangyikepuFile;
    }


    /**
	 * 获取：相关文件
	 */

    public void setFangyikepuFile(String fangyikepuFile) {
        this.fangyikepuFile = fangyikepuFile;
    }
    /**
	 * 设置：内容
	 */
    public String getFangyikepuContent() {
        return fangyikepuContent;
    }


    /**
	 * 获取：内容
	 */

    public void setFangyikepuContent(String fangyikepuContent) {
        this.fangyikepuContent = fangyikepuContent;
    }
    /**
	 * 设置：发布时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：发布时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间  show1 show2 photoShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
