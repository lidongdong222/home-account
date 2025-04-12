package system;

import java.io.IOException;

public class SystemTest {
    /**
     * 使用java关闭操作系统
     * @param args
     */
    public static void main(String[] args) {
        try {
            //shutdown -s -t 0 关机
            //shutdown -r -t 0 重启
            Process process = Runtime.getRuntime().exec("shutdown -s -t 0");
            process.waitFor();
        } catch (IOException e) {
        } catch (InterruptedException e) {
        }
    }
}
