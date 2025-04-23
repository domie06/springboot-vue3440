package com.entity.view;

import com.entity.FangyikepuEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 防疫科普
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("fangyikepu")
public class FangyikepuView extends FangyikepuEntity implements Serializable {
    private static final long serialVersionUID = 1L;




	public FangyikepuView() {

	}

	public FangyikepuView(FangyikepuEntity fangyikepuEntity) {
		try {
			BeanUtils.copyProperties(this, fangyikepuEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}












}
