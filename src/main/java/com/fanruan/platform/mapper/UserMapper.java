package com.fanruan.platform.mapper;

import com.fanruan.platform.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author DELk
 * @@version 1.0.0
 * @since 2020/9/3
 */
@Mapper
public interface UserMapper {
    @Update("update FINEDB1.FINE_USER t set t.ENABLE = 1 where USERNAME IN (SELECT USERNAME FROM BIUSER.CREDIT_USER WHERE BIUSER.CREDIT_USER.STATUS = 1 and USERNAME <> 'admin')")
    void updateUserStatus();

    @Update("update credit_user t set t.status = #{status, jdbcType=INTEGER} where t.user_id = #{userId, jdbcType=INTEGER}")
    void updateCreditUserStatus(@Param("userId") Integer userId,@Param("status") Integer status);

    @Select(
            "<script> \n" +
            "SELECT M.* FROM ( "+
            "SELECT ROWNUM AS rowno,N.* " +
            "FROM ( \n" +
            "SELECT \n" +
            "t.user_id as userId, \n"+
            "t.username as username,\n" +
            "t.password as password,\n" +
            "t.mobile as mobile,\n" +
            "t.email as email,\n" +
            "t.company_name as companyName,\n" +
            "t.dept_name as deptName,\n" +
            "t.name as name,\n" +
            "t.company_code as companyCode,\n" +
            "t.dept_code as deptCode,\n" +
            "t.status as status,\n" +
            "t.newcompany_flag as newcompanyFlag,\n" +
            "F_GET_PERMISSION_BY_USERID(t.user_id) as permissionRoles,\n" +
            "CASE \n" +
            "  WHEN INSTR(F_GET_PERMISSION_BY_USERID(t.user_id),('sub_admin')) &gt; 0 THEN\n" +
            "    1\n" +
            "  ELSE \n" +
            "    0\n" +
            "  END as isSubAdmin\n" +
            "FROM CREDIT_USER T \n" +
            "WHERE username &lt;&gt; 'admin' \n" +
            "<if test=\"name != null and name!=''\">\n" +
            " and (t.name like '%'||#{name, jdbcType=VARCHAR}||'%' ) \n" +
            "</if> \n" +
            "<if test=\"username != null and username!=''\">\n" +
            " and (t.username like '%'||#{username, jdbcType=VARCHAR}||'%' ) \n" +
            "</if> \n" +
            "<if test=\"status != null\">\n" +
            " and (t.status = #{status, jdbcType=INTEGER} ) \n" +
            "</if> \n" +
            "<if test=\"companyCode != null  and companyCode!='' \">\n" +
            " and (t.company_code = #{companyCode, jdbcType=VARCHAR} ) \n" +
            "</if> \n" +
            "ORDER BY isSubAdmin DESC,status desc \n" +
            ") N " +
            "WHERE 1=1 \n" +
            "<if test=\"isSubAdmin != null\">\n" +
            " and (N.isSubAdmin = #{isSubAdmin, jdbcType=INTEGER} ) \n" +
            "</if> \n" +

            ") M\n" +
            "WHERE 1=1 \n" +
            "AND rowno &lt;= #{pageIndex, jdbcType=INTEGER} * #{pageSize, jdbcType=INTEGER}  \n" +
            "AND rowno &gt; (#{pageIndex, jdbcType=INTEGER}-1) * #{pageSize, jdbcType=INTEGER} \n" +
            "</script> "
    )
    @Results(id="getUserList",value = {
            @Result(property = "userId", column = "userId"),
            @Result(property = "username", column = "username"),
            @Result(property = "mobile", column = "mobile"),
            @Result(property = "email", column = "email"),
            @Result(property = "companyName", column = "companyName"),
            @Result(property = "name", column = "name"),
            @Result(property = "companyCode", column = "companyCode"),
            @Result(property = "deptCode", column = "deptCode"),
            @Result(property = "status", column = "status"),
            @Result(property = "newCompanyFlag", column = "newcompanyFlag"),
            @Result(property = "permissionRoles", column = "permissionRoles")
    })
    public List<User> getUserList(@Param("pageIndex") Integer pageIndex,
                                  @Param("pageSize") Integer pageSize,
                                  @Param("username") String username,
                                  @Param("name") String name,
                                  @Param("status") Integer status,
                                  @Param("isSubAdmin") Integer isSubAdmin,
                                  @Param("companyCode") String companyCode);


    @Select(
            "<script> \n" +
                    "SELECT COUNT(*) as co FROM ( \n" +
                    "SELECT \n" +
                    "ROWNUM AS rowno, \n" +
                    "t.user_id as userId, \n"+
                    "t.username as username,\n" +
                    "t.password as password,\n" +
                    "t.mobile as mobile,\n" +
                    "t.email as email,\n" +
                    "t.company_name as companyName,\n" +
                    "t.dept_name as deptName,\n" +
                    "t.name as name,\n" +
                    "t.company_code as companyCode,\n" +
                    "t.dept_code as deptCode,\n" +
                    "t.status as status,\n" +
                    "t.newcompany_flag as newcompanyFlag,\n" +
                    "F_GET_PERMISSION_BY_USERID(t.user_id) as permissionRoles,\n" +
                    "CASE \n" +
                    "  WHEN INSTR(F_GET_PERMISSION_BY_USERID(t.user_id),('sub_admin')) &gt; 0 THEN\n" +
                    "    1\n" +
                    "  ELSE \n" +
                    "    0\n" +
                    "  END as isSubAdmin\n" +
                    "FROM CREDIT_USER T \n" +
                    "WHERE username &lt;&gt; 'admin' \n" +
                    "<if test=\"name != null and name!=''\">\n" +
                    " and (t.name like '%'||#{name, jdbcType=VARCHAR}||'%' ) \n" +
                    "</if> \n" +
                    "<if test=\"username != null and username!=''\">\n" +
                    " and (t.username like '%'||#{username, jdbcType=VARCHAR}||'%' ) \n" +
                    "</if> \n" +
                    "<if test=\"status != null\">\n" +
                    " and (t.status = #{status, jdbcType=INTEGER} ) \n" +
                    "</if> \n" +
                    ") M \n" +
                    "WHERE 1=1 " +
                    "<if test=\"isSubAdmin != null\">\n" +
                    " and (M.isSubAdmin = #{isSubAdmin, jdbcType=INTEGER} ) \n" +
                    "</if> \n" +
                    "<if test=\"companyCode != null  and companyCode!='' \">\n" +
                    " and (M.companyCode = #{companyCode, jdbcType=VARCHAR} ) \n" +
                    "</if> \n" +
                    "</script> "
    )
    @Results(id="getUserListCount", value= {
            @Result(property = "co", column = "co")
    })
    public Integer getUserListCount(@Param("pageIndex") Integer pageIndex,
                                    @Param("pageSize") Integer pageSize,
                                    @Param("username") String username,
                                    @Param("name") String name,
                                    @Param("status") Integer status,
                                    @Param("isSubAdmin") Integer isSubAdmin,
                                    @Param("companyCode") String companyCode);

    @Select(
            "<script> \n" +
                    "SELECT M.* FROM ( "+
                    "SELECT ROWNUM AS rowno,N.* " +
                    "FROM ( \n" +
                    "SELECT \n" +
                    "t.user_id as userId, \n"+
                    "t.username as username,\n" +
                    "t.password as password,\n" +
                    "t.mobile as mobile,\n" +
                    "t.email as email,\n" +
                    "t.company_name as companyName,\n" +
                    "t.dept_name as deptName,\n" +
                    "t.name as name,\n" +
                    "t.company_code as companyCode,\n" +
                    "t.dept_code as deptCode,\n" +
                    "t.status as status,\n" +
                    "t.newcompany_flag as newcompanyFlag,\n" +
                    "F_GET_PERMISSION_BY_USERID(t.user_id) as permissionRoles,\n" +
                    "CASE \n" +
                    "  WHEN INSTR(F_GET_PERMISSION_BY_USERID(t.user_id),('sub_admin')) &gt; 0 THEN\n" +
                    "    1\n" +
                    "  ELSE \n" +
                    "    0\n" +
                    "  END as isSubAdmin\n" +
                    "FROM CREDIT_USER T \n" +
                    "WHERE username &lt;&gt; 'admin' \n" +
                    "AND company_code in (" +
                    "SELECT CODE --自身\n" +
                    "FROM BIUSER.ODS_HR_ORG A\n" +
                    "WHERE  CODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    "UNION ALL\n" +
                    "SELECT CODE --下级公司\n" +
                    "FROM BIUSER.ODS_HR_ORG A\n" +
                    "WHERE SCODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    "UNION ALL  \n" +
                    "SELECT B.CODE --下下级公司\n" +
                    "FROM BIUSER.ODS_HR_ORG A \n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG B ON A.CODE=B.SCODE\n" +
                    "WHERE A.SCODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    "UNION ALL  \n" +
                    "SELECT C.CODE --下下下级公司\n" +
                    "FROM BIUSER.ODS_HR_ORG A \n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG B ON A.CODE=B.SCODE\n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG C ON B.CODE=C.SCODE\n" +
                    "WHERE A.SCODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    "UNION ALL  \n" +
                    "SELECT D.CODE --下下下下级公司\n" +
                    "FROM BIUSER.ODS_HR_ORG A \n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG B ON A.CODE=B.SCODE\n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG C ON B.CODE=C.SCODE\n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG D ON C.CODE=D.SCODE\n" +
                    "WHERE A.SCODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    ") "+
                    "<if test=\"name != null and name!=''\">\n" +
                    " and (t.name like '%'||#{name, jdbcType=VARCHAR}||'%' ) \n" +
                    "</if> \n" +
                    "<if test=\"username != null and username!=''\">\n" +
                    " and (t.username like '%'||#{username, jdbcType=VARCHAR}||'%' ) \n" +
                    "</if> \n" +
                    "<if test=\"status != null\">\n" +
                    " and (t.status = #{status, jdbcType=INTEGER} ) \n" +
                    "</if> \n" +
                    "ORDER BY isSubAdmin DESC,status desc \n" +
                    ") N " +
                    "WHERE 1=1 \n" +
                    "<if test=\"isSubAdmin != null\">\n" +
                    " and (N.isSubAdmin = #{isSubAdmin, jdbcType=INTEGER} ) \n" +
                    "</if> \n" +
                    ") M\n" +
                    "WHERE 1=1 \n" +
                    "AND rowno &lt;= #{pageIndex, jdbcType=INTEGER} * #{pageSize, jdbcType=INTEGER}  \n" +
                    "AND rowno &gt; (#{pageIndex, jdbcType=INTEGER}-1) * #{pageSize, jdbcType=INTEGER} \n" +
                    "</script> "
    )
    @Results(id="getSubUserList",value = {
            @Result(property = "userId", column = "userId"),
            @Result(property = "username", column = "username"),
            @Result(property = "mobile", column = "mobile"),
            @Result(property = "email", column = "email"),
            @Result(property = "companyName", column = "companyName"),
            @Result(property = "name", column = "name"),
            @Result(property = "companyCode", column = "companyCode"),
            @Result(property = "deptCode", column = "deptCode"),
            @Result(property = "status", column = "status"),
            @Result(property = "newCompanyFlag", column = "newcompanyFlag"),
            @Result(property = "permissionRoles", column = "permissionRoles")
    })
    public List<User> getSubUserList(HashMap<String, Object> hs, @Param("pageIndex") Integer pageIndex,
                                     @Param("pageSize") Integer pageSize,
                                     @Param("username") String username,
                                     @Param("name") String name,
                                     @Param("status") Integer status,
                                     @Param("isSubAdmin") Integer isSubAdmin,
                                     @Param("operator") String operator);


    @Select(
            "<script> \n" +
                    "SELECT COUNT(*) as co FROM ( \n" +
                    "SELECT \n" +
                    "ROWNUM AS rowno, \n" +
                    "t.user_id as userId, \n"+
                    "t.username as username,\n" +
                    "t.password as password,\n" +
                    "t.mobile as mobile,\n" +
                    "t.email as email,\n" +
                    "t.company_name as companyName,\n" +
                    "t.dept_name as deptName,\n" +
                    "t.name as name,\n" +
                    "t.company_code as companyCode,\n" +
                    "t.dept_code as deptCode,\n" +
                    "t.status as status,\n" +
                    "t.newcompany_flag as newcompanyFlag,\n" +
                    "F_GET_PERMISSION_BY_USERID(t.user_id) as permissionRoles,\n" +
                    "CASE \n" +
                    "  WHEN INSTR(F_GET_PERMISSION_BY_USERID(t.user_id),('sub_admin')) &gt; 0 THEN\n" +
                    "    1\n" +
                    "  ELSE \n" +
                    "    0\n" +
                    "  END as isSubAdmin\n" +
                    "FROM CREDIT_USER T \n" +
                    "WHERE username &lt;&gt; 'admin' \n" +
                    "AND company_code in (" +
                    "SELECT CODE --自身\n" +
                    "FROM BIUSER.ODS_HR_ORG A\n" +
                    "WHERE  CODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    "UNION ALL\n" +
                    "SELECT CODE --下级公司\n" +
                    "FROM BIUSER.ODS_HR_ORG A\n" +
                    "WHERE SCODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    "UNION ALL  \n" +
                    "SELECT B.CODE --下下级公司\n" +
                    "FROM BIUSER.ODS_HR_ORG A \n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG B ON A.CODE=B.SCODE\n" +
                    "WHERE A.SCODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    "UNION ALL  \n" +
                    "SELECT C.CODE --下下下级公司\n" +
                    "FROM BIUSER.ODS_HR_ORG A \n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG B ON A.CODE=B.SCODE\n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG C ON B.CODE=C.SCODE\n" +
                    "WHERE A.SCODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    "UNION ALL  \n" +
                    "SELECT D.CODE --下下下下级公司\n" +
                    "FROM BIUSER.ODS_HR_ORG A \n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG B ON A.CODE=B.SCODE\n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG C ON B.CODE=C.SCODE\n" +
                    "INNER JOIN BIUSER.ODS_HR_ORG D ON C.CODE=D.SCODE\n" +
                    "WHERE A.SCODE=(SELECT COMPANY_CODE FROM CREDIT_USER WHERE USERNAME=#{operator, jdbcType=VARCHAR})  \n" +
                    ") "+
                    "<if test=\"name != null and name!=''\">\n" +
                    " and (t.name like '%'||#{name, jdbcType=VARCHAR}||'%' ) \n" +
                    "</if> \n" +
                    "<if test=\"username != null and username!=''\">\n" +
                    " and (t.username like '%'||#{username, jdbcType=VARCHAR}||'%' ) \n" +
                    "</if> \n" +
                    "<if test=\"status != null\">\n" +
                    " and (t.status = #{status, jdbcType=INTEGER} ) \n" +
                    "</if> \n" +
                    ") M \n" +
                    "WHERE 1=1 \n" +
                    "<if test=\"isSubAdmin != null\">\n" +
                    " and (M.isSubAdmin = #{isSubAdmin, jdbcType=INTEGER} ) \n" +
                    "</if> \n" +
                    "</script> "
    )
    @Results(id="getSubUserListCount", value= {
            @Result(property = "co", column = "co")
    })
    public Integer getSubUserListCount(@Param("pageIndex") Integer pageIndex,
                                    @Param("pageSize") Integer pageSize,
                                    @Param("username") String username,
                                    @Param("name") String name,
                                    @Param("status") Integer status,
                                    @Param("isSubAdmin") Integer isSubAdmin,
                                    @Param("operator") String operator);
}
