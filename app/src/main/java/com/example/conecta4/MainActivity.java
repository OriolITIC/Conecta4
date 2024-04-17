package com.example.conecta4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button connectButton;
    private TextInputLayout ip;
    private TextInputLayout puerto;
    private TextView textView;
    private TextView lastMessageTextView;
    private Context context = this;

    private Socket socket;
    private BufferedReader input;
    private PrintStream output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = findViewById(R.id.connectButton);
        ip = findViewById(R.id.ip_input_text);
        puerto = findViewById(R.id.puerto_input_text);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConnectTask().execute();
            }
        });
    }

    private void sendMessage(String message) {
        if (socket != null && output != null) {
            Toast.makeText(context, "Enviando mensaje: " + message, Toast.LENGTH_SHORT).show();
            lastMessageTextView.setText("Último mensaje: " + message);
            textView.append("Yo: " + message + "\n");
            new SendTask().execute(message);
        } else {
            Toast.makeText(context, "No hay conexión al servidor", Toast.LENGTH_SHORT).show();
        }
    }

    private class ConnectTask extends AsyncTask<Void, Void, Void> {
        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String ipAddress = ip.getEditText().getText().toString();
                int port = Integer.parseInt(puerto.getEditText().getText().toString());

                InetAddress serverAddr = InetAddress.getByName(ipAddress);
                socket = new Socket(serverAddr, port);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintStream(socket.getOutputStream());
                new ReceiveTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } catch (IOException e) {
                Log.e("ConnectTask", "Error al conectar con el servidor: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    private class SendTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... messages) {
            try {
                if (socket != null && output != null) {
                    output.println(messages[0]);
                    Log.d("SendTask", "Mensaje enviado correctamente");
                }
            } catch (Exception e) {
                Log.e("SendTask", "Error al enviar mensaje: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }

    private class ReceiveTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    publishProgress(message);
                }
            } catch (IOException e) {
                Log.e("ReceiveTask", "Error al recibir mensaje: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textView.append(values[0] + "\n");
        }
    }
}
