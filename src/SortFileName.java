import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

class SortFileName implements Comparator<File> {

    @Override //Переопределяем метод и отправляем название в нужный файл
    public int compare(File file1, File file2) {
        return file1.getName().compareTo(file2.getName()); //Сортируем по имени
    }
}