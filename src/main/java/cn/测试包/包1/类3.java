package cn.测试包.包1;

import cn.anno.clazz.StreamMarker;
import cn.anno.method.StreamOperation;

import static cn.anno.method.OperationType.INCLUDE;

@StreamMarker
public class 类3 {
    @StreamOperation(type = INCLUDE, content = "123")
    public void 方法1() {
        System.out.println(666);
    }
}
