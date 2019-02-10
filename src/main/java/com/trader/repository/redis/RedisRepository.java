package com.trader.repository.redis;

import com.trader.domain.movie.Movie;
import com.trader.domain.redis.MarketOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Map;

@Repository
public class RedisRepository {

    private static final String KEY = "MOVIE";
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisRepository(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    public void add(final Movie movie) {
        hashOperations.put(KEY, movie.getId(), movie.getName());
    }

    public void delete(final String id) {
        hashOperations.delete(KEY, id);
    }
    public Movie findMovie(final String id){
        return (Movie) hashOperations.get(KEY, id);
    }
    public Map<Object, Object> findAllMovies(){
        return hashOperations.entries(KEY);
    }

/*    @Autowired
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
    }*/
    /*MarketOrder findByMarket(String market);*/

}
