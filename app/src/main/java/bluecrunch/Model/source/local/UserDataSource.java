package bluecrunch.Model.source.local;


import com.google.gson.Gson;

import bluecrunch.base.BaseApplication;
import bluecrunch.Model.User;
import bluecrunch.utils.Constants;
import bluecrunch.utils.DataUtil;

public class UserDataSource {

    public static void saveUser(User user) {
        DataUtil.saveData(BaseApplication.getContext(), Constants.USER, new Gson().toJson(user));
    }

    public static User getUser() {
        return new Gson().fromJson(DataUtil.getData(BaseApplication.getContext(), Constants.USER, ""),
                User.class);
    }


}
