package cn.yifan.geekgen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @FileName WordVO
 * @Description
 * @Author yifan
 * @date 2025-03-01 13:30
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Object> core;

    private Map<String, Object> relation;

    private Map<String, Object> ai;

    private List<Object> articles;

}
