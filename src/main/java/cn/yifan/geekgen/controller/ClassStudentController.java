package cn.yifan.geekgen.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.yifan.geekgen.constant.UserRole;
import cn.yifan.geekgen.pojo.dto.CreateClassDTO;
import cn.yifan.geekgen.pojo.dto.CreateClassTaskDTO;
import cn.yifan.geekgen.pojo.dto.FinishClassTaskDTO;
import cn.yifan.geekgen.pojo.dto.JoinClassDTO;
import cn.yifan.geekgen.pojo.vo.ArticleTextVO;
import cn.yifan.geekgen.pojo.vo.ClassVO;
import cn.yifan.geekgen.pojo.vo.StudentClassTaskVO;
import cn.yifan.geekgen.pojo.vo.TeacherClassTaskVO;
import cn.yifan.geekgen.service.business.ClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName ClassStudentController
 * @Description
 * @Author yifan
 * @date 2025-02-28 18:35
 **/

@RestController
@RequestMapping("/class/s")
@SaCheckRole(UserRole.STUDENT)
public class ClassStudentController {

    @Autowired
    private ClassStudentService classStudentService;

    @PostMapping("/classes")
    public void joinClass(@RequestBody JoinClassDTO joinClassDTO) {
        classStudentService.joinClass(joinClassDTO);
    }

    @GetMapping("/classes")
    public List<ClassVO> getClasses() {
        return classStudentService.getClasses();
    }

    @PostMapping("/tasks")
    public void finishTask(@RequestBody FinishClassTaskDTO finishClassTaskDTO) {
        classStudentService.finishTask(finishClassTaskDTO);
    }

    @GetMapping("/tasks")
    public List<StudentClassTaskVO> getTasks() {
        return classStudentService.getTasks();
    }

    @GetMapping("/tasks/{id}")
    public ArticleTextVO getTaskDetail(@PathVariable("id") Long id) {
        return classStudentService.getTaskDetail(id);
    }

}
