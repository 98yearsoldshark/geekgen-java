package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.ClassTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName ClassTaskMapper
 * @Description
 * @Author yifan
 * @date 2025-02-25 22:43
 **/

@Mapper
public interface ClassTaskMapper {

    ClassTask getById(Long id);

    List<ClassTask> getByClassId(Long classId);

    List<ClassTask> getByTeacherId(Long teacherId);

    void insert(ClassTask classTask);

    void update(ClassTask classTask);

}
