package com.trader.controller.redis;

import com.trader.domain.movie.Movie;
import com.trader.repository.redis.RedisRepository;
import com.trader.service.redis.RedisService;
import com.trader.utils.Response;
import com.trader.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RedisController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisRepository redisRepository;

    @RequestMapping(value = "/movie", method = RequestMethod.POST)
    public ResponseEntity<String> add( @RequestParam String key, @RequestParam String value) {
        Movie movie = new Movie(key, value);
        redisRepository.add(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public ResponseEntity<Object>findAll() {
        Map<Object, Object> movieMap = redisRepository.findAllMovies();
        Map<String, String> map = new HashMap<>();
        for(Map.Entry<Object, Object> entry : movieMap.entrySet()){
            String key = (String) entry.getKey();
            map.put(key, movieMap.get(key).toString());
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
