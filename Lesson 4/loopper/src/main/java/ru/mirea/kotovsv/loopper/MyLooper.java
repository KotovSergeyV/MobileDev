package ru.mirea.kotovsv.loopper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public	class	MyLooper	extends	Thread{
    public Handler mHandler;
    private	Handler	mainHandler;
    public	MyLooper(Handler	mainThreadHandler)	{
        mainHandler	=mainThreadHandler;
    }

    public	void	run()	{
        Log.d("MyLooper",	"run");
        Looper.prepare();                                       //Создание цикла

        //получение дланных
        mHandler	=	new	Handler(Looper.myLooper())	{
            public	void	handleMessage(Message msg)	{
                String	job	=	msg.getData().getString("JOB");
                int age =	Integer.parseInt( msg.getData().getString("AGE"));
                Log.d("MyLooper",		job+" --- "+age);
                try {
                    Thread.sleep(age*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Message	message	=	new	Message();
                Bundle bundle	=	new	Bundle();
                bundle.putString("result",	String.format("Job:	%s	Age:	%d	",job ,age));
                message.setData(bundle);
                //	Send	the	message	back	to	main	thread	message	queue	use	main	thread	message	Handler.
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop(); // запуск
    }
}