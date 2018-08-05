package com.trader.repository.redis;

import com.trader.domain.redis.MarketOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class MarketOrderRepository{
    @Autowired
    private RedisTemplate redisTemplate;
    public void save(MarketOrder marketOrder){
        //redisTemplate.opsForValue().set(marketOrder.getMarket(), marketOrder);
        HashOperations<String, Double, MarketOrder> hashOps = redisTemplate.opsForHash();
        hashOps.put(marketOrder.getMarket(), marketOrder.getAmount(), marketOrder);
    }
    //MarketOrder findById(long marketId);
    public MarketOrder findByMarketAndAmount(String market, Double amount){
        HashOperations<String, Double, MarketOrder> hashOps = redisTemplate.opsForHash();
        return hashOps.get(market, amount);
    }
    /*MarketOrder findByMarket(String market);*/

}
