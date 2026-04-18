import subprocess
import time

PACKAGE_NAME = "com.example.haiduk.permissionslab"

def run_adb(command):
    print(f"Выполняю: adb {command}")
    result = subprocess.run(f"adb {command}", shell=True, capture_output=True, text=True)
    return result

print("=== Vector Lab: Ultimate Permission Reset ===")

# 1. Полный сброс всех runtime-разрешений приложения до состояния 'не запрашивалось'
print(f"Сброс всех разрешений для {PACKAGE_NAME}...")
run_adb(f"shell pm reset-permissions {PACKAGE_NAME}")

# 2. Принудительная остановка процесса
print(f"Остановка процесса...")
run_adb(f"shell am force-stop {PACKAGE_NAME}")

# 3. Очистка кэша пакета 
run_adb(f"shell pm trim-caches 4096M")

print("\nГотово! Теперь открой приложение в эмуляторе вручную.")