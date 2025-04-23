package com.dao;

import com.entity.FangyikepuEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.FangyikepuView;

/**
 * 防疫科普 Dao 接口
 *
 * @author 
 */
public interface FangyikepuDao extends BaseMapper<FangyikepuEntity> {

   List<FangyikepuView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
