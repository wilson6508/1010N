https://mvnrepository.com/artifact/com.jcraft/jsch/0.1.55
implementation 'com.jcraft:jsch:0.1.55'
implementation group: 'com.jcraft', name: 'jsch', version: '0.1.55'
<dependency>
    <groupId>com.jcraft</groupId>
    <artifactId>jsch</artifactId>
    <version>0.1.55</version>
</dependency>

package com.service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.stereotype.Service;

@Service
public class SftpService {

    public String transferFileSFTP() {
        String host = "192.168.184.100";
        int port = 22;
        String user = "root";
        String password = "atguigu";
        String sourceFile = "C:/report/apiResponse/demo.xlsx";
        String targetFile = "/home/tony/demo.xlsx";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;

            channelSftp.put(sourceFile, targetFile, ChannelSftp.OVERWRITE);
            channelSftp.exit();
            session.disconnect();
            return "File transferred successfully";
        } catch (Exception e) {
            return "Failed to transfer file: " + e.getMessage();
        }
    }

}
