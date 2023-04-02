package Try.SB;


import com.ystream.anno.clazz.StreamMarker;
import com.ystream.anno.method.StreamOperation;

import static com.ystream.anno.method.OperationType.*;

@StreamMarker
public class Test {


    //有参数测试
    @StreamOperation(type = REGEXP, content = "[0-9]+")
    public static String sb1(String str) {
        return "is number1";
    }


    //有参数测试
    @StreamOperation(type = REGEXP, content = "[0-9]+")
    public static void sb2(String str) {
        System.out.println("is number2");
    }


}
