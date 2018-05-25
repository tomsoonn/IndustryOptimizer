package monitor;

import java.io.IOException;
import java.util.*;
import java.io.File;
import java.lang.ref.WeakReference;

public class FileMonitor
{
    private Timer       timer_;
    private HashMap<File, Long> files_;
    private Collection<WeakReference<FileListener>> listeners_;

    public FileMonitor (long pollingInterval) {
        files_     = new HashMap<File, Long>();
        listeners_ = new ArrayList<WeakReference<FileListener>>();

        timer_ = new Timer (true);
        timer_.schedule (new FileMonitorNotifier(), 0, pollingInterval);
    }

    public void stop() {
        timer_.cancel();
    }

    public void addFile (File file)
    {
        if (!files_.containsKey (file)) {
            long modifiedTime = file.exists() ? file.lastModified() : -1;
            files_.put (file, modifiedTime);
        }
    }

    public void removeFile (File file)
    {
        files_.remove (file);
    }

    public void addListener (FileListener fileListener)
    {
        for (Object aListeners_ : listeners_) {
            WeakReference reference = (WeakReference) aListeners_;
            FileListener listener = (FileListener) reference.get();
            if (listener == fileListener)
                return;
        }

        listeners_.add (new WeakReference<>(fileListener));
    }

    public void removeListener (FileListener fileListener)
    {
        for (Iterator<WeakReference<FileListener>> i = listeners_.iterator(); i.hasNext(); ) {
            WeakReference<FileListener> reference = i.next();
            FileListener listener = reference.get();
            if (listener == fileListener) {
                i.remove();
                break;
            }
        }
    }

    private class FileMonitorNotifier extends TimerTask
    {
        public void run()
        {
            Collection<File> files = new ArrayList<File>(files_.keySet());

            for (File file : files) {
                long lastModifiedTime = files_.get(file);
                long newModifiedTime = file.exists() ? file.lastModified() : -1;

                if (newModifiedTime != lastModifiedTime) {

                    files_.put(file, newModifiedTime);

                    for (Iterator<WeakReference<FileListener>> j = listeners_.iterator(); j.hasNext(); ) {
                        WeakReference<FileListener> reference = j.next();
                        FileListener listener = reference.get();

                        if (listener == null)
                            j.remove();
                        else
                            try {
                                listener.fileChanged(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    }
                }
            }
        }
    }


    public static void main (String args[])
    {
        FileMonitor monitor = new FileMonitor (1000);

        monitor.addFile (new File ("output/task.txt"));

        monitor.addListener (monitor.new TestListener());

        // Avoid program exit
        while (!false) ;
    }


    private class TestListener implements FileListener
    {
        public void fileChanged (File file)
        {
            System.out.println ("File changed: " + file);
        }
    }
}
