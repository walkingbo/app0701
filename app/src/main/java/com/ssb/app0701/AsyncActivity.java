package com.ssb.app0701;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncActivity extends AppCompatActivity {
    TextView asyncdisp;
    ProgressBar progress;
    Button start, end;

    //진행율을 표시하기 위한 변수
    int value;

    BackgroundTask task;

    class BackgroundTask extends AsyncTask<Integer,Integer,Integer>{
        @Override
        //태스크가 시작하면 호출되는 메소드
        public void onPreExecute(){
            value =0;
            progress.setProgress(value);
        }

        @Override
        //백그라운드 스레드로 동작하는 메소드
        //리턴타입의 자료형이 클래스 생성시 설정하는 3번째 매개변수 자료형
        //매개변수는 클래스 생성시 설정하는 2번째 매개변수 자료형
        //...은 파라미터가 몇개 오던지 관계 없음
        public Integer doInBackground(Integer...values){
            while(isCancelled()==false&&value<100){
                value =value+1;
                //UI 갱신을 용청
                publishProgress(value);
                //잠시대기
                try{
                    Thread.sleep(1000);
                }catch (Exception e){}
            }
            return value;
        }
        @Override
        //doInBackground에서 publishProgress를 호출하면 실행되는 메소드-선택
        //이 메소드의 매개변수는 클래스 생성시 첫번째 자료형
        //이 메소드에서 주기적으로 UI 갱신
        public void onProgressUpdate(Integer...values){
                progress.setProgress(values[0]);
                asyncdisp.setText("value:"+values[0]);
        }
        @Override
        //doInBackground가 작업을 종료하면 호출되는 메소드 - 선택
        //매개변수가 doInBackground의 return 값
        public void onPostExecute(Integer result){
            asyncdisp.setText("작업완료");
        }
        @Override
        //스레드가 중지되었을 때 호출되는 메소드
        public  void  onCancelled(){

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        asyncdisp = (TextView)findViewById(R.id.asyncdisp);
        progress = (ProgressBar)findViewById(R.id.progress);
        start =(Button)findViewById(R.id.start);
        end =(Button)findViewById(R.id.end);

        start.setOnClickListener((view)->{
            //AsyncTask 인스턴스를 생성해서 실행
            task = new BackgroundTask();
            task.execute(100);
        });

        end.setOnClickListener((view)->{
            task.cancel(true);
        });

    }
}
