package com.quan12yt.trackingsearchhistory.config;

import com.quan12yt.trackingsearchhistory.dto.SearchRecord;
import com.quan12yt.trackingsearchhistory.util.ValueConstant;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.regex.Matcher;

public class ProxyHandler extends Thread {

    private Socket clientSocket;
    private boolean previousWasR = false;
    KafkaTemplate<String, SearchRecord> kafkaTemplate;
    final String username = System.getProperty("user.name");

    public ProxyHandler(Socket clientSocket, KafkaTemplate<String, SearchRecord> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            String request = readLine(clientSocket);
            kafkaTemplate.send("search-topic", new SearchRecord(username, LocalDateTime.now().toString(), request.substring(8)));
            Matcher matcher = ValueConstant.CONNECT_PATTERN.matcher(request);
            if (matcher.matches()) {
                String header;
                do {
                    header = readLine(clientSocket);

                } while (!"".equals(header));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream(),
                        "ISO-8859-1");

                final Socket forwardSocket;
                try {
                    forwardSocket = new Socket(matcher.group(1), Integer.parseInt(matcher.group(2)));
                    forwardSocket.setSoTimeout(100000);
                    // System.out.println(forwardSocket);
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();  // TODO: implement catch
                    outputStreamWriter.write("HTTP/" + matcher.group(3) + " 502 Bad Gateway\r\n");
                    outputStreamWriter.write("Proxy-agent: Simple/0.1\r\n");
                    outputStreamWriter.write("\r\n");
                    outputStreamWriter.flush();
                    return;
                }
                try {
                    outputStreamWriter.write("HTTP/" + matcher.group(3) + " 200 Connection established\r\n");
                    outputStreamWriter.write("Proxy-agent: Simple/0.1\r\n");
                    outputStreamWriter.write("\r\n");
                    outputStreamWriter.flush();

                    Thread remoteToClient = new Thread() {
                        @Override
                        public void run() {
                            forwardData(forwardSocket, clientSocket);
                        }
                    };
                    remoteToClient.start();
                    try {
                        if (previousWasR) {
                            int read = clientSocket.getInputStream().read();
                            if (read != -1) {
                                if (read != '\n') {
                                    forwardSocket.getOutputStream().write(read);
                                }
                                forwardData(clientSocket, forwardSocket);
                            } else {
                                if (!forwardSocket.isOutputShutdown()) {
                                    forwardSocket.shutdownOutput();
                                }
                                if (!clientSocket.isInputShutdown()) {
                                    clientSocket.shutdownInput();
                                }
                            }
                        } else {
                            forwardData(clientSocket, forwardSocket);
                        }
                    } finally {
                        try {
                            remoteToClient.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();  // TODO: implement catch
                        }
                    }
                } finally {
                    forwardSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // TODO: implement catch
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();  // TODO: implement catch
            }
        }
    }

    private static void forwardData(Socket inputSocket, Socket outputSocket) {
        try {
            InputStream inputStream = inputSocket.getInputStream();
            try {
                OutputStream outputStream = outputSocket.getOutputStream();
                try {
                    byte[] buffer = new byte[10000];
                    int read;
                    do {
                        read = inputStream.read(buffer);
                        if (read > 0) {
                            outputStream.write(buffer, 0, read);
                            if (inputStream.available() < 1) {
                                outputStream.flush();
                            }
                        }
                    } while (read >= 0);
                } finally {
                    if (!outputSocket.isOutputShutdown()) {
                        outputSocket.shutdownOutput();
                    }
                }
            } finally {
                if (!inputSocket.isInputShutdown()) {
                    inputSocket.shutdownInput();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  // TODO: implement catch
        }
    }

    private String readLine(Socket socket) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int next;
        readerLoop:
        while ((next = socket.getInputStream().read()) != -1) {
            if (previousWasR && next == '\n') {
                previousWasR = false;
                continue;
            }
            previousWasR = false;
            switch (next) {
                case '\r':
                    previousWasR = true;
                    break readerLoop;
                case '\n':
                    break readerLoop;
                default:
                    byteArrayOutputStream.write(next);
                    break;
            }
        }
        return byteArrayOutputStream.toString("ISO-8859-1");
    }
}