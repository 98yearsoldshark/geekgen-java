package cn.yifan.geekgen.service.business;

import cn.dev33.satoken.stp.StpUtil;
import cn.yifan.geekgen.constant.ArticleIdPrefix;
import cn.yifan.geekgen.constant.ClassTaskStatus;
import cn.yifan.geekgen.constant.UserRole;
import cn.yifan.geekgen.exception.ApiError;
import cn.yifan.geekgen.exception.ApiException;
import cn.yifan.geekgen.mapper.*;
import cn.yifan.geekgen.pojo.dto.FinishClassTaskDTO;
import cn.yifan.geekgen.pojo.dto.JoinClassDTO;
import cn.yifan.geekgen.pojo.entity.*;
import cn.yifan.geekgen.pojo.entity.Class;
import cn.yifan.geekgen.pojo.mongo.TextUnit;
import cn.yifan.geekgen.pojo.vo.ArticleTextVO;
import cn.yifan.geekgen.pojo.vo.ClassVO;
import cn.yifan.geekgen.pojo.vo.StudentClassTaskVO;
import cn.yifan.geekgen.utils.TimeUtil;
import cn.yifan.geekgen.utils.WordCountUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName ClassStudentService
 * @Description
 * @Author yifan
 * @date 2025-02-28 18:36
 **/

@Service
@Slf4j
public class ClassStudentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private ClassMemberMapper classMemberMapper;
    @Autowired
    private ClassTaskMapper classTaskMapper;
    @Autowired
    private TaskProgressMapper taskProgressMapper;

    @Transactional
    public void joinClass(JoinClassDTO joinClassDTO) {
        // 检验班级是否存在
        Class clazz = classMapper.getByCode(joinClassDTO.getClassCode());
        if (clazz == null) {
            throw new ApiException(ApiError.CLASS_NOT_EXIST);
        }
        // class
        clazz.setStudentCount(clazz.getStudentCount() + 1);
        classMapper.update(clazz);
        // class_member
        Long userId = StpUtil.getLoginIdAsLong();
        ClassMember classMember = new ClassMember(
            clazz.getId(), userId, UserRole.STUDENT
        );
        classMemberMapper.insert(classMember);
        // class_task and task_progress
        User user = userMapper.getById(userId);
        List<ClassTask> classTasks = classTaskMapper.getByClassId(clazz.getId());
        for (ClassTask classTask : classTasks) {
            classTask.setStudentCount(classTask.getStudentCount() + 1);
            classTaskMapper.update(classTask);
            TaskProgress taskProgress = new TaskProgress(
                classTask.getId(), classTask.getTitle(), classTask.getType(),
                userId, user.getName(), clazz.getName(), ClassTaskStatus.UNFINISHED
            );
            taskProgressMapper.insert(taskProgress);
        }
    }

    public List<ClassVO> getClasses() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<ClassMember> classMembers = classMemberMapper.getByUserId(userId);
        List<ClassVO> classVOS = new ArrayList<>();
        for (ClassMember classMember : classMembers) {
            ClassVO classVO = new ClassVO();
            Class clazz = classMapper.getById(classMember.getClassId());
            BeanUtils.copyProperties(clazz, classVO);
            classVOS.add(classVO);
        }
        return classVOS;
    }

    @Transactional
    public void finishTask(FinishClassTaskDTO finishClassTaskDTO) {
        Long userId = StpUtil.getLoginIdAsLong();
        // 校验任务是否存在
        ClassTask classTask = classTaskMapper.getById(finishClassTaskDTO.getTaskId());
        if (classTask == null) {
            throw new ApiException(ApiError.CLASS_TASK_NOT_EXIST);
        }
        // 校验任务是否属于该用户
        TaskProgress taskProgress = taskProgressMapper.getByTaskIdAndUserId(classTask.getId(), userId);
        if (taskProgress == null) {
            throw new ApiException(ApiError.CLASS_TASK_NOT_EXIST);
        }
        // 校验任务是否已经完成
        if (taskProgress.getStatus().equals(ClassTaskStatus.FINISHED)) {
            return; // TODO
        }
        // update task_progress
        taskProgress.setStatus(ClassTaskStatus.FINISHED);
        taskProgressMapper.update(taskProgress);
        // update class_task
        classTask.setFinishCount(classTask.getFinishCount() + 1);
        classTaskMapper.update(classTask);
    }

    public List<StudentClassTaskVO> getTasks() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<TaskProgress> taskProgresses = taskProgressMapper.getByUserId(userId);
        List<StudentClassTaskVO> studentClassTaskVOS = new ArrayList<>();
        for (TaskProgress taskProgress : taskProgresses) {
            StudentClassTaskVO studentClassTaskVO = new StudentClassTaskVO();
            studentClassTaskVO.setId(taskProgress.getTaskId());
            studentClassTaskVO.setTitle(taskProgress.getTaskTitle());
            studentClassTaskVO.setType(taskProgress.getTaskType());
            studentClassTaskVO.setClassName(taskProgress.getClassName());
            studentClassTaskVO.setStatus(taskProgress.getStatus());
            studentClassTaskVO.setTime(TimeUtil.formatTimestamp(taskProgress.getCreatedAt()));
            studentClassTaskVOS.add(studentClassTaskVO);
        }
        return studentClassTaskVOS;
    }

    public ArticleTextVO getTaskDetail(Long id) {
        ClassTask classTask = classTaskMapper.getById(id);
        if (classTask == null) {
            throw new ApiException(ApiError.CLASS_TASK_NOT_EXIST);
        }
        ArticleTextVO articleTextVO = new ArticleTextVO();
        articleTextVO.setArticleId(ArticleIdPrefix.TASK + classTask.getId());
        articleTextVO.setTitle(classTask.getTitle());
        // text
        List<List<List<TextUnit>>> text = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 使用 TypeReference 处理泛型类型信息
            TypeReference<List<List<List<TextUnit>>>> typeReference = new TypeReference<List<List<List<TextUnit>>>>() {};
            text = objectMapper.readValue(classTask.getData(), typeReference);
            articleTextVO.setText(text);
        } catch (JsonProcessingException e) {
            // 记录异常信息
            log.error("将ClassTask:data字符串转换为 List<List<List<TextUnit>>> 类型时出现错误", e);
            throw new ApiException(ApiError.SYSTEM_ERROR);
        }
        // tags
        List<String> tags = new ArrayList<>();
        tags.add(classTask.getClassName());
        tags.add(WordCountUtil.countWords(text)+"词");
        tags.add(TimeUtil.formatTimestamp(classTask.getCreatedAt()));
        articleTextVO.setTags(tags);

        return articleTextVO;
    }
}
