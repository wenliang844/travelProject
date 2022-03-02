package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {


        //1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        request.setCharacterEncoding("utf-8");
        //接收参数rname
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        //接收rname   线路名称
        //String rname = request.getParameter("rname");
        //rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        int cid = 0;//类别id
        //2.处理参数
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }

        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }

        int pageSize = 0;//每页展示的条数  默认第一页   5条
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        //3.调用service查询一个paheBean对象
        PageBean<Route> pb = routeService.pageQuery((cid), currentPage, pageSize, rname);

        System.out.println(pb);
        //4.将pageBean对象序列化为json返回到页面上
        writeValue(pb, response);

    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //1.接收rid
        String rid = request.getParameter("rid");


        //2.调用service查询一个route对象
         Route route = routeService.findOne(rid);
        //3json返回客户端
        writeValue(route,response);
    }

    /**
     * 判断是否收藏当前用户登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //1.获取线路id
        String rid = request.getParameter("rid");

        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if (user == null){
            //用户没登录
            uid=0;
        }else{
            //用户已经登录
            uid=user.getUid();
        }

        //3.调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        //4.把flag写回客户端
        writeValue(flag,response);

    }

    /**
     * 添加收藏的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        //1.获取线路id
        String rid = request.getParameter("rid");

        //2.获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if (user == null){
            //用户没登录
            return ;
        }else{
            //用户已经登录
            uid=user.getUid();
        }
        //--------------------------------------------------------error
        uid = 7;
        System.out.println("RouteServlet--addFavorite------------------"+rid+uid);
        //3.调用service添加
        favoriteService.add(rid,uid);

    }
}
