import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

        public static void main(String[] args) throws IOException{
            String directoryName = "C:\\FolderWithTxt"; // Путь к папке в которой лежат TXT файлы
            String resultFileName = "C:\\FolderWithTxt\\FinishResult.txt";

            FilenameFilter txtFilter = (File dir, String name) -> { //Параметр фильтрации файлов, ищем только TXT
                return (new File(dir.getAbsolutePath()+File.separator+name).isDirectory()) || name.toLowerCase().endsWith(".txt");
            };
            List<File> textFileNameList = getFileName(new File(directoryName), new ArrayList<>(), txtFilter);
            Collections.sort(textFileNameList, new SortFileName());
            String resultText = fileWriter(textFileNameList, Charset.forName("cp1251")); //Устанавливаем кодировкуц
            fileWriter(resultFileName, resultText); //передаем в метод в качестве параметра, путь в файл который надо записать и текст, который мы получили
            new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return false;
                }
            };
        }
    public static List<File> getFileName(File directory, List<File> fileNameList, FilenameFilter txtFilter) { //Метод получения имени файла
        if (directory == null || directory.listFiles(txtFilter)==null) {
            System.out.println("Нет такой директории");
            System.exit(0);
        }
        for (File file : directory.listFiles(txtFilter)) {
            if (file.isDirectory()) getFileName(file, fileNameList, txtFilter);
            else fileNameList.add(file);
        }
        return fileNameList;
    }

    public static String fileWriter(List<File> textFileNameList, Charset charset) { //перегрузка метода для записи в файл
        final StringBuilder allText = new StringBuilder();
        textFileNameList.forEach((file) -> {
            try {
                Files.lines(Paths.get(file.getAbsolutePath()), charset).
                        forEach((t) -> {allText.append(t).append("\r\n");});
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return allText.toString();
    }

    public static boolean fileWriter(String fileName, String text) throws IOException { //перегрузка метода для записи в файл.
        try (BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(fileName))) {
            bof.write(text.getBytes());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}



