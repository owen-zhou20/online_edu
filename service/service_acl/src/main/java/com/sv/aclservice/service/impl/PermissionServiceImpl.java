package com.sv.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sv.aclservice.entity.Permission;
import com.sv.aclservice.entity.RolePermission;
import com.sv.aclservice.entity.User;
import com.sv.aclservice.helper.MemuHelper;
import com.sv.aclservice.helper.PermissionHelper;
import com.sv.aclservice.mapper.PermissionMapper;
import com.sv.aclservice.service.PermissionService;
import com.sv.aclservice.service.RolePermissionService;
import com.sv.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * PermissionServiceImpl
 * </p>
 *
 * @author
 * @since
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserService userService;


    //==============================  Acl  =============================================================================
    // Get all menus
    @Override
    public List<Permission> queryAllMenuSv() {
        // 1. select all permissions data
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(wrapper);

        // 2. Put permissions data to resultList as request
        List<Permission> resultList = bulidPermission1(permissionList);

        return resultList;
    }

    // Delete all permissions by id include children permissions
    @Override
    public void removeChildByIdSv(String id) {
        // 1. create list for all permissions for delete
        List<String> idList = new ArrayList<>();
        // 2. set permission ids for delete to idList
        selectPermissionChildById1(id,idList);

        // put parent id into idList
        idList.add(id);
        // delete all ids in idList include parent id and all children's ids for this parent permission
        baseMapper.deleteBatchIds(idList);
    }

    // Assign permissions(menus) to a role by role id
    // Do assign
    @Override
    public void saveRolePermissionRealtionShipSv(String roleId, String[] permissionId) {
        // create rolePermissionList for save
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (String perId : permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(perId);
            // add rolePermission to rolePermissionList
            rolePermissionList.add(rolePermission);
        }
        // add rolePermissionList to rolePermission table in DB
        rolePermissionService.saveBatch(rolePermissionList);
    }

    // Get all children ids for this permission by id and put into idList include it and children's ids
    private void selectPermissionChildById1(String id, List<String> idList) {
        // get all children ids by parent id
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> childIdList = baseMapper.selectList(wrapper);

        // Get ids form childIdList and put into idList,
        // then use recursion to find children ids for this child item and put children ids into idList
        childIdList.stream().forEach(item ->{
            //Get ids form childIdList and put into idList
            idList.add(item.getId());
            // then use recursion to find children ids for this child item and put children ids into idList
            selectPermissionChildById1(item.getId(),idList);
        });

    }

    /**
     * build tree menus
     * @param
     * @return
     */
    //  Put permissions data to resultList as request
    public static List<Permission> bulidPermission1(List<Permission> permissionList) {
        // create list for return
        List<Permission> finalNode = new ArrayList<>();

        // traverse permissionList and get top permission which is pid = 0, level = 1.
        // get top permission which is pid = 0, level = 1
        for (Permission permissionNode : permissionList)
            if ("0".equals(permissionNode.getPid())) {
                // set top permission level = 1
                permissionNode.setLevel(1);
                // get children nodes and put into top permission node children
                finalNode.add(selectChildren1(permissionNode, permissionList));
            }
        return finalNode;
    }

    /**
     * recursion to get children list as tree
     * @param
     * @return
     */
    private static Permission selectChildren1(Permission permissionNode, List<Permission> permissionList) {
        // 1. init Children for permissionNode
        permissionNode.setChildren(new ArrayList<Permission>());

        // 2. get children permissions which is pid = parent.id and children's level = parent.level+1
        for (Permission permissionOne : permissionList) {
            if(permissionNode.getId().equals(permissionOne.getPid())){
                // set child level = permissionNode level + 1
                int level = permissionNode.getLevel() +1;
                permissionOne.setLevel(level);
                // 1. init Children for permissionNode
                /*if(permissionNode.getChildren() == null){
                    permissionNode.setChildren(new ArrayList<Permission>());
                    System.out.println("Permission init ===============>");
                }*/
                // get children node and add into parent's children list use recursion
                permissionNode.getChildren().add(selectChildren1(permissionOne, permissionList));

            }
        }

        return permissionNode;
    }


    // get menus by role id
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>()
                .orderByAsc("CAST(id AS SIGNED)")); // CAST(id AS SIGNED) => id to int

        // get menus by role id
        List<RolePermission> rolePermissionList = rolePermissionService.list(
                new QueryWrapper<RolePermission>().eq("role_id",roleId));
        // use map set selected menus by role id
//        List<String> permissionIdList = rolePermissionList.stream().map(e ->
//                                            e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        // set selected menu for roleId in allPermissionList
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = bulidPermission1(allPermissionList);
        return permissionList;
    }




    //======================================= Security =================================================================
    // security
    // get assign menus by user id
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //get all menus if admin
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            // get permission_value in table acl_permission by userId. used to check in button in front-end. eg. v-if="hasPerm('role.add')"
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    // get assign menus by user id (Json version)
    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            // get all menus for admin
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    /**
     * check admin for user
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Permission> childList = baseMapper.selectList(new QueryWrapper<Permission>()
                                                .eq("pid", id).select("id"));
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }


}
