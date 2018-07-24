package com.ccbim.service;

import com.ccbim.project.entity.Task;
import com.ccbim.project.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @CachePut(value = "task")
    public Task findById(Integer id) {
        return taskMapper.selectByPrimaryKey(id);
    }

}
