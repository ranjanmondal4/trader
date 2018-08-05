package com.trader.domain.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

//@RedisHash("MarketOrder")
public class MarketOrder implements Serializable{

    /*@Id
    private Long id;*/
   // private String id;
    private String market;
    private double amount;

    public MarketOrder() {
    }

    public MarketOrder(String market, double amount) {
        this.market = market;
        this.amount = amount;
     //   this.id = market;
    }

   /* public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }*/

    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
