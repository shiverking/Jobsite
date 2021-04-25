package project.model;

/**
 * @author ：闫崇傲
 * @description：角色类
 * @date ：2021/4/25 16:23
 */
public class Role {
        //角色ID
        private Integer id;
        //角色编号
        private String name;
        //角色名称
        private String nameZh;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameZh() {
            return nameZh;
        }

        public void setNameZh(String nameZh) {
            this.nameZh = nameZh;
        }
}
