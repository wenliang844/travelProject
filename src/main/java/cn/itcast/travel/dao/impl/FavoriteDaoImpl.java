package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try{
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            System.out.println("这是sql===11111111111------"+sql);
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("请注意:这不是错误,这只是防范域未然@@@!!!");
        }
        return favorite;
    }

    /**
     * 根据rid查询收藏的数量
     * @param rid
     * @return
     */
    public int findCountByRid(int rid) {
        int c=0;
        try{
            String sql = "SELECT count(*) FROM tab_favorite WHERE rid = ? ";
            System.out.println("这是rid---------"+rid+sql);
            c = template.queryForObject(sql,Integer.class,rid);
            System.out.println(sql);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error==============================eroor");
        }
        System.out.println("这是收藏的次数---------------------"+c);
        return c;
    }

    @Override
    public void add(int rid, int uid) {
        System.out.println("add=================================================");
        String sql ="insert into tab_favorite value(?,?,?)";
        System.out.println("这是添加的代码sql"+sql);
        template.update(sql,rid,new Date(),uid);
    }
}
