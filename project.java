import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class project {

    public static void decryption() {
        Scanner scan = new Scanner(System.in);
        String inputText, inputKey;
        int[] textInt;
        char[] text;
        char[] arr;
        char[] key;
        int i = 0;
        int j = 0;
        int a = 0;
        while (i == 0) {
            System.out.print("\nВведите сообщение для дешифровки: ");
            inputText = scan.nextLine();
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
            System.out.print("Введите ключ: ");
            inputKey = scan.nextLine();
            key = inputKey.toCharArray();
            if (textInt.length != inputKey.length()) {
                System.out.println("\n\nОШИБКА! Введена последовательность неверной длинны ИЛИ последовательность введена неправильно!\nДлинна ключа отличается от введённого сообщения на " + (inputKey.length() - textInt.length) + "\n");
            } else {
                System.out.print("Ваше зашифрованное сообщение: ");
                for (i = 0; i < text.length; i++) {
                    System.out.println(text[i]);
                    System.out.println(key[i]);
                    System.out.print((char) (text[i] ^ key[i]));
                }
                i = 1;
            }
        }
        System.out.print("");
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
        char[] text = input.toCharArray();
        char[] key;
        int i = 0;
        while (i != 1) {
            System.out.print("Введите '\\rand', чтобы получить случайный ключ ИЛИ введите свой ключ для зашифровки.\nЭто может быть любая последовательность латинских букв, знаков препинания и цифр, длинной " + input.length() + ": ");
            key = scan.nextLine().toCharArray();
            if (key.length == 5 && key[0] == '\\' && key[1] == 'r' && key[2] == 'a' && key[3] == 'n' && key[4] == 'd') {
                key = randomKey(input.length());
            }
            if (input.length() != key.length) {
                System.out.println("\n\nОШИБКА! Неправильная команда или введена последовательность неверной длинны!\nДлинна ключа отличается от введённого сообщения на " + (key.length - input.length()));
            } else {
                System.out.println("\nВаш ключ: " + new String(key));
                File file = new File("result\\empty.txt");
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
        if (!Objects.equals(input, "Да") & !Objects.equals(input, "Нет") & !Objects.equals(input, "да") & !Objects.equals(input, "нет") & !Objects.equals(input, "Yes") & !Objects.equals(input, "No") & !Objects.equals(input, "yes") & !Objects.equals(input, "no")) {
            System.out.print("\nОШИБКА! Неправильная команда.\n\nПопробуйте ввести команду ещё раз: ");
        } else {
            if (input.equals("Да") | input.equals("да") | input.equals("Yes") | input.equals("yes")) {
                while (i == 1) {
                    System.out.print("Введите название файла: ");
                    String name = scan.nextLine();
                    File file = new File("result\\empty.txt");
                    File newFile = new File("result\\" + name + ".txt");
                    if (file.renameTo(newFile)) {
                        i = 0;
                        System.out.print("Файл успешно создан в папке 'result' в корневом каталоге");
                    } else {
                        System.out.println("\nОШИБКА! Файл с таким названием уже создан! Попробуйте ввести новое имя");
                    }
                }
            } else {
                System.out.print("\nФайл не был создан");
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
                case ("\\dec") -> decryption();
                case ("\\exit") -> {
                    System.out.print("\nВыход...");
                    i = 1;
                }
                default -> System.out.print("\n\nОшибка! Неправильная команда. Попробуйте ввести команду ещё раз...");
            }
        }
    }
}