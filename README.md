# Шифровальная программа
Программа, написанная на `java`, призванная для `шифровки/дешифровки` вводимого сообщения при помощи `ключа` методом `Вернама`

## Оглавление
1. [Меню и команды](#Меню-и-команды)
2. [Принцип шифровки и дешифровки](#Принцип-шифровки-и-дешифровки)
3. [Процесс шифровки](#Процесс-шифровки)
   1) [Процесс генерации ключа](#Процесс-генерации-ключа)
   2) [Процесс сохранения ключа и зашифрованного сообщения](#Процесс-сохранения-ключа-и-зашифрованного-сообщения)
4. [Процесс дешифровки](#Процесс-дешифровки)
   1) [Меню дешифровки](#Меню-дешифровки)
   2) [Дешифровка с помощью распознавания файла](#Дешифровка-с-помощью-распознавания-файла)
   3) [Дешифровка с помощью ручного ввода](#Дешифровка-с-помощью-ручного-ввода)

## Меню и команды
1) `\enc` — данная команда запускает процесс зашифровки
    1) `\rand` — данная команда позволяет создать случайный ключ для сообщения
3) `\dec` — данная команда запускае меню дешифровки
    1) `\man` — данная команда запускает процесс дешифровки с помощью распознавания файла в папке `results` в корневом каталоге программы
    2) `\read` — данная команда запускает процесс дешифровки с помощью ручного ввода
4) `\exit` — данная команда завершает работу программы

[Вернуться к оглавлению](#Оглавление)

## Принцип шифровки и дешифровки
Принцип шифровки и дешифровки основан на методе `шифра Вернама`.
Это простейший шифр на основе бинарной логики, который обладает абсолютной криптографической стойкостью. Без знания ключа, расшифровать его невозможно (доказано Клодом Шенноном).
Исходный алфавит — строчная и прописная латиница, строчная и прописная кирилица, цифры, пробел и символы `!"#$%&'()*+,-./:;<=>?@[\]^_{|}~`.

[:arrow_up:Вернуться к оглавлению](#Оглавление)

## Процесс шифровки
Сообщение разбиваем на отдельные символы и каждый символ представляем в бинарном виде. Далее используем `"Исключающее ИЛИ" (XOR)`. 

XOR принимает сигналы (0 или 1 каждый), проводит над ними логическую операцию и выдает один сигнал, исходя из входных значений.
Если все сигналы равны между собой (0-0 или 1-1 или 0-0-0 и т.д.), то на выходе получаем 0.
Если сигналы не равны (0-1 или 1-0 или 1-0-0 и т.д.), то на выходе получаем 1.

Теперь для шифровки сообщения, вводим сам текст для шифровки и ключ **такой же длины**. Переведем каждую букву в ее бинарный код и выполним действия в соответствии с ключом

Например:
```
сообщение: Энакин
ключ: Вейдер
```

Переведем их в бинарный код и выполним XOR:

```
10000101101 10000111101 10000110000 10000111010 10000111000 10000111101 — Энекин
10000010010 10000110101 10000111001 10000110100 10000110101 10001000000 — Вейдер
_______________________________________________________________________
00000111111 00000001000 00000001001 00000001110 00000001101 00001111101 — ?}
```

Заметим, что для удобства и правмльности представления мы будем добавлять незначащие нули в начале бинарного кода символа, пока не получим равное число нулей и единиц.

В данном конкретном примере на месте результирующих символов мы увидим неотображаемые символы и некоторыевидимые знаки, ведь часть все символов попала в первые 32 служебных символа. Однако, если перевести полученный результат в числа, то получим следующую картину:
```
63 8 9 14 13 125
```
Свиду — это обычный набор чисел, но мы то знаем правду)

Чтобы избежать недопонимания при шифровке/дешифровке, зашифрованнай результат будет представляться в числах.

[Процесс генерации ключа](#Процесс-генерации-ключа)

[Процесс сохранения ключа и зашифрованного сообщения](#Процесс-сохранения-ключа-и-зашифрованного-сообщения)

[:arrow_up:Вернуться к оглавлению](#Оглавление)

### Процесс генерации ключа
Ключ генерируется автоматически, длиной, равной заданному для зашифровки сообщению. 
Все символы выводятся случайно с помощью метода `Math.random() * 10000`, который возвращает псевдослучайное число в промежутке `(0;1)` умноженное на 1000, таким образом, при округлении мы можем получить целые числа в промежутке `от 0, до 9999`.
Получаемое, уже целое число, сравнивается с промежутками `от 32 до 126` и `от 1040 до 1105`, на которых определяются `строчные и прописные буквы латиницы, цифры, пробел и символы ``!"#$%&'()*+,-./:;<=>?@[\]^_{|}~`, а также `строчные и прописные буквы кирилицы` соответственно. Если псевдослучайное число входит в один из этих промежутков, то оно входит в ключ, иначе перебор чисел продолжается.

[Основная глава](#Процесс-шифровки)

[:arrow_up:Вернуться к оглавлению](#Оглавление)

### Процесс сохранения ключа и зашифрованного сообщения
Вовремя шифровки формируется файл под названием `empty.txt`, который содержит следующую инфорацию:
```
Ключ: *****
Сообщение: * * * * * 
```
Каждая `*` — это отдельный символ в ключе и число в сообщении

*Примечание: сообщение всегда сохраняется с пробелом в конце, но в дешифровке, при вводе вручную, пробел в конце необходимо убрать, так как это создаст ошибку*

После завершения формирования файла `empty.txt` на экране появится следующее сообщение:
```
Желаете сохранить файл с зашифрованным сообщением и ключом?
Введите 'Yes' или 'No': 
```
При ответе `Yes` или `Да` или `yes` или `да` Вы сможете сохранить файл под своим любым допустимым именем (файл `empty.txt` будет переименован)
При противоположных ответах сформированный файл `empty.txt` будет удалён

[Основная глава](#Процесс-шифровки)

[:arrow_up:Вернуться к оглавлению](#Оглавление)

## Процесс дешифровки
Процесс дешифровки основан на том же принципе, что и зашифровка. На вход поступает `ключ` и `сообщение`. `Ключ` вводится как обычная строка символов, а `сообщение` – как строка цифр, введённых через пробел через пробел
Последовательность цифр переводится в двоичный код и выполняется XOR.

Например:
```
Введённое сообщение: 63 8 9 14 13 125 ——> 00000111111 00000001000 00000001001 00000001110 00000001101 00001111101
Введённый ключ: Вейдер
```
Далее выполним XOR:

```
10000101101 10000111101 10000110000 10000111010 10000111000 10000111101 — Энекин
00000111111 00000001000 00000001001 00000001110 00000001101 00001111101 — ?}
_______________________________________________________________________
10000010010 10000110101 10000111001 10000110100 10000110101 10001000000 — Вейдер
```
Программа выводит ожидаемый результат

[Меню дешифровки](#Меню-дешифровки)

[Дешифровка с помощью распознавания файла](#Дешифровка-с-помощью-распознавания-файла)

[Дешифровка с помощью ручного ввода](#Дешифровка-с-помощью-ручного-ввода)

[:arrow_up:Вернуться к оглавлению](#Оглавление)

### Меню дешифровки
После выбора дешифровки перед Вами появится следующее меню:
```
Выберите тип ввода данных: 
1) Вручную(\man)
2) Считать с файла(\read)
Ввод команды: 
```
При вводе `\man` Вы сможете запустить дешифровку при помощи ручного ввода
При вводе `\read` Вы сможете запустить дешифровку при помощи ручного ввода

[Основная глава](#Процесс-дешифровки)

[:arrow_up:Вернуться к оглавлению](#Оглавление)

### Дешифровка с помощью распознавания файла
При выборе дешифровки этим способом Вы получите список файлов в папке `results` в корневом каталоге программы. Введя имя одного из файла, будет проведена дешифровка сообщения в нём
*Примечание: категорически нерекомендовано использовать в этом методе свой файл или изменённый файл, созданный методом шифровки. Используйте только автоматически созданные при зашифровке файлы*

[Основная глава](#Процесс-дешифровки)

[:arrow_up:Вернуться к оглавлению](#Оглавление)

### Дешифровка с помощью ручного ввода
При выборе дешифровки этим способом Вы вручную вводите `ключ` и `сообщение`
*Примечание: при дешифровке этим методом необходимо ввести последовательность цифр в сообщении без прбела в конце, так как это создаст ошибку*

[Основная глава](#Процесс-дешифровки)

[:arrow_up:Вернуться к оглавлению](#Оглавление)
