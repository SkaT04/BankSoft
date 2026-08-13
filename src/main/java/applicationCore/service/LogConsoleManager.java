package applicationCore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class LogConsoleManager {

    private static final Logger log = LoggerFactory.getLogger(LogConsoleManager.class);

    // Уникальный заголовок для окна, чтобы Windows могла найти его и закрыть
    private static final String CONSOLE_TITLE = "App_Log_Monitor";
    private static final String LOG_FILE_PATH = "logs/app.log";

    private boolean isOpen = false;

    /**
     * Открывает отдельное окно PowerShell для просмотра логов в режиме реального времени
     */
    public synchronized void openLogConsole() {
        if (isOpen) {
            log.warn("Окно логов уже открыто!");
            return;
        }

        File logFile = new File(LOG_FILE_PATH);

        // Гарантируем, что файл существует до запуска tail
        if (!logFile.exists()) {
            try {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            } catch (IOException e) {
                log.error("Не удалось создать файл логов", e);
                return;
            }
        }

        try {
            ProcessBuilder pb = getProcessBuilder(logFile);

            pb.start();
            isOpen = true;
            log.info("Консоль логов успешно запущена в отдельном окне.");

        } catch (IOException e) {
            log.error("Ошибка при запуске окна логов Windows", e);
        }
    }

    private static ProcessBuilder getProcessBuilder(File logFile) {
        String absolutePath = logFile.getAbsolutePath();

        // Добавляем -Encoding utf8 напрямую в Get-Content
        String psScript = String.format(
                "[Console]::OutputEncoding = [System.Text.Encoding]::UTF8; Get-Content -Path '%s' -Wait -Tail 20 -Encoding utf8",
                absolutePath
        );

        String cmdCommand = String.format(
                "chcp 65001 > nul && title %s && powershell -NoProfile -Command \"%s\"",
                CONSOLE_TITLE, psScript
        );

        return new ProcessBuilder(
                "cmd.exe", "/c", "start", "cmd.exe", "/k", cmdCommand
        );
    }

    /**
     * Находит окно по заголовку и закрывает его
     */
    public synchronized void closeLogConsole() {
        if (!isOpen) {
            log.warn("Окно логов не запущено!");
            return;
        }

        try {
            // taskkill ищет окно с точным заголовком CONSOLE_TITLE и завершает его процесс
            ProcessBuilder pb = new ProcessBuilder(
                    "taskkill", "/FI", "WINDOWTITLE eq " + CONSOLE_TITLE, "/F"
            );

            pb.start();
            isOpen = false;
            log.info("Консоль логов закрыта.");

        } catch (IOException e) {
            log.error("Ошибка при закрытии окна логов", e);
        }
    }

    public boolean isOpen() {
        return isOpen;
    }
}
