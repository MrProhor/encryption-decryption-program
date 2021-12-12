import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class project {

    public static void decryptionRead() {
        int i = 1;
        char[] fileKey,
                fileText;
        while (i == 1) {
            Scanner scan = new Scanner(System.in);
            System.out.println("\nВведите имя файла из предложенного ниже списка: ");
            File dir = new File("results");
            for (File file : dir.listFiles()) {
                System.out.println(file.getName());
            }
            System.out.println("Примечание: Если Вы не видите свой файл в списке, проверьте, находится ли он в папке 'results' в корневом каталоге");
            System.out.print("Введите название файла: ");
            String name = scan.nextLine();
            File file = new File("results\\" + name + ".txt");
            if (!file.exists()) {
                System.out.println("\nОШИБКА! Файла с таким именем не существует!\nПроверьте имя файла или перенесите свой в папку 'results' в корневом каталоге");
            } else {
                i = 0;
                try (FileReader reader = new FileReader("results\\" + name + ".txt");
                     BufferedReader br = new BufferedReader(reader)) {
                    String line;
                    line = br.readLine();
                    fileKey = line.toCharArray();
                    line = br.readLine();
                    fileText = line.toCharArray();
                    int[] textInt = new int[fileKey.length];
                    char[] textChar = new char[fileKey.length];
                    int j = 0;
                    for (i = 11; i < fileText.length; i++) {
                        if (fileText[i] != ' ') {
                            textInt[j] = textInt[j] * 10 + fileText[i] - 48;
                        } else {
                            textChar[j] = (char) textInt[j];
                            j++;
                        }
                    }
                    System.out.println("\n" + Arrays.toString(fileKey));
                    System.out.println(fileText);
                    System.out.print("Дешифрованное сообщение: ");
                    for (j = 6; j < fileKey.length; j++) {
                        System.out.print((char) (fileKey[j] ^ textChar[j - 6]));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void decryptionManually() {
        Scanner scan = new Scanner(System.in);
        String inputText,
                inputKey;
        int[] textInt;
        char[] text,
                arr,
                key;
        int i = 0,
                j = 0,
                a = 0;
        while (i == 0) {
            System.out.print("\nВведите сообщение для дешифровки: ");
            inputText = scan.nextLine() + ' ';
            arr = inputText.toCharArray();
            for (i = 0; i < inputText.length(); i++) {
                if (arr[i] == ' ') {
                    a++;
                }
            }
            text = new char[a + 1];
            textInt = new int[a + 1];
            for (i = 0; i < inputText.length(); i++) {
                if (arr[i] != ' ') {
                    textInt[j] = textInt[j] * 10 + arr[i] - 48;
                } else {
                    text[j] = (char) textInt[j];
                    j++;
                }
            }
            i = 0;
            System.out.print("Введите ключ: ");
            inputKey = scan.nextLine() + ' ';
            key = inputKey.toCharArray();
            if (textInt.length != inputKey.length()) {
                System.out.println("\n\nОШИБКА! Введена последовательность неверной длинны ИЛИ последовательность введена неправильно!\nДлинна ключа отличается от введённого сообщения на " + (inputKey.length() - textInt.length) + "\n");
            } else {
                System.out.print("Ваше дешифрованное сообщение: ");
                for (i = 0; i < text.length; i++) {
                    System.out.print((char) (text[i] ^ key[i]));
                }
                i = 1;
            }
        }
    }

    public static void decryptionMenu() {
        Scanner scan = new Scanner(System.in);
        int i = 1;
        while (i == 1) {
            System.out.print("\nВыберите тип ввода данных: \n1) Вручную(\\man)\n2) Считать с файла(\\read)\nВвод команды: ");
            String ans = scan.nextLine();
            switch (ans) {
                case ("\\man") -> {
                    i = 0;
                    decryptionManually();
                }
                case ("\\read") -> {
                    i = 0;
                    decryptionRead();
                }
                default -> System.out.print("\n\nОшибка! Неправильная команда. Попробуйте ввести команду ещё раз...");
            }
        }
    }

    public static char[] randomKey(int textLength) {
        int randInt;
        char[] keyArr = new char[textLength];
        for (int i = 0; i < textLength; ) {
            while (i < textLength) {
                randInt = (int) (Math.random() * 1000);
                if ((randInt > 31) & (randInt < 127)) {
                    keyArr[i] = (char) randInt;
                    i++;
                } else if ((randInt > 1039) & (randInt < 1106) & (randInt != 1104)) {
                    keyArr[i] = (char) randInt;
                    i++;
                }

            }
        }
        return keyArr;
    }

    public static void encryption() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nВведите сообщение для зашифровки: ");
        String input = scan.nextLine();
        char[] text = input.toCharArray(),
                key;
        int i = 0;
        while (i != 1) {
            System.out.print("Введите '\\rand', чтобы получить случайный ключ ИЛИ введите свой ключ для зашифровки.\nЭто может быть любая последовательность латинских букв, знаков препинания и цифр, длинной " + input.length() + ": ");
            key = scan.nextLine().toCharArray();
            if (key.length == 5 && key[0] == '\\' && key[1] == 'r' && key[2] == 'a' && key[3] == 'n' && key[4] == 'd') {
                key = randomKey(input.length());
            }
            if (input.length() != key.length) {
                System.out.println("\nОШИБКА! Неправильная команда или введена последовательность неверной длинны!\nДлинна ключа отличается от введённого сообщения на " + (key.length - input.length()) + "\n");
            } else {
                System.out.println("\nВаш ключ: " + new String(key));
                File file = new File("results\\empty.txt");
                FileWriter writer = new FileWriter(file);
                System.out.print("Ваше зашифрованное сообщение: ");
                writer.write("Ключ: ");
                for (i = 0; i < key.length; i++) {
                    writer.write(key[i]);
                }
                writer.write("\nСообщение: ");
                for (i = 0; i < text.length; i++) {
                    writer.write((text[i] ^ key[i]) + " ");
                    System.out.print((text[i] ^ key[i]) + " ");
                }
                writer.close();
                i = 1;
            }
        }
        System.out.print("\n\nЖелаете сохранить файл с зашифрованным сообщением и ключом?\nВведите 'Yes' или 'No': ");
        input = scan.nextLine();
        File file = new File("results\\empty.txt");
        if (!Objects.equals(input, "Да") & !Objects.equals(input, "Нет") & !Objects.equals(input, "да") & !Objects.equals(input, "нет") & !Objects.equals(input, "Yes") & !Objects.equals(input, "No") & !Objects.equals(input, "yes") & !Objects.equals(input, "no")) {
            System.out.print("\nОШИБКА! Неправильная команда.\n\nПопробуйте ввести команду ещё раз: ");
        } else {
            if (input.equals("Да") | input.equals("да") | input.equals("Yes") | input.equals("yes")) {
                while (i == 1) {
                    System.out.print("Введите название файла: ");
                    String name = scan.nextLine();
                    File newFile = new File("results\\" + name + ".txt");
                    if (file.renameTo(newFile)) {
                        i = 0;
                        System.out.print("Файл успешно создан в папке 'results' в корневом каталоге");
                    } else {
                        System.out.println("\nОШИБКА! Файл с таким названием уже создан! Попробуйте ввести новое имя");
                    }
                }
            } else {
                if (file.delete()) {
                    System.out.print("\nФайл не был создан");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String freeInput;
        int i = 0;
        System.out.print("Здравствуйте! ");
        while (i != 1) {
            System.out.print("\n \nВыберите режим работы: \n 1) Зашифровка сообщения (\\enc) \n 2) Дешифровка сообщения (\\dec)\n 3) Выход из программы (\\exit)\nВвод команды: ");
            freeInput = scan.nextLine();
            switch (freeInput) {
                case ("\\enc") -> encryption();
                case ("\\dec") -> decryptionMenu();
                case ("\\exit") -> {
                    System.out.print("\nВыход...");
                    i = 1;
                }
                default -> System.out.print("\n\nОшибка! Неправильная команда. Попробуйте ввести команду ещё раз...");
            }
        }
    }
}