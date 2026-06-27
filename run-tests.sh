#!/bin/bash

# === Конфигурация ===

PROJECT_DIR="/home/supernatural/tests/stargazer-testing-api"

REPORTS_DIR="/home/supernatural/allure-reports"

DATE=$(date +"%Y-%m-%d_%H-%M-%S")

LOG_FILE="$PROJECT_DIR/logs/test-run-$DATE.log"

# === Создание директорий ===

mkdir -p "$REPORTS_DIR"

mkdir -p "$PROJECT_DIR/logs"

# === Логирование ===

echo "========================================" | tee -a "$LOG_FILE"

echo "Запуск тестов: $DATE" | tee -a "$LOG_FILE"

echo "========================================" | tee -a "$LOG_FILE"

# === Переход в директорию проекта ===

cd "$PROJECT_DIR"

# === Git pull (получить последние изменения) ===

echo "Обновление кода из Git..." | tee -a "$LOG_FILE"

git pull origin main >> "$LOG_FILE" 2>&1 || echo "Git pull пропущен (не Git репозиторий или нет изменений)" | tee -a "$LOG_FILE"

# === Запуск тестов ===

echo "Запуск Maven тестов..." | tee -a "$LOG_FILE"

mvn clean test >> "$LOG_FILE" 2>&1

TEST_EXIT_CODE=$?

# === Генерация HTML отчета ===

echo "Генерация Allure отчета..." | tee -a "$LOG_FILE"

mvn allure:report >> "$LOG_FILE" 2>&1

# === Копирование отчета с датой ===

REPORT_DEST="$REPORTS_DIR/$DATE"

mkdir -p "$REPORT_DEST"

if [ -d "$PROJECT_DIR/target/site/allure-maven-plugin" ]; then

    cp -r "$PROJECT_DIR/target/site/allure-maven-plugin/"* "$REPORT_DEST/"

    echo "✅ Отчет скопирован в $REPORT_DEST" | tee -a "$LOG_FILE"

else

    echo "❌ Папка с отчетом не найдена!" | tee -a "$LOG_FILE"

fi

# === Обновление "последнего" отчета ===

LATEST_DEST="$REPORTS_DIR/latest"

rm -rf "$LATEST_DEST"

if [ -d "$REPORT_DEST" ]; then

    cp -r "$REPORT_DEST" "$LATEST_DEST"

    echo "✅ Последний отчет обновлен: $LATEST_DEST" | tee -a "$LOG_FILE"

fi

# === Логирование результата ===

echo "========================================" | tee -a "$LOG_FILE"

if [ $TEST_EXIT_CODE -eq 0 ]; then

    echo "✅ Тесты пройдены успешно!" | tee -a "$LOG_FILE"

else

    echo "❌ Тесты провалились! Код выхода: $TEST_EXIT_CODE" | tee -a "$LOG_FILE"

fi

echo "========================================" | tee -a "$LOG_FILE"

# === Очистка старых отчетов (храним 30 дней) ===

find "$REPORTS_DIR" -maxdepth 1 -type d -mtime +30 -not -name "latest" -exec rm -rf {} + 2>/dev/null

exit $TEST_EXIT_CODE
