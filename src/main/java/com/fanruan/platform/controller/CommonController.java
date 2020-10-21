package com.fanruan.platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.fanruan.platform.bean.*;
import com.fanruan.platform.constant.CommonUtils;
import com.fanruan.platform.dao.CompanyDao;
import com.fanruan.platform.dao.NationCodeDao;
import com.fanruan.platform.etl.RiskPushTimer;
import com.fanruan.platform.htmlToPdf.HtmlToPdfUtils;
import com.fanruan.platform.service.CommonService;
import com.fanruan.platform.service.CompanyService;
import com.fanruan.platform.service.UserService;
import com.fanruan.platform.util.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class CommonController {

    @Autowired
    private UserService userService;

    @Autowired
    private NationCodeDao nationCodeDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/common/getArea", method = RequestMethod.POST)
    @ResponseBody
    public String getAreaInfo(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
//        String areaCode = (String) param.get("areaCode");
        List<Area> areaList = RiskPushTimer.areaListCache;
        ObjectMapper objectMapper=new ObjectMapper();
        hs.put("areaList",areaList);
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getUserCompany", method = RequestMethod.POST)
    @ResponseBody
    public String getUserCompany(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        String operator = (String) param.get("operator");
        List<UserCompany> userCompanyList = commonService.getUserCompany(operator);
        hs.put("userCompanyList",userCompanyList);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getNationCode", method = RequestMethod.POST)
    @ResponseBody
    public String getNationCode(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        List<NationCode> nationCode = commonService.getAreaInfo();
        hs.put("nationCode",nationCode);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getIndustry", method = RequestMethod.POST)
    @ResponseBody
    public String getIndustry(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
//        String areaCode = (String) param.get("areaCode");
        Map<String,List<String>> areaList = RiskPushTimer.industryListCache;
        ObjectMapper objectMapper=new ObjectMapper();
        hs.put("areaList",areaList);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getBlackList", method = RequestMethod.POST)
    @ResponseBody
    public String getBlackList(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        String userCode = (String) param.get("userCode");
        List<BlackList> blackList = commonService.getBlackList(userCode);
        hs.put("blackList",blackList);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getPermissionRoles", method = RequestMethod.POST)
    @ResponseBody
    public String getPermissionRoles(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        List<PermissionPoint> permissionRoles = commonService.getPermissionRoles();
        hs.put("permissionRoles",permissionRoles);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getPermissionPointList", method = RequestMethod.POST)
    @ResponseBody
    public String getPermissionPointList(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        List<PermissionPoint> permissionPointList = commonService.getPermissionPointList();
        hs.put("permissionPointList",permissionPointList);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getPermissionPoint", method = RequestMethod.POST)
    @ResponseBody
    public String getPermissionPoint(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        PermissionPoint permissionPoint = commonService.getPermissionPoint(param);
        hs.put("permissionPoint",permissionPoint);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getPermissionRolePoint", method = RequestMethod.POST)
    @ResponseBody
    public String getPermissionRolePoint(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        List<PermissionPoint> permissionPoint = commonService.getPermissionRolePoint(param);
        hs.put("permissionPoint",permissionPoint);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }



    @RequestMapping(value = "/common/savePermissionPoint", method = RequestMethod.POST)
    @ResponseBody
    public String savePermissionPoint(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        Integer id = CommonUtils.getIntegerValue((String)param.get("id"));
        PermissionPoint permissionPoint = commonService.savePermissionPoint(id,param);
        hs.put("permissionPoint",permissionPoint);
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getJson/1001", method = RequestMethod.POST)
    @ResponseBody
    public String getJson1001(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        String name = (String)param.get("name");
        String id = (String)param.get("id");
        String jsonFlag = "1001";
        String type = (String)param.get("type");
        TianYanChaJson tianYanChaJson = null;
        if(!StringUtils.equals(type,"direct")){
            tianYanChaJson =  commonService.getLocalJson(id,name,jsonFlag);
        }
        if(tianYanChaJson==null){
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("name",name);
            String dataStr = requestTianYanChaAPI(paramMap,"/services/open/cb/ic/2.0?");
            tianYanChaJson = commonService.SaveLocalJson(id,name,dataStr,jsonFlag);

        }
        hs.put("result",tianYanChaJson.getJson());
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/getJson/1002", method = RequestMethod.POST)
    @ResponseBody
    public String getJson1002(@RequestBody Map<String,Object> param) throws JsonProcessingException {
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        String name = (String)param.get("name");
        String jsonFlag = "1002";
        String type = (String)param.get("type");
        TianYanChaJson tianYanChaJson = null;
        if(!StringUtils.equals(type,"direct")){
            tianYanChaJson =  commonService.getLocalJson(null,name,jsonFlag);

        }
        if(tianYanChaJson==null){
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("name",name);
            String dataStr = requestTianYanChaAPI(paramMap,"/services/open/cb/judicial/2.0?");
            tianYanChaJson = commonService.SaveLocalJson(null,name,dataStr,jsonFlag);
        }
        hs.put("result",tianYanChaJson.getJson());
        hs.put("code",0);
        hs.put("msg","");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/logCreditOper", method = RequestMethod.POST)
    @ResponseBody
    public String logCreditOper(@RequestBody Map<String,Object> param) throws JsonProcessingException{
        HashMap<String,Object> hs=new HashMap<>();
        ObjectMapper objectMapper=new ObjectMapper();
        commonService.saveLog(param);
        hs.put("code",0);
        hs.put("msg","日志记录成功");
        return objectMapper.writeValueAsString(hs);
    }

    @RequestMapping(value = "/common/searchApplyList",method = RequestMethod.POST)
    @ResponseBody
    public String searchUserListNew(HttpServletRequest request, @RequestBody Map<String,Object> para) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        Integer pageIndex = CommonUtils.getIntegerValue(para.get("pageIndex"));
        Integer pageSize = CommonUtils.getIntegerValue(para.get("pageSize"));
        String operator = (String)para.get("operator");
        Integer isSubAdmin = CommonUtils.getIntegerValue(para.get("isSubAdmin"));
        String zxbCode = (String)para.get("zxbCode");
        String zxbCompanyName = (String)para.get("zxbCompanyName");
        String zxbApprove = (String)para.get("zxbApprove");
        String approveCode = "";
       if(null != zxbApprove){
           if(zxbApprove.equals("通过")){
               approveCode = "1";
           }else if(zxbApprove.equals("不通过")){
               approveCode = "999";
           }else if(zxbApprove.equals("异常")){
               approveCode = "0";
           }
       }
        String zxbInformant = (String)para.get("zxbInformant");
        String zxbApprover = (String)para.get("zxbApprover");
        HashMap<String,Object> hs=new HashMap<>();
        List<ZhongXinBaoLog> applyList = null;
        hs =  commonService.searchApplyList(hs,pageIndex,pageSize,zxbCode,zxbCompanyName,approveCode,zxbInformant,zxbApprover,isSubAdmin,operator);
        applyList = (List<ZhongXinBaoLog> )hs.get("applyList");
        hs.put("applyList",applyList);
        return objectMapper.writeValueAsString(hs);
    }

    private String requestTianYanChaAPI(Map<String,String> paramMap,String uri) {
        String md5Hex = DigestUtils.md5Hex(CommonUtil.TIAN_YAN_CHA_USERNAME+CommonUtil.TIAN_YAN_CHA_KEY);
        StringBuffer sb = new StringBuffer(CommonUtil.TIAN_YAN_CHA+uri);
        sb.append("username="+CommonUtil.TIAN_YAN_CHA_USERNAME)
                .append("&authId="+CommonUtil.TIAN_YAN_CHA_AUTHID)
                .append("&sign="+md5Hex);
        for(Map.Entry<String,String> kv:paramMap.entrySet()){
            String key = kv.getKey();
            String value = kv.getValue();
            sb.append("&").append(key).append("=").append(value);
        }
//        .append("&name="+company.getCompanyName())
        HttpGet get = new HttpGet(sb.toString());
        get.setHeader("Authorization", CommonUtil.TIAN_YAN_CHA_AUTH);
        HttpClient client = new DefaultHttpClient();
        HttpResponse rese = null;
        String dataStr = null;
        try {
            rese = client.execute(get);
            dataStr = EntityUtils.toString(rese.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataStr;
    }

//    @RequestMapping(value = "/common/transformToPdf", method = RequestMethod.POST)
//    @ResponseBody
//    public String transformToPdf(@RequestBody Map<String,Object> param) throws JsonProcessingException {
//        HashMap<String,Object> hs=new HashMap<>();
//        Integer companyId = CommonUtils.getIntegerValue(param.get("companyId")) ;
//        Optional<Company> companyOptional = companyService.findById(companyId);
//        Company company = CommonUtils.getCompanyValue(companyOptional);
//        Map<String,Object> inputMap = Maps.newHashMap();
//        JSONObject model = companyService.getLiteRating(company, param);
//        hs.put("host", "E:\\test\\");
//        hs.put("code",0);
//        hs.put("model",model.getJSONObject("data"));
//        inputMap.put("reportNo",model.getString("requestId"));
//        inputMap.put("endTime",new Date());
//        hs.put("base",inputMap);
////        File file = new File("E:\\other\\帆软资料\\帆软项目\\test.html");
//        String pdfName =  "E:\\test\\test.pdf";
//        String templatePath =  "E:\\test\\pdf_demo\\template\\liteRating_new\\";
//        String exePath = "E:\\test\\pdf_demo\\wkhtmltopdf\\wkhtmltopdf.exe";
//        HtmlToPdfUtils.createHtmlByTemp(templatePath, "E:\\test\\test.html", hs);
//
//        boolean flag = HtmlToPdfUtils.convert("E:\\test\\test.html", "E:\\test\\", exePath, "test.pdf", "","E:\\test\\test.html",4);
//        ObjectMapper objectMapper=new ObjectMapper();
//
//        return objectMapper.writeValueAsString(hs);
//    }



}
