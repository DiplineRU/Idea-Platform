# ✈️ Flight Analyzer (Vladivostok → Tel Aviv)

Программа на Java, которая анализирует файл tickets.json и рассчитывает:

✅ Минимальное время полета между Владивостоком и Тель-Авивом для каждого авиаперевозчика  
✅ Разницу между средней ценой и медианой для указанных перелётов  

---

## 📂 Структура проекта
project-root/
── src/
   ── main/java/
       ── Main.java        # Основная логика программы
       ── model/Ticket.java # Модель данных
   ── resources/
        ── tickets.json      # Исходные данные
── README.md

---

## 🛠 Используемые технологии

- Java 21
- Jackson (для парсинга JSON)
- Java Time API (для расчета времени перелета)
- Stream API (для фильтрации и вычислений)

---

## ▶️ Запуск программы

### 1. Компиляция
Убедись, что у тебя установлен JDK 21.  
Скачай зависимости (Jackson) и скомпилируй:
javac -cp .:jackson-databind-2.15.2.jar Main.java model/Ticket.java

### 2. Запуск
java -cp .:jackson-databind-2.15.2.jar Main

---

## 📥 Формат входного файла tickets.json
{
  "tickets": [
    {
      "origin": "VVO",
      "origin_name": "Владивосток",
      "destination": "TLV",
      "destination_name": "Тель-Авив",
      "departure_date": "12.05.18",
      "departure_time": "16:20",
      "arrival_date": "12.05.18",
      "arrival_time": "22:10",
      "carrier": "TK",
      "stops": 3,
      "price": 12400
    }
    // ... другие билеты
  ]
}

---

## ✅ Результаты программы

Пример вывода:
Минимальное время полета (Владивосток → Тель-Авив):
BA: 8 ч 5 мин
S7: 10 ч 5 мин
SU: 6 ч 0 мин
TK: 9 ч 15 мин

Средняя цена: 14125.00
Медиана: 13600.00
Разница: 525.00

---

## 📌 Примечания

- Программа игнорирует все рейсы, которые не из Владивостока и не в Тель-Авив.
- JSON-файл должен находиться по пути:  
  src/main/resources/tickets.json
- Если нужно запустить с другим файлом, измените путь в Main.java.

---

## 🏆 Автор

Гулян Артем 
Email: artgul52@gmail.com
GitHub: [github.com/DiplineRU](https://github.com/DiplineRU)
