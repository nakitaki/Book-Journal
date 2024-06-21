package bg.nbu.project_f104774.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import bg.nbu.project_f104774.R;

public class MyService extends Service {
    //компонент за възпроизвеждане на музикален файл
    MediaPlayer mediaPlayer;
    // Извиква се, когато сървисът се създава
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true);
        Toast.makeText(this, "Service started ...", Toast.LENGTH_LONG).show();
    }

    // Извиква се, когато клиентът се свързва с bindService()
    //още няма имплементация
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*Методът onStartCommand() може да връща една от следните стойности, в случай че процесът на сървиса бъде
    неочаквано спрян от системата:
    ◼ START_STICKY: сървисът се връща в стартирано състояние, все едно отново е бил извикан метода
    onStartCommand() без предаване на обекта Intent.
    ◼ START_REDELIVER_INTENT: сървисът се връща в  стартирано състояние, все едно отново е бил извикан
    метода onStartCommand() с предаване на обекта Intent.
    ◼ START_NOT_STICKY: сървисът остава в спряно положение.*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return START_STICKY;
    }
    // Извиква се, когато сървисът не се използва повече и се разрушава
    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        Toast.makeText(this, "Service destroyed ...", Toast.LENGTH_LONG).show();
    }
}

