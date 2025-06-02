Set-Location "E:\Kafka\kafka_2.13-3.9.1"

# Запуск ZooKeeper в отдельном окне
Start-Process powershell -ArgumentList "-NoExit", "-Command", ".\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties"

Start-Sleep -Seconds 10  # Ждем, чтобы ZooKeeper успел запуститься

# Запуск Kafka в отдельном окне
Start-Process powershell -ArgumentList "-NoExit", "-Command", ".\bin\windows\kafka-server-start.bat .\config\server.properties"

Start-Sleep -Seconds 10  # Ждем, чтобы Kafka успел запуститься

# Создание топика (если не существует)
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --topic t1_demo_metrics --create --partitions 1 --replication-factor 1

# Запуск консюмера в отдельном окне
Start-Process powershell -ArgumentList "-NoExit", "-Command", ".\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t1_demo_metrics --from-beginning"
