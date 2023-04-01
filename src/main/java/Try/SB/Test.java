package Try.SB;


import com.ystream.anno.clazz.StreamMarker;
import com.ystream.anno.method.StreamOperation;

import static com.ystream.anno.method.OperationType.*;

@StreamMarker
public class Test {
    @StreamOperation(type = INCLUDE, content = "1")
    public static String sb1() {
        return "1";
    }

    @StreamOperation(type = INCLUDE, content = "2")
    public static String sb2() {
        return "2";
    }

    @StreamOperation(type = EXCLUDE, content = "3")
    public static String sb3() {
        return "!3";
    }

    //是否为数字
    @StreamOperation(type = REGEXP, content = "[0-9]+")
    public static String sb4() {
        return "is number";
    }

}
