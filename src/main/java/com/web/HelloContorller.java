package com.web;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class HelloContorller {

    @Transactional
    List<String> findList(){
        return null;
    }

    @Transactional(transactionManager="cloudTransaction")
    List<String> findOne(){
        return null;
    }
}
