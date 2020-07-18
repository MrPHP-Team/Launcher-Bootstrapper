package com.github.lkapitman.updater;

public interface DownloadListener {
    public void onDownloadJobFinished(DownloadJob job);
    public void onDownloadJobProgressChanged(DownloadJob job);
}
