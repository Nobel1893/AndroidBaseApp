package bluecrunch.base;

import android.support.annotation.StringRes;

/**
 * Created by Mohamed Nabil Mohamed on 4/18/2019.
 * m.nabil.fci2015@gmail.com
 */
public class BaseMessage {

    String message;
    String title;
    String posText;
    String negText;
    @StringRes int messageId;
    @StringRes int titleId;
    @StringRes int posTextId;
    @StringRes int negTextId;
}
