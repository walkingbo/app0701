package com.ssb.app0701;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class SocketActivity extends AppCompatActivity {

    EditText msg;
    Button send;
    TextView disp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        msg = (EditText)findViewById(R.id.msg);
        send = (Button)findViewById(R.id.send);
        disp1 = (TextView)findViewById(R.id.disp1);

        send.setOnClickListener((view)->{
            //네트워크 작업은 스레드를 이용
            Thread th = new Thread(){
                public void run(){
                    try{
                        /*
                        //연결할 주소 생성
                        InetAddress ia = InetAddress.getByName("192.168.0.118");
                        //소켓만들기
                        Socket socket = new Socket(ia,9999);
                        //입력한 문자열을 전송
                        PrintWriter pw = new PrintWriter(socket.getOutputStream());
                        pw.println(msg.getText().toString());
                        pw.flush();

                        //전송한 문자열을 읽기
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String content = br.readLine();
                        disp1.setText(content);

                        pw.close();
                        br.close();
                        socket.close();
                        */

                        //데이터를 전송하는 데이터 그램 소켓 생성
                        DatagramSocket ds = new DatagramSocket();
                        //보낼 주소 생성
                        InetAddress ia = InetAddress.getByName("192.168.0.118");
                        //보낼 데이터 생성
                        String data = msg.getText().toString();
                        DatagramPacket dp = new DatagramPacket(data.getBytes(),data.length(),ia,8888);
                        ds.send(dp);

                        disp1.setText("전송성공");


                    }catch (Exception e){
                        disp1.setText("전송실패");

                    }
                }
            };
            th.start();
        });


    }
}
