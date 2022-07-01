# task01
task01

java -jar target\mailmerge.jar ./csvFolder\thankyou.csv ./csvFolder\template.csv

mvn exec:java -Dexec.mainClass="task01.app.Main" -Dexec.args="./csvFolder\thankyou.csv ./csvFolder\template.csv"
