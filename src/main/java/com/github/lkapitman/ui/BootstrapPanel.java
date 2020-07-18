package com.github.lkapitman.ui;

import com.github.lkapitman.launcher.Launcher;
import com.github.lkapitman.ui.components.CustomButton;
import com.github.lkapitman.updater.DownloadJob;
import com.github.lkapitman.updater.DownloadListener;
import com.github.lkapitman.updater.Updater;
import com.github.lkapitman.utils.Constants;
import com.github.lkapitman.utils.OsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Arinonia on 18/06/2020 inside the package - fr.arinonia.valkyria.bootstrap.ui
 */
public class BootstrapPanel extends JPanel implements DownloadListener {

    private final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    JProgressBar progressBar = new JProgressBar();


    public BootstrapPanel() {
        this.setLayout(null);
        this.setBackground(new Color(44,44,44));

        JLabel text = new JLabel();
        text.setText("<html>Добро пожаловать молодой искатель приключений,<br>" +
                "На этом сервере вы найдете различные квесты на протяжении всего вашего прогресса, стабильную экономику, " +
                "новая система фарм и действительно новые дополнения </html>");
        text.setForeground(new Color(92,227,246));
        text.setFont(text.getFont().deriveFont(18.0f));
        text.setBounds(10,10,590,140);
        this.add(text);

        progressBar.setBounds(10,242,400,36);
        progressBar.setForeground(new Color(21, 72, 161));
        progressBar.setBackground(new Color(74, 167, 232));
        progressBar.setBorderPainted(false);
        progressBar.setStringPainted(true);
        progressBar.setString("");
        this.add(progressBar);

        CustomButton launchButton = new CustomButton("запуск !", 140, 40);
        launchButton.setBounds(450,240,140,40);
        launchButton.setLocation(450,240);
        launchButton.setForeground(Color.WHITE);
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (launchButton.isEnabled()){
                    launchButton.setEnabled(false);
                    progressBar.setString("Проверить обновления ...");
                    start();
                }
            }
        });
        this.add(launchButton);
    }

    private void start(){
        DownloadJob javaJob = new DownloadJob("java", this);
        DownloadJob launcherJob = new DownloadJob("launcher", this);

        Updater javaUpdater = new Updater("http://dev.valkyria.fr/download/java/java_" + OsUtil.getOs().toString().toLowerCase() + ".json", new File(createGameDir(), "runtime"), javaJob);
        javaUpdater.start();
        Updater launcherUpdater = new Updater("http://dev.valkyria.fr/download/bootstrap/bootstrap.json",new File(createGameDir(), "launcher") , launcherJob);
        launcherUpdater.start();
        progressBar.setMaximum(javaJob.getAllFiles().size());
        javaJob.startDownloading(executorService);
        launcherJob.startDownloading(executorService);
    }

    @Override
    public void onDownloadJobFinished(DownloadJob job) {
       if (job.getName().equalsIgnoreCase("launcher")){
            progressBar.setValue(progressBar.getMaximum());
            progressBar.setString("Запуск лаунчера ...");
           try {
               new Launcher().launchJar(new File(createGameDir(), "launcher" + File.separator + "Launcher.jar"));
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }

    @Override
    public void onDownloadJobProgressChanged(DownloadJob job) {
        progressBar.setValue(job.getAllFiles().size() - job.getRemainingFiles().size());
        progressBar.setString("Загрузка'" + job.getName() + "': " + job.getRemainingFiles().size() + " остальные файлы");
    }

    private File createGameDir() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            return new File(System.getProperty("user.home") + "\\AppData\\Roaming\\." + Constants.NAME);
        else if (os.contains("mac"))
            return new File(System.getProperty("user.home") + "/Library/Application Support/" +  Constants.NAME);
        else
            return new File(System.getProperty("user.home") + "/." +  Constants.NAME);
    }
}
