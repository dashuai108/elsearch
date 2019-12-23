package com.example.myelastisearch.service;

import com.example.myelastisearch.qo.PageResult;
import com.example.myelastisearch.qo.QueryObject;
import com.example.myelastisearch.repository.IArticleRepository;
import com.example.myelastisearch.repository.IEmPloyeeRepository;
import com.example.myelastisearch.vo.EmployeeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MyIndexService {



    @Autowired
    private IArticleRepository articleRepository;

    @Autowired
    private IEmPloyeeRepository emPloyeeRepository;

    public void setEmPloyeeRepository(IEmPloyeeRepository emPloyeeRepository){
        this.emPloyeeRepository = emPloyeeRepository;
    }

    public void setiArticleRepository(IArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }


    /**
     * 初始化数据，把数据库里的国际化都读入缓存的Map里
     */
    @PostConstruct
    public void init() {
        this.emPloyeeRepository = this.emPloyeeRepository;
        this.articleRepository = this.articleRepository;
    }

    /**
     * 获取所有员工信息
     * @return List<EmployeeVo>
     * */
    public List<EmployeeVo> getAllEmployeeVo(){
        try {
           return emPloyeeRepository.getAll();
        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /*
    *  添加或更新用户信息用户信息
    *  @Param
    * */
    public void addOrUpdateEmployee(EmployeeVo vo){
        try {
            emPloyeeRepository.insertOrUpdate(vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    * 根据id查询员工信息
    * @return Employeevo
    * */
    public EmployeeVo getEmployeeById(String id){
        try {
            return emPloyeeRepository.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 根据id删除员工
    * @param id
    * */
    public void deleteEmployeeById(String id) throws Exception{
        emPloyeeRepository.delete(id);
    }

    public PageResult queryEmployee(QueryObject qo)throws Exception{
//        QueryObject qo = new QueryObject();
        PageResult<EmployeeVo> search = emPloyeeRepository.search(qo);

        return emPloyeeRepository.search(qo);
    }
}

