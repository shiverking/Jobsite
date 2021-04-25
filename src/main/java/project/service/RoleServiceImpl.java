package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.RoleMapper;

/**
 * @author ：闫崇傲
 * @description：角色Service
 * @date ：2021/4/25 16:46
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public Boolean addUserAndRole(int userId, int roleId) {
        //System.out.println("=========tmp========"+tmp);
        //插入成功
        if(roleMapper.addUserAndRole(userId,roleId)==1){
            return true;
        }
        return false;
    }
}
