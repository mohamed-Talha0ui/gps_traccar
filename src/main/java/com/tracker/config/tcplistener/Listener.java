package com.tracker.config.tcplistener;


import com.tracker.config.parser.AvlData;
import com.tracker.config.parser.AvlDataFM4;
import com.tracker.config.parser.AvlDataGH;
import com.tracker.config.parser.CodecStore;

import java.net.ServerSocket;
import java.net.Socket;

public class Listener {

 public static void main(String[] args) throws Exception {

  int port = 1556;
  
  /*if (args.length < 1) {
   System.out.println("v"+Version.getVersion());
   System.out.println("Usage: java -jar avlreceiver.jar listenPortNumber");
   System.exit(1);
  } else {
   port = Integer.parseInt(args[0]);
  }*/
  
  // register supported codecs
  CodecStore.getInstance().register(AvlData.getCodec());
  CodecStore.getInstance().register(AvlDataFM4.getCodec());
  CodecStore.getInstance().register(AvlDataGH.getCodec());

  ServerSocket serverSocket = new ServerSocket(port);
  System.out.println("Listening on TCP port " + port);

  while (true) {
   Socket sock = serverSocket.accept();

   new Thread(new ModuleHandler(sock)).start();
  }
 }
}