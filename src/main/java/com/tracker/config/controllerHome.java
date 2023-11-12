package com.tracker.config;

import com.tracker.config.parser.AvlData;
import com.tracker.config.parser.GpsElement;
import com.tracker.config.tools.RestConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test/api/v1")
public class controllerHome {


    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public ResponseEntity<?> sendData(@RequestBody String all){
        List<AvlData> lstAvlData=new ArrayList<>();
        GpsElement gpsElement=new GpsElement();
        gpsElement.setAltitude((short) 53);
        gpsElement.setAngle((byte)10);
        gpsElement.setSatellites((byte)10);
        gpsElement.setSpeed((short)120);
        gpsElement.setX(-68271150);
        gpsElement.setY(340186549);
        AvlData avlData1=new AvlData();
        avlData1.setGpsElement(gpsElement);
        avlData1.setTimestamp(1698157073000L);
        lstAvlData.add(avlData1);
        RestConfig.sendListAvlData(lstAvlData);
        return ResponseEntity.ok("done");
    }
}