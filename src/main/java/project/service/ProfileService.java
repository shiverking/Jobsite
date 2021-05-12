package project.service;

import org.apache.ibatis.annotations.Param;
import project.model.Profile;

/**
 * ProfileService的接口类
 */
public interface ProfileService {
    //用户的profile是否存在
    public boolean isProfileExist(int userId);

    //返回用户的profile
    public Profile getProfile(int userId);

    //保存一个profile
    public boolean saveProfile(int user_id, String completiontime,
                               String expertize_realm, String compensation,
                               String workexperience, String expertize_level,
                               String biography,String user_name);

    //更新一个profile
    public boolean updateProfile(String completiontime,
                             String expertize_realm, String compensation,
                             String workexperience, String expertize_level,
                             String biography, int user_id);

    //根据简历发id返回profile
    Profile getProfileById(int id);
}
