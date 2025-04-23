package com.controller;








import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 打卡信息
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/dakaxinxi")
public class DakaxinxiController {
    private static final Logger logger = LoggerFactory.getLogger(DakaxinxiController.class);

    @Autowired
    private DakaxinxiService dakaxinxiService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private XueshengService xueshengService;



    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("学生".equals(role))
            params.put("xueshengId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = dakaxinxiService.queryPage(params);

        //字典表数据转换
        List<DakaxinxiView> list =(List<DakaxinxiView>)page.getList();
        for(DakaxinxiView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        DakaxinxiEntity dakaxinxi = dakaxinxiService.selectById(id);
        if(dakaxinxi !=null){
            //entity转view
            DakaxinxiView view = new DakaxinxiView();
            BeanUtils.copyProperties( dakaxinxi , view );//把实体数据重构到view中

                //级联表
                XueshengEntity xuesheng = xueshengService.selectById(dakaxinxi.getXueshengId());
                if(xuesheng != null){
                    BeanUtils.copyProperties( xuesheng , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setXueshengId(xuesheng.getId());
                }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody DakaxinxiEntity dakaxinxi, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,dakaxinxi:{}",this.getClass().getName(),dakaxinxi.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("学生".equals(role))
            dakaxinxi.setXueshengId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        Wrapper<DakaxinxiEntity> queryWrapper = new EntityWrapper<DakaxinxiEntity>()
            .eq("xuesheng_id", dakaxinxi.getXueshengId())
            .eq("dakaxinxi_didian", dakaxinxi.getDakaxinxiDidian())
            .eq("quezhen_types", dakaxinxi.getQuezhenTypes())
            .eq("yishi_types", dakaxinxi.getYishiTypes())
            .eq("gaofengxian_types", dakaxinxi.getGaofengxianTypes())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        DakaxinxiEntity dakaxinxiEntity = dakaxinxiService.selectOne(queryWrapper);
        if(dakaxinxiEntity==null){
            if(dakaxinxi.getQuezhenTypes() == 1
                    || dakaxinxi.getYishiTypes() == 1
                    || dakaxinxi.getGaofengxianTypes() == 1
                    || dakaxinxi.getDakaxinxiTiwen() < 36.0
                    || dakaxinxi.getDakaxinxiTiwen() > 38.1){
                XueshengEntity xueshengEntity = xueshengService.selectById(dakaxinxi.getXueshengId());
                if(xueshengEntity.getXueshengTypes() != 2){
                    xueshengEntity.setXueshengTypes(2);
                    boolean b = xueshengService.updateById(xueshengEntity);
                    if(!b){
                        return R.error("修改学生状态出错");
                    }
                }
            }
            dakaxinxi.setInsertTime(new Date());
            dakaxinxi.setCreateTime(new Date());
            dakaxinxiService.insert(dakaxinxi);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody DakaxinxiEntity dakaxinxi, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,dakaxinxi:{}",this.getClass().getName(),dakaxinxi.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("学生".equals(role))
            dakaxinxi.setXueshengId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<DakaxinxiEntity> queryWrapper = new EntityWrapper<DakaxinxiEntity>()
            .notIn("id",dakaxinxi.getId())
            .andNew()
            .eq("xuesheng_id", dakaxinxi.getXueshengId())
            .eq("dakaxinxi_didian", dakaxinxi.getDakaxinxiDidian())
            .eq("quezhen_types", dakaxinxi.getQuezhenTypes())
            .eq("yishi_types", dakaxinxi.getYishiTypes())
            .eq("gaofengxian_types", dakaxinxi.getGaofengxianTypes())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        DakaxinxiEntity dakaxinxiEntity = dakaxinxiService.selectOne(queryWrapper);
        if(dakaxinxiEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      dakaxinxi.set
            //  }
            dakaxinxiService.updateById(dakaxinxi);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        dakaxinxiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }




    /**
    * 前端列表
    */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("学生".equals(role))
            params.put("xueshengId",request.getSession().getAttribute("userId"));

        // 没有指定排序字段就默认id倒序
        if(StringUtil.isEmpty(String.valueOf(params.get("orderBy")))){
            params.put("orderBy","id");
        }
        PageUtils page = dakaxinxiService.queryPage(params);

        //字典表数据转换
        List<DakaxinxiView> list =(List<DakaxinxiView>)page.getList();
        for(DakaxinxiView c:list)
            dictionaryService.dictionaryConvert(c); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        DakaxinxiEntity dakaxinxi = dakaxinxiService.selectById(id);
            if(dakaxinxi !=null){
                //entity转view
                DakaxinxiView view = new DakaxinxiView();
                BeanUtils.copyProperties( dakaxinxi , view );//把实体数据重构到view中

                //级联表
                    XueshengEntity xuesheng = xueshengService.selectById(dakaxinxi.getXueshengId());
                if(xuesheng != null){
                    BeanUtils.copyProperties( xuesheng , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setXueshengId(xuesheng.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody DakaxinxiEntity dakaxinxi, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,dakaxinxi:{}",this.getClass().getName(),dakaxinxi.toString());
        Wrapper<DakaxinxiEntity> queryWrapper = new EntityWrapper<DakaxinxiEntity>()
            .eq("xuesheng_id", dakaxinxi.getXueshengId())
            .eq("dakaxinxi_didian", dakaxinxi.getDakaxinxiDidian())
            .eq("quezhen_types", dakaxinxi.getQuezhenTypes())
            .eq("yishi_types", dakaxinxi.getYishiTypes())
            .eq("gaofengxian_types", dakaxinxi.getGaofengxianTypes())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        DakaxinxiEntity dakaxinxiEntity = dakaxinxiService.selectOne(queryWrapper);
        if(dakaxinxiEntity==null){
            dakaxinxi.setInsertTime(new Date());
            dakaxinxi.setCreateTime(new Date());
        //  String role = String.valueOf(request.getSession().getAttribute("role"));
        //  if("".equals(role)){
        //      dakaxinxi.set
        //  }
        dakaxinxiService.insert(dakaxinxi);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }




}

