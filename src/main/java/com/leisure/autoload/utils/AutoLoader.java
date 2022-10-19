package com.leisure.autoload.utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Objects;

/**
 * @author leisure
 * @version 1.0.0
 * @since 2022/10/16 16:00
 */
public class AutoLoader {

    private static final System.Logger logger = System.getLogger("AutoLoad");

    // 监听文件路径
    public static final String LISTEN_PATH = "/Users/leiyunhong/IdeaProjects/AutoLoad/src/main/resources/com/leisure/autoload/fxml";

    // resources目录fxml文件路径
    public static String FXML_PATH = "/com/leisure/autoload/fxml";

    // 当前fxml文件路径
    public static String CURRENT_FXML;

    // 当前App窗口的Stage
    public static Stage CURRENT_STAGE;

    // 当前App窗口的宽
    public static double CURRENT_WIDTH;

    // 当前App窗口的高
    public static double CURRENT_HEIGHT;

    // 监文件变化
    static {
        try {
            FileMonitor.init(1_000L);  // 监听间隔1秒
            FileMonitor.monitor(LISTEN_PATH, new FileListener());  // 监听LISTEN_PATH目录
            FileMonitor.start(); // 开始监听
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 反射获取调用者的Stage
    static {
        try {
            String callerClassName = new Exception().getStackTrace()[1].getClassName(); // 获取调用者的类名
            Class<?> callerClass = Class.forName(callerClassName); // 获取调用者的Class对象
            Field field = callerClass.getDeclaredField("stage"); // 获取调用者的stage属性
            CURRENT_STAGE = (Stage) field.get("javafx.stage.Stage");  // 获取调用者的stage属性值
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载fxml
     * @param viewName fxml文件名
     * @param width 宽
     * @param height 高
     * @throws IOException io异常
     */
    public static void loadScene(String viewName, double width, double height) throws IOException {
        Parent root = load(Objects.requireNonNull(AutoLoader.class.getResource(String.format("%s/%s.fxml", FXML_PATH, viewName))));
        CURRENT_FXML = viewName;
        CURRENT_WIDTH = width;
        CURRENT_HEIGHT = height;
        CURRENT_STAGE.setScene(new Scene(root, width, height));
    }

    private static <T> T load(URL var0) throws IOException {
        return FXMLLoader.load(var0);
    }

    /**
     * 重新加载fxml
     * @throws IOException io异常
     */
    private static void reloadScene() throws IOException {
        Parent root = load(Objects.requireNonNull(AutoLoader.class.getResource(String.format("%s/%s.fxml",FXML_PATH, CURRENT_FXML))));
        Platform.runLater(() -> CURRENT_STAGE.setScene(new Scene(root, CURRENT_WIDTH, CURRENT_HEIGHT)));
    }

    /**
     * 重新加载fxml外部方法
     */
    public static void reload() {
        // 方法一: 使用 MVN 重新编译
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"mvn", "compile"});
            int signal = process.waitFor();
            if (signal == 0) {
                //重新加载fxml
                reloadScene();
                logger.log(System.Logger.Level.INFO, "重新加载fxml成功");
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "重新加载fxml失败");
        }

//        // 方法二: 直接将修改好的fxml文件复制到target/classes目录下
//        try {
//            File source = new File(String.format("%s/%s.fxml", LISTEN_PATH, CURRENT_FXML));
//            File target = new File(String.format("%s/%s.fxml", System.getProperty("user.dir") + "/target/classes" + FXML_PATH, CURRENT_FXML));
//            org.apache.commons.io.FileUtils.copyFile(source, target);
//            //重新加载fxml
//            reloadScene();
//            logger.log(System.Logger.Level.INFO, "重新加载fxml成功");
//        } catch (Exception e) {
//            logger.log(System.Logger.Level.ERROR, "重新加载fxml失败");
//        }
    }

    static class FileListener extends FileAlterationListenerAdaptor {

        @Override
        public void onStart(FileAlterationObserver observer) {
            super.onStart(observer);
        }

        @Override
        public void onDirectoryCreate(File directory) {
            super.onDirectoryCreate(directory);
        }

        @Override
        public void onDirectoryChange(File directory) {
            super.onDirectoryChange(directory);
        }

        @Override
        public void onDirectoryDelete(File directory) {
            super.onDirectoryDelete(directory);
        }

        @Override
        public void onFileCreate(File file) {
            super.onFileCreate(file);
        }

        @Override
        public void onFileChange(File file) {
            String compressedPath = file.getAbsolutePath();
            logger.log(System.Logger.Level.INFO, "修改文件：" + compressedPath);
            AutoLoader.reload();
        }

        @Override
        public void onFileDelete(File file) {
            super.onFileDelete(file);
        }

        @Override
        public void onStop(FileAlterationObserver observer) {
            super.onStop(observer);
        }
    }

    static class FileMonitor {

        private static FileAlterationMonitor monitor;

        public static void init(Long interval) {
            monitor = new FileAlterationMonitor(interval);
        }

        /**
         * 给文件添加监听
         *
         * @param path     文件路径
         * @param listener 文件监听器
         */
        public static void monitor(String path, FileAlterationListener listener) {
            FileAlterationObserver observer = new FileAlterationObserver(new File(path));
            monitor.addObserver(observer);
            observer.addListener(listener);
        }

        public static void stop() throws Exception {
            monitor.stop();
        }

        public static void start() throws Exception {
            monitor.start();
        }
    }
}
