package Try;

import com.ystream.YChannel;
import com.ystream.YStreamApplication;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        YStreamApplication.run(Main.class);
        Scanner in = new Scanner(System.in);
        YChannel channel = new YChannel();
        while (true) {
            String msg = in.next();
            channel.process(msg);
            System.out.println("处理结果：" + channel.getResult());
        }
    }
}
