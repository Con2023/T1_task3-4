## Task3-4

*Цель:* Реализовать аспекты для логирования метрик и ошибок с отправкой в Kafka и резервным сохранением в БД.

Процесс работы необходимо начать с запуска Kafka. Для этого необходимо из powerShell запустить скрипт ./start-kafka.ps1.

Скрипт автоматически:

 - Запустит Zookeeper и Kafka-брокер. 
 - Создаст топик t1_demo_metrics.

Далее запускаем приложение и тестируем.

*Примечание.*
В скрипте описана корневая папка Kafka, для успешного запуска нужно указать свой путь к данной папке. 