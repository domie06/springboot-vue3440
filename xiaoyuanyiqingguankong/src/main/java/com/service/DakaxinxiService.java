package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.DakaxinxiEntity;
import java.util.Map;

/**
 * 打卡信息 服务类
 */
public interface DakaxinxiService extends IService<DakaxinxiEntity> {

    /**
    * @param params 查询参数
    * @return 带分页的查询出来的数据
    */
     PageUtils queryPage(Map<String, Object> params);
}