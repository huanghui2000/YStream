package cn.测试包.包2;

import cn.anno.clazz.StreamMarker;
import cn.anno.method.StreamOperation;

import static cn.anno.method.OperationType.INCLUDE;

@StreamMarker
public class 类2 {
    @StreamOperation(type = INCLUDE, content = "123")
    public void 方法1() {
    }
}
