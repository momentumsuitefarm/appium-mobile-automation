#!/bin/bash

# Check if allure command is available
if ! command -v allure &> /dev/null; then
    echo "❌ Allure command not found. Please install Allure command line tool."
    exit 1
fi

# Generate Allure report
echo "📊 Generating Allure report..."
allure generate allure-results --clean -o allure-report

# Check if report generation was successful
if [ $? -eq 0 ]; then
    echo "✅ Allure report generated successfully!"
    echo "📂 Report location: $(pwd)/allure-report"
else
    echo "❌ Failed to generate Allure report"
    exit 1
fi 