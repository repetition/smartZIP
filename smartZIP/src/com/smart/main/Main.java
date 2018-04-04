package com.smart.main;

import com.sun.istack.internal.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by RJYF-ZhangBo on 2018/1/23.
 */
public class Main {
    //解压工具路径
    public static final String haoZIP_Path = "D:\\HaoZip\\HaoZipC.exe";
    //解压文件路径
    public static final String ROOT_Path = "F:\\ThinkWin\\ThinkWinCRV3.5.0\\ROOT.war";
    //目标文件夹
    public static final String ROOT_OUT_Path = "F:\\ThinkWin\\ThinkWinCRV3.5.0\\webapps\\ROOT";
    //配置文件路径
    public static final String ROOT_Spring_Path = ROOT_OUT_Path + "\\WEB-INF\\classes\\config\\spring\\spring.properties";
    public static final String ROOT_Publish_Path = ROOT_OUT_Path + "\\WEB-INF\\classes\\config\\publish.cfg.xml";

    public static void main(String[] args) {


        // showWindow();

        MyFlowLayout myFlowLayout = new MyFlowLayout();

        //TestFlowLayout testFlowLayout = new TestFlowLayout();
    }

    private static void showWindow() {
        // JPanel jPanel = new JPanel();
        //  jPanel.add(new JLabel("文件路径"));
        JTextField mTextInput = new JTextField(10);
        JButton mBTFile = new JButton("111111111open");
        // jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        //    jPanel.add(new JFileChooser("选择路径"));
        // jPanel.add(mTextInput);
        // jPanel.add(mBTFile);

        JFrame jFrame = new JFrame();
        jFrame.add(mTextInput);
        jFrame.add(mBTFile);
        jFrame.setLayout(new FlowLayout());

        jFrame.setSize(600, 400);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mBTFile.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser("选择文件");
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jFileChooser.showDialog(new JLabel(), "选择");
                File selectedFile = jFileChooser.getSelectedFile();
                if (selectedFile.isDirectory()) {
                    System.out.println("文件夹:" + selectedFile.getAbsolutePath());
                } else if (selectedFile.isFile()) {
                    System.out.println("文件:" + selectedFile.getAbsolutePath());
                }
            }
        });
    }


    public static class MyFlowLayout extends JFrame implements ActionListener {
        private static final Logger log = LoggerFactory.getLogger(MyFlowLayout.class);
        private final JTextField mTextInput_rooTwar;
        private final JButton mBTSelect;
        private final JTextField mTextInput_unRoot;
        private final JButton mBTUnSelect;
        private final JButton mBTUnFile;
        private final JLabel mJLabelDelete;
        private final JRadioButton mJRadioBTDelete;
        private final JRadioButton mJRadioBTUnDelete;

        public MyFlowLayout() {

            JPanel jPanel = new JPanel();
            JLabel jLabel = new JLabel("ROOT.war路径:");
            mTextInput_rooTwar = new JTextField(30);
            mTextInput_rooTwar.setText("F:\\ThinkWin\\ROOT.war");
            mBTSelect = new JButton("选择");
            jPanel.add(jLabel);

            jPanel.add(mTextInput_rooTwar);
            jPanel.add(mBTSelect);
            jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JPanel jPanelUn = new JPanel();
            JLabel jLabelUn = new JLabel("解压路径:");
            mTextInput_unRoot = new JTextField(30);
            mTextInput_unRoot.setText("F:\\ThinkWin\\ThinkWinCRV3.5.0\\webapps\\ROOT");
            mBTUnSelect = new JButton("选择");
            jPanelUn.setLayout(new FlowLayout(FlowLayout.LEFT));
            jPanelUn.add(jLabelUn);
            jPanelUn.add(mTextInput_unRoot);
            jPanelUn.add(mBTUnSelect);

            JPanel jPanelUnSetting = new JPanel();
            mJLabelDelete = new JLabel("是否删除ROOT文件夹：");
            mBTUnFile = new JButton("解压");
            ButtonGroup radioGroup = new ButtonGroup();
            mJRadioBTDelete = new JRadioButton();
            mJRadioBTDelete.setText("删除");
            mJRadioBTDelete.setSelected(true);
            mJRadioBTUnDelete = new JRadioButton();
            mJRadioBTUnDelete.setText("不删除");
            radioGroup.add(mJRadioBTDelete);
            radioGroup.add(mJRadioBTUnDelete);

            jPanelUnSetting.add(mJLabelDelete);
            jPanelUnSetting.add(mJRadioBTDelete);
            jPanelUnSetting.add(mJRadioBTUnDelete);
            jPanelUnSetting.add(mBTUnFile);


            // this.setContentPane(jPanel);
            //  this.setContentPane(jPanelUn);
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.getContentPane().add(jPanel);
            this.getContentPane().add(jPanelUn);
            this.getContentPane().add(jPanelUnSetting);

            // this.add(jPanel);
            // this.add(jPanelUn);
            //this.add(jPanelUnSetting);
            //4. 设置窗体属性
            this.setTitle("演示流布局管理器"); //设置标题
            this.setSize(600, 300);       //设置
            this.setLocation(400, 400);   //设置窗体出现的位置
            this.setVisible(true);        //设置窗体可见
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置窗体关闭的同时关闭jvm
            this.setResizable(false);  //Resizable:可调整大小的，设置窗体大小不可变

            mBTSelect.addActionListener(this);
            mBTUnSelect.addActionListener(this);
            mBTUnFile.addActionListener(this);

        }

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == mBTSelect) {
                String selectPath = showSelectPath(JFileChooser.FILES_ONLY, new FileNameExtensionFilter("ROOT.war(*.war)", "war"), "F:\\ThinkWin");
                mTextInput_rooTwar.setText(selectPath);
            }
            if (event.getSource() == mBTUnSelect) {

                String selectPath = showSelectPath(JFileChooser.DIRECTORIES_ONLY, null, "F:\\ThinkWin");
                mTextInput_unRoot.setText(selectPath);
            }
            if (event.getSource() == mBTUnFile) {

                String rootWarPath = mTextInput_rooTwar.getText();
                String unRootWarPath = mTextInput_unRoot.getText();
                log.info("ROOT.war路径 - " + rootWarPath);
                log.info("解压路径 - " + rootWarPath);
                if (null == rootWarPath || rootWarPath.trim().equals("")) {
                    JOptionPane.showMessageDialog(mTextInput_rooTwar, "没有选择ROOT.war路径", "ROOT.war路径为空", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (null == unRootWarPath || unRootWarPath.trim().equals("")) {
                    JOptionPane.showMessageDialog(mTextInput_rooTwar, "没有选择解压路径", "ROOT.war解压路径为空", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //    JOptionPane.showMessageDialog(mTextInput_rooTwar, "正在解压", "解压中", JOptionPane.PLAIN_MESSAGE);

                if (mJRadioBTDelete.isSelected()) {
                    deleteAllFile(unRootWarPath);
                }
                if (mJRadioBTUnDelete.isSelected()) {
                    // unZIP(rootWarPath, unRootWarPath);
                }
            }
        }

        /**
         * 递归删除文件
         *
         * @param unRootWarPath
         */
        private void deleteAllFile(String unRootWarPath) {
            File ROOT_file = new File(unRootWarPath);
            File[] listFiles = ROOT_file.listFiles();
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    deleteAllFile(file.getAbsolutePath());
                    //空文件夹直接删除
                    file.delete();
                    log.info(file.getAbsolutePath() + "文件夹删除成功!");
                }
                //如果是文件直接删除
                if (file.isFile()) {
                    file.delete();
                    log.info(file.getAbsolutePath() + "文件删除成功!");
                }
            }
            //删除root目录
            if (ROOT_file.listFiles().length == 0) {
                ROOT_file.delete();
                log.info(ROOT_file.getAbsolutePath() + "文件删除成功!");
            }
        }

        /**
         * 解压文件
         *
         * @param rootWarPath   ROOT.war路径
         * @param unRootWarPath ROOT.war解压路径路径
         */
        private void unZIP(String rootWarPath, String unRootWarPath) {
            try {
                ZipFile zipFile = new ZipFile(rootWarPath, Charset.forName("GBK"));
                long start = System.currentTimeMillis();
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                int size = zipFile.size();
                log.info("文件数：" + size);
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    String zipEntryName = zipEntry.getName();

                    File decDir = new File(unRootWarPath);
                    if (!decDir.exists()) {
                        decDir.mkdirs();
                    }
                    File outFile = new File(decDir.getAbsoluteFile() + "\\" + zipEntry.getName());
                    if (!outFile.getParentFile().exists()) {
                        outFile.getParentFile().mkdirs();
                    }
                    //解压文件如果是文件夹则创建文件夹
                    if (zipEntry.isDirectory()) {
                        outFile.mkdirs();
                        //如果是文件夹则创建文件夹跳出循环
                        continue;
                    }
                    //解压文件如果是文件则创建文件写入
                    InputStream zipIS = zipFile.getInputStream(zipEntry);
                    outFile.createNewFile();
                    FileOutputStream decFOS = new FileOutputStream(outFile);
                    byte[] bytes = new byte[1024 * 8];
                    int len;
                    while ((len = zipIS.read(bytes)) != -1) {
                        decFOS.write(bytes, 0, len);
                    }
                    decFOS.flush();
                    decFOS.close();
                    zipIS.close();
                    // log.info(zipEntryName);
                }
                long end = System.currentTimeMillis();
                log.info("解压完毕 - 耗时:" + (end - start) + "ms");
            } catch (IOException e) {
                e.printStackTrace();
                log.error("解压异常！- " + e.getMessage());
            }
        }


        /**
         * 文件选择器
         *
         * @param mode
         * @param filter
         * @param path
         * @return
         */
        private String showSelectPath(int mode, @Nullable FileNameExtensionFilter filter, String path) {
            JFileChooser jFileChooser = new JFileChooser("选择文件");
            jFileChooser.setFileSelectionMode(mode);
            // jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("war(*.war)", "war"));
            // jFileChooser.setFileFilter(new FileNameExtensionFilter("war(*.war)", "war"));
            if (null != filter) {
                jFileChooser.setFileFilter(filter);
            }
            // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
            // jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
            // 设置默认使用的文件过滤器
            // jFileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));
       /*             FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "Excel文件(*.xls)", "xls");
                    jFileChooser.setFileFilter(filter);*/
            jFileChooser.setCurrentDirectory(new File(path));
            jFileChooser.showSaveDialog(new JLabel());
            File selectedFile = jFileChooser.getSelectedFile();

           /* if (null!=selectedFile.getAbsolutePath()) {
                return selectedFile.getAbsolutePath();
            }*/
            return null == selectedFile ? "" : selectedFile.getAbsolutePath();
        }
    }

    public static class TestFlowLayout extends JFrame {  //0.继承JFrame
        //1. 定义组件
        JButton jButton1, jButton2, jButton3, jButton4, jButton5;

        public TestFlowLayout() {
            //2. 创建组件
            jButton1 = new JButton("A");
            jButton2 = new JButton("B");
            jButton3 = new JButton("C");
            jButton4 = new JButton("D");
            jButton5 = new JButton("E");

            //3. 添加各个组件
            this.add(jButton1);
            this.add(jButton2);
            this.add(jButton3);
            this.add(jButton4);
            this.add(jButton5);
            //设置流布局
            //        this.setLayout(new FlowLayout()); //默认布局方式为居中
            this.setLayout(new FlowLayout(FlowLayout.LEFT));

            //4. 设置窗体属性
            this.setTitle("演示流布局管理器"); //设置标题
            this.setSize(200, 200);       //设置
            this.setLocation(200, 200);   //设置窗体出现的位置
            this.setVisible(true);        //设置窗体可见
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置窗体关闭的同时关闭jvm
            this.setResizable(false);  //Resizable:可调整大小的，设置窗体大小不可变
        }

    }

    private static void setConfig() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(ROOT_Spring_Path));
            properties.setProperty("common.db.url", "jdbc:mysql://localhost:3306/cr_4_0_6_100?useOldAliasMetadataBehavior=true&characterEncoding=utf-8");
            properties.setProperty("common.db.username", "root");
            properties.setProperty("common.db.password", "root");

        } catch (IOException e) {
            e.printStackTrace();
        }

        DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = builder.newDocumentBuilder();
            Document parse = documentBuilder.parse(new FileInputStream(ROOT_Publish_Path));

            Element documentElement = parse.getDocumentElement();
            NodeList childNodes = documentElement.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {

                Node item = childNodes.item(i);

                //  System.out.println(item.getNodeName() +  ": " + item.getTextContent());
                if (item.getNodeName().equals("baseResourcePath")) {
                    System.out.println(item.getTextContent());
                    // TODO: 2018/4/2 设置资源文件路径
                    item.setTextContent("");
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean UnROOT() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File("F:\\ThinkWin\\ThinkWinCRV3.5.0\\webapps\\ROOT");
        // File[] files = file.listFiles();
        String[] list = file.list();

    /*    if (null==files) {
            return;
        }*/

        for (int i = 0; i < list.length; i++) {
            System.out.println("length: " + list.length + "path:" + list[i]);
            //  System.out.println("name: " + f.getName() + ",lastTime :" + format.format(f.lastModified()) + "\n" + f.getAbsolutePath());
            boolean delete = false;

            delete = new File(file, list[i]).delete();
            System.out.println(delete);

            if (!delete) {
                return true;
            }
        }

        ProcessBuilder builder = new ProcessBuilder();

        List<String> cmd = new ArrayList<>();
        // cmd.add("cmd.exe");
        //  cmd.add("/c");
/*
        cmd.add("ipconfig");
        cmd.add("/all");
*/
        cmd.add(haoZIP_Path);
        cmd.add("x"); //解压文件 e
        cmd.add(ROOT_Path);
        cmd.add("-o" + ROOT_OUT_Path);//输出路径
        cmd.add("-Y");

        System.out.println("cmd： " + cmd.toString());

        try {
            //Process p = builder.command("D:\\HaoZip\\HaoZipC.exe","x","F:\\\\ThinkWin\\\\ThinkWinCRV3.5.0\\\\ROOT.war","-oF:\\\\ThinkWin\\\\ThinkWinCRV3.5.0\\\\webapps\\\\ROOT","-Y").start();
            long start = System.currentTimeMillis();
            Process p = builder.command(cmd).start();
            InputStream in = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(in, "gb2312");
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
                System.out.println(line);
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时 ：" + (end - start) / 1000 + "s");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
