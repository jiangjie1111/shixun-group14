package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.Category;
import com.example.demo.mapper.CategoryMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Resource
    CategoryMapper CategoryMapper;

    @PostMapping
    public Result<?> save(@RequestBody Category Category) {
        CategoryMapper.insert(Category);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Category Category) {
        CategoryMapper.updateById(Category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        CategoryMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        return Result.success(CategoryMapper.selectById(id));
    }

    /**
     * 分类父子查询
     *
     * @return
     */
    @GetMapping
    public Result<?> getAll() {
        // 先查询所有的数据
        List<Category> allCategories = CategoryMapper.selectList(null);
        return Result.success(loopQuery(null, allCategories));
    }

    /**
     * 递归查询子集
     *
     * @param pid
     * @param allCategories
     * @return
     */
    private List<Category> loopQuery(Integer pid, List<Category> allCategories) {
        List<Category> categoryList = new ArrayList<>();
        for (Category category : allCategories) {
            if (pid == null) {
                if (category.getPid() == null) {
                    categoryList.add(category);
                    // 继续递归查询子集
                    category.setChildren(loopQuery(category.getId(), allCategories));
                }
            } else if (pid.equals(category.getPid())) {
                categoryList.add(category);
                // 继续递归查询子集
                category.setChildren(loopQuery(category.getId(), allCategories));
            }
        }
        return categoryList;
    }
}
