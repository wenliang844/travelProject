package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * g根据roiute的id查询图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
