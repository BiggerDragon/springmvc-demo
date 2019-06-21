package com.demo;

import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * 使用嵌入式tomcat来启动spring上下文
 *
 */
public class TomcatStarter {

    public static File getRootFolder() {
        try {
            File root;
            String runningJarPath = TomcatStarter.class.getProtectionDomain().getCodeSource().getLocation().toURI()
                    .getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            } else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            System.out.println("application resolved root folder: " + root.getAbsolutePath());
            return root;
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static void main(String[] args) throws LifecycleException, IOException {
        File root = getRootFolder();
        System.out.println(root.getAbsolutePath());
        Path temDir = Files.createTempDirectory("tomcat-base-dir");
        System.out.println(temDir);
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(temDir.toString());
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        System.out.println(webPort);
        tomcat.setPort(Integer.valueOf(webPort));
        tomcat.setHostname("localhost");
        File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
        if(!webContentFolder.exists()) {
            webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
        }
        StandardContext ctx = (StandardContext)tomcat.addWebapp("/zhu", webContentFolder.getAbsolutePath());
        System.out.println("context base name="+ctx.getBaseName());
        tomcat.start();
        Host host = tomcat.getHost();
        System.out.println(host.getAppBase());
        Service service = tomcat.getService();
        Connector connector = new Connector();
        connector.setPort(8080);
        service.addConnector(connector);
        Connector[] connectors = service.findConnectors();
        System.out.println("connectors=" + Arrays.toString(connectors));
        tomcat.getServer().await();
    }
}
