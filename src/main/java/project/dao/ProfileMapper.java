package project.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import project.model.Profile;

/**
 * @author ：闫崇傲
 * @description：简历的Mapper类
 * @date ：2021/4/26 22:08
 */
@Mapper
@Component
public interface ProfileMapper {
    //根据ID找到用户的Profile
    Profile getProfileById(@Param("user_id") int user_id);

    //是否存在用户的Profile
    boolean isProfileExist(@Param("user_id") int user_id);

    //添加一个新的用户Profile
    int insertNewProfile(@Param("user_id") int user_id, @Param("completiontime") String completiontime,
                         @Param("expertize_realm") String expertize_realm, @Param("compensation") String compensation,
                         @Param("workexperience") String workexperience,@Param("expertize_level") String expertize_level,
                         @Param("biography") String biography);

    //更新一个用户的Profile信息
    int updatePofileById(@Param("completiontime") String completiontime,
                         @Param("expertize_realm") String expertize_realm, @Param("compensation") String compensation,
                         @Param("workexperience") String workexperience,@Param("expertize_level") String expertize_level,
                         @Param("biography") String biography,@Param("user_id") int user_id);
}
