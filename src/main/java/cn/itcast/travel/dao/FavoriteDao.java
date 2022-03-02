package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据rid和uid插叙收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    public int findCountByRid(int rid);

    /**
     * 添加收藏的方法
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);
}
