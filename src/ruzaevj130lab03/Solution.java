package ruzaevj130lab03;

import java.net.UnknownHostException;

/**
 * Класс предназначен для тестирования передачи файла по сети с использованием протокола TCP/IP
 * @author Andrey Ruzaev
 */
public class Solution {
    public static void main(String[] args) {
        
        
        ///////////////////////////////////////ПОЛУЧЕНИЕ ФАЙЛА///////////////////////////////////////
        System.out.println("File receiver started...");
        new FileReceiver().run();
        System.out.println("File receiver finished.");
        
        //////////////////////////////////////ОТПРАВКА ФАЙЛА////////////////////////////////////////
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
