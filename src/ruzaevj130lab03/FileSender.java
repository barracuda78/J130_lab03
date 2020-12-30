/*
Дан класс FileReceiver, предназначенный для получения файла по сети и его
сохранения в локальной файловой системе. Определите парный к нему класс
FileSender, который отправляет по сети файл, предназначенный для получения
классом FileReceiver.
 */
package ruzaevj130lab03;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileSender {
    //1. Имя файла сериализовать:
    //            // Имя файла приходит в виде сериализованной строки
    //            int n = in.read(buf);
    //            File file = createFile(new String(buf, 0, n));
    
    private final String inetAddress;
    private final int serverPort;

    public FileSender(String inetAddress, int serverPort) throws UnknownHostException {
        this.inetAddress = inetAddress;
        this.serverPort = serverPort;
    }
    
    /**
    * Метод для сериализации имени файла:
    * Предназначен для отправки имени файла по сети на основе протокола TCP/IP
    * @param path - Объект класса Path указывающий на путь к файлу для чтения его имени в массив байт.
    * @return массив байт из имени файла.
    */
    private byte[] readFileName(String filePath) {
        File file = new File(filePath);
        String s = file.getName();
        return s.getBytes();
    }
     
    /**
    * Метод для чтения байт-массива данных из файла:
    * Предназначен для отправки файла по сети на основе протокола TCP/IP
    * @param path - Объект класса Path указывающий на путь к файлу для чтения его в массив байт.
    * @return массив байт из файла, путь которого передан в качестве параметра метода.
    */
    private byte[] readFromFile(Path path){
        byte[] array = null;
        try {
            array = Files.readAllBytes(path);
        } catch (IOException ex) {
            System.out.println("Ошибка в методе readFromFile(Path path): не удалось прочитать файл. " + ex.getMessage());
            ex.printStackTrace();
        }
        return array;
    }
    
    
//    client = new Socket("server", port);
//    bis = new BufferedInputStream(new FileInputStream("somefile.dat"));
//    bos = new BufferedOutputStream(client.getOutputStream());
//    byteArray = new byte[8192];
//    while ((in = bis.read(byteArray)) != -1){
//        bos.write(byteArray,0,in);
//    }
//    bis.close();
//    bos.close();
    
    /**
    * Метод представляет собой API класса. 
    * Предназначен для отправки файла по сети на основе протокола TCP/IP
    * @param filePath - строка, представляющая собой путь к файлу для отправки. 
    */
    public void send(String filePath){
        byte[] bytesFileName = readFileName(filePath);
        byte[] bytes = readFromFile(Paths.get(filePath));
        try(Socket s = new Socket(InetAddress.getByName(inetAddress), serverPort);
            OutputStream outputStream = s.getOutputStream();
            InputStream inputStream = s.getInputStream()){
                
            
                byte[] byteArray = new byte[8192];
                int in = 0;
                while ((in = inputStream.read(bytes)) != -1){
                    outputStream.write(byteArray,0,in);
}
                
//                outputStream.write(bytesFileName, 0, bytesFileName.length);
//                outputStream.flush();
//                outputStream.write(bytes, 0, bytes.length);
//                outputStream.flush();
//                System.out.println("Файл записан в сокет.");
            
        }catch(IOException e){
            System.out.println("Ошибка в методе send(String filePath): Ошибка ввода-вывода. " + e.getMessage());
            e.getStackTrace();
        }
    }
    
    public static void main(String[] args) {
        FileSender fileSender = null;
        try {
            fileSender = new FileSender("localhost", 34567);
        } catch (UnknownHostException ex) {
            System.out.println("Ошибка конструктора FileSender: Указанный адрес не найден." + ex.getMessage());
            ex.printStackTrace();
        }
        
        fileSender.send("D:\\coding\\politeh\\RuzaevJ130Lab03\\src\\ruzaevj130lab03\\files\\sendMe.txt");
    }
}
