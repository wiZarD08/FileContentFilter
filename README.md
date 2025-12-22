# FileContentFilter
# Утилита фильтрации содержимого файлов

Java version 22.0.1
Gradle 8.5
Сторонние библиотеки:
Apache Commons CLI 1.5.0, https://mvnrepository.com/artifact/commons-cli/commons-cli/1.5.0
Lombok 1.18.30, https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.30

С помощью системы сборки Gradle генерируем .jar файл с включенными зависимостями
./gradlew shadowJar

Запускаем .jar файл с нужными опциями, в конце добавляем имена файлов с входными данными
Пример:
java -jar build/libs/util.jar -a -f file.txt


Usage: java -jar util.jar [OPTIONS] <input-files...>
 -a          Append mode for output
 -f          Enable full statistics
 -h,--help   Display help information
 -o <arg>    Set the output path
 -p <arg>    Set the prefix to the output files
 -s          Enable short statistics
