package com.fanruan.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.fanruan.platform.bean.*;
import com.fanruan.platform.constant.CommonUtils;
import com.fanruan.platform.dao.*;
import com.fanruan.platform.mapper.CommonMapper;
import com.fanruan.platform.mapper.CommonsMapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

;


@Service
public class CommonService {

    @Autowired
    private TianYanChaJsonDao tianYanChaJsonDao;

    @Autowired
    private BlackListDao blackListDao;

    @Autowired
    private NationCodeDao nationCodeDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private CommonsMapper commonsMapper;

    @Autowired
    private LogCreditOperDao logCreditOperDao;

    @Deprecated
    public List<BlackList> getBlackList() {//废弃
        java.util.Date date = DateUtils.addMonths(new java.util.Date(System.currentTimeMillis()), -1);
        return blackListDao.findAllByStatusAndStartDateAfter("2", new Date(date.getTime()));
    }

    //改进后的黑名单
    public List<BlackList> getBlackList(String userCode) {
        return commonsMapper.getBlackList(userCode);
    }

    public TianYanChaJson getLocalJson(String id,String name, String jsonFlag) {
        List<TianYanChaJson> jsonList = null;
        if(StringUtils.isNotBlank(id)){
            jsonList = tianYanChaJsonDao.findByNameAndIdAndAndJsonFlagOrderByUpdateTimeDesc(id,name, jsonFlag);
        }else {
            jsonList = tianYanChaJsonDao.findByNameAndJsonFlagOrderByUpdateTimeDesc(name, jsonFlag);
        }
        if(CollectionUtils.isEmpty(jsonList)){
            return null;
        }
        return jsonList.get(0);
    }

    public TianYanChaJson SaveLocalJson(String id, String name, String dataStr, String jsonFlag) {
        TianYanChaJson tianYanChaJson = new TianYanChaJson();
        JSONObject jsonObject = JSONObject.parseObject(dataStr);
        String reason = jsonObject.getString("reason");
        Integer errorCode = jsonObject.getInteger("error_code");
        tianYanChaJson.setId(id);
        tianYanChaJson.setName(name);
        tianYanChaJson.setJson(dataStr);
        tianYanChaJson.setErrorCode(String.valueOf(errorCode));
        tianYanChaJson.setReason(reason);
        tianYanChaJson.setJsonFlag(jsonFlag);
        tianYanChaJson.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return tianYanChaJsonDao.saveAndFlush(tianYanChaJson);
    }

    public List<NationCode> getAreaInfo() {
        return nationCodeDao.findAll();
    }

    public List<PermissionPoint> getPermissionRoles() {
        return commonMapper.getPermissionPoint();
    }

    public List<PermissionPoint> getPermissionPointList() {
        return permissionPointDao.findAll();
    }

    public PermissionPoint getPermissionPoint(Map<String, Object> param) {
        PermissionPoint permissionPoint = null;
        Integer id = CommonUtils.getIntegerValue((String)param.get("id"));
        String point = (String) param.get("permissionPoint");
        if(id!=null){
            Optional<PermissionPoint> allById = permissionPointDao.findById(id);
            if(allById.isPresent()){
                permissionPoint = allById.get();
            }
        }else if(StringUtils.isNotBlank(point)){
            Optional<PermissionPoint> permissionPointOptional = permissionPointDao.findByPermissionPoint(point);
            if(permissionPointOptional.isPresent()){
                permissionPoint = permissionPointOptional.get();
            }
        }
        return permissionPoint;
    }

    public PermissionPoint savePermissionPoint(Integer id, Map<String, Object> param) {
        PermissionPoint  point = new PermissionPoint();
        if(id!=null){
            Optional<PermissionPoint> allById = permissionPointDao.findAllById(id);
            if(allById.isPresent()){
                point = allById.get();
            }
        }
        String permissionRole = (String) param.get("permissionRole");
        String permissionRoleName = (String) param.get("permissionRoleName");
        String permissionPoint = (String) param.get("permissionPoint");
        String permissionPointName = (String) param.get("permissionPointName");
        String userId = (String) param.get("userId");
        point.setPermissionPoint(permissionPoint);
        point.setPermissionPointName(permissionPointName);
        point.setPermissionRole(permissionRole);
        point.setPermissionRoleName(permissionRoleName);
        point.setUpdateBy(userId);
        point.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return permissionPointDao.saveAndFlush(point);
    }

    public List<PermissionPoint> getPermissionRolePoint(Map<String, Object> param) {
        List<PermissionPoint>  permissionPointList = Lists.newArrayList();
        String permissionRole = (String) param.get("permissionRole");
        if(StringUtils.isNotBlank(permissionRole)){
            permissionPointList = permissionPointDao.findAllByPermissionRole(permissionRole);
        }
        return permissionPointList;
    }

    public List<UserCompany> getUserCompany(String operator) {
        if(StringUtils.isBlank(operator)){
            return null;
        }
        List<UserCompany> userCompany = null;
//         if(StringUtils.equals("admin",operator)){
//             userCompany = commonMapper.getAllUserCompany();
//         }else {
             userCompany = commonsMapper.getUserCompany(operator);
//         }
        return userCompany;
    }


    public boolean saveLog(Map<String, Object> param){
        LogCreditOper logCreditOper = new LogCreditOper();
        String userName = (String)param.get("userName");
        String userCode = (String)param.get("userCode");
        String formPath= (String)param.get("formPath");
        String formPageName = (String)param.get("formPageName");
        String toPath = (String)param.get("toPath");
        String toPageName = (String)param.get("toPageName");
        String queryPara =  (String)param.get("queryPara");
        logCreditOper.setUserName(userName);
        logCreditOper.setUserCode(userCode);
        logCreditOper.setFormPath(formPath);
        logCreditOper.setFormPageName(formPageName);
        logCreditOper.setToPath(toPath);
        logCreditOper.setToPageName(toPageName);
        logCreditOper.setQueryPara(queryPara);
        logCreditOper.setQDate(new java.util.Date());
        logCreditOperDao.saveAndFlush(logCreditOper);
        return true;
    }

    public HashMap<String,Object> searchApplyList(HashMap<String, Object> hs, Integer pageIndex, Integer pageSize, String zxbCode, String zxbCompanyName,String approveCode,String zxbInformant,String zxbApprover,Integer isSubAdmin, String operator) {
        Integer totalRecords = commonsMapper.getZxbApplyListCount(pageIndex, pageSize, zxbCode, zxbCompanyName,approveCode,zxbInformant,zxbApprover,isSubAdmin, operator);
        hs.put("totalRecords",totalRecords);
        hs.put("totalPages",Math.ceil(totalRecords/pageSize));
        List<ZhongXinBaoLog> applyList = commonsMapper.getZxbApplyList(hs, pageIndex, pageSize, zxbCode, zxbCompanyName, approveCode,zxbInformant,zxbApprover, isSubAdmin, operator);
        hs.put("applyList",applyList);
        hs.put("code",0);
        return hs;
    }

}
