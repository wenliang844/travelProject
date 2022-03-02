package cn.itcast.travel.service.impl;


import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户的方法
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //判断这个u是否为null
        if (u!=null){
            //用户名存在 注册失败
            return false;
        }

        //2.保存用户信息
            //2.1设置激活码,唯一字符串 uuid生成一个全球唯一的字符串;保证唯一;
        user.setCode(UuidUtil.getUuid());
            //2.2设置激活的状态
        user.setStatus("N");
        userDao.save(user);

        //3.激活邮件发送
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活[黑马旅游网]</a>";

        MailUtils.sendMail(user.getEmail(),content,"激活邮件文亮");

        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象  存在 正确 完成激活码修改状态的方法
        User user = userDao.findByCode(code);
        if (user != null){
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 登录方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
