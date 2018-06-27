package lbx.xtools;


import com.google.gson.annotations.SerializedName;

/**
 * @author lbx
 * @date 2018/6/6.
 * 泛型解析类
 */

public class Result<T> {

    @SerializedName(value = "code", alternate = {"resultCode"})
    private int code;
    @SerializedName(value = "message", alternate = {"info", "resultMsg"})
    private String message;
    @SerializedName(value = "data", alternate = {"res", "resultData"})
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
