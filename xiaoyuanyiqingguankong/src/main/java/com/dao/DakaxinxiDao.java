package com.dao;

import com.entity.DakaxinxiEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.view.DakaxinxiView;

/**
 * 打卡信息 Dao 接口
 *
 * @author 
 */
public interface DakaxinxiDao extends BaseMapper<DakaxinxiEntity> {

   List<DakaxinxiView> selectListView(Pagination page,@Param("params")Map<String,Object> params);

}
