package com.shanhh.demo.platform.commons.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于文件操作操作的工具类
 * 
 * @author dan
 * 
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 获取文件中的内容, 以long显示, 失败则返回0L
     * 
     * @param filename
     * @return longValue
     */
    public static long getLong(String filename) {
        long id = 0L;
        BufferedReader in = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                logger.error("file not exist, filename={0}", filename);
                return id;
            }
            in = new BufferedReader(new FileReader(file));
            String s = in.readLine();
            if (s != null) {
                try {
                    id = Long.parseLong(s);
                } catch (NumberFormatException e) {
                    logger.error("File does not contain a parsable long, filename={0}", e, filename);
                    id = 0L;
                }
            }
        } catch (IOException e) {
            logger.error("Read file error, filename={0}", e, filename);
            id = 0L;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return id;
    }

    /**
     * 写入String形的内容到文件中
     * 
     * @param filename
     * @param data
     * @throws IOException
     */
    public static void setString(String filename, Object data)
            throws IOException {
        File file = new File(filename);
        if (!file.exists()) { file.createNewFile(); }
        
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        out.print(data);
        out.close();
    }
    
    /**
     * 写入map到文件
     * @param filename
     * @param map
     * @param split 分隔符号
     * @throws IOException
     */
    public static void setMap(String filename, Map map, String split) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        Entry entry;
        for (Object obj : map.entrySet()) {
            entry = (Entry) obj;
            bw.write(String.valueOf(entry.getKey()));
            bw.write(',');
            bw.write(String.valueOf(entry.getValue()));
            bw.write('\n');
        }
        bw.close();
    }
    
    /**
     * 从文件中读取一个long形的列表
     * 
     * @param filename
     * @return long value list
     */
    public static List<Long> getLongList(String filename) {
        List<Long> list = new ArrayList<Long>();
        BufferedReader in = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                logger.error("file not exist, filename={0}", filename);
                return null;
            }
            in = new BufferedReader(new FileReader(file));
            while (true) {
                String s = in.readLine();
                if (s != null) {
                    list.add(Long.parseLong(s));
                } else {
                    break;
                }
            }
        } catch (NumberFormatException e) {
            logger.error("String does not contain a parsable long, filename={0}", e, filename);
        } catch (IOException e) {
            logger.error("Read file error, filename={0}", e, filename);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return list;
    }

    /**
     * 在文件中写入long行的列表
     * 
     * @param filename
     * @param list
     * @throws IOException
     */
    public static void setLongList(String filename, long[] list)
            throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        for (long l : list) {
            bw.write(l + "\n");
        }
        bw.close();
    }

    /**
     * 在文件中写入String行的列表
     * 
     * @param filename
     * @param list
     * @throws IOException
     */
    public static void setStringList(String filename, String[] list)
            throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));
        for (String l : list) {
            bw.write(l + "\n");
        }
        bw.close();
    }

    /**
     * 从文件中读取一个int形的列表
     * 
     * @param filename
     * @return int value list
     */
    public static List<Integer> getIntList(String filename) {
        List<Integer> list = new ArrayList<Integer>();
        BufferedReader in = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                logger.error("file not exist, filename={0}", filename);
                return null;
            }
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while (true) {
                String s = in.readLine();
                if (s != null) {
                    list.add(Integer.parseInt(s));
                } else {
                    break;
                }
            }
        } catch (NumberFormatException e) {
            logger.error("String does not contain a parsable int, filename={0}", e, filename);
        } catch (IOException e) {
            logger.error("Read file error, filename={0}", e, filename);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return list;
    }

    /**
     * 从文件中读取一个String形的列表
     * 
     * @param filename
     * @return string value list
     */
    public static List<String> getStringList(String filename) {
        List<String> list = new ArrayList<String>();
        BufferedReader in = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                logger.error("file not exist, filename={0}", filename);
                return null;
            }
            in = new BufferedReader(new FileReader(file));
            while (true) {
                String s = in.readLine();
                if (s != null) {
                    list.add(s);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Read file error, filename={0}", e, filename);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return list;
    }


    /**
     * 从文件中读取一个String
     * 
     * @param filename
     * @return string value
     */
    public static String getString(String filename) {
        return getString(filename, "utf-8");
    }
    
    public static String getString(String filename, String charsetName) {
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        try {
            File file = new File(filename);
            if (!file.exists()) {
                logger.error("file not exist, filename={0}", filename);
                return null;
            }
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            while (true) {
                String s = in.readLine();
                if (s != null) {
                    sb.append(s).append("\n");
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Read file error, filename={0}", e, filename);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }
}