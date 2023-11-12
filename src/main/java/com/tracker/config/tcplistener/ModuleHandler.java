package com.tracker.config.tcplistener;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tracker.config.parser.AvlData;
import com.tracker.config.parser.CodecStore;
import com.tracker.config.tools.RestConfig;
import com.tracker.config.tools.Tools;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ModuleHandler implements Runnable {


 String imei;
 String liveData;
 private List<AvlData> lstAvlData=new ArrayList<>();
 private Socket moduleSocket;

 public ModuleHandler(Socket sock) { // Modify the constructor
  this.moduleSocket = sock;
 }



  public void run() {
  try {
   //System.out.println("New connection from module:" + moduleSocket);
   
   DataInputStream dis = new DataInputStream(moduleSocket.getInputStream());
   DataOutputStream dos = new DataOutputStream(moduleSocket.getOutputStream());
   
   imei = dis.readUTF();
   //System.out.println("Module IMEI:" + imei);
   dos.writeBoolean(true);



   while (true) {
    byte[] packet = ByteWrapper.unwrapFromStream(dis);
    
    if (packet == null) {
     //System.out.println("Closing connection: " + moduleSocket);
     break;
    }
    
    AvlData decoder = CodecStore.getInstance().getSuitableCodec(packet);

    if (decoder == null) {
     System.out.println("Unknown packet format: " + Tools.bufferToHex(packet));
     dos.writeInt(0);
    } else {
     //System.out.println("Codec found: " + decoder);
     
     AvlData[] decoded = decoder.decode(packet);
     
     //System.out.println(new Date().toLocaleString() + ": Received records:" + decoded.length);
     for (AvlData avlData : decoded) {
       liveData = "@"+imei+","+avlData;
       System.out.println("Data "+liveData+"\n\n");
      lstAvlData.add(avlData);
       if(avlData != null){
        System.out.println("Done !!");
       }
       new writeToFileClass().readData(liveData,imei);
     }
     RestConfig.sendListAvlData(lstAvlData);
    }
   }

  } catch (EOFException ee) {
   System.out.println("Closed connection:" + moduleSocket);
  } catch (Exception e) {
   e.printStackTrace();
  }
 }

}