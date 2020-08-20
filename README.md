# Deadline

Тестирование функции входа в лиичный кабинет с использованием базы данных mysql.

## Начало работы


Клонировать репозиторий [Deadline](https://github.com/podnebessssni/Deadline) на локальный комппьютер и запустить проект в Intellij IDE.




### Установка и запуск

1. Запустить контейнер в командной строке `doker-compose up`
1. Запустить в командной строке приложение app-deadline.jar `java -jar ./artifacts/app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass`
1. Запустить автотесты
1. Перед повторным запуском тестов нужно перезапустить приложение app-deadline.jar
