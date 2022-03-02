package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {

        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        return favorite!=null;//有值就是true  没值就是false
    }

    @Override
    public void add(String rid, int uid) {
        System.out.println("FavoriteService---------------------------------"+rid+"---"+uid);
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
