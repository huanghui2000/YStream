package Try.SB;

import com.ystream.anno.clazz.StreamMarker;
import com.ystream.anno.method.OperationType;
import com.ystream.anno.method.StreamOperation;

@StreamMarker
public class Test2 {

    @StreamOperation(type = OperationType.INCLUDE, content = "1")
    public static String sb1(String str) {
        return "1";
    }
    @StreamOperation(type = OperationType.INCLUDE, content = "2")
    public static String sb2(String str) {
        return "2";
    }

    @StreamOperation(type = OperationType.EXCLUDE, content = "3")
    public static String sb3(String str) {
        return "！3";
    }


}
