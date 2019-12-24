package com.example.myelastisearch.controller;

import com.example.myelastisearch.qo.PageResult;
import com.example.myelastisearch.qo.QueryObject;
import com.example.myelastisearch.service.MyIndexService;
import com.example.myelastisearch.utils.Result;
import com.example.myelastisearch.utils.ResultUtil;
import com.example.myelastisearch.vo.EmployeeVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/myindex")
@Slf4j
public class MyIndexController {

    @Autowired
    private MyIndexService indexService;

    @ApiOperation(value = "查询所有员工", notes = "查询所有员工列表", tags = {"员工模块接口"})
    @ResponseBody
    @RequestMapping(value = "/getAllEmployee", method = RequestMethod.GET)
    public Result getAllEmployee() {
        indexService.getAllEmployeeVo();

        return ResultUtil.success(indexService.getAllEmployeeVo());
    }

    @ApiOperation(value = "添加员工", notes = "添加员工", tags = {"员工模块接口"})
    @ResponseBody
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public Result insertEmployee(@ApiParam(value = "员工信息", required = true) @Valid @RequestBody EmployeeVo employeevo) {
        indexService.addOrUpdateEmployee(employeevo);
        return ResultUtil.success();
    }

    @ApiOperation(value = "修改员工信息", notes = "修改员工信息", tags = "员工模块接口")
    @ResponseBody
    @RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
    public Result updateEmployee(@ApiParam(value = "员工信息", required = true) @Valid @RequestBody EmployeeVo employeeVo) {
        indexService.addOrUpdateEmployee(employeeVo);
        return ResultUtil.success();
    }

    @ApiOperation(value = "获取员工信息", notes = "获取员工信息", tags = "员工模块接口")
    @ResponseBody
    @RequestMapping(value = "/getEmployee{id}", method = RequestMethod.GET)
    public Result getEmployee(@ApiParam(value = "员工id", required = true) @Valid @PathVariable String id) {

        EmployeeVo employeeById = indexService.getEmployeeById(id);
        return ResultUtil.success(employeeById);
    }

    @ApiOperation(value = "删除员工",notes = "删除员工",tags = "员工模块接口")
    @ResponseBody
    @RequestMapping(value = "/deleteEmployee{id}",method = RequestMethod.GET)
    public Result deleteEmployee(@ApiParam(value = "员工id",required = true) @Valid @PathVariable String id ){
        try {
            indexService.deleteEmployeeById(id);
            return ResultUtil.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error("error","服务器内部错误!");
    }

    @ApiOperation(value = "查询员工",notes = "查询员工信息",tags = "员工模块接口")
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Result queryByParam(@ApiParam(value = "查询字段",required = true) @Valid @RequestBody QueryObject qo){
        try {
            return ResultUtil.success(indexService.queryEmployee(qo));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success("");
    }
}
