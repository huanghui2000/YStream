package Test.strategys;


import com.ystream.anno.clazz.StreamMarker;
import com.ystream.anno.method.StreamOperation;

import static com.ystream.anno.method.OperationType.*;

@StreamMarker
public class Strategy1 {

    /*
    匹配为一个11位的数字
     */
    @StreamOperation(type = REGEXP, content = "^\\d{3}$")
    public static String isPhone(String i) {
        return "这是一个三位的数字";
    }

}
