# Утилита фильтрации содержимого файлов
## Требования
- Java 22.0.1
- Gradle 8.5
- Apache Commons CLI 1.5.0, [Maven repository commons-cli](https://mvnrepository.com/artifact/commons-cli/commons-cli/1.5.0)
- Lombok 1.18.30, [Maven repository Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.30)

## Инструкция по запуску
С помощью системы сборки Gradle генерируем .jar файл с включенными зависимостями
```
./gradlew shadowJar
```

Запускаем .jar файл с нужными опциями, в конце добавляем имена файлов с входными данными
### Пример запуска с одним файлом:
```
java -jar build/libs/util.jar file.txt
```
Путь к файлам нужно указывать либо относительно места, откуда вы запускаете утилиту, либо относительно корневой директории, начиная с '/'

### Пример запуска с опциями:
```
java -jar build/libs/util.jar -a -s -o build -p prefix_ /Java_Projects/FileContentFilter/file.txt file1.txt
```
Файлы prefix_integers.txt, prefix_floats.txt и prefix_strings.txt будут найдены (или созданы) в папке build относительно места запуска, если такая папка существует

## Особенности обработки ошибок:
- Если дан несуществующий входной файл, программа пропустит его и отфильтрует все остальные
- Если указана несуществующая опция, программа её проигнорирует
- После опции '-o' нужно указать существующую директорию, иначе программа будет приостановлена
- После опции '-p' нужно указать префикс, подходящий для названия файла
- При возникновении любой ошибки пользователь увидит сообщение в консоли

## Доступные опции:
```
Usage: java -jar util.jar [OPTIONS] <input-files...>
 -a          Append mode for output
 -f          Enable full statistics
 -h,--help   Display help information
 -o <arg>    Set the output path
 -p <arg>    Set the prefix to the output files
 -s          Enable short statistics
```
