package com.fanruan.platform.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fanruan.platform.bean.*;
import com.fanruan.platform.constant.CommonUtils;
import com.fanruan.platform.dao.*;
import com.fanruan.platform.mapper.CommonMapper;
import com.fanruan.platform.mapper.UserMapper;
import com.fanruan.platform.mapper.UsersMapper;
import com.fanruan.platform.util.MD5Util;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserFocusDao userFocusDao;

    @Autowired
    private UserPermissionDao userPermissionDao;

    @Autowired
    private PermissionPointDao permissionPointDao;

    @Autowired
    private NewCompanyDao newCompanyDao;

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UsersMapper usersMapper;


    public UserFocus saveRelation(HashMap<String, Object> hs, Integer userId, Integer companyId, Map<String, Integer> relations) {
        Map<String,Integer> resultMap = Maps.newHashMap();
        Optional<User> userOptional = userDao.findById(userId);
        User user = CommonUtils.getUserValue(userOptional);
        Optional<Company> companyOptional = companyDao.findById(companyId);
        Company company = CommonUtils.getCompanyValue(companyOptional);
        if(user==null||company==null){
            hs.put("code","2");
            hs.put("msg"," 用户或公司在主库中不存在");
        }
        UserFocus userFocus = userFocusDao.findAllByUserIdAndCompanyId(userId,companyId);
      if(userFocus==null){
          userFocus = new UserFocus();
          userFocus.setUserId(userId);
          userFocus.setCompanyId(companyId);
          userFocus.setCompanyName(company.getCompanyName());
      }
      if(StringUtils.isNotBlank(userFocus.getRelations())){
          Map mapRelations = JSON.parseObject(userFocus.getRelations(),Map.class);
          resultMap.putAll(mapRelations);
      }
        resultMap.putAll(relations);
        Collection<Integer> values = resultMap.values();
        if(!values.contains(1)){
            userFocusDao.delete(userFocus);
            return null;
        }
        userFocus.setRelations(JSONObject.toJSONString(resultMap));
        return userFocusDao.saveAndFlush(userFocus);
    }





    public String getCareCompanySet(Integer userId) {
        if(userId==null){
            return null;
        }
        List<UserFocus> userFocusList = userFocusDao.findAllByUserId(userId);
        List<Integer> collect = userFocusList.stream().map(x -> x.getCompanyId()).collect(Collectors.toList());
        List<Company> companyIdIn = companyDao.findAllByCompanyIdIn(collect);
        Map<String, Company> companyHashMap = Maps.newHashMap();
        for(Company company:companyIdIn){
            companyHashMap.put(String.valueOf(company.getCompanyId()),company);
        }
        List<Map<String,Object>> result = Lists.newArrayList();
        for(UserFocus userFocus:userFocusList){
            Map<String,Object> map = Maps.newHashMap();
            Company company = companyHashMap.get(String.valueOf(userFocus.getCompanyId()));
            if(company!=null){
                map.put("id",company.getId());
            }else {
                map.put("id","-1");
            }
            map.put("userId",userFocus.getUserId());
            map.put("companyId",userFocus.getCompanyId());
            map.put("creditCode",company.getCreditCode());
            map.put("companyName",userFocus.getCompanyName());
            Map mapRelations = JSON.parseObject(userFocus.getRelations(),Map.class);
            map.putAll(mapRelations);
            result.add(map);
        }
        return JSONObject.toJSONString(result);
    }


    public User getUserCheck(String username, String password) {
        Optional<User> userOptional = null;
        if(StringUtils.isBlank(password)){
            userOptional = userDao.findByUsernameAndStatus(username, 1);
        }else {
            userOptional = userDao.findByUsernameAndPasswordAndStatus(username, password, 1);
        }
        return CommonUtils.getUserValue(userOptional);
    }

    public String getCareStatus(Integer userId, Integer companyId) {
        if(userId==null||companyId==null){
            return null;
        }
        UserFocus userFocus = userFocusDao.findAllByUserIdAndCompanyId(userId, companyId);
        Map<String,Object> map = Maps.newHashMap();
        if(userFocus==null){
            Optional<Company> companyOptional = companyDao.findByCompanyId(companyId);
            Company company = CommonUtils.getCompanyValue(companyOptional);
            map.put("userId",userId);
            map.put("companyId",companyId);
            map.put("companyName",company.getCompanyName());
        }else {
            map.put("userId",userFocus.getUserId());
            map.put("companyId",userFocus.getCompanyId());
            map.put("companyName",userFocus.getCompanyName());
            Map mapRelations = JSON.parseObject(userFocus.getRelations(),Map.class);
            map.putAll(mapRelations);
        }
        return JSONObject.toJSONString(map);
    }

    public List<User> getUserList(Integer pageIndex, Integer pageSize) {
        Sort sort = Sort.by("userId").descending();
        PageRequest pageable = PageRequest.of(pageIndex-1, pageSize,sort);
        Page<User> users = userDao.findAll(pageable);
        return users.stream().filter(user -> !StringUtils.equalsIgnoreCase(user.getUsername(), "admin")).collect(Collectors.toList());
    }

    public User getUserById(Integer userId) {
        if(userId == null){
            return null;
        }
        Optional<User> userOptional = userDao.findById(userId);
        return CommonUtils.getUserValue(userOptional);
    }

    public User updateUser(String operator, Integer userId, Map<String, Object> para) {
        boolean isNew = false;
        User user = null;
        if(userId!=null){
            Optional<User> userOptional = userDao.findById(userId);
            user = CommonUtils.getUserValue(userOptional);
        }else {//为新增用户
            user = new User();
            user.setStatus(1);
            //同步到FR
            isNew = true;
        }
        if(user!=null){
            Integer status = CommonUtils.getIntegerValue(para.get("status"));
            String password = (String)para.get("password");
            String username = (String)para.get("username");
            String name = (String)para.get("name");
            String mobile = (String)para.get("mobile");
            String email = (String)para.get("email");
            String companyCode = (String)para.get("companyCode");
            String companyName = (String)para.get("companyName");
            String deptName = (String)para.get("deptName");
            String deptCode = (String)para.get("deptCode");
            String permissionRoles = (String)para.get("permissionRoles");
            if(StringUtils.isNotBlank(username)){
                user.setUsername(username);
            }
            if(StringUtils.isNotBlank(name)){
                user.setName(name);
            }
            if(companyName!=null){
                user.setCompanyName(companyName);
            }
            if(user.getUserId()!=null){
                user.setNewCompanyFlag(1);
            }
            if(companyCode!=null){
                user.setCompanyCode(companyCode);
            }
            if(deptName!=null){
                user.setDeptName(deptName);
            }
            if(deptCode!=null){
                user.setDeptCode(deptCode);
            }
            if(status!=null){
                user.setStatus(status);
            }
            if(StringUtils.isNotBlank(password)&&!password.equals(user.getPassword())){
                password = MD5Util.MD5(username+password);
                user.setPassword(password);
            }
            if(mobile!=null){
                user.setMobile(mobile);
            }
            if(email!=null){
                user.setEmail(email);
            }
            if(permissionRoles!=null){
                user.setPermissionRoles(permissionRoles);
            }
            User result = userDao.saveAndFlush(user);
            if(isNew){//新增用户同步到FR
                insertUserToFr(user);
            }
            updatePermission(operator, result.getUserId(), permissionRoles);
            result.setPermissionRoles(permissionRoles);
            return result;
        }
        return null;
    }

    private boolean insertUserToFr(User user){
        Integer f1 = usersMapper.insertStep1(user);
        Integer f2 = usersMapper.insertStep2(user);
        Integer f3 = usersMapper.insertStep3(user);
        Integer f4 = usersMapper.insertStep4(user);
        Integer f5 = usersMapper.insertStep5(user);
        return true;
    }

    private void updatePermission(String operator, Integer userId, String permissionRoles) {
        if(StringUtils.isBlank(operator)){
            return;
        }
        List<UserPermission> permissionList = Lists.newArrayList();
        List<UserPermission> oldPermissionList = userPermissionDao.findAllByUserIdAndPermissionStatus(userId,"1");
        userPermissionDao.deleteAll(oldPermissionList);
        if(StringUtils.isBlank(permissionRoles)){
            userPermissionDao.flush();
            return;
        }
        String[] split = permissionRoles.split(",");
        for(String permissionStr:split){
            UserPermission userPermission = new UserPermission();
            userPermission.setUserId(userId);
            userPermission.setPermissionRole(permissionStr);
            userPermission.setPermissionStatus("1");
            userPermission.setUpdateBy(operator);
            userPermission.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            permissionList.add(userPermission);
        }
        userPermissionDao.saveAll(permissionList);
        userPermissionDao.flush();
    }

    public List<User> searchUserList(HashMap<String, Object> hs, Integer pageIndex, Integer pageSize, String username, String name, Integer status) {
        if(pageIndex==null||pageSize==null){
            return null;
        }
        List<Integer> statusList = Lists.newArrayList();
        if(status!=null){
            statusList.add(status);
        }else {
            statusList.add(0);
            statusList.add(1);
        }
        Sort sort = Sort.by("userId").descending();
        PageRequest pageable = PageRequest.of(pageIndex-1, pageSize,sort);
        Page<User> users = null;
        if(StringUtils.isNotBlank(username)&&StringUtils.isBlank(name)){
            users = userDao.findAllByUsernameLikeAndStatusInOrderByStatusDesc(pageable, "%"+username+"%",statusList);
        }else if(StringUtils.isNotBlank(name)&&StringUtils.isBlank(username)){
            users = userDao.findAllByNameLikeAndStatusInOrderByStatusDesc(pageable, "%"+name+"%",statusList);
        }else if(StringUtils.isNotBlank(username)&&StringUtils.isNotBlank(name)){
            users =  userDao.findAllByUsernameLikeAndNameLikeAndStatusInOrderByStatusDesc(pageable, "%"+username+"%", "%"+name+"%",statusList);
        }else {
            users = userDao.findAllByStatusInOrderByStatusDesc(pageable,statusList);
        }
       if(users!=null){
           hs.put("totalPages",users.getTotalPages());
           hs.put("totalRecords",users.getTotalElements());
           return users.get().collect(Collectors.toList());
       }else {
           return null;
       }
    }

    /**
     * XXX
     * @param hs
     * @param pageIndex
     * @param pageSize
     * @param username
     * @param name
     * @param status
     * @param isSubAadmin
     * @return
     */
    public HashMap<String, Object> getUserList(
            HashMap<String, Object> hs,
            Integer pageIndex, Integer pageSize,
            String username, String name, Integer status, Integer isSubAadmin,String companyCode){
        Integer totalRecords = userMapper.getUserListCount(pageIndex, pageSize, username, name, status, isSubAadmin,companyCode);
        hs.put("totalRecords",totalRecords);
        hs.put("totalPages",Math.ceil(totalRecords/pageSize));
        hs.put("userList",userMapper.getUserList(pageIndex, pageSize, username, name, status, isSubAadmin,companyCode));
        hs.put("code",0);
        return hs;
    }

    public User getUserInfo(Integer userId, String operator) {
        Optional<User> userOptional = userDao.findById(userId);
        User user = CommonUtils.getUserValue(userOptional);
        if(StringUtils.isNotBlank(operator)&&user!=null){
            StringBuffer sb = new StringBuffer("");
            List<UserPermission> userPermissions = userPermissionDao.findAllByUserIdAndPermissionStatus(userId,"1");
           for(UserPermission permission:userPermissions){
               sb.append(permission.getPermissionRole()).append(",");
           }
           if(sb.length()>0){
               sb.substring(0,sb.length()-1);
           }
            user.setPermissionRoles(sb.toString());
        }
        return user;
    }

    public Map<String, Boolean> verifyPermissions(HashMap<String, Object> hs, Integer userId, String permissionPoint) {
        if(userId==null||StringUtils.isBlank(permissionPoint)){
            hs.put("code",1);
            hs.put("msg","参数校验失败，请验证用户id或者权限参数");
            return null;
        }
        Map<String,Boolean> result = Maps.newHashMap();
        List<UserPermission> userPermissions = userPermissionDao.findAllByUserIdAndPermissionStatus(userId,"1");
        if(CollectionUtils.isEmpty(userPermissions)){
            hs.put("code",2);
            hs.put("msg","用户权限查询为空");
            return null;
        }
        List<String> list = Lists.newArrayList();
        for (UserPermission userPermission:userPermissions){
            list.add(userPermission.getPermissionRole());
        }
        List<PermissionPoint> permissionPointList = permissionPointDao.findAllByPermissionRoleIn(list);
        Set<String> points = Sets.newHashSet();
        for(PermissionPoint point:permissionPointList){
            points.add(point.getPermissionPoint());
        }
        String[] split = permissionPoint.split(",");
        for(String pointStr:split){
            if(points.contains(pointStr)){
                result.put(pointStr,true);
            }else {
                result.put(pointStr,false);
            }
        }

        return result;
    }

    public List<PermissionPoint> getPermissionList(String operator) {
        if(StringUtils.isBlank(operator)){
            return null;
        }
        Optional<User> byUsernameAndStatus = userDao.findByUsernameAndStatus(operator, 1);
        User user = CommonUtils.getUserValue(byUsernameAndStatus);
        Integer userId = user.getUserId();
        List<UserPermission> userPermissionList = userPermissionDao.findAllByUserIdAndPermissionStatus(userId, "1");
        List<String> collect = userPermissionList.stream().map(x -> x.getPermissionRole()).collect(Collectors.toList());
        List<PermissionPoint> permissionPoints = permissionPointDao.findAllByPermissionRoleIn(collect);
        List<PermissionPoint> result = null;
        if(StringUtils.equals(operator,"admin")){
            result = permissionPoints.stream().filter(x -> !StringUtils.equals(x.getPermissionRole(), "admin")).collect(Collectors.toList());
        }else {
            result = permissionPoints.stream().filter(x -> StringUtils.equals(x.getIsExtend(), "1")).collect(Collectors.toList());
        }
        return result;
    }

    public List<User> fillUpdatePermission(HashMap<String, Object> hs,List<User> userList, String operator,Integer isSubAdmin, Integer pageSize) {
        if(StringUtils.isBlank(operator)){
            return userList;
        }
        List<User> result = Lists.newArrayList();
        List<Integer> collect1 = userList.stream().map(x -> x.getUserId()).collect(Collectors.toList());
        List<UserPermission> permissionList = userPermissionDao.findAllByUserIdInAndPermissionStatus(collect1, "1");
        Map<Integer,String> permissionMap = Maps.newHashMap();
        for(UserPermission userPermission:permissionList){
            String permissionStr = permissionMap.get(userPermission.getUserId());
            permissionStr = permissionStr==null?userPermission.getPermissionRole():permissionStr+","+userPermission.getPermissionRole();
            permissionMap.put(userPermission.getUserId(),permissionStr);
        }
        if(StringUtils.equals(operator,"admin")){
            for(User user:userList){
                user.setUpdatePermission("1");
                user.setPermissionRoles(permissionMap.get(user.getUserId()));
                result.add(user);
            }
        }else {
            List<User> permissionUsers = commonMapper.getPermissionUsers(operator);
            List<String> collect = permissionUsers.stream().map(x -> x.getUsername()).collect(Collectors.toList());
            for(User user:userList){
                if((collect!=null&&collect.contains(user.getUsername()))){
                    user.setUpdatePermission("1");
                    result.add(user);
                }else {
                    user.setUpdatePermission("0");
                }
                user.setPermissionRoles(permissionMap.get(user.getUserId()));
            }
        }
        //只返回管理员
        if(isSubAdmin!=null&&isSubAdmin.equals(1)){
            List<User> subAdmin = result.stream().filter(x -> StringUtils.contains(x.getPermissionRoles(), "sub_admin")).collect(Collectors.toList());
            hs.put("totalPages",Math.ceil(subAdmin.size()/pageSize));
            hs.put("totalRecords",subAdmin.size());
            return subAdmin;
        }
        return result;

    }

    public List<User> fillUpdatePermissionNew(HashMap<String, Object> hs,List<User> userList, String operator) {
        if(StringUtils.isBlank(operator)){
            return userList;
        }
        List<User> result = Lists.newArrayList();
        List<Integer> collect1 = userList.stream().map(x -> x.getUserId()).collect(Collectors.toList());
        List<UserPermission> permissionList = userPermissionDao.findAllByUserIdInAndPermissionStatus(collect1, "1");
        Map<Integer,String> permissionMap = Maps.newHashMap();
        for(UserPermission userPermission:permissionList){
            String permissionStr = permissionMap.get(userPermission.getUserId());
            permissionStr = permissionStr==null?userPermission.getPermissionRole():permissionStr+","+userPermission.getPermissionRole();
            permissionMap.put(userPermission.getUserId(),permissionStr);
        }
        if(StringUtils.equals(operator,"admin")){
            for(User user:userList){
                user.setUpdatePermission("1");
                user.setPermissionRoles(permissionMap.get(user.getUserId()));
                result.add(user);
            }
        }else {
            for(User user:userList){
                user.setPermissionRoles(permissionMap.get(user.getUserId()));
                result.add(user);
            }
        }
        return result;

    }

    public List<User> getSubAdminUserList(HashMap<String, Object> hs,Integer pageIndex, Integer pageSize, String operator) {
        List<User> permissionUsers = commonMapper.getPermissionUsers(operator);
        if(CollectionUtils.isEmpty(permissionUsers)||(pageIndex-1)*pageSize>permissionUsers.size()){
            return null;
        }
        hs.put("totalPages",Math.ceil(permissionUsers.size()/pageSize));
        hs.put("totalRecords",permissionUsers.size());
        List<User> userList = permissionUsers.subList((pageIndex - 1) * pageSize, Math.min(permissionUsers.size(), pageIndex * pageSize));
        List<String> collect = userList.stream().map(x -> x.getUsername()).collect(Collectors.toList());
        return userDao.findAllByUsernameIn(collect);
    }

    public List<User> searchSubAdminUserList(HashMap<String, Object> hs, Integer pageIndex, Integer pageSize, String username, String name, Integer status, String operator) {
        List<User> permissionUsers = commonMapper.getPermissionUsers(operator);
        Set<String> set = permissionUsers.stream().map(x -> x.getUsername()).collect(Collectors.toSet());
        List<User> userList = searchUserList(hs, 1, 100000, username, name, status);
        List<User> result = Lists.newArrayList();
        for(User user:userList){
            if(set!=null&&set.contains(user.getUsername())){
                result.add(user);
            }
        }
        hs.put("totalPages",Math.ceil(result.size()/pageSize));
        hs.put("totalRecords",result.size());
        if((pageIndex-1)*pageSize>result.size()){
            return null;
        }else {
            result = result.subList((pageIndex-1)*pageSize,Math.min(result.size(),pageIndex*pageSize)) ;
        }
        return result;
    }

    public HashMap<String,Object> searchSubAdminUserListNew(HashMap<String, Object> hs, Integer pageIndex, Integer pageSize, String username, String name, Integer status, Integer isSubAdmin, String operator,String companyCode) {
        Integer totalRecords = usersMapper.getSubUserListCount(pageIndex, pageSize, username, name, status, isSubAdmin, operator,companyCode);
        hs.put("totalRecords",totalRecords);
        hs.put("totalPages",Math.ceil(totalRecords/pageSize));
        List<User> userList = usersMapper.getSubUserList(hs, pageIndex, pageSize, username, name, status,  isSubAdmin, operator,companyCode);
        hs.put("userList",userList);
        hs.put("code",0);
        return hs;
    }

    public User getUserByUsername(String username) {
        Optional<User> byUsername = userDao.findByUsername(username);
        return CommonUtils.getUserValue(byUsername);
    }

    public HashMap<String, Object> getUserListLevel(HashMap<String, Object> hs, Integer pageIndex, Integer pageSize, String username, String name, Integer status, Integer isSubAdmin, String companyCode) {
        Integer totalRecords = usersMapper.getUserListLevelCount(hs,pageIndex, pageSize, username, name, status, isSubAdmin,companyCode);
        hs.put("totalRecords",totalRecords);
        hs.put("totalPages",Math.ceil(totalRecords/pageSize));
        List<User> userList = usersMapper.getUserListLevel(hs, pageIndex, pageSize, username, name, status,  isSubAdmin,companyCode);
        hs.put("userList",userList);
        hs.put("code",0);
        return hs;
    }
}
