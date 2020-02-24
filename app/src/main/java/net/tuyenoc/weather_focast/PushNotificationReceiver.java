package net.tuyenoc.weather_focast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class PushNotificationReceiver extends BroadcastReceiver {
    private static final int TIME_VIBRATE = 1000;
    private static final String NOTIFICATION_CHANNEL_ID = "weather_channel";


    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Weather Forecast";
            String description = "Weather forecast";
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.e("NOTIFI", "HIHI");
//        createNotificationChannel(context);
//        if (schedules.isEmpty()) {
//            this.contentText = "NgÃ y mai báº¡n ráº£nh ^_^";
//            Intent notificationIntent = new Intent(context, MainActivity.class);
//            notificationIntent
//                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            int requestID = (int) System.currentTimeMillis();
//            PendingIntent contentIntent = PendingIntent
//                    .getActivity(context, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//            NotificationCompat.Builder builder =
//                    new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//                            .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
//                            .setChannelId("id_channel")
//                            .setSmallIcon(R.mipmap.ic_logo_round)
//                            .setContentTitle("NgÃ y mai")
//                            .setContentText(contentText)
//                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                            .setDefaults(Notification.DEFAULT_SOUND)
//                            .setAutoCancel(false)
//                            .setPriority(6)
//                            .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
//                                    TIME_VIBRATE})
//                            .setContentIntent(contentIntent);
//            NotificationManager notificationManager =
//                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0, builder.build());
//        } else {
//            for (int i = 0; i < this.schedules.size(); i++) {
//                Schedule schedule = this.schedules.get(i);
//                contentText = "";
//                if (schedule.getSoBaoDanh().isEmpty()) {
//                    contentText += "MÃ´n há»c: " + schedule.getTenMon();
//                    contentText += "\nThá»i gian: " + schedule.getThoiGian();
//                    contentText += "\nÄá»‹a Ä‘iá»ƒm: " + schedule.getDiaDiem();
//                    contentText += "\nGiÃ¡o viÃªn: " + schedule.getGiaoVien();
//                } else if (!schedule.getThoiGian().isEmpty()) {
//                    contentText += "MÃ´n thi: " + schedule.getTenMon();
//                    contentText += "\nSBD: " + schedule.getSoBaoDanh();
//                    contentText += "\nThá»i gian: " + schedule.getThoiGian();
//                    contentText += "\nÄá»‹a Ä‘iá»ƒm: " + schedule.getDiaDiem();
//                    contentText += "\nHÃ¬nh thá»©c: " + schedule.getHinhThuc();
//                }
//                if (schedule.getThoiGian().isEmpty()) {
//                    contentText += "TiÃªu Ä‘á»: " + schedule.getMaMon();
//                    contentText += "\nNá»™i dung: " + schedule.getTenMon();
//                }
//                //push
//                Intent notificationIntent = new Intent(context, MainActivity.class);
//                notificationIntent
//                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                int requestID = (int) System.currentTimeMillis();
//                PendingIntent contentIntent = PendingIntent
//                        .getActivity(context, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                NotificationCompat.Builder builder =
//                        new NotificationCompat.Builder(context)
//                                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
//                                .setChannelId("id_channel")
//                                .setSmallIcon(R.mipmap.ic_logo_round)
//                                .setContentTitle("NgÃ y mai")
//                                .setContentText(contentText)
//                                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                                .setDefaults(Notification.DEFAULT_SOUND)
//                                .setAutoCancel(false)
//                                .setPriority(6)
//                                .setVibrate(new long[]{TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE, TIME_VIBRATE,
//                                        TIME_VIBRATE})
//                                .setContentIntent(contentIntent);
//                NotificationManager notificationManager =
//                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(i, builder.build());
//            }
//        }
    }
}
