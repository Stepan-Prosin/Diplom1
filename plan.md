# План по проверке и автоматизации приложения

## Описание приложения 
1. Название: Мобильный хоспис
2. Функционал: Чтение новостей и цитат, содание, редактирование, удаление новостей.
3. Блоки: Авторизация, Новости, "About", Главное, Хоспис,содание, редактирование, удаление новостей.

##  Исследование  приложения, определение «границ» приложения, реализованных функций и подготовка основу для тестов;

## Типы тестирования: Функциональное тестирование

##  Чеклист
Тестирование разделов: main,news,about "love is all", авторизации.

## 1 Тест кейсы
Анализ работы полей авторизации, ввода даты и названия фильтере, ввода даты, названия, категории, времени, описания при создании новости, работы кнопок и перехода по разделам.

## 2 Планирумые сценарии для автоматизации: все тесткейсы 1-26 

## 3 Автоматизация тестирования: 
1. Добавление необходимых библеотек.
2. Добавление директории тестов .
3. настройка тестового класса.
4. Написание UI-тестов.
5. Создание отчетов.

## 4 Используемые инструменты:
* **Операционная система^**  Windows 10 Pro
* **IDE:** Android Studio Koala | 2024.1.1 Patch 1
* **Java:** OpenJDK 11
* **build system** gradle
* **Эмулятор** Medium Phone API 33 Android 13


## 5 использумые библиотеки:
1. espresso для E2E тестирования и совмещения с allure
2. lombok для краткости и скорости написания, и уменьшения обьёма кода 
3. allure для понятных  и подробных но не слишком репортов

## 6 Перечень и описание возможных рисков при автоматизации.
1. Сложности реализации тестов
2. Долгое время срабатывания тестов на виртуальной машине


## 7 Перечень необходимых специалистов для автоматизации.
1. Автотестировщик

## 8 Интервальная оценка с учётом рисков в часах:96+