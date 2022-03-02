package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.sql.SQLException;
import java.util.List;

public interface RouteDao {

    /**
     *1.根据cid查询总记录数
     */
    public int findTotalCount(int cid, String rname) throws SQLException;

    /**
     * 2.根据cid,start,pagesize查询当前页的数据集合   limit?  ?
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 3.根据id查询
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
