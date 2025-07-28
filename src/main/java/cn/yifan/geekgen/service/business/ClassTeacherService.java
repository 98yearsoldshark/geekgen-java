package cn.yifan.geekgen.service.business;

import cn.dev33.satoken.stp.StpUtil;
import cn.yifan.geekgen.constant.ArticleIdPrefix;
import cn.yifan.geekgen.constant.ClassTaskStatus;
import cn.yifan.geekgen.constant.UserRole;
import cn.yifan.geekgen.exception.ApiError;
import cn.yifan.geekgen.exception.ApiException;
import cn.yifan.geekgen.mapper.*;
import cn.yifan.geekgen.pojo.dto.CreateClassDTO;
import cn.yifan.geekgen.pojo.dto.CreateClassTaskDTO;
import cn.yifan.geekgen.pojo.entity.*;
import cn.yifan.geekgen.pojo.entity.Class;
import cn.yifan.geekgen.pojo.mongo.TextUnit;
import cn.yifan.geekgen.pojo.vo.ArticleTextVO;
import cn.yifan.geekgen.pojo.vo.ClassVO;
import cn.yifan.geekgen.pojo.vo.TeacherClassTaskVO;
import cn.yifan.geekgen.service.base.ApiService;
import cn.yifan.geekgen.utils.ClassCodeUtil;
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

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName ClassTeacherService
 * @Description
 * @Author yifan
 * @date 2025-02-28 18:36
 **/

@Service
@Slf4j
public class ClassTeacherService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApiService apiService;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private ClassMemberMapper classMemberMapper;
    @Autowired
    private ClassTaskMapper classTaskMapper;
    @Autowired
    private TaskProgressMapper taskProgressMapper;

    @Transactional
    public void createClass(CreateClassDTO createClassDTO) {
        String className = createClassDTO.getClassName();
        Class clazz = classMapper.getByName(className);
        if (clazz != null) {
            throw new ApiException(ApiError.CLASS_HAD_EXISTED);
        }
        String classCode = null;
        do {
            classCode = ClassCodeUtil.generateClassCode();
            clazz = classMapper.getByCode(classCode);
        } while (clazz != null);
        Long teacherId = StpUtil.getLoginIdAsLong();
        User teacher = userMapper.getById(teacherId);
        Class newClass = new Class(
            classCode, className, teacherId, teacher.getName(), 0L
        );
        classMapper.insert(newClass);
        ClassMember classMember = new ClassMember(newClass.getId(), teacherId, UserRole.TEACHER);
        classMemberMapper.insert(classMember);
    }

    public List<ClassVO> getClasses() {
        Long teacherId = StpUtil.getLoginIdAsLong();
        List<Class> classes = classMapper.getByTeacherId(teacherId);
        List<ClassVO> classVOS = new ArrayList<>();
        for (Class clazz : classes) {
            ClassVO classVO = new ClassVO();
            BeanUtils.copyProperties(clazz, classVO);
            classVOS.add(classVO);
        }
        return classVOS;
    }

    @Transactional
    public void createTask(CreateClassTaskDTO createClassTaskDTO) {
        Class clazz = classMapper.getByCode(createClassTaskDTO.getClassCode());
        if (clazz == null) {
            throw new ApiException(ApiError.CLASS_NOT_EXIST);
        }
        Long teacherId = StpUtil.getLoginIdAsLong();
        if (!clazz.getTeacherId().equals(teacherId)) {
            throw new ApiException(ApiError.CLASS_TEACHER_ERROR);
        }
        // analyze content
        List<List<List<TextUnit>>> analyzedText = apiService.textAnalyze(createClassTaskDTO.getContent());
        ObjectMapper objectMapper = new ObjectMapper();
        String data = null;
        try {
            data = objectMapper.writeValueAsString(analyzedText);
        } catch (JsonProcessingException e) {
            log.error("对文本进行分析时序列化错误，错误信息：{}", e.getMessage());
            throw new ApiException(ApiError.SYSTEM_ERROR);
        }
        // class_task
        ClassTask classTask = new ClassTask(
            createClassTaskDTO.getType(),
            createClassTaskDTO.getTitle(),
            data,
            clazz.getName(),
            clazz.getId(),
            teacherId,
            clazz.getStudentCount(),
            0L
        );
        classTaskMapper.insert(classTask);
        // task_progress
        List<ClassMember> classMembers = classMemberMapper.getByClassId(clazz.getId());
        for (ClassMember classMember : classMembers) {
            if (classMember.getRole().equals(UserRole.STUDENT)) {
                // TODO 优化，ClassMember 能不能冗余User:name
                User user = userMapper.getById(classMember.getUserId());
                TaskProgress taskProgress = new TaskProgress(
                    classTask.getId(), classTask.getTitle(), classTask.getType(),
                    user.getId(), user.getName(), clazz.getName(), ClassTaskStatus.UNFINISHED
                );
                taskProgressMapper.insert(taskProgress);
            }
        }
    }

    public List<TeacherClassTaskVO> getTasks() {
        Long teacherId = StpUtil.getLoginIdAsLong();
        List<ClassTask> classTasks = classTaskMapper.getByTeacherId(teacherId);
        List<TeacherClassTaskVO> teacherClassTaskVOS = new ArrayList<>();
        for (ClassTask classTask : classTasks) {
            TeacherClassTaskVO teacherClassTaskVO = new TeacherClassTaskVO();
            BeanUtils.copyProperties(classTask, teacherClassTaskVO);
            teacherClassTaskVO.setTime(TimeUtil.formatTimestamp(classTask.getCreatedAt()));
            teacherClassTaskVOS.add(teacherClassTaskVO);
        }
        return teacherClassTaskVOS;
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
