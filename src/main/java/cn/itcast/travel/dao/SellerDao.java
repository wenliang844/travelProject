package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {

    /**
     * 根据id查询
     * @return
     */
    public Seller findById(int id);
}
