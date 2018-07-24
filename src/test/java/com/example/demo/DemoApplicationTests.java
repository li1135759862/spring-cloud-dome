package com.example.demo;

import com.ccbim.project.entity.Task;
import com.ccbim.project.mapper.TaskMapper;
import com.ccbim.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Test
    public void contextLoads() {
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForValue().set(String.valueOf(i),String.valueOf(i));
            System.out.println(redisTemplate.opsForValue().get(String.valueOf(i)));
        }
    }

}
