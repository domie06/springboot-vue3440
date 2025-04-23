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
 * 学生
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/xuesheng")
public class XueshengController {
    private static final Logger logger = LoggerFactory.getLogger(XueshengController.class);

    @Autowired
    private XueshengService xueshengService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service



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
        PageUtils page = xueshengService.queryPage(params);

        //字典表数据转换
        List<XueshengView> list =(List<XueshengView>)page.getList();
        for(XueshengView c:list){
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
        XueshengEntity xuesheng = xueshengService.selectById(id);
        if(xuesheng !=null){
            //entity转view
            XueshengView view = new XueshengView();
            BeanUtils.copyProperties( xuesheng , view );//把实体数据重构到view中

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
    public R save(@RequestBody XueshengEntity xuesheng, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,xuesheng:{}",this.getClass().getName(),xuesheng.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        Wrapper<XueshengEntity> queryWrapper = new EntityWrapper<XueshengEntity>()
            .eq("username", xuesheng.getUsername())
            .or()
            .eq("xuesheng_phone", xuesheng.getXueshengPhone())
            .or()
            .eq("xuesheng_id_number", xuesheng.getXueshengIdNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XueshengEntity xueshengEntity = xueshengService.selectOne(queryWrapper);
        if(xueshengEntity==null){
            xuesheng.setXueshengTypes(1);
            xuesheng.setCreateTime(new Date());
            xuesheng.setPassword("123456");
            xueshengService.insert(xuesheng);
            return R.ok();
        }else {
            return R.error(511,"账户或者身份证号或者手机号已经被使用");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody XueshengEntity xuesheng, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,xuesheng:{}",this.getClass().getName(),xuesheng.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        //根据字段查询是否有相同数据
        Wrapper<XueshengEntity> queryWrapper = new EntityWrapper<XueshengEntity>()
            .notIn("id",xuesheng.getId())
            .andNew()
            .eq("username", xuesheng.getUsername())
            .or()
            .eq("xuesheng_phone", xuesheng.getXueshengPhone())
            .or()
            .eq("xuesheng_id_number", xuesheng.getXueshengIdNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XueshengEntity xueshengEntity = xueshengService.selectOne(queryWrapper);
        if("".equals(xuesheng.getXueshengPhoto()) || "null".equals(xuesheng.getXueshengPhoto())){
                xuesheng.setXueshengPhoto(null);
        }
        if(xueshengEntity==null){
            //  String role = String.valueOf(request.getSession().getAttribute("role"));
            //  if("".equals(role)){
            //      xuesheng.set
            //  }
            xueshengService.updateById(xuesheng);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"账户或者身份证号或者手机号已经被使用");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        xueshengService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
    * 登录
    */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        XueshengEntity xuesheng = xueshengService.selectOne(new EntityWrapper<XueshengEntity>().eq("username", username));
        if(xuesheng==null || !xuesheng.getPassword().equals(password))
            return R.error("账号或密码不正确");
        //  // 获取监听器中的字典表
        // ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        // Map<String, Map<Integer, String>> dictionaryMap= (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
        // Map<Integer, String> role_types = dictionaryMap.get("role_types");
        // role_types.get(.getRoleTypes());
        String token = tokenService.generateToken(xuesheng.getId(),username, "xuesheng", "学生");
        R r = R.ok();
        r.put("token", token);
        r.put("role","学生");
        r.put("username",xuesheng.getXueshengName());
        r.put("tableName","xuesheng");
        r.put("userId",xuesheng.getId());
        return r;
    }

    /**
    * 注册
    */
    @IgnoreAuth
    @PostMapping(value = "/register")
    public R register(@RequestBody XueshengEntity xuesheng){
    //    	ValidatorUtils.validateEntity(user);
        if(xueshengService.selectOne(new EntityWrapper<XueshengEntity>().eq("username", xuesheng.getUsername()).orNew().eq("xuesheng_phone",xuesheng.getXueshengPhone()).orNew().eq("xuesheng_id_number",xuesheng.getXueshengIdNumber())) !=null)
            return R.error("账户已存在或手机号或身份证号已经被使用");
        xuesheng.setXueshengTypes(1);
        xuesheng.setCreateTime(new Date());
        xueshengService.insert(xuesheng);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @GetMapping(value = "/resetPassword")
    public R resetPassword(Integer  id){
        XueshengEntity xuesheng = new XueshengEntity();
        xuesheng.setPassword("123456");
        xuesheng.setId(id);
        xueshengService.updateById(xuesheng);
        return R.ok();
    }

    /**
    * 获取用户的session用户信息
    */
    @RequestMapping("/session")
    public R getCurrXuesheng(HttpServletRequest request){
        Integer id = (Integer)request.getSession().getAttribute("userId");
        XueshengEntity xuesheng = xueshengService.selectById(id);
        if(xuesheng !=null){
            //entity转view
            XueshengView view = new XueshengView();
            BeanUtils.copyProperties( xuesheng , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }


    /**
    * 退出
    */
    @GetMapping(value = "logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
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
        PageUtils page = xueshengService.queryPage(params);

        //字典表数据转换
        List<XueshengView> list =(List<XueshengView>)page.getList();
        for(XueshengView c:list)
            dictionaryService.dictionaryConvert(c); //修改对应字典表字段
        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XueshengEntity xuesheng = xueshengService.selectById(id);
            if(xuesheng !=null){
                //entity转view
                XueshengView view = new XueshengView();
                BeanUtils.copyProperties( xuesheng , view );//把实体数据重构到view中

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
    public R add(@RequestBody XueshengEntity xuesheng, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,xuesheng:{}",this.getClass().getName(),xuesheng.toString());
        Wrapper<XueshengEntity> queryWrapper = new EntityWrapper<XueshengEntity>()
            .eq("username", xuesheng.getUsername())
            .or()
            .eq("xuesheng_phone", xuesheng.getXueshengPhone())
            .or()
            .eq("xuesheng_id_number", xuesheng.getXueshengIdNumber());
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XueshengEntity xueshengEntity = xueshengService.selectOne(queryWrapper);
        if(xueshengEntity==null){
            xuesheng.setCreateTime(new Date());
            xuesheng.setXueshengTypes(1);
        xuesheng.setPassword("123456");
        xueshengService.insert(xuesheng);
            return R.ok();
        }else {
            return R.error(511,"账户或者身份证号或者手机号已经被使用");
        }
    }




}

