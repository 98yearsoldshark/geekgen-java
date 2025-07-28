package cn.yifan.geekgen.pojo.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @FileName TextUnit
 * @Description
 * @Author yifan
 * @date 2025-02-28 16:26
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextUnit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String word;

    private String level;

    private String lemma;

}
