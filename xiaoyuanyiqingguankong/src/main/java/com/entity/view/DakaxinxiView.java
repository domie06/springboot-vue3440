package com.entity.view;

import com.entity.DakaxinxiEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 打卡信息
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("dakaxinxi")
public class DakaxinxiView extends DakaxinxiEntity implements Serializable {
    private static final long serialVersionUID = 1L;

		/**
		* 一月内是否接触过确诊病例的值
		*/
		private String quezhenValue;
		/**
		* 一月内是否接触过疑似病例的值
		*/
		private String yishiValue;
		/**
		* 一月内是否去过中高风险地区的值
		*/
		private String gaofengxianValue;



		//级联表 xuesheng
			/**
			* 学生姓名
			*/
			private String xueshengName;
			/**
			* 身份证号
			*/
			private String xueshengIdNumber;
			/**
			* 手机号
			*/
			private String xueshengPhone;
			/**
			* 照片
			*/
			private String xueshengPhoto;
			/**
			* 学生状态
			*/
			private Integer xueshengTypes;
				/**
				* 学生状态的值
				*/
				private String xueshengValue;

	public DakaxinxiView() {

	}

	public DakaxinxiView(DakaxinxiEntity dakaxinxiEntity) {
		try {
			BeanUtils.copyProperties(this, dakaxinxiEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



			/**
			* 获取： 一月内是否接触过确诊病例的值
			*/
			public String getQuezhenValue() {
				return quezhenValue;
			}
			/**
			* 设置： 一月内是否接触过确诊病例的值
			*/
			public void setQuezhenValue(String quezhenValue) {
				this.quezhenValue = quezhenValue;
			}
			/**
			* 获取： 一月内是否接触过疑似病例的值
			*/
			public String getYishiValue() {
				return yishiValue;
			}
			/**
			* 设置： 一月内是否接触过疑似病例的值
			*/
			public void setYishiValue(String yishiValue) {
				this.yishiValue = yishiValue;
			}
			/**
			* 获取： 一月内是否去过中高风险地区的值
			*/
			public String getGaofengxianValue() {
				return gaofengxianValue;
			}
			/**
			* 设置： 一月内是否去过中高风险地区的值
			*/
			public void setGaofengxianValue(String gaofengxianValue) {
				this.gaofengxianValue = gaofengxianValue;
			}














				//级联表的get和set xuesheng
					/**
					* 获取： 学生姓名
					*/
					public String getXueshengName() {
						return xueshengName;
					}
					/**
					* 设置： 学生姓名
					*/
					public void setXueshengName(String xueshengName) {
						this.xueshengName = xueshengName;
					}
					/**
					* 获取： 身份证号
					*/
					public String getXueshengIdNumber() {
						return xueshengIdNumber;
					}
					/**
					* 设置： 身份证号
					*/
					public void setXueshengIdNumber(String xueshengIdNumber) {
						this.xueshengIdNumber = xueshengIdNumber;
					}
					/**
					* 获取： 手机号
					*/
					public String getXueshengPhone() {
						return xueshengPhone;
					}
					/**
					* 设置： 手机号
					*/
					public void setXueshengPhone(String xueshengPhone) {
						this.xueshengPhone = xueshengPhone;
					}
					/**
					* 获取： 照片
					*/
					public String getXueshengPhoto() {
						return xueshengPhoto;
					}
					/**
					* 设置： 照片
					*/
					public void setXueshengPhoto(String xueshengPhoto) {
						this.xueshengPhoto = xueshengPhoto;
					}
					/**
					* 获取： 学生状态
					*/
					public Integer getXueshengTypes() {
						return xueshengTypes;
					}
					/**
					* 设置： 学生状态
					*/
					public void setXueshengTypes(Integer xueshengTypes) {
						this.xueshengTypes = xueshengTypes;
					}


						/**
						* 获取： 学生状态的值
						*/
						public String getXueshengValue() {
							return xueshengValue;
						}
						/**
						* 设置： 学生状态的值
						*/
						public void setXueshengValue(String xueshengValue) {
							this.xueshengValue = xueshengValue;
						}


}
