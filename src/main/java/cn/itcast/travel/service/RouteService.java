package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.sql.SQLException;

public interface RouteService {
    /**
     * 根据类别进行分类查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     * @throws SQLException
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) throws SQLException;

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
