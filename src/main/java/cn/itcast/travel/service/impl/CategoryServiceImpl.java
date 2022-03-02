package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis查询
        //1.1获取jedis客户端
        Jedis jedis = new Jedis();
        //1.2可使用jedis的sortedset查询
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3要查询sortedset中的分数(cid)和值cname
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs = null;
        //2.判断查询的集合是否为空
        if (categorys == null || categorys.size() == 0) {
            System.out.println("从数据库查询。。。");
            //3.如果为空就是第一次访问,需要从数据库查询 将数据存入redis
            //3.1从数据库查询
            cs = categoryDao.findAll();
            //3.2将其存到redis中 categoty的key中soredset
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else{
            System.out.println("从redis查询。。。");
            //4.如果不为空  直接返回就可以了
            //将set的数据存入一个list
            cs=new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                cs.add(category);
            }
        }



        return cs;
    }
}
