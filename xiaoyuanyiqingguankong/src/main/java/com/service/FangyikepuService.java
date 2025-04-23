package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.FangyikepuEntity;
import java.util.Map;

/**
 * 防疫科普 服务类
 */
public interface FangyikepuService extends IService<FangyikepuEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);
}