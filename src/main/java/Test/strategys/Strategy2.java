package Test.strategys;

import com.ystream.anno.clazz.StreamMarker;
import com.ystream.anno.method.OperationType;
import com.ystream.anno.method.StreamOperation;

@StreamMarker
public class Strategy2 {
    @StreamOperation(type = OperationType.INCLUDE, content = "fuck")
    public static String saidFuck() {
        return "Don't said fucking!";
    }
}
