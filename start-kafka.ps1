Set-Location "E:\Kafka\kafka_2.13-3.9.1"

Start-Process powershell -ArgumentList "-NoExit", "-Command", ".\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties"
Start-Sleep -Seconds 10
Start-Process powershell -ArgumentList "-NoExit", "-Command", ".\bin\windows\kafka-server-start.bat .\config\server.properties"
Start-Sleep -Seconds 10

.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --topic t1_demo_metrics --create --partitions 1 --replication-factor 1
Start-Process powershell -ArgumentList "-NoExit", "-Command", ".\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic t1_demo_metrics --from-beginning"
