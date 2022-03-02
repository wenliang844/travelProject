package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.sql.SQLException;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDaoImpl favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) throws SQLException {
        //封装一个PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页展示条数
        pb.setPageSize(pageSize);
        //设置一下总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //设置当前页显示的集合
        List<Route> list;
        int start = (currentPage - 1) * pageSize;//就是开始的记录数
        list = routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        pb.setTotalPage(totalPage);
        System.out.println("pbservice"+pb);
        return pb;
    }

    /**
     *查询单个详情
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //根据id去routeDao中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //根据route的 id查询imgList
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //2.2将img集合设置到route对象
        route.setRouteImgList(routeImgList);
        //3.根据route的sid查询卖家的id  商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);

        //查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }
}
