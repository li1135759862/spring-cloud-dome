package com.ccbim.web;

import com.ccbim.project.entity.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController(value = "/front/task")
@Api(basePath = "/task",
        description = "任务",
        tags = "任务接口",
        value = "/front/task")
public class TaskController {

    @ApiOperation(value = "任务列表接口",notes = "任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "项目名称",value = "projectId",required = true)})
    @PostMapping("/list")
    public List<Task> list(){
        return new ArrayList<Task>();
    }

    @PostMapping("/add")
    public Task add(){
        return new Task();
    }

    @PostMapping("/del")
    void del(){

    }

    @PostMapping("/update")
    void update(){

    }
}
