package cn.trigger;

import cn.trigger.frame.Tactics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 将标记集装配到对应的策略集中
 * 返回装配后的策略集
 */
@SuppressWarnings({"deprecation", "unused"})
public class PolicyAssembler {
    static ArrayList<Tactics> tacticList = new ArrayList<>();

    /**
     * 封装所有被标记的策略集
     */
    public static ArrayList<Tactics> getTacticList() {
        run();
        //将tacticList中相同的策略集的Map合并为一个新的实例中
        for (int i = 0; i < tacticList.size(); i++)
            for (int j = i + 1; j < tacticList.size(); j++)
                if (tacticList.get(i).getAnnotationType().equals(tacticList.get(j).getAnnotationType())) {
                    tacticList.get(i).getTriggerArgs().putAll(tacticList.get(j).getTriggerArgs());
                    tacticList.remove(j);
                    j--;
                }
        return tacticList;
    }

    /**
     * 将标记集装配到对应的策略集中
     */
    public static void run() {
        //获取策略集和标记集
        HashMap<Class<? extends Annotation>, ArrayList<Class<?>>> policyCollection = DataClassifier.getPolicyCollection();
        HashMap<Class<? extends Annotation>, HashMap<HashMap<String, String>, Method>> markupContainers = DataClassifier.getMarkupContainers();
        //遍历策略集
        for (Class<? extends Annotation> annotation : policyCollection.keySet()) {
            //判断标记集中是否有相同注解的标记集
            if (!markupContainers.containsKey(annotation))
                continue;
            //找到相同注解的标记集
            HashMap<HashMap<String, String>, Method> hashMapMethodHashMap = markupContainers.get(annotation);
            //遍历标记集的Map
            for (HashMap<String, String> map : hashMapMethodHashMap.keySet()) {
                //遍历策略集
                for (Class<?> aClass : policyCollection.get(annotation)) {
                    //找到相同注解的策略集
                    Tactics tactics = null;
                    try {
                        tactics = (Tactics) aClass.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //判断标记集中的标记是否是策略集的触发条件
                    //判断该策略集是否存在于tacticList中
                    if (tactics != null && tactics.isTactics(map, hashMapMethodHashMap.get(map)))
                        if (!tacticList.contains(tactics))
                            tacticList.add(tactics);
                }
            }
        }
    }

}
