package bluecrunch.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karim on 10/31/18.
 */

public class BaseResponse<T> {

    @SerializedName("code")
    int code;
    @SerializedName("error")
    String error ;
    @SerializedName("data")
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
