package com.shop.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.R.Result;
import com.shop.domain.dto.TypeDto;
import com.shop.domain.pojo.Type;
import com.shop.service.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags={"分类接口"})
@CrossOrigin
@RestController
@RequestMapping("/type")
public class TypeController {
        @Autowired
        private TypeService typeService;


        @ApiOperation("根据分类ID查询分类")
        @GetMapping("/typeId/{typeId}")
        public Result getTypeById(@PathVariable String typeId) {
                Result r = typeService.getTypeById(typeId);
                return r;
        }

        @ApiOperation("根据分类名查询分类")
        @GetMapping("/name/{typeName}")
        public Result getTypeByName(@PathVariable String typeName) {
                return typeService.getTypeByName(typeName);
        }




        @ApiOperation("添加新分类")
        @PostMapping("/add")
        public Result addType(@RequestBody TypeDto typeDto) {
                Type type = Convert.convert(Type.class, typeDto);
                Result r = typeService.addType(type);
                return r;
        }

        @ApiOperation("修改分类信息")
        @PutMapping("/update")
        public Result update(@RequestBody TypeDto typeDto) {
                Type type = Convert.convert(Type.class, typeDto);
                return typeService.updateType(type);
        }

        @ApiOperation("删除分类")
        @DeleteMapping("/delete/{typeId}")
        public Result deleteType(@PathVariable String typeId) {
                return  typeService.deleteType(typeId);
        }


        @ApiOperation("获取全部分类")
        @GetMapping("/getList")
        public Result getTypeList() {
                return   typeService.getTypeList();
        }
}


