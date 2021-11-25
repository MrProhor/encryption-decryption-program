import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class project {

    public static char[] randomKey(int textLength) {
        int randInt;
        char[] keyArr = new char[textLength];
        for (int i = 0; i < textLength;) {
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

    public static void encryption() {
        Scanner scan = new Scanner(System.in);
        System.out.print("\nВведите сообщение для зашифровки: ");
        String inputText = scan.nextLine();
        char[] text = inputText.toCharArray();
        char[] key;
        int i = 0;
        while (i != 1) {
            System.out.print("\nВведите '\\rand', чтобы получить случайный ключ ИЛИ введите свой ключ для зашифровки.\nЭто может быть любая последовательность латинских букв, знаков препинания и цифр, длинной " + inputText.length() + ": ");
            key = scan.nextLine().toCharArray();
            if (key.length == 5 && key[0] == '\\' && key[1] == 'r' && key[2] == 'a' && key[3] == 'n' && key[4] == 'd') {
                key = randomKey(inputText.length());
            }
            System.out.println("Ваш сгенерированный ключ: " + new String(key));
            if (inputText.length() != key.length) {
                System.out.println("\n\nОШИБКА! Неправильная команда или введена последовательность неверной длинны!");
            } else {
                System.out.print("Ваше зашифрованное сообщение: ");
                int bigger = Math.max(inputText.length(), key.length);
                char[] binElementOfInputText, binElementOfKey;
                int[] binInputText = new int[bigger * 16],
                        finBool = new int[16];
                boolean[] inputTextBool = new boolean[bigger * 16],
                        keyBool = new boolean[bigger * 16],
                        encTextBool = new boolean[bigger * 16];
                for (i = 0; i < bigger; i++) {
                    binElementOfInputText = (Integer.toBinaryString(text[i])).toCharArray();
                    binElementOfKey = (Integer.toBinaryString(key[i])).toCharArray();
                    for (int n = 0; n < bigger; n++) {
                        inputTextBool[n] = binElementOfInputText[n] == '1';
                        keyBool[n] = binElementOfKey[n] == '1';
                        encTextBool[n] = inputTextBool[n] ^ keyBool[n];
                        if (encTextBool[n]) {
                            binInputText[n] = 1;
                        } else {
                            binInputText[n] = 0;
                        }
                        finBool[n] = binInputText[n];
                    }
                    int result = 0;
                    for (int j = bigger-1; j < bigger; j++) {
                        //Надо что-то придумать...
                    }
                    System.out.print(result + " ");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String freeInput;
        int i = 0;
        System.out.print("Здравствуйте! ");
        int a = 36;
        int b = 25;
        int c = a ^ b;
        System.out.print(c);
        while (i != 1) {
            System.out.print("\n \nВыберите режим работы: \n 1) Зашифровка сообщения (\\enc) \n 2) Дешифровка сообщения (\\dec)\n 3) Выход из программы (\\exit)\nВвод команды: ");
            freeInput = scan.nextLine();
            switch (freeInput) {
                case ("\\enc") -> encryption();
                //case ("\\dec") -> decryption();
                case ("\\exit") -> {
                    System.out.print("\nВыход...");
                    i = 1;
                }
                default -> System.out.print("\n\nОшибка! Попробуйте ввести команду ещё раз...");
            }
        }
    }
}