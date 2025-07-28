package cn.yifan.geekgen.exception;

import lombok.Getter;

/**
 * @FileName ApiException
 * @Description
 * @Author yifan
 * @date 2025-01-28 20:34
 **/

@Getter
public class ApiException extends RuntimeException {

    private final ApiError error;

    public ApiException(ApiError error) {
        super(error.getMessage());
        this.error = error;
    }

}
