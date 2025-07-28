package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.TaskProgress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName TaskProgressMapper
 * @Description
 * @Author yifan
 * @date 2025-02-25 22:47
 **/

@Mapper
public interface TaskProgressMapper {

    TaskProgress getById(Long id);

    List<TaskProgress> getByTaskId(Long taskId);

    List<TaskProgress> getByUserId(Long userId);

    TaskProgress getByTaskIdAndUserId(Long taskId, Long userId);

    void insert(TaskProgress taskProgress);

    void update(TaskProgress taskProgress);

}
