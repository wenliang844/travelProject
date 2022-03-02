package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotalCount(int cid, String rname) throws SQLException {//多条件的组合查询
//        String sql = "select count(*) from tab_route where cid = ?";
        //1.定义一个sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();//条件们  可能有 可能没有
        //2.判断参数是否有值
        if (cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);//添加?对应得值
        }
        if (rname != null&&!"null".equals(rname)){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();

        System.out.println(sql);
        //System.out.println("这是cid------"+cid);
        int i=1;
        i = template.queryForObject(sql, Integer.class, params.toArray());
        /*Connection conn = JDBCUtils.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        //preparedStatement.setInt(1,cid);
        int j=1;
        for (Object param : params) {
            preparedStatement.setInt(j++, Integer.parseInt(param.toString()));
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        //i = resultSet.getInt(1);
        while(resultSet.next())
        {
        //打印的就是总记录数。把检索结果看成只有一跳记录一个字段的表
            i=resultSet.getInt(1);
            System.out.println("这是while方法的cid---"+i);
        }

        System.out.println(cid+"----"+"这是tatoalCount"+i);
        resultSet.close();
        preparedStatement.close();
        conn.close();*/
        return i;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //String sql = "select * from tab_route where limit ? , ?";
        String sql = "select * from tab_route where 1=1 ";

        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();//条件们  可能有 可能没有
        //2.判断参数是否有值
        if (cid != 0){
            sb.append(" and cid = ? ");
            params.add(cid);//添加?对应得值
        }



        if (rname != null &&!"null".equals(rname)){// || !"null".equals(rname)
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件
        sql = sb.toString();
        System.out.println("这是rname=---"+rname);

        params.add(start);
        params.add(pageSize);
        System.out.println("这是sql---"+sql);
        List<Route> routeList = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        System.out.println("这是list---"+routeList);
        return routeList;
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }
}
