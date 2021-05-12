package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.dao.ProfileMapper;
import project.model.Profile;

/**
 * @author ：闫崇傲
 * @description：ProfileService的实现类
 * @date ：2021/4/27 13:56
 */
@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    ProfileMapper profileMapper;

    /**
     * 判断用户是否拥有Profile
     * @param userId
     * @return
     */
    @Override
    public boolean isProfileExist(int userId) {
        //如果没有找到简历，则返回false
        return profileMapper.isProfileExist(userId);
    }

    /**
     * 根据用户ID返回用户的profile提前必须进行判断，否则会返回null
     * @param userId
     * @return
     */
    @Override
    public Profile getProfile(int userId) {
        return profileMapper.getProfileByUserId(userId);
    }

    /**
     * 保存一个profile,保存成功返回true，保存失败返回false
     * @param user_id
     * @param completiontime
     * @param expertize_realm
     * @param compensation
     * @param workexperience
     * @param expertize_level
     * @param biography
     * @return
     */
    @Override
    public boolean saveProfile(int user_id, String completiontime, String expertize_realm, String compensation, String workexperience, String expertize_level, String biography,String user_name) {
        if(profileMapper.insertNewProfile(user_id,completiontime,expertize_realm,compensation,workexperience,expertize_level,biography,user_name)==1){
            return true;
        }
        return false;
    }

    /**
     * 更新一个profile
     * @param completiontime
     * @param expertize_realm
     * @param compensation
     * @param workexperience
     * @param expertize_level
     * @param biography
     * @param user_id
     * @return
     */
    @Override
    public boolean updateProfile(String completiontime, String expertize_realm, String compensation, String workexperience, String expertize_level, String biography, int user_id) {
        if(profileMapper.updatePofileById(completiontime,expertize_realm,compensation,workexperience,expertize_level,biography,user_id)==1){
            return true;
        }
        return false;
    }

    /**
     * 根据其id查找一个profile
     * @param id
     * @return
     */
    @Override
    public Profile getProfileById(int id) {
        return profileMapper.getProfileById(id);
    }
}
