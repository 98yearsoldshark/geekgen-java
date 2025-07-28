package cn.yifan.geekgen.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.yifan.geekgen.constant.UserRole;
import cn.yifan.geekgen.pojo.dto.CreateClassDTO;
import cn.yifan.geekgen.pojo.dto.CreateClassTaskDTO;
import cn.yifan.geekgen.pojo.vo.ArticleTextVO;
import cn.yifan.geekgen.pojo.vo.ClassVO;
import cn.yifan.geekgen.pojo.vo.TeacherClassTaskVO;
import cn.yifan.geekgen.service.business.ClassTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName ClassTeacherController
 * @Description
 * @Author yifan
 * @date 2025-02-28 18:35
 **/

@RestController
@RequestMapping("/class/t")
@SaCheckRole(UserRole.TEACHER)
public class ClassTeacherController {

    @Autowired
    private ClassTeacherService classTeacherService;

    @PostMapping("/classes")
    public void createClass(@RequestBody CreateClassDTO createClassDTO) {
        classTeacherService.createClass(createClassDTO);
    }

    @GetMapping("/classes")
    public List<ClassVO> getClasses() {
        return classTeacherService.getClasses();
    }

    @PostMapping("/tasks")
    public void createTask(@RequestBody CreateClassTaskDTO createClassTaskDTO) {
        classTeacherService.createTask(createClassTaskDTO);
    }

    @GetMapping("/tasks")
    public List<TeacherClassTaskVO> getTasks() {
        return classTeacherService.getTasks();
    }

    @GetMapping("/tasks/{id}")
    public ArticleTextVO getTaskDetail(@PathVariable("id") Long id) {
        return classTeacherService.getTaskDetail(id);
    }

}
