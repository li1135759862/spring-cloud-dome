package com.example.demo;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nio {

    private static String fileName = "/Users/xinleili/Desktop/test";
    private static String fileDir = "/Users/xinleili/tmp/do_file_2018-06-25/merge/temp/wfs5v9x1dd1529896876307";
    private static String outFile = "/Users/xinleili/tmp/do_file_2018-06-25/merge/temp/wfs5v9x1dd1529896876307/py.zip";
    private static String DS_Store = "/Users/xinleili/tmp/do_file_2018-06-25/merge/temp/wfs5v9x1dd1529896876307/.DS_Store";

    public static void main(String[] args) throws IOException {

//        readFileByChars();
//        Optional<User> user = getName();
//        user.map(User::getName).orElse(null);
//        System.out.println(user.map(User::getName).orElse(null));
        File file = new File(DS_Store);
        File  storestore= new File(outFile);
        storestore.delete();
        file.delete();
        mergePartFiles("","",1,"");
    }
    private static void mergeFile() throws IOException {
        List<File> files = Arrays.asList(new File(fileDir).listFiles());
        FileOutputStream outStream = new FileOutputStream(outFile);
        Collections.sort(files);
        for (File el : files) {
            //读入块文件，然后一个个合并进来；
            FileInputStream inStream = new FileInputStream(el);
            int len = -1;
            byte[] buff = new byte[1024];
            while ((len = inStream.read(buff)) != -1) {
                outStream.write(buff, 0, len);
            }
            outStream.flush();
            inStream.close();
        }
        outStream.close();

    }
    /**
     * 合并文件
     *
     * @param dirPath 拆分文件所在目录名
     * @param partFileSuffix 拆分文件后缀名
     * @param partFileSize 拆分文件的字节数大小
     * @param mergeFileName 合并后的文件名
     * @throws IOException
     */
    public static void mergePartFiles(String dirPath, String partFileSuffix,
                               long partFileSize, String mergeFileName) throws IOException {
        mergeFileName = outFile;
        List<File> partFiles = Arrays.asList(new File(fileDir).listFiles());
        partFileSize = partFiles.get(0).length();
//        Collections.sort(partFiles, new FileComparator());
        Collections.sort(partFiles);
        RandomAccessFile randomAccessFile = new RandomAccessFile(mergeFileName,
                "rw");
        randomAccessFile.setLength(partFileSize * (partFiles.size() - 1)
                + partFiles.get(partFiles.size() - 1).length());
        randomAccessFile.close();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                partFiles.size(), partFiles.size() * 3, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(partFiles.size() * 2));

//        for (int i = 0; i < partFiles.size(); i++) {
//            threadPool.execute(new MergeRunnable(i * partFileSize,
//                    mergeFileName, partFiles.get(i)));
//        }
        for (int i = 0; i < partFiles.size(); i++) {
            threadPool.execute(new MergeRunnableNio(i * partFileSize,
                    mergeFileName, partFiles.get(i)));
        }
        if(!threadPool.isShutdown()){
            threadPool.shutdown();
        }
    }

    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    private static void readFileByByte(){
        File file = new File(fileName);
        InputStream in = null;
        System.out.println("以字节为单位读取文件内容，一次读一个字节：");
        // 一次读一个字节
        try {
            in = new FileInputStream(file);
            int tmp;
            while ((tmp=in.read())!=-1){
                System.out.write(tmp);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    private static void readFileByChar(){
        File file = new File(fileName);
        Reader reader = null;
        System.out.println("以字符为单位读取文件内容，一次读一个字节：");
        // 一次读一个字符
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            int top;
            while ((top = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if((char)top!='\r'){
                    System.out.print((char) top);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static void readFileByChars(){
        File file = new File(fileName);
        Reader reader = null;
        char[] tmp = new char[30];
        int charRead = 0;
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            while ((charRead=reader.read(tmp))!=-1){
                if(charRead==tmp.length&& (tmp[tmp.length - 1] != '\r')){
                    System.out.print(tmp);
                }else {
                    for (int i = 0; i < charRead; i++) {
                        if (tmp[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tmp[i]);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    private void nio() throws IOException {
        RandomAccessFile accessFile =
                new RandomAccessFile("/Users/xinleili/Desktop/test", "rw");

        FileChannel fileChannel = accessFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int byteRead = fileChannel.read(buf);
        while (byteRead!=-1){
            System.out.println("read"+byteRead);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.print(buf.get());
            }
            buf.clear();
            byteRead = fileChannel.read(buf);
        }
        accessFile.close();
    }

    private static Optional<User> getName(){
        User user = new User();
        user.setName("as");
        return Optional.ofNullable(user);
    }
}
class User{
    private String name;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}

class FileComparator implements Comparator<File> {
    public int compare(File o1, File o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
class MergeRunnable implements Runnable {
    long startPos;
    String mergeFileName;
    File partFile;

    public MergeRunnable(long startPos, String mergeFileName, File partFile) {
        this.startPos = startPos;
        this.mergeFileName = mergeFileName;
        this.partFile = partFile;
    }

    public void run() {
        RandomAccessFile rFile=null;
        FileInputStream fs = null;
        try {
            rFile = new RandomAccessFile(mergeFileName, "rw");
            rFile.seek(startPos);
            fs = new FileInputStream(partFile);
            byte[] b = new byte[fs.available()];
            fs.read(b);
            fs.close();
            rFile.write(b);
            rFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fs!=null){
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(rFile!=null){
                try {
                    rFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class MergeRunnableNio implements Runnable {
    long startPos;
    String mergeFileName;
    File partFile;

    public MergeRunnableNio(long startPos, String mergeFileName, File partFile) {
        this.startPos = startPos;
        this.mergeFileName = mergeFileName;
        this.partFile = partFile;
    }

    public void run() {
        RandomAccessFile in=null;
        RandomAccessFile out=null;


        try {
            out = new RandomAccessFile(mergeFileName, "rw");
            in = new RandomAccessFile(partFile, "rw");
            out.seek(startPos);
            FileChannel outChannel = out.getChannel();
            FileChannel inChannel = in.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int tmp = inChannel.read(buf);
            while (tmp!=-1){
                buf.flip();
                outChannel.write(buf);
                buf.clear();
                tmp = inChannel.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}