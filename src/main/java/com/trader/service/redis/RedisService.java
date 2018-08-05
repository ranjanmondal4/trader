package com.trader.service.redis;

import com.trader.domain.redis.MarketOrder;
import com.trader.repository.redis.MarketOrderRepository;
import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private MarketOrderRepository marketOrderRepository;

    public Response addMarketOrder(String market, double amount){
        MarketOrder marketOrder = new MarketOrder(market, amount);
        marketOrderRepository.save(marketOrder);
        return ResponseHandler.generateServiceResponse(true, "Success", marketOrder, null);
    }

  public Response getMarketOrder(String market, double amount){
        MarketOrder marketOrder = marketOrderRepository.findByMarketAndAmount(market, amount);
        if(marketOrder != null){
            return ResponseHandler.generateServiceResponse(true, "Success", marketOrder, null);
        }
        return ResponseHandler.generateServiceResponse(false, "Error", null, null);
    }
 /*
    public Response updateMarketOrder(long marketId, double amount){
        MarketOrder marketOrder = null; //marketOrderRepository.findById(marketId);
        if(marketOrder != null){
            marketOrder.setAmount(amount);
            marketOrder = marketOrderRepository.save(marketOrder);
            if(marketOrder != null)
                return ResponseHandler.generateServiceResponse(true, "Updated Successfully", marketOrder, null);
        }
        return ResponseHandler.generateServiceResponse(false, "Error", null, null);
    }

    public Response deleteMarketOrder(long marketId){
        MarketOrder marketOrder = null;//marketOrderRepository.findById(marketId);
        if(marketOrder != null){
            marketOrderRepository.delete(marketOrder);
            return ResponseHandler.generateServiceResponse(true, "Deleted Successfully", marketOrder, null);
        }
        return ResponseHandler.generateServiceResponse(false, "Error", null, null);
    }*/
}
